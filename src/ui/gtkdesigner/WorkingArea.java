package ui.gtkdesigner;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import ui.InterfaceRoot;
import ui.widgets.GtkContainer;
import ui.widgets.GtkWidget;
import ui.widgets.RenderableWidget;

@SuppressWarnings("serial")
public class WorkingArea extends JPanel
{
	public static final Color BG_COLOR = new Color(23, 26, 33);
	
	private InterfaceRoot interfaceRoot;
	
	public WorkingArea(InterfaceRoot interfaceRoot)
	{
		this.interfaceRoot = interfaceRoot;
		
		setBackground(BG_COLOR);
	}
	
	@Override
	public void paintComponent(Graphics graphics)
	{
		super.paintComponent(graphics);
		
		Graphics2D g = (Graphics2D)graphics;
		
		for(GtkWidget topLevelWidget : interfaceRoot)
		{
			if(topLevelWidget instanceof GtkContainer container)
				container.sendRecursiveRenderSignal(g);
			else if(topLevelWidget instanceof RenderableWidget renderable)
				renderable.getRenderer().render(g);
		}
	}
}
