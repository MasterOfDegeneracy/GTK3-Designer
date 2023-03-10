package ui.widgets;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

public class BlueprintRenderer extends WidgetRenderer
{
	public BlueprintRenderer(IGtkWidget widget)
	{
		super(widget);
	}
	
	@Override
	public void render(Graphics2D g)
	{
		g.setColor(Color.blue.darker());
		
		int width = widget.getMinimumSize().width;
		int height = widget.getMinimumSize().height;
		
		g.drawRect(0, 0, width, height);
	}
	
	@Override
	public Dimension getContentSize()
	{
		return widget.getMinimumSize();
	}
}
