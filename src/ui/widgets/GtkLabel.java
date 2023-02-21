package ui.widgets;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder =  {"name", "value", "start", "end"})
public class GtkLabel implements GTKWidget
{
	@XmlElement private String name;
	@XmlElement private String value;
	@XmlElement private int start;
	@XmlElement private int end;
	
	public GtkLabel(String name, String value, int start, int end)
	{
		this.name = name;
		this.value = value;
		this.start = start;
		this.end = end;
	}
	public GtkLabel()
	{
		this("", "", 0, 0);
	}
	
	public String getName()
	{
		return name;
	}
	public String getValue()
	{
		return value;
	}
	public int getStart()
	{
		return start;
	}
	public int getEnd()
	{
		return end;
	}
}
