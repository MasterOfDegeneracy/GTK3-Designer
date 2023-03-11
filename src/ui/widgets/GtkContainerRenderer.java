package ui.widgets;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

public class GtkContainerRenderer extends WidgetRenderer
{
	public static final int BORDER_OFFSET = 1;
	public static final Color BG_COLOR = new Color(255, 255, 255, 20);
	public static final Color BORDER_COLOR = new Color(255, 255, 255, 20);

	public GtkContainerRenderer(GtkContainer widget)
	{
		super(widget);
	}

	@Override
	public void render(Graphics2D g)
	{
		g.setColor(BG_COLOR);
		Dimension minSize = widget.getMinimumSize();
		g.fillRect(0, 0, minSize.width, minSize.height);
		
		g.setColor(BORDER_COLOR);
		g.drawRect(BORDER_OFFSET, BORDER_OFFSET, minSize.width - BORDER_OFFSET * 2, minSize.height - BORDER_OFFSET * 2);
	}

	@Override
	public Dimension getContentSize()
	{
		return widget.getMinimumSize();
	}

}
