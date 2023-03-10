package ui;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import ui.widgets.GtkWidget.WidgetChangedListener;
import ui.widgets.IGtkWidget;

public class InterfaceRoot implements Iterable<IGtkWidget>
{
	public interface WidgetAddedListener
	{
		public void onWidgetAdded(IGtkWidget widget);
	}
	public interface WidgetRemovedListener
	{
		public void onWidgetRemoved(IGtkWidget widget);
	}
	public interface ChildChangedListener
	{
		public void onChildChanged(IGtkWidget widget);
	}
	
	/**
	 * The vertical gap between top level widgets rendered to this WorkingArea in pixels
	 */
	public static final int TOP_LEVEL_WIDGET_SPACING = 50;
	
	/**
	 * The origin of the top level widgets rendered to this WorkingArea
	 */
	public static final Point TOP_LEVEL_WIDGET_ORIGIN = new Point(20, 20);
	
	private List<IGtkWidget> widgets = new LinkedList<>();
	
	private List<WidgetChangedListener> changedListeners = new LinkedList<>();
	private List<WidgetAddedListener> addedListeners = new LinkedList<>();
	private List<WidgetRemovedListener> removedListeners = new LinkedList<>();
	
	/**
	 * This map is used to determine which {@code WidgetChangedListener} corresponds to which {@code IGtkWidget}. This is needed
	 * for the removeWidget function to unregister the correct listener from the widget.
	 */
	private HashMap<IGtkWidget, WidgetChangedListener> changedListenerMap = new HashMap<>();
	
	/**
	 * Holds the position of the top level widgets
	 */
	private HashMap<IGtkWidget, Point> topLevelPositionMapping = new HashMap<>();
	
	public InterfaceRoot()
	{
		
	}
	
	public boolean addWidgetAddedListener(WidgetAddedListener l)
	{
		return addedListeners.add(l);
	}
	public boolean addWidgetRemovedListener(WidgetRemovedListener l)
	{
		return removedListeners.add(l);
	}
	public boolean addWidgetChangedListener(WidgetChangedListener l)
	{
		return changedListeners.add(l);
	}
	public boolean removeWidgetAddedListener(WidgetAddedListener l)
	{
		return addedListeners.remove(l);
	}
	public boolean removeWidgetRemovedListener(WidgetRemovedListener l)
	{
		return removedListeners.remove(l);
	}
	public boolean removeWidgetChangedListener(WidgetChangedListener l)
	{
		return changedListeners.remove(l);
	}
	public WidgetAddedListener[] getWidgetAddedListeners()
	{
		return addedListeners.toArray(new WidgetAddedListener[0]);
	}
	public WidgetRemovedListener[] getWidgetRemovedListeners()
	{
		return removedListeners.toArray(new WidgetRemovedListener[0]);
	}
	private void fireWidgetAddedListeners(IGtkWidget w)
	{
		for(WidgetAddedListener l : addedListeners)
			l.onWidgetAdded(w);
	}
	private void fireWidgetRemovedListeners(IGtkWidget w)
	{
		for(WidgetRemovedListener l : removedListeners)
			l.onWidgetRemoved(w);
	}
	private void fireWidgetChangedListeners(IGtkWidget w)
	{
		for(WidgetChangedListener l : changedListeners)
			l.onChange(w);
	}
	
	public boolean addWidget(IGtkWidget widget)
	{
		if(widget == null)
			throw new IllegalArgumentException("widget may not be null.");
		
		if(!this.widgets.add(widget))
			return false;
		
		WidgetChangedListener l = new WidgetChangedListener() {

			@Override
			public void onChange(IGtkWidget w) {
				fireWidgetChangedListeners(w);
			}
			
		};
		if(!widget.addWidgetChangedListener(l))
			return false;
		
		assert changedListenerMap.get(widget) == null;
		changedListenerMap.put(widget, l);
		
		widget.addWidgetChangedListener((w)->fireWidgetChangedListeners(w));
		
		updateTopLevelWidgetPositions();
		
		fireWidgetAddedListeners(widget);
		return true;
	}
	public boolean removeWidget(IGtkWidget widget)
	{
		if(!this.widgets.remove(widget))
			return false;
		
		if(!widget.removeWidgetChangedListener(changedListenerMap.get(widget)))
			return false;
		
		updateTopLevelWidgetPositions();
		
		fireWidgetRemovedListeners(widget);
		return true;
	}
	public boolean isTopLevelWidget(IGtkWidget widget)
	{
		return this.widgets.contains(widget);
	}

	@Override
	public Iterator<IGtkWidget> iterator()
	{
		return widgets.iterator();
	}
	
	private void updateTopLevelWidgetPositions()
	{
		this.topLevelPositionMapping.clear();
		
		Point topLevelWidgetPos = (Point) TOP_LEVEL_WIDGET_ORIGIN.clone();
		for(IGtkWidget topLevelWidget : this)
		{
			topLevelPositionMapping.put(topLevelWidget, (Point) topLevelWidgetPos.clone());
			
			topLevelWidgetPos.y += topLevelWidget.getBlueprintRenderer().getContentSize().height + TOP_LEVEL_WIDGET_SPACING;
		}
	}
	
	public Point getTopLevelWidgetPosition(IGtkWidget topLevelWidget)
	{
		Point pos = topLevelPositionMapping.get(topLevelWidget);
		if(pos == null)
			throw new IllegalArgumentException("No position found. (Probably the widget is not a top-level widget)");
		return pos;
	}
	
	/**
	 * TODO This function assumes that top level widgets can not overlap. This will break my code in the future but idc
	 */
	public IGtkWidget getTopLevelWidgetAt(Point position)
	{
		for(IGtkWidget topLevelWidget : this)
		{
			Point pos = getTopLevelWidgetPosition(topLevelWidget);
			Dimension size = topLevelWidget.getMinimumSize();
			Rectangle rect = new Rectangle(pos, size);
			if(rect.contains(position))
				return topLevelWidget;
		}
		return null;
	}
}
