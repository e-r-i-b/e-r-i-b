package com.rssl.phizic.config.debug;

import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

/**
 *
 * Конфиг для считывания настроек включения/отключения логирования. Только для отладки.
 * Не учитывается доступность по уровням логирования.
 *
 * @ author: Gololobov
 * @ created: 08.07.2013
 * @ $Author$
 * @ $Revision$
 */

public class DebugRefreshConfig extends Config
{
	//Логирование изменения курсов валют(true - включено/false - отключено)
	private static final String RATE_LOGING_ENABLED = "com.rssl.iccs.rate.loging.enabled";

	private boolean needRateLoging; //признак необходимости логирования изменений курсов валют

	public DebugRefreshConfig(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * Обновление данных
	 * @throws ConfigurationException
	 */
	protected void doRefresh() throws ConfigurationException
	{
		needRateLoging = getBoolProperty(RATE_LOGING_ENABLED);
	}

	/**
	 * @return - нужно ли логировать изменение курсов валют
	 */
	public boolean isNeedRateLoging()
	{
		return needRateLoging;
	}
}
