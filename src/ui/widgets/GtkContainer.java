package ui.widgets;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

import javax.xml.bind.annotation.XmlType;

import ui.InterfaceRoot;
import ui.gtkdesigner.EditorWindow;
import utils.Nullable;

@XmlType(name = GtkContainer.XML_NAME)
public abstract class GtkContainer extends GtkWidget implements Iterable<IGtkWidget>
{
	public static final String XML_NAME = "GtkContainer";
	
	/**
	 * TODO LinkedList may be inefficient compared to an ArrayList. Check if a LinkedList does make sense after implementation!
	 */
	private List<IGtkWidget> children = new LinkedList<>();
	
	public GtkContainer(InterfaceRoot interfaceRoot, IGtkWidget[] children)
	{
		super(interfaceRoot);
		
		for(IGtkWidget child : children)
			this.children.add(child);
	}
	public GtkContainer(InterfaceRoot interfaceRoot)
	{
		super(interfaceRoot);
	}
	
	/**
	 * Adds a child to the container. If the child is already added to the container, the child will not be added.
	 * @param widget The widget to add to the container
	 * @return {@code true} if the child was added to the container successfully
	 */
	public boolean addChild(IGtkWidget widget)
	{
		if(widget == null)
			return false;
		if(!widget.setParent(this))
			return false;
		if(!children.add(widget))
			return false;
		
		return true;
	}
	
	/**
	 * Returns the child at the specified index, or null if the index is invalid.
	 * @param index The child's index
	 */
	@Nullable public IGtkWidget getChildOrNull(int index)
	{
		return index < 0 || index >= children.size() ? null : children.get(index);
	}
	
	/**
	 * Returns the child at the specified index or throws an exception if the index is invalid.
	 * @param index The child's index
	 */
	public IGtkWidget getChild(int index)
	{
		return children.get(index);
	}
	
	/**
	 * Removes a child from the container.
	 * @param widget The widget to be removed
	 * @return {@code true} if the {@code IGtkWidget} was removed from this container
	 */
	public boolean removeChild(IGtkWidget widget)
	{
		return children.remove(widget);
	}
	
	/**
	 * Finds all {@code IGtkWidget} that match the condition and returns the result as an array.
	 * Note that the order of elements in the resulting array must not be the order of the {@code IGtkWidgets}
	 * in the container.
	 * @param predicate The condition function
	 * @return All {@code IGtkWidget} matching the condition
	 */
	public IGtkWidget[] filter(Predicate<IGtkWidget> predicate)
	{
		final LinkedList<IGtkWidget> result = new LinkedList<>();
		
		children.stream().parallel().forEach(child -> {
			if(predicate.test(child))
				result.add(child);
		});
		
		return result.toArray(new IGtkWidget[result.size()]);
	}
	
	/**
	 * Returns the relative position of the child
	 */
	public abstract Point getChildPos(IGtkWidget child);
	
	public IGtkWidget getChildAt(Point absolutePosition)
	{
		for(IGtkWidget child : this)
		{
			Point pos = child.getAbsolutePosition();
			Dimension size = child.getMinimumSize();
			if(new Rectangle(pos, size).contains(absolutePosition))
			{
				return child;
			}
		}
		return null;
	}
	
	public abstract boolean dropChild(IGtkWidget child, Point relativeLocation);
	
	/**
	 * Recursively renders all children
	 */
	public void sendRecursiveRenderSignal(EditorWindow ew, InterfaceRoot ir, Graphics2D g, Point offset)
	{
		
		for(IGtkWidget widget : children)
		{
				
			if(widget instanceof GtkContainer container)
				container.sendRecursiveRenderSignal(ew, ir, g, offset);
			if(widget instanceof RenderableWidget renderable)
				renderable.getRenderer().render(g, new Point(renderable.getAbsolutePosition().x + offset.x, renderable.getAbsolutePosition().y + offset.y));
			
			if(ew.getSelectedWidget() == widget)
				widget.getBlueprintRenderer().render(g, new Point(widget.getAbsolutePosition().x + offset.x, widget.getAbsolutePosition().y + offset.y));
		}
	}
	
	@Override
	public Iterator<IGtkWidget> iterator()
	{
		return children.iterator();
	}
}
