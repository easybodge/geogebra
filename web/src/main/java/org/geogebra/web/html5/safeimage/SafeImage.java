package org.geogebra.web.html5.safeimage;

import java.util.ArrayList;
import java.util.List;

import org.geogebra.common.util.FileExtensions;
import org.geogebra.common.util.StringUtil;

public class SafeImage {
	private final FileExtensions extension;
	private String content;
	private String fileName;
	private List<ImagePreprocessor> preprocessors;

	public SafeImage(String fileName, String content) {
		this.fileName = fileName;
		this.extension = StringUtil.getFileExtension(fileName);
		this.content = content;
		preprocessors = new ArrayList<>();
		preprocessors.add(new SVGPreprocessor());

	}

	public boolean isImage() {
		return extension.isImage();
	}

	public void process() {

	}
}
