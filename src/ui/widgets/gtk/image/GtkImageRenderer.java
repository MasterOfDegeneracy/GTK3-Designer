package ui.widgets.gtk.image;

import java.awt.Dimension;
import java.awt.Graphics2D;

import ui.widgets.WidgetRenderer;

public class GtkImageRenderer extends WidgetRenderer
{
	public GtkImageRenderer(GtkImage image)
	{
		super(image);
	}

	@Override
	public void render(Graphics2D g)
	{
		g.drawImage(((GtkImage)widget).image, 0, 0, null);
	}

	@Override
	public Dimension getContentSize()
	{
		GtkImage image = (GtkImage)widget;
		return new Dimension(image.image.getWidth(null), image.image.getHeight(null));
	}
}
