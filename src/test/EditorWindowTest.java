package test;

import ui.InterfaceRoot;
import ui.gtkdesigner.EditorWindow;
import ui.widgets.gtk.grid.GtkGrid;
import ui.widgets.gtk.label.GtkLabel;

public class EditorWindowTest
{
	public static void main(String[] args) throws InterruptedException
	{
		InterfaceRoot interfaceRoot = new InterfaceRoot();
		
		GtkLabel label = new GtkLabel(interfaceRoot);
		label.setProperty("label", "This is a label :)");
		
		GtkLabel label2 = new GtkLabel(interfaceRoot);
		label2.setProperty("label", "And you won't believe it, but this is another label. In another cell of the grid. Wow.");
		
		GtkLabel label3 = new GtkLabel(interfaceRoot);
		label3.setProperty("label", "Now there is a final label. It will probably destroy my layout code.");
		
		GtkLabel label4 = new GtkLabel(interfaceRoot);
		label4.setProperty("label", "Aaaaandd it did. But hopefully it's fixed now. hOpEfUlLy...");
		
		GtkGrid grid = new GtkGrid(interfaceRoot);
		boolean ret1 = grid.addChild(label, 0, 0);
		boolean ret2 = grid.addChild(label2, 1, 1);
		boolean ret3 = grid.addChild(label3, 1, 2);
		boolean ret4 = grid.addChild(label4, 1, 3);
		
		if(!ret1 || !ret2 || !ret3 || !ret4)
		{
			throw new RuntimeException("addChild failed!");
		}
		
		interfaceRoot.addWidget(grid);
		
		new EditorWindow("Editor Window Test", interfaceRoot);
		
		GtkGrid placeGrid = new GtkGrid(interfaceRoot);
		placeGrid.setProperty("width-request", 100);
		placeGrid.setProperty("height-request", 100);
	}
}
