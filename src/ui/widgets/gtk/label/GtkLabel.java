package ui.widgets.gtk.label;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.util.Map;

import javax.xml.bind.annotation.XmlType;

import ui.InterfaceRoot;
import ui.widgets.BlueprintRenderer;
import ui.widgets.GtkWidget;
import ui.widgets.RenderableWidget;
import utils.gtkdefs.GtkJustification;
import utils.gtkdefs.PangoEllipsizeMode;
import utils.gtkdefs.PangoWrapMode;

@XmlType(name = GtkLabel.XML_NAME, propOrder =  {})
public class GtkLabel extends GtkWidget implements RenderableWidget
{
	public static final String XML_NAME = "GtkLabel";
	
	private GtkLabelRenderer renderer;
	
	public GtkLabel(InterfaceRoot interfaceRoot)
	{
		super(interfaceRoot);
		
		this.renderer = new GtkLabelRenderer(this);
	}
	
	@Override
	public GtkLabelRenderer getRenderer()
	{
		return renderer;
	}
	
	@Override
	public Dimension getMinimumSize()
	{
		Dimension minSize = super.getMinimumSize();
		Dimension contentSize = renderer.getContentSize();
		return new Dimension(Math.max(minSize.width, contentSize.width),
				Math.max(minSize.height, contentSize.height));
	}
	
	@Override
	public BlueprintRenderer createBlueprintRenderer()
	{
		return new BlueprintRenderer(this);
	}
	
	/* Property section */
	
	@Override
	public void registerProperties(Map<String, Object> properties)
	{
		super.registerProperties(properties);
		
		properties.put("angle", (Double) (0d));
		properties.put("attributes", null); /* TODO this is nonsense. Add a valid PangoAttrList */
		// cursor-position is not writable
		properties.put("ellipsize", PangoEllipsizeMode.PANGO_ELLIPSIZE_NONE);
		properties.put("justify", GtkJustification.GTK_JUSTIFY_CENTER);
		properties.put("label", "");
		properties.put("lines", -1);
		properties.put("max-width-chars", -1);
		properties.put("mnemonic-keyval", 0);
		properties.put("mnemonic-widget", null); /* TODO is this nonsense? */
		properties.put("pattern", "");
		properties.put("selectable", false);
		// selection-bound is not writable
		properties.put("single-line-mode", false);
		properties.put("track-visited-links", false);
		properties.put("use-markup", false);
		properties.put("use-underline", false);
		properties.put("width-chars", -1);
		properties.put("wrap", false);
		properties.put("wrap-mode", PangoWrapMode.PANGO_WRAP_WORD);
		properties.put("xalign", 0.5f);
		properties.put("yalign", 0.5f);
	}
	
	@Override
	public boolean setProperty(String propertyName, Object newValue)
	{
		if(!super.setProperty(propertyName, newValue))
			return false;
		if(propertyName.equals("label"))
			this.updateSize();
		return true;
	}
	
	public double getAngle()
	{
		return (double) properties.get("angle");
	}
	public String getLabel()
	{
		return (String) properties.get("label");
	}
	public void setLabel(String label)
	{
		this.setProperty("label", label);
	}
	
	@Override
	public void receiveKeyPress(char keyChar)
	{
		String prevLabel = getLabel();
		
		switch(keyChar)
		{
		case KeyEvent.VK_BACK_SPACE:
			if(prevLabel.length() >= 1)
				setLabel(prevLabel.substring(0, prevLabel.length() - 1));
			break;
		case KeyEvent.VK_ENTER:
			break; // TODO support multi-line labels
		default:
			setLabel(prevLabel + keyChar);
		}
	}
	
}
