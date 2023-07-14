package com.rssl.phizic.business.locale.dynamic.resources.multi.block;

import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockDictionaryRecordBase;
import com.rssl.phizic.locale.dynamic.resources.LanguageResources;

import java.io.Serializable;

/**
 * @author komarov
 * @ created 25.09.2014
 * @ $Author$
 * @ $Revision$
 *
 * ������� ����� �������� ��������
 */
public class MultiBlockLanguageResources extends MultiBlockDictionaryRecordBase implements LanguageResources<String>
{
	private String localeId;

	public String getId()
	{
		return getMultiBlockRecordId();
	}

	/**
	 * @param uuid ���
	 */
	public void setId(String uuid)
	{
		setUuid(uuid);
	}

	/**
	 * @return ������
	 */
	public String getLocaleId()
	{
		return localeId;
	}

	/**
	 * @param localeId ������
	 */
	public void setLocaleId(String localeId)
	{
		this.localeId = localeId;
	}
}
