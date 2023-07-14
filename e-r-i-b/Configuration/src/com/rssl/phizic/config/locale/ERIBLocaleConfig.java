package com.rssl.phizic.config.locale;

import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

/**
 * @author komarov
 * @ created 10.09.2014
 * @ $Author$
 * @ $Revision$
 * Конфиг для механизма многоязычности
 */
public class ERIBLocaleConfig extends Config
{

	private static final String PROPERTY_IS_USE_ERIB_MESSAGES_MODE  = "com.rssl.iccs.locale.PROPERTY_IS_USE_ERIB_MESSAGES_MODE";
	private static final String PROPERTY_DEFAULT_LOCALE_ID  = "com.rssl.iccs.locale.PROPERTY_DEFAULT_LOCALE_ID";
	private static final String PROPERTY_AUTO_REFRESH_TIME = "com.rssl.iccs.locale.PROPERTY_AUTO_REFRESH_TIME";
	private static final String USE_ATM_MESSAGES_FROM_DB = "com.rssl.iccs.locale.USE_ATM_MESSAGES_FROM_DB";
	private static final String USE_LOCALED_EXTERNAL_SYSTEM_ERROR_MESSAGES_PROPERTY_KEY = "com.rssl.iccs.locale.USE_LOCALED_EXTERNAL_SYSTEM_ERROR_MESSAGES";
	private static final String DB_INSTANCE_NAME = "com.rssl.iccs.locale.db.instance.name";

	private boolean isUseERIBMessagesMode;
	private String defaultLocaleId;
	private Long autoRefreshTime;
	private boolean useAtmMessagesFromDB;
	private boolean useLocaledExternalSystemErrorMessages;
	private String dbInstanceName;

	/**
	 * @param reader ридер
	 */
	public ERIBLocaleConfig(PropertyReader reader)
	{
		super(reader);
	}


	@Override
	protected void doRefresh() throws ConfigurationException
	{
		isUseERIBMessagesMode = getBoolProperty(PROPERTY_IS_USE_ERIB_MESSAGES_MODE);
		defaultLocaleId = getProperty(PROPERTY_DEFAULT_LOCALE_ID);
		autoRefreshTime = getLongProperty(PROPERTY_AUTO_REFRESH_TIME);
		useAtmMessagesFromDB = getBoolProperty(USE_ATM_MESSAGES_FROM_DB);
		useLocaledExternalSystemErrorMessages = getBoolProperty(USE_LOCALED_EXTERNAL_SYSTEM_ERROR_MESSAGES_PROPERTY_KEY);
		dbInstanceName = getProperty(DB_INSTANCE_NAME);
	}

	/**
	 * @param application приложение в рамках которого необходимо получить настройку
	 * @param localeId локаль для которой получаем данную настройку
	 * @return использовать ли новый механизм получения текстовок
	 */
	public boolean isUseERIBMessagesMode(Application application, String localeId)
	{
		if(!defaultLocaleId.equals(localeId))
			return true;
		if(ApplicationInfo.isMobileApi(application) && isUseERIBMessagesMode)
			return true;
		if(application == Application.atm && useAtmMessagesFromDB)
			return true;
		return false;
	}

	/**
	 * @return идентификатор дефолтной локали
	 */
	public String getDefaultLocaleId()
	{
		return defaultLocaleId;
	}

	/**
	 * @return период автообновления текстовок
	 */
	public Long getAutoRefreshTime()
	{
		return autoRefreshTime;
	}

	/**
	 * @return необходимо ли использовать локалкзависимые сообщения ошибок внешних систем
	 */
	public boolean isUseLocaledExternalSystemErrorMessages()
	{
		return useLocaledExternalSystemErrorMessages;
	}

	/**
	 * @return имя инстанса БД, из которой берем текстовку для статических ресурсов
	 */
	public String getDbInstanceName()
	{
		return dbInstanceName;
	}
}
