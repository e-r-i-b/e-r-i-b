package com.rssl.phizic.business.migration;

/**
 * @author akrenev
 * @ created 30.09.2014
 * @ $Author$
 * @ $Revision$
 *
 * Код результата миграции
 */

public enum MigrationResult
{
	MIGRATED(0),
	ERROR(1);

	private final long code;

	private MigrationResult(long code)
	{
		this.code = code;
	}

	/**
	 * получить результат миграции по коду ошибки
	 * @param code код ошибки
	 * @return результат миграции
	 */
	public static MigrationResult getFromCode(long code)
	{
		for (MigrationResult result : MigrationResult.values())
		{
			if (code == result.code)
				return result;
		}
		throw new IllegalArgumentException("Неизвестный код ошибки миграции code = " + code);
	}
}
