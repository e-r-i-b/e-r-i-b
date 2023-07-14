package com.rssl.phizic.config.profileSynchronization;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

/**
 * @author lepihina
 * @ created 23.05.14
 * $Author$
 * $Revision$
 * Конфиг для работы с настройками синхронизации профиля клиента
 */
public class ProfileSynchronizationConfig extends Config
{
	private static final String PERSON_SETTINGS_USE_REPLICATE_NAME = "com.rssl.phizic.config.person.settings.use.replicate";
	private boolean useReplicatePersonSettings = false;

	/**
	 * Любой конфиг должен реализовать данный конструктор.
	 *
	 * @param reader ридер.
	 */
	public ProfileSynchronizationConfig(PropertyReader reader)
	{
		super(reader);
	}

	protected void doRefresh() throws ConfigurationException
	{
		useReplicatePersonSettings = getBoolProperty(PERSON_SETTINGS_USE_REPLICATE_NAME);
	}

	/**
	 * @return true - включен механизм сохранения данных профиля клиента в резервное хранилище
	 */
	public boolean isUseReplicatePersonSettings()
	{
		return useReplicatePersonSettings;
	}
}
