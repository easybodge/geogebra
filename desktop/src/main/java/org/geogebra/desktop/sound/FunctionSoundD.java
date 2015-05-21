package org.geogebra.desktop.sound;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import org.geogebra.common.kernel.geos.GeoFunction;
import org.geogebra.common.main.App;
import org.geogebra.common.sound.FunctionSound;
import org.geogebra.desktop.main.AppD;

/**
 * Class for playing function-generated sounds.
 * 
 * @author G. Sturr
 *
 */
public final class FunctionSoundD extends FunctionSound implements LineListener {

	private AppD app;

	// threaded class to play function
	private SoundThread soundThread;

	// streaming audio fields
	private static AudioFormat af;
	private static SourceDataLine sdl;

	/**
	 * Constructs instance of FunctionSound
	 * 
	 * @throws Exception
	 */
	public FunctionSoundD(AppD app) throws Exception {
		super();
		this.app = app;

		if (!initStreamingAudio(getSampleRate(), getBitDepth())) {
			throw new Exception("Cannot initialize streaming audio");
		}
	}

	/**
	 * Initializes instances of AudioFormat and SourceDataLine
	 * 
	 * @param sampleRate
	 *            = 8000, 16000, 11025, 16000, 22050, or 44100
	 * @param bitDepth
	 *            = 8 or 16
	 * @return
	 */
	protected boolean initStreamingAudio(int sampleRate, int bitDepth) {
		if (super.initStreamingAudio(sampleRate, bitDepth) == false) {
			return false;
		}
		
		boolean success = true;

		af = new AudioFormat(sampleRate, bitDepth, 1, true, true);
		try {
			sdl = AudioSystem.getSourceDataLine(af);
			// add listener when debugging
			// sdl.addLineListener(this);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
			success = false;
		}
		return success;
	}

	/**
	 * Plays a sound generated by the time valued GeoFunction f(t), from t = min
	 * to t = max in seconds. The function is assumed to have range [-1,1] and
	 * will be clipped to this range otherwise.
	 * 
	 * @param geoFunction
	 * @param min
	 * @param max
	 * @param sampleRate
	 * @param bitDepth
	 */

	@Override
	public void playFunction(final GeoFunction geoFunction, final double min,
			final double max, final int sampleRate, final int bitDepth) {
		if (!checkFunction(geoFunction, min, max, sampleRate, bitDepth)) {
			return;
		}

		// close current sound thread and prepare sdl
		if (soundThread != null) {
			soundThread.interrupt();
			sdl.flush();
			sdl.close();
		}

		// spawn a new SoundThread to play the function sound
		soundThread = new SoundThread();
		soundThread.start();

	}

	/**
	 * Pauses/resumes sound generation
	 * 
	 * @param doPause
	 */
	public void pause(boolean doPause) {

		if (doPause) {
			setMin(getT());
			soundThread.stopSound();
		} else {
			playFunction(getF(), getMin(), getMax(), getSampleRate(),
					getBitDepth());
		}
	}

	/**
	 * Listener for line events, used for debugging.
	 */
	public void update(LineEvent le) {

		LineEvent.Type type = le.getType();

		if (type == LineEvent.Type.OPEN) {
			App.debug("OPEN");
		} else if (type == LineEvent.Type.CLOSE) {
			App.debug("CLOSE");
		} else if (type == LineEvent.Type.START) {
			App.debug("START");
		} else if (type == LineEvent.Type.STOP) {
			App.debug("STOP");
		}
	}

	/**********************************************************
	 * Class SoundThread
	 * 
	 * Plays sounds from time-valued functions.
	 *********************************************************/
	private class SoundThread extends Thread {

		private volatile boolean stopped = false;


		public SoundThread() {
		}

		@Override
		public void run() {
			generateFunctionSound();
		}

		private void generateFunctionSound() {

			stopped = false;

			// time between samples
			setSamplePeriod(1.0 / getSampleRate());

			// create internal buffer for mathematically generated sound data
			// a small buffer minimizes latency when the function changes
			// dynamically
			// TODO: find optimal buffer size
			int frameSetSize = getSampleRate() / 50; // 20ms ok?
			if (getBitDepth() == 8)
				setBuf(new byte[frameSetSize]);
			else
				setBuf(new byte[2 * frameSetSize]);

			// generate the function sound
			try {

				// open the sourceDataLine
				// TODO: the sdl buffer size is relative to our internal buffer
				// need to experiment for best sizing factor
				sdl.open(af, 10 * getBufLength());
				sdl.start();

				if (getBitDepth() == 16) {
					setT(getMin());
					loadBuffer16(getT());
					doFade(getBuf()[0], false);
					sdl.write(getBuf(), 0, getBufLength());
					do {
						setT(getT() + getSamplePeriod() * frameSetSize);
						loadBuffer16(getT());
						sdl.write(getBuf(), 0, getBufLength());
					} while (getT() < getMax() && !stopped);

					doFade(getBuf()[getBufLength() - 1], true);

				} else {
					// use 8-bit samples
					setT(getMin());
					loadBuffer8(getT());
					doFade(getBuf()[0], false);
					sdl.write(getBuf(), 0, getBufLength());
					do {
						setT(getT() + getSamplePeriod() * frameSetSize);
						loadBuffer8(getT());
						sdl.write(getBuf(), 0, getBufLength());
					} while (getT() < getMax() && !stopped);

					if (!stopped)
						doFade(getBuf()[getBufLength() - 1], true);

				}

				// finish transfer of bytes from internal buffer to the sdl
				// buffer
				sdl.drain();

				// stop and close the sourceDataLine
				sdl.stop();
				sdl.close();

			} catch (LineUnavailableException e) {
				e.printStackTrace();
			}

		}
		/**
		 * Shapes ends of waveform to fade sound data TODO: is this actually
		 * working?
		 * 
		 * @param peakValue
		 * @param isFadeOut
		 */
		private void doFade(short peakValue, boolean isFadeOut) {

			byte[] fadeBuf = getFadeBuffer(peakValue, isFadeOut);
			sdl.write(fadeBuf, 0, fadeBuf.length);
		}

		/**
		 * Stops function sound
		 */
		public void stopSound() {
			stopped = true;
		}

	}
	// ================================
	// END SoundThread class

}
