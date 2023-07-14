package com.rssl.phizic.business.profile.images;

import java.awt.*;

/**
 *  онтейнер дл€ изображени€.
 *
 * @author bogdanov
 * @ created 27.06.14
 * @ $Author$
 * @ $Revision$
 */

public class ImageContainer
{
	private ImageInfo imageInfo;
	private Image image;

	/**
	 * @return информаци€ об изображении.
	 */
	public ImageInfo getImageInfo()
	{
		return imageInfo;
	}

	/**
	 * @param imageInfo информаци€ об изображении.
	 */
	public void setImageInfo(ImageInfo imageInfo)
	{
		this.imageInfo = imageInfo;
	}

	/**
	 * @return изображение.
	 */
	public Image getImage()
	{
		return image;
	}

	/**
	 * @param image изображение.
	 */
	public void setImage(Image image)
	{
		this.image = image;
	}
}
