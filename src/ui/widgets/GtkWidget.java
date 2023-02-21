package ui.widgets;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlType;

import utils.Nullable;
import utils.gtkdefs.GdkEventMask;
import utils.gtkdefs.GtkAlign;

@XmlType(name = GtkWidget.XML_NAME)
public abstract class GtkWidget implements IGtkWidget
{
	public static final String XML_NAME = "GtkWidget";
	
	/**
	 * The widget's parent widget. This field contains {@code null} if the widget's
	 * parent left to get milk.
	 */
	@Nullable private GtkContainer parent;
	
	/**
	 * The widget's GTK properties
	 */
	protected Map<String, Object> properties;
	
	/**
	 * Constructs a new GtkWidget and registers its properties.
	 */
	public GtkWidget()
	{
		super();
		registerProperties(properties = new HashMap<>());
	}
	
	@Override
	public Map<String, Object> getProperties()
	{
		return properties; /* TODO should return a clone */
	}
	
	/**
	 * Returns the parent widget or {@code null} if no parent exists.
	 */
	@Override @Nullable public GtkContainer getParent()
	{
		return parent;
	}
	
	/**
	 * Determines whether the widget has a parent widget.
	 * @return {@code true} if the parent has a parent widget, {@code false otherwise}
	 */
	@Override public boolean hasParent()
	{
		return getParent() != null;
	}
	
	/**
	 * Updates a value for a specific property. This call fails if the propertyName is invalid or the new value
	 * has a different type than the old value.
	 * @param propertyName The name of the property to be changed
	 * @param newValue The new value
	 * @return {@code true} if the property has been updated. {@code false} if the call failed.
	 */
	public boolean setProperty(String propertyName, Object newValue)
	{
		if(!properties.containsKey(propertyName))
			return false;
		
		Class<?> oldValClass = properties.get(propertyName).getClass();
		Class<?> newValClass = newValue.getClass();
		
		
		if(!oldValClass.equals(newValClass))
		{
			try
			{
				newValue = oldValClass.cast(newValue);
			}
			catch(ClassCastException cce)
			{
				throw new IllegalArgumentException("This property only accepts values of type " + oldValClass.getSimpleName() + ". "
						+ "Casting " + newValClass.getSimpleName() + " to " + oldValClass.getSimpleName() + " is not possible.");
			}
		}
		
		properties.put(propertyName, newValue);
		return true;
	}
	
	/**
	 * Adds the widget's properties to the map
	 * @param properties The property-map
	 */
	public void registerProperties(Map<String, Object> properties)
	{
		properties.put("app-paintable", false);
		properties.put("can-default", false);
		properties.put("can-focus", false);
		// composite-child is not writable
		properties.put("double-buffered", false);
		properties.put("events", GdkEventMask.GDK_ALL_EVENTS_MASK);
		properties.put("expand", false);
		properties.put("focus-on-click", false);
		properties.put("halign", GtkAlign.GTK_ALIGN_CENTER);
		properties.put("has-default", false);
		properties.put("has-focus", false);
		properties.put("has-tooltip", false);
		properties.put("height-request", -1);
		properties.put("hexpand", false);
		properties.put("hexpand-set", false);
		properties.put("is-focus", false);
		properties.put("margin", 0);
		properties.put("margin-bottom", 0);
		properties.put("margin-end", 0);
		properties.put("margin-left", 0);
		properties.put("margin-right", 0);
		properties.put("margin-start", 0);
		properties.put("margin-top", 0);
		properties.put("name", "");
		properties.put("no-show-all", false);
		properties.put("opacity", 1d);
		properties.put("parent", null); /* TODO probably this is nonsense */
		properties.put("receives-default", false);
		// scale-factor is not writable
		properties.put("sensitive", false);
		properties.put("style", null); /* TODO set a valid GtkStyle */
		properties.put("tooltip-markup", "");
		properties.put("tooltip-text", "");
		properties.put("valign", GtkAlign.GTK_ALIGN_CENTER);
		properties.put("vexpand", false);
		properties.put("vexpand-set", false);
		properties.put("visible", true);
		properties.put("width-request", -1);
		// window is not writable
	}
}
