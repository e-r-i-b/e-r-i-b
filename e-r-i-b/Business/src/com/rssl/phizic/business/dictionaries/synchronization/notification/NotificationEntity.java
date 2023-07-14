package com.rssl.phizic.business.dictionaries.synchronization.notification;

import java.io.Serializable;

/**
 * @author akrenev
 * @ created 04.02.2014
 * @ $Author$
 * @ $Revision$
 *
 * �������� � ������� ������������ ��� ������ �������� �������������
 */

public class NotificationEntity implements Serializable
{
	private final Class dictionaryRecordClass;
	private final SynchronizationMode mode;

	/**
	 * �����������
	 * @param dictionaryRecordClass ����� �����������
	 * @param mode ����������� ����� �������������
	 */
	public NotificationEntity(Class dictionaryRecordClass, SynchronizationMode mode)
	{
		this.dictionaryRecordClass = dictionaryRecordClass;
		this.mode = mode;
	}

	/**
	 * @return ����� �����������
	 */
	public Class getDictionaryRecordClass()
	{
		return dictionaryRecordClass;
	}

	/**
	 * @return ����������� ����� �������������
	 */
	public SynchronizationMode getMode()
	{
		return mode;
	}
}
