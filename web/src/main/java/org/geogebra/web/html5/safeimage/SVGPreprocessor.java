package org.geogebra.web.html5.safeimage;

import java.util.Arrays;
import java.util.List;

import org.geogebra.common.util.FileExtensions;
import org.geogebra.common.util.ImageManager;
import org.geogebra.web.html5.Browser;

public class SVGPreprocessor implements ImagePreprocessor {
	private static final List<String> tagsToCut = Arrays.asList("script",
			"foreignObject");
	private String content = "";

	@Override
	public boolean match(FileExtensions extension) {
		return FileExtensions.SVG.equals(extension);
	}

	@Override
	public String process(String content) {
		this.content = content;
		cutTags();
		return encodeSVG();
	}

	private void cutTags() {
		for (String tag: tagsToCut) {
			cutTag(tag);
		}
	}

	private String encodeSVG() {
		return Browser.encodeSVG(ImageManager.fixSVG(this.content));
	}

	private void cutTag(String tag) {
		int cutFrom = content.indexOf("<" + tag);
		if (cutFrom == -1) {
			return;
		}

		String closeTag = "</" + tag + ">";
		int cutTo = content.indexOf(closeTag) + closeTag.length();

		content = content.substring(0, cutFrom) + content.substring(cutTo);
	}

	public String getContent() {
		return content;
	}
}
