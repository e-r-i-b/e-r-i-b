package com.rssl.phizic.business.migration;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

/**
 * @author akrenev
 * @ created 07.10.2014
 * @ $Author$
 * @ $Revision$
 *
 * конфиг параметров миграции
 */

public class MigrationConfig extends Config
{
	protected static final String MIGRATION_STATE_PAGE_DELAY_PROPERTY_NAME    = "com.rssl.iccs.pages.migration.state.delay";
	protected static final String MIGRATION_THREADS_COUNT_PROPERTY_NAME       = "com.rssl.iccs.migration.threads.count";
	protected static final String MIGRATION_TIMEOUT_PROPERTY_NAME             = "com.rssl.iccs.migration.timeout";
	protected static final String SHOW_ADDITIONAL_CLIENT_STATUS_PROPERTY_NAME = "com.rssl.iccs.pages.clients.detail.status.additional.show";

	private int migrationThreadsCount;
	private long migrationStatePageDelay;
	private boolean showAdditionalClientStatus;
	private int migrationTimeout;

	/**
	 * конструктор.
	 * @param reader ридер.
	 */
	public MigrationConfig(PropertyReader reader)
	{
		super(reader);
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
		migrationThreadsCount = getIntProperty(MIGRATION_THREADS_COUNT_PROPERTY_NAME);
		migrationTimeout = getIntProperty(MIGRATION_TIMEOUT_PROPERTY_NAME);
		migrationStatePageDelay = getLongProperty(MIGRATION_STATE_PAGE_DELAY_PROPERTY_NAME);
		showAdditionalClientStatus = getBoolProperty(SHOW_ADDITIONAL_CLIENT_STATUS_PROPERTY_NAME);
	}

	/**
	 * @return количество потоков миграции
	 */
	public int getMigrationThreadsCount()
	{
		return migrationThreadsCount;
	}

	/**
	 * @return максимальное время работы процедуры миграции
	 */
	public int getMigrationTimeout()
	{
		return migrationTimeout;
	}

	/**
	 * @return интервал (в секундах) перезагрузки страницы состояния миграции
	 */
	public long getMigrationStatePageDelay()
	{
		return migrationStatePageDelay;
	}

	/**
	 * @return Необходимо ли отображать доп. статус в анкете клиента
	 */
	public boolean isShowAdditionalClientStatus()
	{
		return showAdditionalClientStatus;
	}
}
