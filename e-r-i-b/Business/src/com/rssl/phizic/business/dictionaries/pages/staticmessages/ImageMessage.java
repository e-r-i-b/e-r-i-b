package com.rssl.phizic.business.dictionaries.pages.staticmessages;

import com.rssl.phizic.business.image.Image;

import java.io.Serializable;

/**
 * ������ ��������� � ������������
 * @author gladishev
 * @ created 22.12.2011
 * @ $Author$
 * @ $Revision$
 */
public class ImageMessage implements Serializable
{
	private Image image; //��������
	private String messageKey; //���������

	public ImageMessage()
	{
	}

	public ImageMessage(Image image, String messageKey)
	{
		this.image = image;
		this.messageKey = messageKey;
	}

	public Image getImage()
	{
		return image;
	}

	public void setImage(Image image)
	{
		this.image = image;
	}

	public String getMessageKey()
	{
		return messageKey;
	}

	public void setMessageKey(String messageKey)
	{
		this.messageKey = messageKey;
	}
}
