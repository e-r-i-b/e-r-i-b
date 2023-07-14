package com.rssl.phizic.config.propertysyncinfo;

/**
 * Created with IntelliJ IDEA.
 * User: tisov
 * Date: 20.10.14
 * Time: 18:24
 * Статус операции синхронизации настроек
 */
public enum PropertySyncInfoStatus
{

	OK("Синхронизировано"),
	ERROR("Ошибка"),
	PROCESSING("В обработке");

	private String description;

	private PropertySyncInfoStatus(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}
}
