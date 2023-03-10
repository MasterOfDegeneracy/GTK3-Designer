package ui.widgets;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Map;

import ui.InterfaceRoot;
import ui.widgets.GtkWidget.WidgetChangedListener;
import utils.Nullable;

public interface IGtkWidget
{
	/**
	 * Returns the parent widget or {@code null} if no parent exists.
	 */
	@Nullable public GtkContainer getParent();
	
	/**
	 * Getter method for an object's GTK properties. The {@code String} is the property name, the {@code Object} is the value.
	 */
	public Map<String, Object> getProperties();
	
	/**
	 * Determines whether the widget has a parent widget.
	 * @return {@code true} if the parent has a parent widget, {@code false otherwise}
	 */
	public boolean hasParent();
	
	public InterfaceRoot getInterfaceRoot();
	
	/**
	 * Sets the parent of this widget. If this widget already has a parent, the parent will not be changed and the method returns {@code false}.
	 */
	public boolean setParent(GtkContainer parent);
	
	/**
	 * Returns the widget's position relative to the top-level element.
	 */
	public default Point getAbsolutePosition()
	{
		if(!this.hasParent())
			return getInterfaceRoot().getTopLevelWidgetPosition(this);
		
		GtkContainer parent = getParent();
		
		Point pt = parent.getAbsolutePosition();
		Point relPos = parent.getChildPos(this);
		pt = new Point(pt.x + relPos.x, pt.y + relPos.y);
		return pt;
	}
	
	/**
	 * Returns the minimum size of this widget. This is usually the size of the rendered content
	 */
	public abstract Dimension getMinimumSize();
	
	public abstract int getWidthRequest();
	public abstract int getHeightRequest();
	public abstract String getName();
	
	/**
	 * Updates a value for a specific property. This call fails if the propertyName is invalid or the new value
	 * has a different type than the old value.
	 * @param propertyName The name of the property to be changed
	 * @param newValue The new value
	 * @return {@code true} if the property has been updated.
	 */
	public boolean setProperty(String propertyName, Object newValue);
	
	
	/**
	 * Returns the blueprint renderer for this widget. A blueprint renderer is a renderer that draws the GtkWidget
	 * in a way, that the user sees relevant information of the widget while placing or transforming it. For example
	 * the blueprint renderer for a GtkGrid should draw the outlines of its cells.
	 */
	public BlueprintRenderer getBlueprintRenderer();
	
	/**
	 * Creates a new blueprint renderer for this widget
	 */
	public BlueprintRenderer createBlueprintRenderer();
	
	public void fireChangedListeners();
	public boolean addWidgetChangedListener(WidgetChangedListener l);
	public boolean removeWidgetChangedListener(WidgetChangedListener l);
}
