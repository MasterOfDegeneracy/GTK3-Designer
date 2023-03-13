package ui.gtkdesigner.propertypanel;

import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JCheckBox;

import ui.widgets.IGtkWidget;
import ui.widgets.gtk.label.GtkLabel;

@SuppressWarnings("serial")
public class GtkLabelTab extends PropertyTabPanel
{
	public GtkLabelTab(PropertyPanel propPanel)
	{
		super(propPanel);
		setLayout(new GridLayout(8, 0));
	}
	
	private HashMap<String, JCheckBox> booleanProperties = new HashMap<>();

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
		booleanProperties.put("selectable", addCheckBox(null, "selectable", null, NO_PROP_DESC, true, createCheckBoxCallback("selectable")));
		booleanProperties.put("single-line-mode", addCheckBox(null, "single-line-mode", null, NO_PROP_DESC, true, createCheckBoxCallback("single-line-mode")));
		booleanProperties.put("track-visited-links", addCheckBox(null, "track-visited-links", null, NO_PROP_DESC, true, createCheckBoxCallback("track-visited-links")));
		booleanProperties.put("use-markup", addCheckBox(null, "use-markup", null, NO_PROP_DESC, true, createCheckBoxCallback("use-markup")));
		booleanProperties.put("use-underline", addCheckBox(null, "use-underline", null, NO_PROP_DESC, true, createCheckBoxCallback("use-underline")));
		// add element for width-chars
		booleanProperties.put("wrap", addCheckBox(null, "wrap", null, NO_PROP_DESC, true, createCheckBoxCallback("wrap")));
		// add element for wrap-mode
		// add element for xalign
		// add element for yalign
	}
	
	public void refresh(IGtkWidget data)
	{
		assert data instanceof GtkLabel;
		
		for(Map.Entry<String, JCheckBox> entry : booleanProperties.entrySet())
		{
			String propertyName = entry.getKey();
			JCheckBox checkBox = entry.getValue();
			checkBox.setSelected((boolean)data.getProperties().get(propertyName));
			checkBox.repaint();
		}
	}
}
