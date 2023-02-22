package ui.gtkdesigner;

import java.awt.Color;
import java.awt.Image;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import resourcemanagement.ImageFactory;
import resourcemanagement.ImageResource;
import ui.gtkdesigner.uielements.WidgetTreeRenderer;

@SuppressWarnings("serial")
public class WidgetPanel extends JPanel
{
	public static class WidgetPanelTreeNodeData
	{
		public String label;
		public Image icon;
		public String description;
		
		public WidgetPanelTreeNodeData(String label, Image icon, String description)
		{
			this.label = label;
			this.icon = icon;
			this.description = description;
		}
	}
	
	public static final Color bgColor = new Color(32, 35, 41);
	
	public WidgetPanel()
	{
		setBackground(bgColor);
		setLayout(null);
	}
	
	public void createTabs()
	{
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(new WidgetPanelTreeNodeData("Widgets", null, ""));
		
		root.add(new DefaultMutableTreeNode( new WidgetPanelTreeNodeData("GtkLabel",
				ImageFactory.get(ImageResource.GTK_LABEL_ICON_IMAGE).getScaledInstance(14, 14, Image.SCALE_SMOOTH),
				"Add a new GtkLabel...")));
		root.add(new DefaultMutableTreeNode( new WidgetPanelTreeNodeData("GtkGrid",
				ImageFactory.get(ImageResource.GTK_GRID_ICON_IMAGE).getScaledInstance(14, 14, Image.SCALE_SMOOTH),
				"Add a new GtkGrid...")));
		
		JTree tree = new JTree(root);
		tree.setCellRenderer(new WidgetTreeRenderer());
		tree.setBackground(bgColor);
		
		JScrollPane scrollPane = new JScrollPane(tree);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(0, 0, getWidth(), getHeight());
		add(scrollPane);
	}
}
