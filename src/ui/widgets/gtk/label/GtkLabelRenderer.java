package ui.widgets.gtk.label;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

import ui.widgets.WidgetRenderer;

public class GtkLabelRenderer extends WidgetRenderer
{

	public GtkLabelRenderer(GtkLabel widget)
	{
		super(widget);
	}
	
	private Font getFont()
	{
		Font font = new Font("Arial", 0, 25);
		return font;
	}

	@Override
	public void render(Graphics2D g)
	{
		g.setColor(Color.white);
		g.fillRect(0, 0, 500, 500);
		
		/* TODO most properties are ignored here. Fix that, future me! */
		/* TODO also the rotation does only work for angles in [0;90]. Deal with it or fix it idc */
		
		GtkLabel label = (GtkLabel) widget;
		
		double angle = label.getAngle();
		
		Point pos = widget.getAbsolutePosition();
		
		int fontHeight = getContentSize(g.getFontRenderContext(), true).height;
		
		Dimension contentSize = getContentSize();
		
		Font font = getFont();
		
		g.setColor(Color.black);
		
		double fontHeightDelta = fontHeight * Math.cos(angle * (Math.PI / 180));
		Point stringTopPos = new Point(pos.x, (int) (pos.y + fontHeightDelta));
		
		g.rotate(label.getAngle() * (Math.PI / 180), stringTopPos.x, stringTopPos.y);
		
		g.setFont(font);
		g.drawString(label.getLabel(), stringTopPos.x, stringTopPos.y);
		
		g.rotate(-label.getAngle() / (180 / Math.PI), stringTopPos.x, stringTopPos.y);
		
		g.setColor(Color.red);
		
		g.drawRect(pos.x, pos.y, contentSize.width, contentSize.height);
	}

	private Dimension getContentSize(FontRenderContext frc, boolean ignoreRotation)
	{
		Font font = getFont();
		Rectangle2D strBounds = font.getStringBounds(((GtkLabel) widget).getLabel(), frc);
		
		double angle = ((GtkLabel) widget).getAngle();
		
		int cWidth, cHeight;
		
		if(ignoreRotation)
		{
			cHeight = (int) (strBounds.getHeight() + 1);
			cWidth = (int) (strBounds.getWidth() + 1);
		}
		else
		{
			int fontHeight = getContentSize(frc, true).height;
			double fontHeightDelta = fontHeight * Math.cos(angle * (Math.PI / 180));
			
			double fontWidthDelta = fontHeight * Math.sin(angle * (Math.PI / 180));
			
			cHeight = (int) (Math.max((Math.abs(Math.sin(angle / (180 / Math.PI))) * strBounds.getWidth()) + fontHeightDelta, strBounds.getHeight()));
			cWidth = (int) Math.max((Math.abs(Math.cos(angle / (180 / Math.PI))) * strBounds.getWidth()) + fontWidthDelta, strBounds.getHeight());
		}
		
		return new Dimension(cWidth + 1, cHeight + 1);
	}
	
	@Override
	public Dimension getContentSize()
	{
		return getContentSize(new FontRenderContext(null, true, true), false);
	}
}
