package ui.widgets;

import java.awt.Dimension;
import java.awt.Graphics2D;

import utils.Nullable;

public abstract class WidgetRenderer
{
	protected RenderableWidget widget;
	
	public WidgetRenderer(RenderableWidget widget)
	{
		this.widget = widget;
	}
	
	@Nullable private WidgetRenderer getParent()
	{
		IGtkWidget parent = widget.getParent();
		
		return parent == null
				? null
				: parent instanceof RenderableWidget renderableParent
					? renderableParent.getRenderer()
					: null;
	}
	
	public abstract void render(Graphics2D g);
	
	
	/**
	 * Returns the size of the displayed content in pixels.
	 */
	public abstract Dimension getContentSize();
}
