package com.rssl.phizic.business.dictionaries.synchronization.notification;

import java.io.Serializable;

/**
 * @author akrenev
 * @ created 04.02.2014
 * @ $Author$
 * @ $Revision$
 *
 * Сущность с данными необходимыми для начала процесса синхронизации
 */

public class NotificationEntity implements Serializable
{
	private final Class dictionaryRecordClass;
	private final SynchronizationMode mode;

	/**
	 * конструктор
	 * @param dictionaryRecordClass класс справочника
	 * @param mode необходимый режим синхронизации
	 */
	public NotificationEntity(Class dictionaryRecordClass, SynchronizationMode mode)
	{
		this.dictionaryRecordClass = dictionaryRecordClass;
		this.mode = mode;
	}

	/**
	 * @return класс справочника
	 */
	public Class getDictionaryRecordClass()
	{
		return dictionaryRecordClass;
	}

	/**
	 * @return необходимый режим синхронизации
	 */
	public SynchronizationMode getMode()
	{
		return mode;
	}
}
