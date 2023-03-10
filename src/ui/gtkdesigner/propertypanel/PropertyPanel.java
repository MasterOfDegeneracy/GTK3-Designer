package ui.gtkdesigner.propertypanel;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import ui.gtkdesigner.EditorWindow;
import ui.gtkdesigner.InterfaceTree;
import ui.widgets.IGtkWidget;
import ui.widgets.gtk.label.GtkLabel;
import utils.Nullable;

@SuppressWarnings("serial")
public class PropertyPanel extends JPanel
{
	public static final Color BG_COLOR = new Color(32, 35, 41);
	
	/**
	 * The component containing all property tabs
	 */
	private JTabbedPane tabs = new JTabbedPane();
	
	private GtkWidgetTab gtkWidgetTab;
	private GtkLabelTab gtkLabelTab;
	
	/**
	 * The widget to be edited by this panel
	 */
	private IGtkWidget curWidget;
	
	public PropertyPanel(EditorWindow ew)
	{
		ew.addSelectionListener(widget->initForWidget(widget));
	}
	
	/**
	 * Initializes this {@code PropertyPanel}. This cannot be done in constructor, because the
	 * user of this class has to set the bounds of this panel before calling {@code initComponents()}.
	 */
	public void initComponents(InterfaceTree interfaceTree)
	{
		setLayout(null);
		setBackground(BG_COLOR);
		
		tabs.setBounds(0, 0, getWidth(), getHeight());
		add(tabs);
		
		initGtkWidgetTab();
		
		interfaceTree.addSelectionListener(widget->initForWidget(widget));
		
		
		initForWidget(null);
	}
	
	/**
	 * Sets the widget that is currently selected.
	 * @param widget The widget to be selected. This value may be {@code null}. In that case, no properties are shown.
	 * 
	 * TODO display the property states in the tabs
	 */
	public void initForWidget(@Nullable IGtkWidget widget)
	{
		tabs.removeAll();
		
		curWidget = widget;
		
		if(widget != null)
		{
			tabs.add("GtkWidget", gtkWidgetTab);
			
			if(widget instanceof GtkLabel)
				tabs.add("GtkLabel", gtkLabelTab);
		}
	}
	
	/**
	 * Returns the {@code IGtkWidget} whose properties are currently shown
	 */
	public IGtkWidget getWidget()
	{
		return curWidget;
	}
	
	private void initGtkWidgetTab()
	{
		gtkWidgetTab = new GtkWidgetTab(this);
		gtkWidgetTab.initComponents();
		
		gtkLabelTab = new GtkLabelTab(this);
		gtkLabelTab.initComponents();
	}
}
