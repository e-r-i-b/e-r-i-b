package com.rssl.phizic.business.dictionaries.providers;

/**
 * класс дл€ вывода результатов поиска попул€рных поставщиков
 * @author Pankin
 * @ created 18.02.15
 * @ $Author$
 * @ $Revision$
 */
public class ServiceProviderShortForATM
{
	protected Long  id; //id поставщика
	private String  name;  // название поставщика
	private String  description; // описание
	private Long    imageId; // id картинки
	private boolean autoPaymentSupported;  //доступен ли автоплатеж

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

	public boolean isAutoPaymentSupported()
	{
		return autoPaymentSupported;
	}

	public void setAutoPaymentSupported(boolean autoPaymentSupported)
	{
		this.autoPaymentSupported = autoPaymentSupported;
	}
}
