package ui.widgets.gtk.grid;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.Map;

import ui.InterfaceRoot;
import ui.widgets.BlueprintRenderer;
import ui.widgets.GtkContainer;
import ui.widgets.IGtkWidget;
import ui.widgets.RenderableWidget;

/**
 * A simplified implementation of the GtkGrid.
 * TODO add support for homogenous rows and columns
 *
 */
public class GtkGrid extends GtkContainer
{
	@SuppressWarnings("unused")
	protected static class CellData
	{
		private LinkedList<IGtkWidget> children = new LinkedList<>();
		private Dimension sizeRequest = new Dimension(0, 0);
		public Point location;
		public Dimension actualSize;
		
		public void addChild(IGtkWidget widget)
		{
			children.add(widget);
			updateSizeRequest();
		}
		public void removeChild(IGtkWidget widget)
		{
			children.remove(widget);
			updateSizeRequest();
		}
		
		public Dimension getSizeRequest()
		{
			return sizeRequest;
		}
		
		private void updateSizeRequest()
		{
			int requestWidth = 0;
			int requestHeight = 0;
			
			for(IGtkWidget child : children)
			{
				Dimension minimumSize = child instanceof RenderableWidget renderable
						? renderable.getMinimumSize()
						: new Dimension(0, 0);
				
				int wReq = Math.max(child.getWidthRequest(), minimumSize.width);
				int hReq = Math.max(child.getHeightRequest(), minimumSize.height);
				
				if(wReq > requestWidth)
					requestWidth = wReq;
				if(hReq > requestHeight)
					requestHeight = hReq;
			}
			
			sizeRequest = new Dimension(requestWidth, requestHeight);
		}
	}
	
	protected LinkedList<LinkedList<CellData>> grid = new LinkedList<>();
	private int width = 0;
	private int height = 0;
	private Dimension minimumSize = new Dimension(0, 0);
	
	public GtkGrid(InterfaceRoot interfaceRoot)
	{
		super(interfaceRoot, new IGtkWidget[0]);
		
		appendCol();
		appendRow();
	}
	
	@Override
	public BlueprintRenderer createBlueprintRenderer()
	{
		return new GtkGridBlueprintRenderer(this);
	}

	@Override
	public Dimension getMinimumSize()
	{
		return new Dimension(
				Math.max(minimumSize.width, getWidthRequest()),
				Math.max(minimumSize.height, getHeightRequest()));
	}

	@Override
	public Point getChildPos(IGtkWidget child)
	{
		for(LinkedList<CellData> row : grid)
			for(CellData data : row)
				for(IGtkWidget curChild : data.children)
					if(child == curChild)
						return data.location;
		throw new IllegalArgumentException("This child is not contained in this grid.");
	}

	@Override
	public void registerProperties(Map<String, Object> properties)
	{
		super.registerProperties(properties);
		
		properties.put("baseline-row", 0);
		properties.put("column-homogenous", false);
		properties.put("column-spacing", 0);
		properties.put("row-homogenous", false);
		properties.put("row-spacing", 0);
		
		this.setProperty("width-request", 100);
		this.setProperty("height-request", 100);
	}
	
	@Override
	public boolean addChild(IGtkWidget child)
	{
		return addChild(child, 0, 0);
	}
	
	public boolean addChild(IGtkWidget child, int x, int y)
	{
		if(!super.addChild(child))
			return false;
		
		while(x >= width)
			appendCol();
		while(y >= height)
			appendRow();
		
		grid.get(x).get(y).addChild(child);
		
		updateChildrenGrid();
		
		super.fireChangedListeners();
		
		return true;
	}
	
	private void appendRow()
	{
		for(LinkedList<CellData> row : grid)
			row.add(new CellData());
		
		height++;
		
		updateChildrenGrid();
	}
	private void appendCol()
	{
		LinkedList<CellData> newLst = new LinkedList<>();
		
		for(int i = 0; i < width; i++)
			newLst.add(new CellData());
		
		grid.add(newLst);
		width++;
		
		updateChildrenGrid();
	}
	
	/**
	 * Updates the cell positions of the grid
	 * TODO this implementation is not efficient
	 */
	private void updateChildrenGrid()
	{
		int curX = 0;
		int curY = 0;
		
		int highestX = 0;
		int highestY = 0;
		
		for(int y = 0; y < height; y++)
		{
			int highest = 0;
			for(int x = 0; x < width; x++)
			{
				int req = grid.get(x).get(y).getSizeRequest().height;
				if(req > highest)
					highest = req;
			}
			
			for(int x = 0; x < width; x++)
			{
				int widest = 0;
				for(int y1 = 0; y1 < height; y1++)
				{
					int req = grid.get(x).get(y1).getSizeRequest().width;
					if(req > widest)
						widest = req;
				}
				
				CellData curCellData = grid.get(x).get(y);
				curCellData.location = new Point(curX, curY);
				curCellData.actualSize = new Dimension(widest, highest);
				
				curX += widest;
				
				if(curX > highestX)
					highestX = curX;
			}
			curX = 0;
			
			System.out.println("actualsize set");
			
			curY += highest;
			
			if(curY > highestY)
				highestY = curY;
		}
		
		/* Update minimums size */
		if(width != 0 && height != 0) /* This may only be the case if the constructor hasn't been executed completely */
		{
			minimumSize.width = highestX;
			minimumSize.height = highestY;
		}
	}

	private Point getCellAt(Point location)
	{
		for(LinkedList<CellData> row : grid)
			for(CellData data : row)
				if(new Rectangle(data.location, data.actualSize).contains(location))
					return new Point(grid.indexOf(row), row.indexOf(data));
		return null;
	}
	protected CellData getCellData(int x, int y)
	{
		return grid.get(y).get(x);
	}
	public int getWidth()
	{
		return width;
	}
	public int getHeight()
	{
		return height;
	}
	
	@Override
	public boolean dropChild(IGtkWidget child, Point relativeLocation)
	{
		System.out.println("Drop at " + relativeLocation.toString());
		Point cell = getCellAt(relativeLocation);
		assert cell != null; // This can not be null becuase a child can only be dropped inside of this GtkGrid and the complete space of this grid is covered by cells.
		
		return addChild(child, cell.x, cell.y);
	}
}
