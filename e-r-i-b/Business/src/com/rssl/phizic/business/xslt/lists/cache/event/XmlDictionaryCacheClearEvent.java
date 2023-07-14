package com.rssl.phizic.business.xslt.lists.cache.event;

import com.rssl.phizic.events.Event;

/**
 * Событие очистки кеша для xml справочников
 * @author Puzikov
 * @ created 02.12.13
 * @ $Author$
 * @ $Revision$
 */

public class XmlDictionaryCacheClearEvent implements Event
{
	private Long recordId;
	private Class objectClazz;

	public XmlDictionaryCacheClearEvent(Long recordId, Class objectClazz)
	{

		this.recordId = recordId;
		this.objectClazz = objectClazz;
	}

	public String getStringForLog()
	{
		StringBuilder result = new StringBuilder(getClass().getSimpleName());
		result.append(" ").append(objectClazz.getSimpleName());
		return result.toString();
	}

	public Long getRecordId()
	{
		return recordId;
	}

	public Class getObjectClazz()
	{
		return objectClazz;
	}
}
