package com.rssl.phizic.business.dictionaries.providers;

/**
 * @author khudyakov
 * @ created 24.12.2009
 * @ $Author$
 * @ $Revision$
 */

public enum ServiceProviderState
{
	NOT_ACTIVE(0),      //неактивный
	ACTIVE(1),          //активный
	DELETED(2),         //удален
	ARCHIVE(3),         //данный поставщик при репликации не был описан в загружаемом файле, поэтому,
						//чтобы он не был удален и отличить от остальных, выставляем ему отдельный статус
	MIGRATION(4);       //поставщиков iqwave заблокирован для новых платежей, cвоего рода полуактивность

	private int value;

	ServiceProviderState(int value)
	{
		this.value = value;
	}

	/**
	 * Получить тип статуса поставщика
	 * @param value значение
	 * @return статус
	 */
	public static ServiceProviderState fromValue(int value)
	{
		if (ACTIVE.value == value)
			return ACTIVE;
		if (NOT_ACTIVE.value == value)
			return NOT_ACTIVE;
		if (DELETED.value == value)
			return DELETED;
		if (ARCHIVE.value == value)
			return ARCHIVE;
		if (MIGRATION.value == value)
			return MIGRATION;

		throw new IllegalArgumentException("Неизвестный тип поля [" + value + "]");
	}

	/**
	 * @return значение
	 */
	public int getValue()
	{
		return value;
	}
}
