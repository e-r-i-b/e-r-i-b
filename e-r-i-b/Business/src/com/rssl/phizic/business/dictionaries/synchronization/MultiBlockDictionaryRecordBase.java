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
 * ������� ����� �������� �����������
 */

public abstract class MultiBlockDictionaryRecordBase implements MultiBlockDictionaryRecord
{
	private String uuid;

	public String getMultiBlockRecordId()
	{
		return getUuid();
	}

	/**
	 * @return ���������� ���� ��������
	 */
	public String getUuid()
	{
		if (StringHelper.isEmpty(uuid))
			uuid = new RandomGUID().getStringValue();

		return uuid;
	}

	/**
	 * ������ ���������� ���� ��������
	 * @param uuid ���������� ���� ��������
	 */
	public void setUuid(String uuid)
	{
		this.uuid = uuid;
	}
}
