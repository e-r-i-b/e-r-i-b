package com.rssl.phizic.business.advertising;

import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockDictionaryRecordBase;
import com.rssl.phizic.business.image.Image;
import com.rssl.phizic.utils.StringHelper;

/**
 * Сущность "Кнопка баннера"
 * @author komarov
 * @ created 19.12.2011
 * @ $Author$
 * @ $Revision$
 */

public class AdvertisingButton extends MultiBlockDictionaryRecordBase implements AdvertisingOrderedField
{
	private Long id; //Идентификатор.
	private String title; //Заголовок кнопки.
    private String url; //Ссылка.
	private Image image; //Изображение.; //Изображение на кнопке
    private Boolean show = true; //Отображать/не отображать.
    private Long orderIndex; //Порядок отображения.

	public AdvertisingButton()
	{		
	}

	public AdvertisingButton(Long orderIndex)
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

	public Image getImage()
	{
		return image;
	}

	public void setImage(Image image)
	{
		this.image = image;
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
		return StringHelper.isNotEmpty(title)  || StringHelper.isNotEmpty(url) || image != null;
	}
}
