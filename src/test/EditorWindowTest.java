package test;

import ui.InterfaceRoot;
import ui.gtkdesigner.EditorWindow;
import ui.widgets.gtk.label.GtkLabel;

public class EditorWindowTest
{
	public static void main(String[] args) throws InterruptedException
	{
		InterfaceRoot interfaceRoot = new InterfaceRoot();
		GtkLabel label = new GtkLabel();
		label.setProperty("label", "This is a label :)");
		interfaceRoot.addWidget(label);
		
		EditorWindow ew = new EditorWindow("Editor Window Test", interfaceRoot);
		
		while(true)
		{
			ew.repaint();
			Thread.sleep(100);
		}
	}
}
