package com.rssl.phizic.business.exception.uuid;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.exception.locale.ExceptionMappingResources;

/**
 * @author osminin
 * @ created 31.03.15
 * @ $Author$
 * @ $Revision$
 *
 * ���������� ������������� ��� ������������� ����� ��������� �� ������ � ���������������
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
	 * @param uuid ������������� �������������
	 */
	public ExceptionMappingResourceUUID(String uuid) throws BusinessException
	{
		String[] parameters = uuid.split(SPLITTER);

		if (parameters.length < UUID_SIZE)
		{
			throw new BusinessException("������������� " + uuid + " �� ������������� �������: hash + groupId + localeId");
		}

		hash = parameters[UUID_HASH];
		group = Long.parseLong(parameters[UUID_GROUP]);
		localeId = parameters[UUID_LOCALE_ID];
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

	/**
	 * @return ������������ ������
	 */
	public String getLocaleId()
	{
		return localeId;
	}

	/**
	 * ��������� ������������� ��� �������������
	 * @param resource ������
	 * @return �������������
	 */
	public static String createUUID(ExceptionMappingResources resource)
	{
		if (resource == null)
		{
			throw new IllegalArgumentException("������ �� ����� ���� null.");
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
