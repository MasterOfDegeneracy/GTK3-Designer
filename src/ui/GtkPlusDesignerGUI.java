package ui;

import java.awt.Rectangle;

import javax.swing.JFrame;

import main.Version;

public class GtkPlusDesignerGUI
{
	public static final String FRAME_TITLE = "Gtk+ Designer " + Version.version;
	
	public JFrame frame;
	
	public GtkPlusDesignerGUI(Rectangle bounds)
	{
		frame = new JFrame(FRAME_TITLE);
		frame.setBounds(bounds);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initMenuBar();
		frame.setVisible(true);
	}
	
	private void initMenuBar()
	{
		
	}
}
