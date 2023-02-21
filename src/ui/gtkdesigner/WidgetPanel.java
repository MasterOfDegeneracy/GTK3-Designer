package ui.gtkdesigner;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import ui.gtkdesigner.uielements.WidgetTreeRenderer;

@SuppressWarnings("serial")
public class WidgetPanel extends JPanel
{
	public static final Color bgColor = new Color(32, 35, 41);
	
	public WidgetPanel()
	{
		setBackground(bgColor);
		setLayout(null);
		
		//createTabs();
	}
	
	public void createTabs()
	{
		DefaultMutableTreeNode root = new DefaultMutableTreeNode( "Widgets" );
		
		root.add(new DefaultMutableTreeNode( "Label" ));
		
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
