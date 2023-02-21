package resourcemanagement;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

/**
 * This class is the interface between pictures and ImageResources. Every picture that was requested once, will be stored in RAM.
 * This is extremely inefficient memory management. I successfully solved that issue by stopping to care about it.
 */
public class ImageFactory
{
	private static final Map<ImageResource, BufferedImage> imgBuffer = new HashMap<>();
	
	public static BufferedImage get(ImageResource imgRes)
	{
		if(imgBuffer.containsKey(imgRes))
			return imgBuffer.get(imgRes);
		
		BufferedImage newImg;
		try
		{
			newImg = ImageIO.read(imgRes.getFile());
		}
		catch (IOException e)
		{
			throw new RuntimeException(imgRes + " links to a resource that was not readable.", e);
		}
		imgBuffer.put(imgRes, newImg);
		return newImg;
	}
}
