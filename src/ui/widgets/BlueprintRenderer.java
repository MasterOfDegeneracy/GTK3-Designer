package ui.widgets;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

public class BlueprintRenderer extends WidgetRenderer
{
	public static final int BORDER_OFFSET = 2;
	public static final Color BORDER_COLOR = new Color(255, 255, 255);
	
	public BlueprintRenderer(IGtkWidget widget)
	{
		super(widget);
	}
	
	@Override
	public void render(Graphics2D g)
	{
		Dimension actualSize = widget.getMinimumSize();
		
		g.setColor(new Color(255, 255, 255, 30));
		g.fillRect(0, 0, actualSize.width, actualSize.height);
		
		g.setColor(BORDER_COLOR);
		
		int width = actualSize.width;
		int height = actualSize.height;
		
		g.drawRect(BORDER_OFFSET, BORDER_OFFSET, width - BORDER_OFFSET * 2, height - BORDER_OFFSET * 2);
	}
	
	@Override
	public Dimension getContentSize()
	{
		return widget.getMinimumSize();
	}
}
