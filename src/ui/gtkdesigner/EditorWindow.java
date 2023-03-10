package ui.gtkdesigner;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;

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
	
	public static final Rectangle DEFAULT_BOUNDS = new Rectangle(
			SCREEN_SIZE.width / 10,
			SCREEN_SIZE.height / 10,
			(int)(SCREEN_SIZE.width * 0.8),
			(int)(SCREEN_SIZE.height * 0.8));
	
	private JFrame frame;
	
	private PropertyPanel propertyPanel;
	private WorkingArea workingArea;
	private InterfaceTree interfaceTree;
	private WidgetPanel widgetPanel;
	
	private List<SelectionListener> selectionListeners = new LinkedList<>();
	
	private volatile IGtkWidget selectedWidget;
	
	public EditorWindow(String title, InterfaceRoot interfaceRoot)
	{
		frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(DEFAULT_BOUNDS);
		frame.setLayout(null);
		frame.setVisible(true);
		frame.setResizable(false); /* TODO support resizing frame. */
		initComponents(interfaceRoot);
		frame.revalidate();
		frame.repaint();
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
		
		int widgetPanelWidth = (int) (frameSize.width * 0.2);
		int workingAreaWidth = (int) (frameSize.width * 0.6);
		int ifTreeWidth = (int) (frameSize.width * 0.2);
		
		widgetPanel = new WidgetPanel();
		widgetPanel.setBounds(0, 0, widgetPanelWidth, (int)(frameSize.height));
		frame.add(widgetPanel);
		widgetPanel.createTabs();
		
		workingArea = new WorkingArea(this, interfaceRoot);
		workingArea.setBounds(widgetPanelWidth, 0, workingAreaWidth, (int) (frameSize.height * 0.8));
		frame.add(workingArea);
		
		interfaceTree = new InterfaceTree(this, interfaceRoot);
		interfaceTree.setBounds(widgetPanelWidth + workingAreaWidth, 0, ifTreeWidth, frameSize.height);
		interfaceTree.initComponents();
		interfaceTree.addSelectionListener(this::selectWidget);
		frame.add(interfaceTree);
		
		
		propertyPanel = new PropertyPanel(this);
		propertyPanel.setBounds(widgetPanelWidth, (int)(frameSize.height*0.8), workingAreaWidth, (int)(frameSize.height*0.2));
		propertyPanel.initComponents(interfaceTree);
		frame.add(propertyPanel);
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
