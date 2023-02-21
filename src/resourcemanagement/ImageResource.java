package resourcemanagement;

import java.io.File;

public enum ImageResource
{
	GTK_LABEL_ICON_IMAGE(new File("./images/widget/gtkLabel.png"));
	
	private File file;
	
	private ImageResource(File file)
	{
		this.file = file;
	}
	
	public File getFile()
	{
		return file;
	}
}
