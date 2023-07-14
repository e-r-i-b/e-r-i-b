package com.rssl.phizic.common.types.documents.templates;

/**
 * Описание статусов активности шаблонов документов
 *
 * @author khudyakov
 * @ created 20.05.14
 * @ $Author$
 * @ $Revision$
 */
public enum ActivityCode
{
	REMOVED("удален клиентом"),                 //удален клиентом
	ACTIVE("активный");                         //активный


	private String description;

	ActivityCode(String description)
	{
		this.description = description;
	}

	/**
	 * @return описание
	 */
	public String getDescription()
	{
		return description;
	}
}
