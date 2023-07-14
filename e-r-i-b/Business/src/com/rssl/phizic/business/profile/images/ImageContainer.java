package com.rssl.phizic.business.profile.images;

import java.awt.*;

/**
 * ��������� ��� �����������.
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
	 * @return ���������� �� �����������.
	 */
	public ImageInfo getImageInfo()
	{
		return imageInfo;
	}

	/**
	 * @param imageInfo ���������� �� �����������.
	 */
	public void setImageInfo(ImageInfo imageInfo)
	{
		this.imageInfo = imageInfo;
	}

	/**
	 * @return �����������.
	 */
	public Image getImage()
	{
		return image;
	}

	/**
	 * @param image �����������.
	 */
	public void setImage(Image image)
	{
		this.image = image;
	}
}
