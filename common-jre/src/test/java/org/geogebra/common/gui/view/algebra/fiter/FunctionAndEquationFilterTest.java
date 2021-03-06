package org.geogebra.common.gui.view.algebra.fiter;

import org.geogebra.common.BaseUnitTest;
import org.geogebra.common.kernel.geos.GeoElement;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class FunctionAndEquationFilterTest extends BaseUnitTest {

    private FunctionAndEquationFilter filter = new FunctionAndEquationFilter();

    @Test
    public void testIsAllowed() {
        GeoElement f = addAvInput("f(x) = x");
        assertThat(filter.isAllowed(f), is(true));
    }
}