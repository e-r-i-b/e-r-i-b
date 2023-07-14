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
 * ������� ��� ��������
 */

public class ImageWrapper
{
	private Image image;

	/**
	 * �����������
	 * @param url ��� ��������
	 * @param minWidth ����������� �����
	 * @param minHeight ����������� ������
	 * @param maxWidth ������������ �����
	 * @param maxHeight ������������ ������
	 * @throws PDFBuilderException
	 */
	public ImageWrapper(String url, int minWidth, int minHeight, int maxWidth, int maxHeight) throws PDFBuilderException
	{
		this.image = getImage(url);
		resize(minWidth, minHeight, maxWidth, maxHeight);
	}

	/**
	 * �����������
	 * @param imageData ��������
	 * @param minWidth ����������� �����
	 * @param minHeight ����������� ������
	 * @param maxWidth ������������ �����
	 * @param maxHeight ������������ ������
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
	 * @return ����� �������� (� ������ ������)
	 */
	public int getWidth()
	{
		return Math.round(image.getPlainWidth());
	}

	/**
	 * @return ������ �������� (� ������ ������)
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
