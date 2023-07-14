package com.rssl.phizic.business.ext.sbrf.dictionaries;

import com.rssl.phizic.config.*;

/**
 * @author Mescheryakova
 * @ created 15.02.2012
 * @ $Author$
 * @ $Revision$
 *
 * Настройка места хранения показа минивыписок на главной: сессия или БД
 */

public class ShowOperationsSettings extends Config
{
	private static final String SHOW_OPERATION = "com.rssl.iccs.show.operations.data.base";

	/**
	 * Любой конфиг должен реализовать данный конструктор.
	 *
	 * @param reader ридер.
	 */
	public ShowOperationsSettings(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * Храним в БД или в сессии
	 * @return true - в БД, false - сессии
	 */
	public static boolean isDataBaseSetting()
	{
		return ConfigFactory.getConfig(ShowOperationsSettings.class).isDataBaseSetting0();
	}

	private boolean isDataBaseSetting0()
	{
		return getBoolProperty(SHOW_OPERATION);
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
	}
}
