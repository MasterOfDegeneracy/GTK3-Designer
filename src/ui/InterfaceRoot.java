package ui;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import ui.widgets.GtkWidget;

/**
 * TODO This exports two interface tags. I don't know how to fix this. Send help pls :'(
 */
@XmlRootElement(name = "interface")
public class InterfaceRoot
{
	@XmlElementWrapper(name = "interface")
	private List<GtkWidget> widgets = new LinkedList<>();
	
	public InterfaceRoot()
	{
		
	}
	
	public void addWidget(GtkWidget widget)
	{
		this.widgets.add(widget);
	}
}
