package ui.widgets.gtk.grid;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;

import ui.widgets.BlueprintRenderer;
import ui.widgets.gtk.grid.GtkGrid.CellData;

public class GtkGridBlueprintRenderer extends BlueprintRenderer
{
	public static final Color CELL_BOUNDS_COLOR = new Color(97, 88, 82, 255);

	public GtkGridBlueprintRenderer(GtkGrid widget)
	{
		super(widget);
	}

	@Override
	public void render(Graphics2D g)
	{
		g.setColor(CELL_BOUNDS_COLOR);
		
		GtkGrid grid = (GtkGrid)this.widget;
		
		for(LinkedList<CellData> row : grid.grid)
		{
			for(CellData data : row)
			{
				g.drawRect(data.location.x, data.location.y, data.actualSize.width, data.actualSize.height);
			}
		}
		
		super.render(g);
	}
}
