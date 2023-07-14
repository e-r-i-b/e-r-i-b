package com.rssl.phizic.business.exception.locale;

import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockDictionaryRecordBase;
import com.rssl.phizic.business.exception.uuid.ExceptionMappingResourceUUID;
import com.rssl.phizic.locale.dynamic.resources.LanguageResources;

/**
 * @author komarov
 * @ created 10.10.2014
 * @ $Author$
 * @ $Revision$
 */
public class ExceptionMappingResources extends MultiBlockDictionaryRecordBase implements LanguageResources<String>
{
	private String hash;
	private Long group;
	private String localeId;
	private String message;

	@Override
	public String getUuid()
	{
		return ExceptionMappingResourceUUID.createUUID(this);
	}

	/**
	 * @param hash ���
	 */
	public void setHash(String hash)
	{
		this.hash = hash;
	}

	/**
	 * @param groupId ������
	 */
	public void setGroup(Long groupId)
	{
		this.group = groupId;
	}

	/**
	 * @param localeId ������
	 */
	public void setLocaleId(String localeId)
	{
		this.localeId = localeId;
	}

	public String getId()
	{
		return hash;
	}

	/**
	 * @return ���
	 */
	public String getHash()
	{
		return hash;
	}

	/**
	 * @return ������
	 */
	public Long getGroup()
	{
		return group;
	}

	public String getLocaleId()
	{
		return localeId;
	}

	/**
	 * @return ���������
	 */
	public String getMessage()
	{
		return message;
	}

	/**
	 * @param message ���������
	 */
	public void setMessage(String message)
	{
		this.message = message;
	}


}
