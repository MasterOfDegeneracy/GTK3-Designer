package ui.gtkdesigner;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.io.IOException;

import javax.swing.JPanel;

import ui.InterfaceRoot;
import ui.widgets.GtkContainer;
import ui.widgets.IGtkWidget;
import ui.widgets.RenderableWidget;
import ui.widgets.gtk.grid.GtkGrid;
import ui.widgets.gtk.label.GtkLabel;

@SuppressWarnings("serial")
public class WorkingArea extends JPanel
{
	
	
	public static final Color BG_COLOR = new Color(23, 26, 33);
	
	/**
	 * The interface root that the WorkingArea displays. This field is volatile because it is accessed
	 * through the swing listeners of this panel
	 */
	private volatile InterfaceRoot interfaceRoot;
	
	/**
	 * The {@code EditorWindow} this {@code WorkingArea} is added to
	 */
	private EditorWindow editorWindow;
	
	public WorkingArea(EditorWindow ew, InterfaceRoot interfaceRoot)
	{
		this.editorWindow = ew;
		this.interfaceRoot = interfaceRoot;
		
		interfaceRoot.addWidgetChangedListener(	widget->WorkingArea.this.repaint());
		interfaceRoot.addWidgetAddedListener(	widget->repaint());
		interfaceRoot.addWidgetRemovedListener(	widget->repaint());
		
		setBackground(BG_COLOR);
		
		DropTargetAdapter dta = new DropTargetAdapter() {

			@Override
			public void drop(DropTargetDropEvent e)
			{
				String strData;
				try
				{
					strData = (String) e.getTransferable().getTransferData(e.getTransferable().getTransferDataFlavors()[0]);
				}
				catch (UnsupportedFlavorException | IOException e1)
				{
					e1.printStackTrace();
					return;
				}
				
				IGtkWidget newWidget;
				
				if(strData.equals("GtkLabel"))
					newWidget = new GtkLabel(interfaceRoot);
				else if(strData.equals("GtkGrid"))
					newWidget = new GtkGrid(interfaceRoot);
				else
					throw new UnsupportedOperationException("Not implemented yet.");
				
				Point dropLocation = e.getLocation();
				
				IGtkWidget widgetDroppedOn = interfaceRoot.getTopLevelWidgetAt(dropLocation);
				
				if(widgetDroppedOn != null)
				{
					if(widgetDroppedOn instanceof GtkContainer)
					{
						Point widgetPos = interfaceRoot.getTopLevelWidgetPosition(widgetDroppedOn);
						Point relativePos = new Point(dropLocation.x-widgetPos.x, dropLocation.y-widgetPos.y);
						
						if(!((GtkContainer) widgetDroppedOn).dropChild(newWidget, relativePos))
							e.rejectDrop();
						else
							editorWindow.selectWidget(newWidget);
					}
					else
						e.rejectDrop();
				}
				else
					interfaceRoot.addWidget(newWidget);
			}

		};
		
		new DropTarget(this, DnDConstants.ACTION_COPY, dta, true, null);
		
		this.setTransferHandler(new WidgetPanelTransferHandler());
	}
	
	@Override
	public void paintComponent(Graphics graphics)
	{
		super.paintComponent(graphics);
		
		Graphics2D g = (Graphics2D)graphics;
		
		for(IGtkWidget topLevelWidget : interfaceRoot)
		{
			if(topLevelWidget instanceof GtkContainer container)
				container.sendRecursiveRenderSignal(editorWindow, interfaceRoot, g, new Point(0, 0));
			else if(topLevelWidget instanceof RenderableWidget renderable)
				renderable.getRenderer().render(g, renderable.getAbsolutePosition());
			
			if(editorWindow.getSelectedWidget() == topLevelWidget)
				topLevelWidget.getBlueprintRenderer().render(g, topLevelWidget.getAbsolutePosition());
		}
		
	}
	
}
