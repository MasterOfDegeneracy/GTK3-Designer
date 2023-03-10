package ui.widgets;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;

public abstract class WidgetRenderer
{
	protected IGtkWidget widget;
	
	public WidgetRenderer(IGtkWidget widget)
	{
		this.widget = widget;
	}
	
	public abstract void render(Graphics2D g);
	
	public void render(Graphics2D g, Point offset)
	{
		g.translate(offset.x, offset.y);
		render(g);
		g.translate(-offset.x, -offset.y);
	}
	
	/**
	 * Returns the size of the displayed content in pixels.
	 */
	public abstract Dimension getContentSize();
}
