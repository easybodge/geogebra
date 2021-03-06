package org.geogebra.common.kernel.geos;

import org.geogebra.common.BaseUnitTest;
import org.geogebra.common.gui.view.algebra.EvalInfoFactory;
import org.geogebra.common.kernel.commands.EvalInfo;
import org.geogebra.common.main.App;
import org.geogebra.test.UndoRedoTester;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SliderTest extends BaseUnitTest {

	private EvalInfo info;

	@Before
	public void setUp() {
		info = EvalInfoFactory.getEvalInfoForAV(getApp(), true);
	}

	@Test
	public void setShowExtendedAV() {
		GeoNumeric slider = add("a = 1", info);
		slider.initAlgebraSlider();
		slider.setShowExtendedAV(false);
		assertThat(slider.showInEuclidianView(), is(true));
	}

	@Test
	public void testMarbleFunctionalityWithUndoRedo() {
		App app = getApp();
		UndoRedoTester undoRedo = new UndoRedoTester(app);
		undoRedo.setupUndoRedo();

		GeoNumeric slider = add("a = 1", info);
		app.storeUndoInfo();
		slider.setEuclidianVisible(true);
		app.storeUndoInfo();
		slider.setShowExtendedAV(false);
		app.storeUndoInfo();
		slider.setEuclidianVisible(false);
		app.storeUndoInfo();
		slider.setEuclidianVisible(true);
		app.storeUndoInfo();

		slider = undoRedo.getAfterUndo("a");
		assertThat(slider.isEuclidianVisible(), is(false));

		slider = undoRedo.getAfterUndo("a");
		assertThat(slider.isEuclidianVisible(), is(true));

		slider = undoRedo.getAfterUndo("a");
		assertThat(slider.isShowingExtendedAV(), is(true));

		slider = undoRedo.getAfterRedo("a");
		assertThat(slider.isShowingExtendedAV(), is(false));

		slider = undoRedo.getAfterRedo("a");
		assertThat(slider.isEuclidianVisible(), is(false));

		slider = undoRedo.getAfterRedo("a");
		assertThat(slider.isEuclidianVisible(), is(true));
	}

	@Test
	public void removeSlider() {
		App app = getApp();
		UndoRedoTester undoRedo = new UndoRedoTester(app);
		undoRedo.setupUndoRedo();

		GeoNumeric slider = add("a = 1", info);
		app.storeUndoInfo();
		slider.createSlider();
		app.storeUndoInfo();
		slider.setEuclidianVisible(true);
		app.storeUndoInfo();
		slider.removeSlider();
		app.storeUndoInfo();
		assertThat(slider.isSetEuclidianVisible(), is(false));

		slider = undoRedo.getAfterUndo("a");
		assertThat(slider.isEuclidianVisible(), is(true));
	}
}