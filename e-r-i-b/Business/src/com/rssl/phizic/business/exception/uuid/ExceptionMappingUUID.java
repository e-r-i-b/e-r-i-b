package com.rssl.phizic.business.exception.uuid;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.exception.ExceptionMapping;

/**
 * @author osminin
 * @ created 31.03.15
 * @ $Author$
 * @ $Revision$
 *
 * ���������� ������������� �������� ������ ��� �������������
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
	 * @param uuid ������������� �������������
	 */
	public ExceptionMappingUUID(String uuid) throws BusinessException
	{
		String[] parameters = uuid.split(SPLITTER);

		if (parameters.length < UUID_SIZE)
		{
			throw new BusinessException("������������� " + uuid + " �� ������������� �������: hash +  groupId");
		}

		hash = parameters[UUID_HASH];
		group = Long.parseLong(parameters[UUID_GROUP]);
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
	 * ��������� ������������� ��� �������������
	 * @param mapping �������
	 * @return �������������
	 */
	public static String createUUID(ExceptionMapping mapping)
	{
		if (mapping == null)
		{
			throw new IllegalArgumentException("������� ������ �� ����� ���� null.");
		}

		return mapping.getHash() + SPLITTER + mapping.getGroup();
	}
}
