package ui.gtkdesigner.uielements;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;

import resourcemanagement.ImageFactory;
import resourcemanagement.ImageResource;

public class WidgetTreeRenderer implements TreeCellRenderer
{
	public static final Font FONT = new Font("Liberation Sans", 0, 15);
	
	public static final Color SELECTED_COLOR = Color.white;
	public static final Color EXPANDED_COLOR = Color.gray;
	public static final Color STANDARD_COLOR = Color.white;
	
	public static final Color LEAF_BACKGROUND_COLOR = Color.white;
	
	public static final Image LABEL_ICON_IMAGE = ImageFactory.get(ImageResource.GTK_LABEL_ICON_IMAGE).getScaledInstance(20, 20, Image.SCALE_SMOOTH);
	public static final String LABEL_ICON_DESC = "Add a new GtkLabel...";
	
	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus)
	{
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
		
		if(!leaf)
		{
			JLabel label = new JLabel(node.getUserObject().toString());
			label.setForeground(selected ? SELECTED_COLOR : expanded ? EXPANDED_COLOR : STANDARD_COLOR);
			label.setFont(FONT);
			return label;
		}
		else
		{
			JButton button = new JButton(node.getUserObject().toString());
			button.setForeground(Color.black);
			button.setBackground(LEAF_BACKGROUND_COLOR);
			button.setFont(FONT);
			button.setIcon(new ImageIcon(LABEL_ICON_IMAGE, LABEL_ICON_DESC));
			return button;
		}
	}
	
}