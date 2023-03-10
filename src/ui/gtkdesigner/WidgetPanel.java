package ui.gtkdesigner;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import resourcemanagement.ImageFactory;
import resourcemanagement.ImageResource;

@SuppressWarnings("serial")
public class WidgetPanel extends JPanel
{
	public static final Color bgColor = new Color(32, 35, 41);
	
	public WidgetPanel()
	{
		setBackground(bgColor);
		setLayout(null);
	}
	
	public void createTabs()
	{
		JPanel widgets = new JPanel();
		widgets.setBackground(bgColor);
		widgets.add(createButton("GtkLabel", ImageResource.GTK_LABEL_ICON_IMAGE, "Add a new GtkLabel..."));
		widgets.add(createButton("GtkGrid", ImageResource.GTK_GRID_ICON_IMAGE, "Add a new GtkGrid..."));
		
		JScrollPane scrollPane = new JScrollPane(widgets);
		scrollPane.setBackground(bgColor);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(0, 0, getWidth(), getHeight());
		add(scrollPane);
	}
	
	private JComponent createButton(String text, ImageResource image, String desc)
	{
		DragGestureListener dlistener = new DragGestureListener() {

			@Override
			public void dragGestureRecognized(DragGestureEvent dge)
			{
				Transferable transferable = new StringSelection(text);
				System.out.println("recognized");
				dge.startDrag(null, transferable);
			}
			
		};
		
		JButton button = new JButton();
		
		DragSource ds1 = new DragSource();
	    ds1.createDefaultDragGestureRecognizer(button, DnDConstants.ACTION_COPY, dlistener);
	    button.setTransferHandler(new WidgetPanelTransferHandler());
		
		button.setForeground(Color.black);
		button.setBackground(Color.black);
		button.setFont(new Font("Liberation Sans", 0, 15));
		button.setIcon(new ImageIcon(ImageFactory.get(image).getScaledInstance(16, 16, Image.SCALE_SMOOTH), desc));
		button.setFocusable(false);
		button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		return button;
	}
}
