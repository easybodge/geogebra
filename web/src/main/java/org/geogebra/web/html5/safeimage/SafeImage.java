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
		this.extension = StringUtil.getFileExtension(fileName);
		this.content = content;
		this.fileName  = processFileName(fileName);
		preprocessors = new ArrayList<>();
		preprocessors.add(new SVGPreprocessor());

	}

	/**
	 * Bug in old versions (PNG saved with wrong extension)
	 * Change BMP, TIFF, TIF -> PNG
	 */
	private String processFileName(String fileName) {
		if (extension.isAllowedImage()) {
			return fileName;
		}

		return StringUtil.changeFileExtension(fileName,
				FileExtensions.PNG);
	}

	public boolean isImage() {
		return extension.isImage();
	}

	public void process() {
		for (ImagePreprocessor preprocessor: preprocessors) {
			if (preprocessor.match(extension)) {
				content = preprocessor.process(content);
			}
		}
	}

	public String getFileName() {
		return fileName;
	}

	public String getContent() {
		return content;
	}
}
