package ui.gtkdesigner.propertypanel;

import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JCheckBox;

import ui.widgets.GtkWidget;
import ui.widgets.IGtkWidget;

@SuppressWarnings("serial")
public class GtkWidgetTab extends PropertyTabPanel
{
	private HashMap<String, JCheckBox> booleanProperties = new HashMap<>();
	
	public GtkWidgetTab(PropertyPanel propPanel)
	{
		super(propPanel);
		setLayout(new GridLayout(8, 0));
	}
	
	@Override
	public void initComponents()
	{
		/* TODO add elements */
		
		booleanProperties.put("app-paintable", addCheckBox(null, "app-paintable", null, NO_PROP_DESC, true, createCheckBoxCallback("app-paintable")));
		booleanProperties.put("can-default", addCheckBox(null, "can-default", null, NO_PROP_DESC, true, createCheckBoxCallback("can-default")));
		booleanProperties.put("can-focus", addCheckBox(null, "can-focus", null, NO_PROP_DESC, true, createCheckBoxCallback("can-focus")));
		booleanProperties.put("double-buffered", addCheckBox(null, "double-buffered", null, NO_PROP_DESC, true, createCheckBoxCallback("double-buffered")));
		// add element for events
		booleanProperties.put("expand", addCheckBox(null, "expand", null, NO_PROP_DESC, true, createCheckBoxCallback("expand")));
		booleanProperties.put("focus-on-click", addCheckBox(null, "focus-on-click", null, NO_PROP_DESC, true, createCheckBoxCallback("focus-on-click")));
		// add element for halign
		booleanProperties.put("has-default", addCheckBox(null, "has-default", null, NO_PROP_DESC, true, createCheckBoxCallback("has-default")));
		booleanProperties.put("has-focus", addCheckBox(null, "has-focus", null, NO_PROP_DESC, true, createCheckBoxCallback("has-focus")));
		booleanProperties.put("has-tooltip", addCheckBox(null, "has-tooltip", null, NO_PROP_DESC, true, createCheckBoxCallback("has-tooltip")));
		// add element for height request
		booleanProperties.put("hexpand", addCheckBox(null, "hexpand", null, NO_PROP_DESC, true, createCheckBoxCallback("hexpand")));
		booleanProperties.put("hexpand-set", addCheckBox(null, "hexpand-set", null, NO_PROP_DESC, true, createCheckBoxCallback("hexpand-set")));
		booleanProperties.put("is-focus", addCheckBox(null, "is-focus", null, NO_PROP_DESC, true, createCheckBoxCallback("is-focus")));
		// add element for margin
		// add element for margin-bottom
		// add element for margin-end
		// add element for margin-left
		// add element for margin-right
		// add element for margin-start
		// add element for margin-top
		// add element for name
		booleanProperties.put("no-show-all", addCheckBox(null, "no-show-all", null, NO_PROP_DESC, true, createCheckBoxCallback("no-show-all")));
		// add element for opacity
		// add element for parent
		booleanProperties.put("receives-default", addCheckBox(null, "receives-default", null, NO_PROP_DESC, true, createCheckBoxCallback("receives-default")));
		booleanProperties.put("sensitive", addCheckBox(null, "sensitive", null, NO_PROP_DESC, true, createCheckBoxCallback("sensitive")));
		// add element for style
		// add element for tooltip-markup
		// add element for tooltip-text
		// add element for valign
		booleanProperties.put("vexpand", addCheckBox(null, "vexpand", null, NO_PROP_DESC, true, createCheckBoxCallback("vexpand")));
		booleanProperties.put("vexpand-set", addCheckBox(null, "vexpand-set", null, NO_PROP_DESC, true, createCheckBoxCallback("vexpand-set")));
		booleanProperties.put("visible", addCheckBox(null, "visible", null, NO_PROP_DESC, true, createCheckBoxCallback("visible")));
		// add element for width-request
	}

	public void refresh(IGtkWidget data)
	{
		assert data instanceof GtkWidget;
		
		for(Map.Entry<String, JCheckBox> entry : booleanProperties.entrySet())
		{
			String propertyName = entry.getKey();
			JCheckBox checkBox = entry.getValue();
			checkBox.setSelected((boolean)data.getProperties().get(propertyName));
			
			if(propertyName.equals("vexpand"))
				System.out.println(checkBox.isSelected());
		}
	}
}
