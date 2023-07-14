package com.rssl.phizic.business.dictionaries.payment.services;

/**
 * класс для вывода результатов поиска популярных услуг
 * @author Jatsky
 * @ created 11.06.2013
 * @ $Author$
 * @ $Revision$
 */

public class PaymentServiceShort
{
	protected Long  id; //id услуги
	private String  name;  // название услуги
	private String  description; // описание
	private Long    imageId; // id картинки

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

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public Long getImageId()
	{
		return imageId;
	}

	public void setImageId(Long imageId)
	{
		this.imageId = imageId;
	}
}
