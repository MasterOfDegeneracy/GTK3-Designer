package ui.widgets.gtk.image;

import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;

import ui.InterfaceRoot;
import ui.widgets.BlueprintRenderer;
import ui.widgets.GtkWidget;
import ui.widgets.RenderableWidget;
import ui.widgets.WidgetRenderer;
import utils.gtkdefs.GtkImageType;
import utils.gtkdefs.IconSize;

public class GtkImage extends GtkWidget implements RenderableWidget
{
	public static final Image MISSING_IMAGE;
	
	static
	{
		try
		{
			MISSING_IMAGE = ImageIO.read(new File("./images/missing.png")).getScaledInstance(12, 12, Image.SCALE_SMOOTH);
		}
		catch(IOException e)
		{
			// TODO Maybe the program should not crash without an error report if this image is not present?
			throw new RuntimeException("A critical image resource was not found.", e);
		}
	}
	
	protected volatile Image image = MISSING_IMAGE;
	
	public GtkImageRenderer renderer;
	
	public GtkImage(InterfaceRoot interfaceRoot)
	{
		super(interfaceRoot);
		
		this.renderer = new GtkImageRenderer(this);
		
		this.addPropertyListener((propertyName, newVal)-> {
			assert newVal instanceof String; // Should be a fair assertion, right?
			
			if(propertyName.equals("file"))
			{
				try
				{
					image = ImageIO.read(new File((String) newVal));
				}
				catch (IOException e)
				{
					image = MISSING_IMAGE;
				}
			}
		});
	}

	@Override
	public Dimension getMinimumSize()
	{
		Dimension minSize = super.getMinimumSize();
		return new Dimension(
				Math.max(minSize.width, image.getWidth(null)),
				Math.max(minSize.height, image.getHeight(null)));
	}

	@Override
	public BlueprintRenderer createBlueprintRenderer()
	{
		return new BlueprintRenderer(this);
	}

	@Override
	public void registerProperties(Map<String, Object> properties)
	{
		super.registerProperties(properties);
		properties.put("file", "");
		properties.put("gicon", null); // TODO Add GIcon
		properties.put("icon-name", "");
		properties.put("icon-set", null); // TODO Add GtkIconSet
		properties.put("icon-size", IconSize.GTK_ICON_SIZE_INHERIT);
		properties.put("pixbuf", null); // TODO Add pixbuf
		properties.put("pixbuf-animation", null); // TODO Add GdkPixbufAnimation
		properties.put("pixel-size", 0);
		properties.put("resource", "");
		properties.put("stock", "");
		properties.put("storage-type", GtkImageType.GTK_IMAGE_EMPTY);
		properties.put("surface", null); // TODO Add cairo_surface_t
		properties.put("use-fallback", false);
	}

	@Override
	public WidgetRenderer getRenderer()
	{
		return renderer;
	}
}
