package ui.gtkdesigner.propertypanel;

import java.awt.GridLayout;

@SuppressWarnings("serial")
public class GtkLabelTab extends PropertyTabPanel
{
	public GtkLabelTab(PropertyPanel propPanel)
	{
		super(propPanel);
		setLayout(new GridLayout(8, 0));
	}

	@Override
	public void initComponents()
	{
		/* TODO add elements */
		// add element for angle
		// add element for attributes
		// add element for ellipsize
		// add element for justify
		// add element for label
		// add element for lines
		// add element for max-width-chars
		// add element for mnemonic-keyval
		// add element for mnemonic-widget
		// add element for pattern
		addCheckBox(null, "selectable", null, NO_PROP_DESC, true, createCheckBoxCallback("selectable"));
		addCheckBox(null, "single-line-mode", null, NO_PROP_DESC, true, createCheckBoxCallback("single-line-mode"));
		addCheckBox(null, "track-visited-links", null, NO_PROP_DESC, true, createCheckBoxCallback("track-visited-links"));
		addCheckBox(null, "use-markup", null, NO_PROP_DESC, true, createCheckBoxCallback("use-markup"));
		addCheckBox(null, "use-underline", null, NO_PROP_DESC, true, createCheckBoxCallback("use-underline"));
		// add element for width-chars
		addCheckBox(null, "wrap", null, NO_PROP_DESC, true, createCheckBoxCallback("wrap"));
		// add element for wrap-mode
		// add element for xalign
		// add element for yalign
	}
}
