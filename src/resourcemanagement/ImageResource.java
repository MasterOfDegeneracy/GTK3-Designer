package resourcemanagement;

import java.io.File;

public enum ImageResource
{
	GTK_LABEL_ICON_IMAGE(new File("./images/widget/GtkLabel.png")),
	GTK_GRID_ICON_IMAGE(new File("./images/widget/GtkGrid.png"));
	
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
