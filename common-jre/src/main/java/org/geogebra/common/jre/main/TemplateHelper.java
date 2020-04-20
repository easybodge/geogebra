package org.geogebra.common.jre.main;

import org.geogebra.common.kernel.Kernel;
import org.geogebra.common.main.App;

public class TemplateHelper {
	private final Kernel kernel;
	private App app;

	/**
	 * @param app target app
	 */
	public TemplateHelper(App app) {
		this.app = app;
		this.kernel = app.getKernel();
	}

	/**
	 * Copy settings and defaults from another app
	 * @param templateApp other app
	 */
	public void applyTemplate(App templateApp) {
		app.setLabelingStyle(templateApp.getLabelingStyle());
		kernel.setConstructionDefaults(templateApp.getKernel());
		kernel.setVisualStyles(templateApp.getKernel());
		kernel.updateConstruction(false);
	}
}
