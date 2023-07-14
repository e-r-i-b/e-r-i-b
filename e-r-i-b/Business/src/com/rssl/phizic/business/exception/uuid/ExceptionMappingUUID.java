package com.rssl.phizic.business.exception.uuid;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.exception.ExceptionMapping;

/**
 * @author osminin
 * @ created 31.03.15
 * @ $Author$
 * @ $Revision$
 *
 * Уникальный идентификатор маппинга ошибок для синхронизации
 */
public class ExceptionMappingUUID
{
	private static final String SPLITTER = ";";

	private static final int UUID_SIZE = 2;
	private static final int UUID_HASH = 0;
	private static final int UUID_GROUP = 1;

	private String hash;
	private Long group;

	/**
	 * ctor
	 * @param uuid идентификатор синхронизации
	 */
	public ExceptionMappingUUID(String uuid) throws BusinessException
	{
		String[] parameters = uuid.split(SPLITTER);

		if (parameters.length < UUID_SIZE)
		{
			throw new BusinessException("Идентификатор " + uuid + " не соответствует формату: hash +  groupId");
		}

		hash = parameters[UUID_HASH];
		group = Long.parseLong(parameters[UUID_GROUP]);
	}

	/**
	 * @return хеш
	 */
	public String getHash()
	{
		return hash;
	}

	/**
	 * @return группа
	 */
	public Long getGroup()
	{
		return group;
	}

	/**
	 * Построить идентификатор для синхронизации
	 * @param mapping маппинг
	 * @return идентификатор
	 */
	public static String createUUID(ExceptionMapping mapping)
	{
		if (mapping == null)
		{
			throw new IllegalArgumentException("Маппинг ошибок не может быть null.");
		}

		return mapping.getHash() + SPLITTER + mapping.getGroup();
	}
}
