package com.rssl.phizic.business.exception;

import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockDictionaryRecordBase;
import com.rssl.phizic.business.exception.uuid.ExceptionMappingUUID;

import java.io.Serializable;
import java.util.List;

/**
 * @author komarov
 * @ created 03.09.2014
 * @ $Author$
 * @ $Revision$
 */
public class ExceptionMapping extends MultiBlockDictionaryRecordBase implements Serializable
{
	private String hash;
	private Long group;
	private String message;
	private List<ExceptionMappingRestriction> restrictions;

	@Override
	public String getUuid()
	{
		return ExceptionMappingUUID.createUUID(this);
	}

	/**
	 * @return хеш
	 */
	public String getHash()
	{
		return hash;
	}

	/**
	 * @param hash хеш
	 */
	public void setHash(String hash)
	{
		this.hash = hash;
	}

	/**
	 * @return группа
	 */
	public Long getGroup()
	{
		return group;
	}

	/**
	 * @param group группа
	 */
	public void setGroup(Long group)
	{
		this.group = group;
	}

	/**
	 * @return сообщение
	 */
	public String getMessage()
	{
		return message;
	}

	/**
	 * @param message сообщение
	 */
	public void setMessage(String message)
	{
		this.message = message;
	}

	/**
	 * @return
	 */
	public List<ExceptionMappingRestriction> getRestrictions()
	{
		return restrictions;
	}

	/**
	 * @param restrictions
	 */
	public void setRestrictions(List<ExceptionMappingRestriction> restrictions)
	{
		this.restrictions = restrictions;
	}
}
