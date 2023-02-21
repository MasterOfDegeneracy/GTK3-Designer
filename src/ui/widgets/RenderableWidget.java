package ui.widgets;

import java.awt.Dimension;

/**
 * This interface is implemented by all widgets that are rendered to the screen.
 * Not every Widget needs a renderer because some Widgets are only used for screen
 * layouts (e.g. GtkGrid, GtkBox, ...).
 */
public interface RenderableWidget extends IGtkWidget
{
	/**
	 * Returns the widget's renderer.
	 */
	public WidgetRenderer getRenderer();
	
	/**
	 * Returns the minimum size of this widget. This is usually the size of the rendered content
	 */
	public default Dimension getMinimumSize()
	{
		return getRenderer().getContentSize();
	}
}
