package org.geogebra.web.html5.safeimage;

import org.geogebra.common.util.FileExtensions;

public interface ImagePreprocessor {
	boolean match(FileExtensions extension);
	String process(String content);
}
