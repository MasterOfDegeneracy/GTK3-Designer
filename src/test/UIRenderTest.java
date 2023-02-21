package test;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;

import ui.widgets.gtk.label.GtkLabel;

public class UIRenderTest
{
	@SuppressWarnings({ "serial", "deprecation" })
	public static void main(String[] args) throws InterruptedException
	{
		GtkLabel label = new GtkLabel();
		label.setProperty("label", "This is a label.");
		
		JFrame frame = new JFrame("UI Component render test") {
			@Override
			public void paint(Graphics g)
			{
				g.setColor(Color.black);
				label.getRenderer().render((Graphics2D) g);
			}
		};
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(0, 0, 500, 500);
		frame.show();
		
		while(true)
		{
			frame.repaint(0);
			Thread.sleep(100);
		}
	}
}
