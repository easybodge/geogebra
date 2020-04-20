package org.geogebra.web.html5.safeimage;

import org.geogebra.common.util.FileExtensions;
import org.geogebra.common.util.ImageManager;
import org.geogebra.web.html5.Browser;

public class SVGPreprocessor implements ImagePreprocessor {
	private static final String SCRIPT_OPEN_TAG = "<script";
	private static final String SCRIPT_CLOSE_TAG = "</script>";
	public static final String FOREIGN_OBJECT_OPEN_TAG = "<foreignObject";
	public static final String FOREIGN_OBJECT_CLOSE_TAG = "</foreignObject>";

	private String content = "";

	@Override
	public boolean match(FileExtensions extension) {
		return FileExtensions.SVG.equals(extension);
	}

	@Override
	public void process(String content) {
		this.content = content;
		cutTag(SCRIPT_OPEN_TAG, SCRIPT_CLOSE_TAG);
		cutTag(FOREIGN_OBJECT_OPEN_TAG,
				FOREIGN_OBJECT_CLOSE_TAG);
		String fixedContent =
				Browser.encodeSVG(ImageManager.fixSVG(this.content));
	}

	private void cutTag(String openTag, String closeTag) {
		int cutFrom = content.indexOf(openTag);
		if (cutFrom == -1) {
			return;
		}

		int cutTo = content.indexOf(closeTag) + closeTag.length();

		content = content.substring(0, cutFrom) + content.substring(cutTo);
	}

	public String getContent() {
		return content;
	}
}
