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
			"  <foreignObject x=\"20\" y=\"20\" width=\"160\" height=\"160\">\n" +
			"    <!--\n" +
			"      In the context of SVG embedded in an HTML document, the XHTML \n" +
			"      namespace could be omitted, but it is mandatory in the \n" +
			"      context of an SVG document\n" +
			"    -->\n" +
			"    <div xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
			"      Lorem ipsum dolor sit amet, consectetur adipiscing elit.\n" +
			"      Sed mollis mollis mi ut ultricies. Nullam magna ipsum,\n" +
			"      porta vel dui convallis, rutrum imperdiet eros. Aliquam\n" +
			"      erat volutpat.\n" +
			"    </div>\n" +
			"  </foreignObject>" +
			"</svg>\n";

	private String ScriptTag = "<script type=\"text/javascript\"><![CDATA[\n" +
			"    var svg = document.getElementsByTagName('svg')[0];\n" +
			"    var el = document.getElementById('item');\n" +
			"    var y = parseFloat(el.getAttributeNS(null, 'y'));\n" +
			"    el.setAttributeNS(null, 'y', y + 10);\n" +
			"  ]]></script>";

	private String foreignObjectTag ="<foreignObject x=\"20\" y=\"20\" width=\"160\" height=\"160\">\n" +
			"    <!--\n" +
			"      In the context of SVG embedded in an HTML document, the XHTML \n" +
			"      namespace could be omitted, but it is mandatory in the \n" +
			"      context of an SVG document\n" +
			"    -->\n" +
			"    <div xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
			"      Lorem ipsum dolor sit amet, consectetur adipiscing elit.\n" +
			"      Sed mollis mollis mi ut ultricies. Nullam magna ipsum,\n" +
			"      porta vel dui convallis, rutrum imperdiet eros. Aliquam\n" +
			"      erat volutpat.\n" +
			"    </div>\n" +
			"  </foreignObject>";

	@Test
	public void testMatch() {
		assertTrue(preprocessor.match(FileExtensions.SVG));
	}

	@Test
	public void testProcess() {
		int scriptIndex = svg.indexOf("<script");
		int foreignObjectIndex = svg.indexOf("<foreignObject");
		preprocessor.process(svg);
		String result = preprocessor.getContent();
		String noScript = result.substring(0, scriptIndex)
				+	ScriptTag + result.substring(scriptIndex);

		String actual = noScript.substring(0, foreignObjectIndex)
				+ foreignObjectTag
				+ noScript.substring(foreignObjectIndex);

		assertEquals(svg, actual);
	}
}
