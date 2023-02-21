package test;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import ui.widgets.GtkLabel;

public class XMLEncodingTest
{
	public static void main(String[] args) throws JAXBException
	{
		GtkLabel label = new GtkLabel("LabelName", "LabelValue", 1, 2);
		
		JAXBContext context = JAXBContext.newInstance(GtkLabel.class);
	    Marshaller mar= context.createMarshaller();
	    mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	    mar.marshal(label, new File("./label.xml"));
	}
}
