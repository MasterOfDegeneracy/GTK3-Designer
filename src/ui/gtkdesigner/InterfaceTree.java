package ui.gtkdesigner;

import java.awt.Color;
import java.awt.Dimension;
import java.util.LinkedList;
import java.util.function.Consumer;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import ui.InterfaceRoot;
import ui.InterfaceRoot.WidgetAddedListener;
import ui.gtkdesigner.uielements.InterfaceTreeRenderer;
import ui.widgets.GtkContainer;
import ui.widgets.GtkWidget.WidgetChangedListener;
import ui.widgets.IGtkWidget;

@SuppressWarnings("serial")
public class InterfaceTree extends JPanel
{
	public static final Color BG_COLOR = new Color(32, 35, 41);
	
	private LinkedList<Consumer<IGtkWidget>> selectionListeners = new LinkedList<>();
	
	private InterfaceRoot interfaceRoot;
	private EditorWindow editorWindow;
	private JTree tree;
	
	public InterfaceTree(EditorWindow editorWindow, InterfaceRoot interfaceRoot)
	{
		setLayout(null);
		setBackground(Color.black);
		
		this.editorWindow = editorWindow;
		this.interfaceRoot = interfaceRoot;
		
		this.interfaceRoot.addWidgetAddedListener(new WidgetAddedListener() {

			@Override
			public void onWidgetAdded(IGtkWidget widget)
			{
				refreshTree();
			}
			
		});
		// The changed listener is needed because the WidgetAddedListener doesn't fire if a widget is added to a non-top-level container. 
		this.interfaceRoot.addWidgetChangedListener(new WidgetChangedListener() {
			
			@Override
			public void onChange(IGtkWidget widget)
			{
				refreshTree();
			}
		});
	}
	
	public void addSelectionListener(Consumer<IGtkWidget> listener)
	{
		selectionListeners.add(listener);
	}
	
	public void initComponents()
	{
		removeAll();
		
		DefaultTreeSelectionModel selectionModel = new DefaultTreeSelectionModel();
		selectionModel.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		tree = new JTree();
		refreshTree();
		tree.setCellRenderer(new InterfaceTreeRenderer());
		tree.setBounds(0, 0, getPreferredSize().width, getPreferredSize().height);
		tree.setBackground(BG_COLOR);
		tree.setSelectionModel(selectionModel);
		tree.setRootVisible(false);
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
				
				editorWindow.selectWidget(selectedWidget);
				
				for(Consumer<IGtkWidget> l : selectionListeners)
					l.accept(selectedWidget);
			}
			
		});
		add(tree);
	}
	
	private void refreshTree()
	{
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(null);
		
		for(IGtkWidget widget : interfaceRoot)
		{
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(widget);
			rootNode.add(node);
			
			if(widget instanceof GtkContainer container)
			{
				addNodes(node, container);
			}
		}
		
		tree.setModel(new DefaultTreeModel(rootNode, false));
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
	
	@Override
	public void setPreferredSize(Dimension dimension)
	{
		super.setPreferredSize(dimension);
		if(tree != null)
			tree.setBounds(0, 0, dimension.width, dimension.height);
	}
}
