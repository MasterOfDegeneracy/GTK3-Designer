package ui.widgets;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ui.InterfaceRoot;
import utils.Nullable;
import utils.gtkdefs.GdkEventMask;
import utils.gtkdefs.GtkAlign;

public abstract class GtkWidget implements IGtkWidget
{
	public static interface WidgetChangedListener
	{
		public void onChange(IGtkWidget widget);
	}
	public static interface PropertyListener
	{
		public void onPropertyChanged(String propertyName, Object newVal);
	}
	
	public static final String XML_NAME = "GtkWidget";
	
	/**
	 * The blueprint renderer for this GtkWidget.
	 * @see IGtkWidget#getBlueprintRenderer() 
	 */
	protected BlueprintRenderer blueprintRenderer;
	
	/**
	 * The widget's parent widget. This field contains {@code null} if the widget's
	 * parent left to get milk.
	 */
	@Nullable private GtkContainer parent;
	
	/**
	 * The widget's GTK properties
	 */
	protected Map<String, Object> properties;
	
	protected InterfaceRoot interfaceRoot;
	
	private List<WidgetChangedListener> widgetChangedListeners = new LinkedList<>();
	private List<PropertyListener> propertyListeners = new LinkedList<>();
	
	/**
	 * Constructs a new GtkWidget and registers its properties.
	 */
	public GtkWidget(InterfaceRoot interfaceRoot)
	{
		super();
		
		this.interfaceRoot = interfaceRoot;
		
		this.blueprintRenderer = createBlueprintRenderer();
			
		registerProperties(properties = new HashMap<>());
	}
	
	@Override
	public InterfaceRoot getInterfaceRoot()
	{
		return interfaceRoot;
	}
	
	@Override
	public BlueprintRenderer getBlueprintRenderer()
	{
		return blueprintRenderer;
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
	
	@Override public boolean setParent(GtkContainer parent)
	{
		if(!(this.hasParent() ^ parent != null))
			return false;
		this.parent = parent;
		return true;
	}
	
	/**
	 * equivalent to setParent(null).
	 */
	public boolean detachParent()
	{
		return setParent(null);
	}
	
	@Override
	public boolean setProperty(String propertyName, Object newValue)
	{
		if(!properties.containsKey(propertyName))
			throw new IllegalArgumentException("Unknown property \"" + propertyName + "\" for type \"" + getClass().getSimpleName() + "\".");
		
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
		fireChangedListeners();
		firePropertyListeners(propertyName, newValue);
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
	
	public Dimension getMinimumSize()
	{
		return new Dimension(getWidthRequest(), getHeightRequest());
	}
	public boolean isAppPaintable()
	{
		return (boolean) properties.get("app-paintable");
	}
	public int getWidthRequest()
	{
		return (int) properties.get("width-request");
	}
	public int getHeightRequest()
	{
		return (int) properties.get("height-request");
	}
	public String getName()
	{
		return (String) properties.get("name");
	}
	
	@Override
	public void fireChangedListeners()
	{
		if(this.hasParent())
			this.getParent().fireChangedListeners();
		
		for(WidgetChangedListener l : widgetChangedListeners)
			l.onChange(this);
	}
	public void firePropertyListeners(String propertyName, Object newVal)
	{
		for(PropertyListener l : propertyListeners)
			l.onPropertyChanged(propertyName, newVal);
	}
	
	@Override
	public boolean addWidgetChangedListener(WidgetChangedListener l)
	{
		return widgetChangedListeners.add(l);
	}
	public boolean addPropertyListener(PropertyListener l)
	{
		return propertyListeners.add(l);
	}
	@Override
	public boolean removeWidgetChangedListener(WidgetChangedListener l)
	{
		return widgetChangedListeners.remove(l);
	}
	public boolean removePropertyListener(PropertyListener l)
	{
		return propertyListeners.remove(l);
	}
}
