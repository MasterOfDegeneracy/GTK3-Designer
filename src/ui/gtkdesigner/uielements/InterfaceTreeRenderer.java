package ui.gtkdesigner.uielements;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;

import ui.widgets.IGtkWidget;

public class InterfaceTreeRenderer implements TreeCellRenderer
{
	public static final Font EXPANDED_FONT = new Font("Liberation Sans", Font.ITALIC, 15);
	public static final Font NOT_EXPANDED_FONT = new Font("Liberation Sans", 0, 15);

	public static final Color SELECTED_COLOR = Color.gray;
	public static final Color NOT_SELECTED_COLOR = Color.black;
	
	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus)
	{
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
		
		JLabel label = new JLabel(node.getUserObject() instanceof IGtkWidget
				? getNameForDisplay((IGtkWidget) node.getUserObject())
				: node.getUserObject().toString());
		
		label.setFont(expanded ? EXPANDED_FONT : NOT_EXPANDED_FONT);
		label.setForeground(Color.white);
		label.setBackground(selected ? SELECTED_COLOR : NOT_SELECTED_COLOR);
		label.setOpaque(true);
		
		return label;
	}
	
	private String getNameForDisplay(IGtkWidget widget)
	{
		String widgetName = widget.getName();
		return widgetName.isBlank() ? widget.getClass().getSimpleName() : widgetName;
	}
}
