package com.rssl.phizic.business.dictionaries.synchronization;

import com.rssl.phizic.dictionaries.synchronization.MultiBlockDictionaryRecord;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author akrenev
 * @ created 27.02.2014
 * @ $Author$
 * @ $Revision$
 *
 * Базовый класс сущности справочника
 */

public abstract class MultiBlockDictionaryRecordBase implements MultiBlockDictionaryRecord
{
	private String uuid;

	public String getMultiBlockRecordId()
	{
		return getUuid();
	}

	/**
	 * @return уникальный ключ сущности
	 */
	public String getUuid()
	{
		if (StringHelper.isEmpty(uuid))
			uuid = new RandomGUID().getStringValue();

		return uuid;
	}

	/**
	 * задать уникальный ключ сущности
	 * @param uuid уникальный ключ сущности
	 */
	public void setUuid(String uuid)
	{
		this.uuid = uuid;
	}
}
