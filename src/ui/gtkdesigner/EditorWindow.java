package ui.gtkdesigner;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JFrame;

import ui.InterfaceRoot;

public class EditorWindow
{
	public static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
	
	public static final Rectangle DEFAULT_BOUNDS = new Rectangle(
			SCREEN_SIZE.width / 10,
			SCREEN_SIZE.height / 10,
			(int)(SCREEN_SIZE.width * 0.8),
			(int)(SCREEN_SIZE.height * 0.8));
	
	private JFrame frame;
	
	public EditorWindow(String title, InterfaceRoot interfaceRoot)
	{
		frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(DEFAULT_BOUNDS);
		frame.setLayout(null);
		frame.setVisible(true);
		initComponents(interfaceRoot);
	}
	
	public void repaint()
	{
		frame.repaint(0);
	}
	
	public void initComponents(InterfaceRoot interfaceRoot)
	{
		Dimension frameSize = frame.getContentPane().getSize();
		
		int widgetPanelWidth = (int) (frameSize.width * 0.2f);
		
		WidgetPanel widgetPanel = new WidgetPanel();
		widgetPanel.setBounds(0, (int) (frameSize.height * 0.1f), widgetPanelWidth, (int)(frameSize.height * 0.9f));
		frame.add(widgetPanel);
		widgetPanel.createTabs();
		
		WorkingArea workingArea = new WorkingArea(interfaceRoot);
		workingArea.setBounds(widgetPanelWidth, 0, frameSize.width - widgetPanelWidth, frameSize.height);
		frame.add(workingArea);
	}
}
