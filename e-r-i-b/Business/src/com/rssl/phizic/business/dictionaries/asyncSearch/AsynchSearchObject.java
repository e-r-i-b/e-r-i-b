package com.rssl.phizic.business.dictionaries.asyncSearch;

/**
 * Инфомация о сущности из справочников, которую необходимо обновить в базе асинхронного поиска
 *
 * @ author: Gololobov
 * @ created: 22.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class AsynchSearchObject
{
	private Long id;
	private String objectClassName;
	private String objectKey;
	private AsynchSearchObjectState objectState;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getObjectClassName()
	{
		return objectClassName;
	}

	public void setObjectClassName(String objectClassName)
	{
		this.objectClassName = objectClassName;
	}

	public String getObjectKey()
	{
		return objectKey;
	}

	public void setObjectKey(String objectKey)
	{
		this.objectKey = objectKey;
	}

	public AsynchSearchObjectState getObjectState()
	{
		return objectState;
	}

	public void setObjectState(AsynchSearchObjectState objectState)
	{
		this.objectState = objectState;
	}
}
