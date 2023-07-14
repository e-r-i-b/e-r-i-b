package com.rssl.phizic.business.migration;

/**
 * @author akrenev
 * @ created 25.09.2014
 * @ $Author$
 * @ $Revision$
 *
 * Состояние имграции
 */

public enum MigrationState
{
	INITIALIZE, // инициализация параметров
	WAIT,       // ожидание запуска процесса миграции
	PROCESS,    // процесс миграции запущен
	WAIT_STOP,  // ожидание остановки процесса миграции
	STOP        // процесс миграции остановлен
}
