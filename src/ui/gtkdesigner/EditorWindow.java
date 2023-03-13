package ui.gtkdesigner;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ui.InterfaceRoot;
import ui.gtkdesigner.propertypanel.PropertyPanel;
import ui.widgets.IGtkWidget;

public class EditorWindow
{
	public interface SelectionListener
	{
		public void onWidgetSelected(IGtkWidget widget);
	}
	
	public static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
	
	public static final int PROPERTY_PANEL_HEIGHT = 200;
	public static final int WIDGET_PANEL_WIDTH = 170;
	public static final int INTERFACE_TREE_WIDTH = 200;
	
	public static final Rectangle DEFAULT_BOUNDS = new Rectangle(
			SCREEN_SIZE.width / 10,
			SCREEN_SIZE.height / 10,
			(int)(SCREEN_SIZE.width * 0.8),
			(int)(SCREEN_SIZE.height * 0.8));
	
	private JFrame frame;
	
	private PropertyPanel propertyPanel;
	private WorkingArea workingArea;
	private JScrollPane workingAreaScrollPane;
	private InterfaceTree interfaceTree;
	private WidgetPanel widgetPanel;
	
	private List<SelectionListener> selectionListeners = new LinkedList<>();
	
	private volatile IGtkWidget selectedWidget;
	
	public EditorWindow(String title, InterfaceRoot interfaceRoot)
	{
		frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(DEFAULT_BOUNDS);
		frame.setVisible(true);
		frame.setLayout(new BorderLayout());
		frame.setResizable(true);
		initComponents(interfaceRoot);
		frame.revalidate();
		frame.repaint();
		frame.setMinimumSize(new Dimension(660, 500));
		frame.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e)
			{
				if(selectedWidget != null)
					selectedWidget.receiveKeyPress(e.getKeyChar());
			}

			@Override
			public void keyPressed(KeyEvent e)
			{
				
			}

			@Override
			public void keyReleased(KeyEvent e)
			{
				
			}
			
		});
		frame.getContentPane().addComponentListener(new ComponentListener() {

			@Override
			public void componentResized(ComponentEvent e)
			{
				JPanel source = (JPanel) e.getSource();
				Dimension size = source.getSize();
				
				propertyPanel.setPreferredSize(new Dimension(size.width, propertyPanel.getSize().height));
				interfaceTree.setPreferredSize(new Dimension(interfaceTree.getPreferredSize().width, size.height - PROPERTY_PANEL_HEIGHT));
				
				source.repaint();
			}

			@Override
			public void componentMoved(ComponentEvent e) { }

			@Override
			public void componentShown(ComponentEvent e) { }

			@Override
			public void componentHidden(ComponentEvent e) { }
			
		});
	}
	
	public void repaint()
	{
		frame.repaint(0);
	}
	
	public PropertyPanel getPropertyPanel()
	{
		return propertyPanel;
	}
	public WorkingArea getWorkingArea()
	{
		return workingArea;
	}
	public InterfaceTree getInterfaceTree()
	{
		return interfaceTree;
	}
	
	public void initComponents(InterfaceRoot interfaceRoot)
	{
		Dimension frameSize = frame.getContentPane().getSize();
		
		widgetPanel = new WidgetPanel();
		widgetPanel.setPreferredSize(new Dimension(WIDGET_PANEL_WIDTH, (int)(frameSize.height)));
		frame.add(widgetPanel, BorderLayout.LINE_START);
		widgetPanel.createTabs();
		
		workingArea = new WorkingArea(this, interfaceRoot);
		workingAreaScrollPane = new JScrollPane(workingArea);
		workingAreaScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		workingAreaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		frame.add(workingAreaScrollPane, BorderLayout.CENTER);
		
		interfaceTree = new InterfaceTree(this, interfaceRoot);
		interfaceTree.initComponents();
		interfaceTree.setPreferredSize(new Dimension(INTERFACE_TREE_WIDTH, frameSize.height));
		interfaceTree.addSelectionListener(this::selectWidget);
		frame.add(interfaceTree, BorderLayout.LINE_END);
		
		propertyPanel = new PropertyPanel(this);
		propertyPanel.setPreferredSize(new Dimension(frameSize.width, PROPERTY_PANEL_HEIGHT));
		propertyPanel.initComponents(interfaceTree);
		frame.add(propertyPanel, BorderLayout.PAGE_END);
	}
	
	public boolean addSelectionListener(SelectionListener l)
	{
		return selectionListeners.add(l);
	}
	public boolean removeSelectionListener(SelectionListener l)
	{
		return selectionListeners.remove(l);
	}
	private void fireSelectionListeners(IGtkWidget w)
	{
		for(SelectionListener l : selectionListeners)
			l.onWidgetSelected(w);
	}
	
	public IGtkWidget getSelectedWidget()
	{
		return selectedWidget;
	}
	public void selectWidget(IGtkWidget widget)
	{
		this.selectedWidget = widget;
		fireSelectionListeners(widget);
		frame.repaint();
	}
}
