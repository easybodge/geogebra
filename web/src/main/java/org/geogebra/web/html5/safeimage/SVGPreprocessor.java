package org.geogebra.web.html5.safeimage;

import org.geogebra.common.util.FileExtensions;
import org.geogebra.common.util.ImageManager;
import org.geogebra.web.html5.Browser;

public class SVGPreprocessor implements ImagePreprocessor {
	public static final String SCRIPT_OPEN_TAG = "<script";
	public static final String SCRIPT_CLOSE_TAG = "</script>";
	private String result = "";

	@Override
	public boolean match(FileExtensions extension) {
		return FileExtensions.SVG.equals(extension);
	}

	@Override
	public void process(String content) {
		result = stripFromTag(content, SCRIPT_OPEN_TAG, SCRIPT_CLOSE_TAG);
		String fixedContent =
				Browser.encodeSVG(ImageManager.fixSVG(result));
	}

	private String stripFromTag(String content,  String openTag, String closeTag) {
		int cutFrom = content.indexOf(openTag);
		if (cutFrom == -1) {
			return content;
		}

		int cutTo = content.indexOf(closeTag) + closeTag.length();

		return content.substring(0, cutFrom) + content.substring(cutTo);
	}

	public String getResult() {
		return result;
	}
}
