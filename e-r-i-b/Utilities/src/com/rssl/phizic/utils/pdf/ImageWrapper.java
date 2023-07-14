package com.rssl.phizic.utils.pdf;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Image;

import java.io.IOException;

/**
 * @author akrenev
 * @ created 28.09.13
 * @ $Author$
 * @ $Revision$
 *
 * Враппер для картинки
 */

public class ImageWrapper
{
	private Image image;

	/**
	 * конструктор
	 * @param url урл картинки
	 * @param minWidth минимальная длина
	 * @param minHeight минимальная высота
	 * @param maxWidth максимальная длина
	 * @param maxHeight максимальная высота
	 * @throws PDFBuilderException
	 */
	public ImageWrapper(String url, int minWidth, int minHeight, int maxWidth, int maxHeight) throws PDFBuilderException
	{
		this.image = getImage(url);
		resize(minWidth, minHeight, maxWidth, maxHeight);
	}

	/**
	 * конструктор
	 * @param imageData картинка
	 * @param minWidth минимальная длина
	 * @param minHeight минимальная высота
	 * @param maxWidth максимальная длина
	 * @param maxHeight максимальная высота
	 * @throws PDFBuilderException
	 */
	public ImageWrapper(byte[] imageData, int minWidth, int minHeight, int maxWidth, int maxHeight) throws PDFBuilderException
	{
		this.image = getImage(imageData);
		resize(minWidth, minHeight, maxWidth, maxHeight);
	}

	private Image getImage(String url) throws PDFBuilderException
	{
		try
		{
			return Image.getInstance(url);
		}
		catch (BadElementException e)
		{
			throw new PDFBuilderException(e);
		}
		catch (IOException e)
		{
			throw new PDFBuilderException(e);
		}
	}

	private Image getImage(byte[] imageData) throws PDFBuilderException
	{
		try
		{
			return Image.getInstance(imageData);
		}
		catch (BadElementException e)
		{
			throw new PDFBuilderException(e);
		}
		catch (IOException e)
		{
			throw new PDFBuilderException(e);
		}
	}

	private void resize(int minWidth, int minHeight, int maxWidth, int maxHeight)
	{
		float width = getWidth();
		float height = getHeight();
		float scale = 1;
		if (minWidth > width)
			scale = Math.max(scale, minWidth / width);
		if (minHeight > height)
			scale = Math.max(scale, minHeight / height);
		if (maxWidth < width)
			scale = Math.min(scale, maxWidth / width);
		if (maxHeight < height)
			scale = Math.min(scale, maxHeight / height);

		image.scaleAbsolute(width * scale, height * scale);
	}

	/**
	 * @return длина картинки (с учетом границ)
	 */
	public int getWidth()
	{
		return Math.round(image.getPlainWidth());
	}

	/**
	 * @return высота картинки (с учетом границ)
	 */
	public int getHeight()
	{
		return Math.round(image.getPlainHeight());
	}

	Image getImage()
	{
		return image;
	}
}
