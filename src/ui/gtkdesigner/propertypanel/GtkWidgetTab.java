package ui.gtkdesigner.propertypanel;

import java.awt.GridLayout;

@SuppressWarnings("serial")
public class GtkWidgetTab extends PropertyTabPanel
{
	public GtkWidgetTab(PropertyPanel propPanel)
	{
		super(propPanel);
		setLayout(new GridLayout(8, 0));
	}
	
	@Override
	public void initComponents()
	{
		/* TODO add elements */
		
		addCheckBox(null, "app-paintable", null, NO_PROP_DESC, true, createCheckBoxCallback("app-paintable"));
		addCheckBox(null, "can-default", null, NO_PROP_DESC, true, createCheckBoxCallback("can-default"));
		addCheckBox(null, "can-focus", null, NO_PROP_DESC, true, createCheckBoxCallback("can-focus"));
		addCheckBox(null, "double-buffered", null, NO_PROP_DESC, true, createCheckBoxCallback("double-buffered"));
		// add element for events
		addCheckBox(null, "expand", null, NO_PROP_DESC, true, createCheckBoxCallback("expand"));
		addCheckBox(null, "focus-on-click", null, NO_PROP_DESC, true, createCheckBoxCallback("focus-on-click"));
		// add element for halign
		addCheckBox(null, "has-default", null, NO_PROP_DESC, true, createCheckBoxCallback("has-default"));
		addCheckBox(null, "has-focus", null, NO_PROP_DESC, true, createCheckBoxCallback("has-focus"));
		addCheckBox(null, "has-tooltip", null, NO_PROP_DESC, true, createCheckBoxCallback("has-tooltip"));
		// add element for height request
		addCheckBox(null, "hexpand", null, NO_PROP_DESC, true, createCheckBoxCallback("hexpand"));
		addCheckBox(null, "hexpand-set", null, NO_PROP_DESC, true, createCheckBoxCallback("hexpand-set"));
		addCheckBox(null, "is-focus", null, NO_PROP_DESC, true, createCheckBoxCallback("is-focus"));
		// add element for margin
		// add element for margin-bottom
		// add element for margin-end
		// add element for margin-left
		// add element for margin-right
		// add element for margin-start
		// add element for margin-top
		// add element for name
		addCheckBox(null, "no-show-all", null, NO_PROP_DESC, true, createCheckBoxCallback("no-show-all"));
		// add element for opacity
		// add element for parent
		addCheckBox(null, "receives-default", null, NO_PROP_DESC, true, createCheckBoxCallback("receives-default"));
		addCheckBox(null, "sensitive", null, NO_PROP_DESC, true, createCheckBoxCallback("sensitive"));
		// add element for style
		// add element for tooltip-markup
		// add element for tooltip-text
		// add element for valign
		addCheckBox(null, "vexpand", null, NO_PROP_DESC, true, createCheckBoxCallback("vexpand"));
		addCheckBox(null, "vexpand-set", null, NO_PROP_DESC, true, createCheckBoxCallback("vexpand-set"));
		addCheckBox(null, "visible", null, NO_PROP_DESC, true, createCheckBoxCallback("visible"));
		// add element for width-request
	}
}
