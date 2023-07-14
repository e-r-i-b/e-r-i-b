package com.rssl.phizic.business.exception.uuid;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.exception.locale.ExceptionMappingResources;

/**
 * @author osminin
 * @ created 31.03.15
 * @ $Author$
 * @ $Revision$
 *
 * Уникальный идентификатор для синхронизации связи сообщений об ошибке с многоязычностью
 */
public class ExceptionMappingResourceUUID
{
	private static final String SPLITTER = ";";

	private static final int UUID_SIZE = 3;
	private static final int UUID_HASH = 0;
	private static final int UUID_GROUP = 1;
	private static final int UUID_LOCALE_ID = 2;

	private String hash;
	private String localeId;
	private Long group;

	/**
	 * ctor
	 * @param uuid идентификатор синхронизации
	 */
	public ExceptionMappingResourceUUID(String uuid) throws BusinessException
	{
		String[] parameters = uuid.split(SPLITTER);

		if (parameters.length < UUID_SIZE)
		{
			throw new BusinessException("Идентификатор " + uuid + " не соответствует формату: hash + groupId + localeId");
		}

		hash = parameters[UUID_HASH];
		group = Long.parseLong(parameters[UUID_GROUP]);
		localeId = parameters[UUID_LOCALE_ID];
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
	 * @return идентифкатор локали
	 */
	public String getLocaleId()
	{
		return localeId;
	}

	/**
	 * Построить идентификатор для синхронизации
	 * @param resource ресурс
	 * @return идентификатор
	 */
	public static String createUUID(ExceptionMappingResources resource)
	{
		if (resource == null)
		{
			throw new IllegalArgumentException("Ресурс не может быть null.");
		}

		StringBuilder builder = new StringBuilder();
		builder.append(resource.getHash())
				.append(SPLITTER)
				.append(resource.getGroup())
				.append(SPLITTER)
				.append(resource.getLocaleId());

		return builder.toString();
	}
}
