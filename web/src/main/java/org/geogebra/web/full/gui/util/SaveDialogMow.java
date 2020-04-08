package org.geogebra.web.full.gui.util;

import org.geogebra.common.gui.SetLabels;
import org.geogebra.common.main.MaterialVisibility;
import org.geogebra.common.main.SaveController.SaveListener;
import org.geogebra.common.move.ggtapi.models.Material;
import org.geogebra.common.move.ggtapi.models.Material.MaterialType;
import org.geogebra.web.full.gui.view.algebra.InputPanelW;
import org.geogebra.web.html5.gui.FastClickHandler;
import org.geogebra.web.html5.gui.util.FormLabel;
import org.geogebra.web.html5.gui.view.button.StandardButton;
import org.geogebra.web.html5.main.AppW;
import org.geogebra.web.html5.main.LocalizationW;
import org.geogebra.web.shared.components.ComponentCheckbox;
import org.geogebra.web.shared.DialogBoxW;
import org.geogebra.web.shared.DialogUtil;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author csilla
 * 
 *		 simplified save dialog with material design
 *
 */
public class SaveDialogMow extends DialogBoxW
		implements SetLabels, FastClickHandler, SaveListener, SaveDialogI {
	private FlowPanel inputPanel;
	private FormLabel titleLbl;
	private InputPanelW titleField;
	private StandardButton cancelBtn;
	private StandardButton saveBtn;
	private LocalizationW loc;
	private ComponentCheckbox templateCheckbox;
	private Label templateTxt;

	/**
	 * @param app see {@link AppW}
	 */
	public SaveDialogMow(AppW app) {
		super(app.getPanel(), app);
		this.addStyleName("saveDialogMow");
		this.loc = app.getLocalization();
		initGUI();
		initActions();
		DialogUtil.hideOnLogout(app, this);
	}

	private void initGUI() {
		inputPanel = new FlowPanel();
		inputPanel.setStyleName("mowInputPanelContent");
		inputPanel.addStyleName("emptyState");
		titleField = new InputPanelW("", app, 1, 25, false);
		titleLbl = new FormLabel().setFor(titleField.getTextComponent());
		titleLbl.addStyleName("inputLabel");
		titleField.getTextComponent().getTextBox().getElement().setAttribute(
				"placeholder", loc.getMenu("Untitled"));
		titleField.addStyleName("inputText");
		inputPanel.add(titleLbl);
		inputPanel.add(titleField);
		templateTxt = new Label();
		templateCheckbox = new ComponentCheckbox(false, templateTxt);
		FlowPanel buttonPanel = new FlowPanel();
		buttonPanel.setStyleName("DialogButtonPanel");
		cancelBtn = new StandardButton("", app);
		cancelBtn.addFastClickHandler(this);
		saveBtn = new StandardButton("", app);
		saveBtn.addFastClickHandler(this);
		buttonPanel.add(cancelBtn);
		buttonPanel.add(saveBtn);
		FlowPanel dialogContent = new FlowPanel();
		dialogContent.add(inputPanel);
		dialogContent.add(templateCheckbox);
		dialogContent.add(buttonPanel);
		setLabels();
		this.add(dialogContent);
	}

	private void initActions() {
		// set focus to input field!
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {

			@Override
			public void execute() {
				getInputField().getTextComponent().setFocus(true);
			}
		});
		addFocusBlurHandlers();
		addHoverHandlers();
	}

	/**
	 * Add mouse over/ out handlers
	 */
	private void addHoverHandlers() {
		titleField.getTextComponent().getTextBox()
				.addMouseOverHandler(new MouseOverHandler() {

					@Override
					public void onMouseOver(MouseOverEvent event) {
						getInputPanel().addStyleName("hoverState");
					}
				});
		titleField.getTextComponent().getTextBox()
				.addMouseOutHandler(new MouseOutHandler() {

					@Override
					public void onMouseOut(MouseOutEvent event) {
						getInputPanel().removeStyleName("hoverState");
					}
				});
	}

	private void addFocusBlurHandlers() {
		titleField.getTextComponent().getTextBox()
				.addFocusHandler(new FocusHandler() {

					@Override
					public void onFocus(FocusEvent event) {
						setFocusState();
					}
				});
		titleField.getTextComponent().getTextBox()
				.addBlurHandler(new BlurHandler() {

					@Override
					public void onBlur(BlurEvent event) {
						resetInputField();
					}
				});
	}

	/**
	 * sets the style of InputPanel to focus state
	 */
	protected void setFocusState() {
		getInputPanel().setStyleName("mowInputPanelContent");
		getInputPanel().addStyleName("focusState");
	}

	/**
	 * Resets input style on blur
	 */
	protected void resetInputField() {
		getInputPanel().removeStyleName("focusState");
		getInputPanel().addStyleName("emptyState");
	}

	/**
	 * Input changed (paste or key event happened)
	 */
	protected void onInput() {
		getInputPanel().addStyleName("focusState");
		getInputPanel().removeStyleName("emptyState");
	}

	/**
	 * @return panel holding input with label and error label
	 */
	public FlowPanel getInputPanel() {
		return inputPanel;
	}

	/**
	 * @return input text field
	 */
	public InputPanelW getInputField() {
		return titleField;
	}

	@Override
	public void onClick(Widget source) {
		if (source == cancelBtn) {
			hide();
			app.getSaveController().cancel();
		} else if (source == saveBtn) {
			if (templateCheckbox.isSelected()) {
				setSaveType(((AppW) app).getVendorSettings().getTemplateType());
				app.getSaveController().ensureTypeOtherThan(Material.MaterialType.ggs);
			} else {
				setSaveType(MaterialType.ggs);
				app.getSaveController().ensureTypeOtherThan(Material.MaterialType.ggsTemplate);
			}
			app.getSaveController().saveAs(getInputField().getText(),
					getSaveVisibility(), this);
		}
	}

	private MaterialVisibility getSaveVisibility() {
		Material activeMaterial = ((AppW) app).getActiveMaterial();
		if (activeMaterial == null) {
			return MaterialVisibility.Private;
		}

		MaterialVisibility visibility = MaterialVisibility.value(activeMaterial.getVisibility());
		if (visibility == MaterialVisibility.Shared && hasNewName(activeMaterial)) {
			return MaterialVisibility.Private;
		}
		return visibility;
	}

	private boolean hasNewName(Material material) {
		return !material.getTitle().equals(getInputField().getText());
	}

	@Override
	public void setLabels() {
		defaultSaveCaptionAndCancel();
		titleLbl.setText(loc.getMenu("Title"));
		saveBtn.setLabel(loc.getMenu("Save"));
		titleField.getTextComponent().getTextBox().getElement().setAttribute(
				"placeholder", loc.getMenu("Untitled"));
		templateTxt.setText(loc.getMenu("saveTemplate"));
	}

	private void defaultSaveCaptionAndCancel() {
		templateCheckbox.setVisible(true);
		setCaptionKey("Save");
		cancelBtn.setLabel(loc.getMenu("Cancel"));
	}

	private void setCaptionKey(String key) {
		getCaption().setText(loc.getMenu(key));
	}

	@Override
	public void show() {
		defaultSaveCaptionAndCancel();
		super.show();
		center();
		setTitle();
		Material activeMaterial = ((AppW) app).getActiveMaterial();
		templateCheckbox.setSelected(activeMaterial != null && MaterialType.ggsTemplate
				.equals(activeMaterial.getType()));
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				getInputField().getTextComponent().setFocus(true);
			}
		});
	}

	/**
	 * Sets initial title for the material to save.
	 */
	@Override
	public void setTitle() {
		app.getSaveController()
				.updateSaveTitle(getInputField().getTextComponent(), "");
	}

	/**
	 * Sets material type to be saved.
	 * 
	 * @param saveType
	 *			for the dialog.
	 */
	@Override
	public void setSaveType(MaterialType saveType) {
		app.getSaveController().setSaveType(saveType);
	}

	@Override
	public void setDiscardMode() {
		setCaptionKey("DoYouWantToSaveYourChanges");
		cancelBtn.setLabel(loc.getMenu("Discard"));
	}

	@Override
	public void showAndPosition(Widget anchor) {
		if (anchor == null) {
			center();
		} else {
			showRelativeTo(anchor);
		}
		templateCheckbox.setVisible(false);
	}
}
