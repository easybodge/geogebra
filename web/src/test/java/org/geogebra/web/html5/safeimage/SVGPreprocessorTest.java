package org.geogebra.web.html5.safeimage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.geogebra.common.util.FileExtensions;
import org.geogebra.web.html5.Browser;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.gwtmockito.GwtMockitoTestRunner;
import com.google.gwtmockito.WithClassesToStub;

@RunWith(GwtMockitoTestRunner.class)
@WithClassesToStub({Browser.class})
public class SVGPreprocessorTest {
	private SVGPreprocessor preprocessor = new SVGPreprocessor();
	private String svg = "<svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 60 20\" height=\"20\" width=\"60\">\n" +
			"  <rect width=\"80\" height=\"60\" fill=\"#eee\"/>\n" +
			"  <text id=\"item\" font-size=\"5px\" x=\"2\" y=\"8\">External SVG Object</text>\n" +
			"  <script type=\"text/javascript\"><![CDATA[\n" +
			"    var svg = document.getElementsByTagName('svg')[0];\n" +
			"    var el = document.getElementById('item');\n" +
			"    var y = parseFloat(el.getAttributeNS(null, 'y'));\n" +
			"    el.setAttributeNS(null, 'y', y + 10);\n" +
			"  ]]></script>\n" +
			"</svg>\n";

	private String ScriptTag = "<script type=\"text/javascript\"><![CDATA[\n" +
			"    var svg = document.getElementsByTagName('svg')[0];\n" +
			"    var el = document.getElementById('item');\n" +
			"    var y = parseFloat(el.getAttributeNS(null, 'y'));\n" +
			"    el.setAttributeNS(null, 'y', y + 10);\n" +
			"  ]]></script>";

	@Test
	public void testMatch() {
		assertTrue(preprocessor.match(FileExtensions.SVG));
	}

	@Test
	public void testProcess() {
		int scriptIndex = svg.indexOf("<script");
		preprocessor.process(svg);
		String result = preprocessor.getResult();
		assertEquals(svg, result.substring(0, scriptIndex)
			+	ScriptTag + result.substring(scriptIndex));
	}
}
