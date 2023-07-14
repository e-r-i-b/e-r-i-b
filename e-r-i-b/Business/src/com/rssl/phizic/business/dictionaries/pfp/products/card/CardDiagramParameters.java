package com.rssl.phizic.business.dictionaries.pfp.products.card;

/**
 * @author akrenev
 * @ created 05.07.2013
 * @ $Author$
 * @ $Revision$
 */

public class CardDiagramParameters
{
	private boolean useImage;
	private String color;
	private Long imageId;
	private boolean useNet;

	public boolean isUseImage()
	{
		return useImage;
	}

	public void setUseImage(boolean useImage)
	{
		this.useImage = useImage;
	}

	public String getColor()
	{
		return color;
	}

	public void setColor(String color)
	{
		this.color = color;
	}

	public Long getImageId()
	{
		return imageId;
	}

	public void setImageId(Long imageId)
	{
		this.imageId = imageId;
	}

	public boolean isUseNet()
	{
		return useNet;
	}

	public void setUseNet(boolean useNet)
	{
		this.useNet = useNet;
	}
}
