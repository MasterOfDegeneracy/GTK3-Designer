package ui.gtkdesigner;

import java.awt.Color;
import java.util.LinkedList;
import java.util.function.Consumer;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import ui.InterfaceRoot;
import ui.gtkdesigner.uielements.InterfaceTreeRenderer;
import ui.widgets.GtkContainer;
import ui.widgets.IGtkWidget;

@SuppressWarnings("serial")
public class InterfaceTree extends JPanel
{
	public static final Color BG_COLOR = new Color(32, 35, 41);
	
	private LinkedList<Consumer<IGtkWidget>> selectionListeners = new LinkedList<>();
	
	private InterfaceRoot interfaceRoot;
	private JTree tree;
	
	public InterfaceTree(InterfaceRoot interfaceRoot)
	{
		setLayout(null);
		setBackground(Color.black);
		
		this.interfaceRoot = interfaceRoot;
	}
	
	public void addSelectionListener(Consumer<IGtkWidget> listener)
	{
		selectionListeners.add(listener);
	}
	
	public void initComponents()
	{
		removeAll();
		
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Widget Tree");
		
		interfaceRoot.forEach((IGtkWidget widget) -> {
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(widget);
			rootNode.add(node);
			
			if(widget instanceof GtkContainer container)
			{
				addNodes(node, container);
			}
		});
		
		DefaultTreeSelectionModel selectionModel = new DefaultTreeSelectionModel();
		selectionModel.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		tree = new JTree(rootNode);
		tree.setCellRenderer(new InterfaceTreeRenderer());
		tree.setBounds(0, 0, getWidth(), getHeight());
		tree.setBackground(BG_COLOR);
		tree.setSelectionModel(selectionModel);
		tree.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent e)
			{
				TreePath selectionPath = ((JTree)e.getSource()).getSelectionPath();
				
				IGtkWidget selectedWidget = null;
				
				if(selectionPath != null)
				{
					DefaultMutableTreeNode selected = (DefaultMutableTreeNode) selectionPath.getLastPathComponent();
					
					Object userObj = selected.getUserObject();
					
					if(userObj instanceof IGtkWidget w)
						selectedWidget = w;
				}
				
				for(Consumer<IGtkWidget> l : selectionListeners)
					l.accept(selectedWidget);
			}
			
		});
		add(tree);
	}
	
	private void addNodes(DefaultMutableTreeNode superNode, GtkContainer container)
	{
		for(IGtkWidget widget : container)
		{
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(widget);
			superNode.add(node);
			
			if(widget instanceof GtkContainer subContainer)
			{
				addNodes(node, subContainer);
			}
		}
	}
}
