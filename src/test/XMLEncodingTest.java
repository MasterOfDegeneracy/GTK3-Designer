package test;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import ui.InterfaceRoot;
import ui.widgets.gtk.label.GtkLabel;

public class XMLEncodingTest
{
	public static void main(String[] args) throws JAXBException
	{
		InterfaceRoot iface = new InterfaceRoot();
		GtkLabel label = new GtkLabel();
		
		iface.addWidget(label);
		
		JAXBContext context = JAXBContext.newInstance(InterfaceRoot.class, GtkLabel.class);
	    Marshaller mar= context.createMarshaller();
	    mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	    mar.marshal(iface, new File("./iface.xml"));
	}
}
