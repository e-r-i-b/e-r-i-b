package com.rssl.phizic.business.image;

import com.rssl.phizic.utils.StringHelper;

import java.util.Date;

/**
 * @author akrenev
 * @ created 26.04.2010
 * @ $Author$
 * @ $Revision$
 */
public class Image
{
	private Long id;
	private String imageHelp;
	private Date updateTime;
	private String extendImage;
	private String innerImage;  //картинка из коммон скин
	private String linkURL;
	private String name;
	/**
	 * md5 код картинки.
	 */
	private String md5;
	private boolean haveImageBlob;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getLinkURL()
	{
		return linkURL;
	}

	public void setLinkURL(String linkURL)
	{
		this.linkURL = linkURL;
	}

	/**
	 * @return пояснение (описание) иконки
	 */
	public String getImageHelp()
	{
		return imageHelp;
	}

	public void setImageHelp(String imageHelp)
	{
		this.imageHelp = imageHelp;
	}

	public Date getUpdateTime()
	{
		return updateTime;
	}

	public void setUpdateTime(Date updateTime)
	{
		this.updateTime = updateTime;
	}

	/**
	 * @return прямая ссылка на внешнюю иконку
	 */
	public String getExtendImage()
	{
		return extendImage;
	}

	public void setExtendImage(String extendImage)
	{
		this.extendImage = extendImage;
	}

	public String getInnerImage()
	{
		return innerImage;
	}

	public void setInnerImage(String innerImage)
	{
		this.innerImage = innerImage;
	}

	/**
	 * @return есть ли содержимое в картинке
	 */
	public boolean isEmpty()
	{
		return !isHaveImageBlob() && StringHelper.isEmpty(extendImage) && StringHelper.isEmpty(innerImage);
	}

	public String getMd5()
	{
		return md5;
	}

	public void setMd5(String md5)
	{
		this.md5 = md5;
	}

	/**
	 * метод заточен под редактирование картинки
	 * при редактировании возможны значения с диска и с внешрего ресурса
	 * @return тип источника картинки
	 */
	public ImageSourceKind getSourceKind()
	{
		if (StringHelper.isNotEmpty(extendImage))
			return ImageSourceKind.EXTERNAL;

		return ImageSourceKind.DISC;
	}

	public boolean isHaveImageBlob()
	{
		return haveImageBlob;
	}

	public void setHaveImageBlob(boolean haveImageBlob)
	{
		this.haveImageBlob = haveImageBlob;
	}
}