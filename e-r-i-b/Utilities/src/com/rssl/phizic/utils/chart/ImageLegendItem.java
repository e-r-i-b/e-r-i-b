package com.rssl.phizic.utils.chart;

import org.jfree.chart.LegendItem;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * @author akrenev
 * @ created 17.10.2012
 * @ $Author$
 * @ $Revision$
 *
 * Элемент легенды с картинкой
 */

public class ImageLegendItem
{
	private String text;    // легенда
	private BufferedImage image;  //картинка

	public ImageLegendItem(String text, String url)
	{
		this.text = text;
		this.image = create(url);
	}

	private static BufferedImage create(String path)
	{
		try
		{
			return ImageIO.read(new File(path));
		}
		catch (IOException ignore)
		{
			return null;
		}
	}

	/**
	 * @return JFreeChart'овский элемент легенды
	 */
	public LegendItem getLegendItem()
	{
		if (image == null)
			return null;
		
		Rectangle rectangle = new Rectangle(0, 0, image.getWidth(), image.getHeight());
		ImagePaint imagePaint = new ImagePaint(image, rectangle);
		return new LegendItem(text, null, null, null, rectangle, imagePaint);
	}
}
