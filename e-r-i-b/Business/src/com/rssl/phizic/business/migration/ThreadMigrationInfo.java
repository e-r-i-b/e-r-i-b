package com.rssl.phizic.business.migration;

/**
 * @author akrenev
 * @ created 26.09.2014
 * @ $Author$
 * @ $Revision$
 *
 * Информация потока о миграции
 */

public class ThreadMigrationInfo extends MigrationInfo
{
	private boolean needStop;

	/**
	 * конструктор
	 */
	public ThreadMigrationInfo(){}

	/**
	 * @return флаг остановки
	 */
	public boolean isNeedStop()
	{
		return needStop;
	}
}
