package com.rssl.phizic.business.profile.images;

import java.awt.*;

/**
 * Контейнер для изображения.
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
	 * @return информация об изображении.
	 */
	public ImageInfo getImageInfo()
	{
		return imageInfo;
	}

	/**
	 * @param imageInfo информация об изображении.
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
