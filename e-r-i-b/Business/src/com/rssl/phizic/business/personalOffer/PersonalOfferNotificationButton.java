package com.rssl.phizic.business.personalOffer;

import com.rssl.phizic.utils.StringHelper;

/**
 * @author lukina
 * @ created 20.12.2013
 * @ $Author$
 * @ $Revision$
 */
public class PersonalOfferNotificationButton implements PersonalOfferOrderedField
{
	private Long id; //Идентификатор.
	private String title; //Заголовок кнопки.
	private String url; //Ссылка.
	private Long imageId; //Изображение.; //Изображение на кнопке
	private Boolean show = true; //Отображать/не отображать.
	private Long orderIndex; //Порядок отображения.

	public PersonalOfferNotificationButton()
	{
	}

	public PersonalOfferNotificationButton(Long orderIndex)
	{
		this.orderIndex = orderIndex;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public Long getImageId()
	{
		return imageId;
	}

	public void setImageId(Long imageId)
	{
		this.imageId = imageId;
	}

	public Boolean getShow()
	{
		return show;
	}

	public void setShow(Boolean show)
	{
		this.show = show;
	}

	public Long getOrderIndex()
	{
		return orderIndex;
	}

	public void setOrderIndex(Long orderIndex)
	{
		this.orderIndex = orderIndex;
	}

	/**
	 * Нужно ли сохранять кнопку
	 * @return да/нет
	 */
	public boolean needSave()
	{
		return StringHelper.isNotEmpty(title)  || StringHelper.isNotEmpty(url) || imageId != null;
	}
}
