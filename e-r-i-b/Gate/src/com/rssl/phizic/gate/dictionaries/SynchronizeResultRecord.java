package com.rssl.phizic.gate.dictionaries;

import java.util.Collections;
import java.util.List;

/**
 * Результат синхронизации записи справочника.
 * @author Pankin
 * @ created 26.05.2011
 * @ $Author$
 * @ $Revision$
 */

public class SynchronizeResultRecord
{
	private Comparable synchKey;
	private SynchronizeResultStatus status;
	private List<String> errorDescriptions;

	/**
	 * Получить ключ записи
	 * @return ключ записи
	 */
	public Comparable getSynchKey()
	{
		return synchKey;
	}

	/**
	 * Установить ключ записи
	 * @param synchKey ключ записи
	 */
	public void setSynchKey(Comparable synchKey)
	{
		this.synchKey = synchKey;
	}

	/**
	 * Получить статус результата синхронизации для записи
	 * @return статус результата синхронизации
	 */
	public SynchronizeResultStatus getStatus()
	{
		return status;
	}

	/**
	 * Установить статус результата синхронизации записи
	 * @param status статус результата синхронизации
	 */
	public void setStatus(SynchronizeResultStatus status)
	{
		this.status = status;
	}

	/**
	 * Получить список описаний ошибок по результатам синхронизации
	 * @return список описаний ошибок
	 */
	public List<String> getErrorDescriptions()
	{
		return errorDescriptions == null ? null : Collections.unmodifiableList(errorDescriptions);
	}

	/**
	 * Установить список ошибок по результатам синхронизации
	 * @param errorDescriptions список ошибок
	 */
	@SuppressWarnings({"AssignmentToCollectionOrArrayFieldFromParameter"})
	public void setErrorDescriptions(List<String> errorDescriptions)
	{
		this.errorDescriptions = errorDescriptions;
	}
}
