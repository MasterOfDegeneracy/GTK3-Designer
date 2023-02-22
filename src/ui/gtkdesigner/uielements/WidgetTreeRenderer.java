package ui.gtkdesigner.uielements;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;

import ui.gtkdesigner.WidgetPanel.WidgetPanelTreeNodeData;

public class WidgetTreeRenderer implements TreeCellRenderer
{
	public static final Font FONT = new Font("Liberation Sans", 0, 15);
	
	public static final Color SELECTED_COLOR = Color.white;
	public static final Color EXPANDED_COLOR = Color.gray;
	public static final Color STANDARD_COLOR = Color.white;
	
	public static final Color LEAF_BACKGROUND_COLOR = Color.white;
	
	public static final String LABEL_ICON_DESC = "Add a new GtkLabel...";
	
	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus)
	{
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
		
		WidgetPanelTreeNodeData data = (WidgetPanelTreeNodeData) node.getUserObject();
		String text = data.label;
		
		if(!leaf)
		{
			JLabel label = new JLabel(text);
			label.setForeground(selected ? SELECTED_COLOR : expanded ? EXPANDED_COLOR : STANDARD_COLOR);
			label.setFont(FONT);
			return label;
		}
		else
		{
			JButton button = new JButton(text);
			button.setForeground(Color.black);
			button.setBackground(LEAF_BACKGROUND_COLOR);
			button.setFont(FONT);
			button.setIcon(new ImageIcon(data.icon, LABEL_ICON_DESC));
			button.addActionListener(new ActionListener()
			{

				@Override
				public void actionPerformed(ActionEvent e)
				{
					System.out.println(button.getText());
				}
				
			});
			return button;
		}
	}
	
}