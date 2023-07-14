package com.rssl.phizic.gate.config.csaadmin;

import com.rssl.phizic.config.*;

/**
 * @author akrenev
 * @ created 03.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Параметры для доступа к приложению ЦСА Админ
 */

public class CSAAdminGateConfigImpl extends CSAAdminGateConfig
{
	private static final String URL_KEY = "com.rssl.phizic.csaadmin.listener.url";
	private static final String TIMEOUT_KEY = "com.rssl.phizic.csaadmin.listener.timeout";
	private static final String MULTI_BLOCK_MODE_KEY = "com.rssl.phizic.csaadmin.multiBlockMode";
	private static final String LOGIN_URL_KEY = "com.rssl.phizic.csaadmin.login.url";
	private static final String DICTIONARY_INSTANCE_KEY = "com.rssl.phizic.csaadmin.dictionary.log.instance";
	private static final String MAIL_MULTI_BLOCK_MODE_KEY = "com.rssl.phizic.csaadmin.mailMultiBlockMode";

	private String listenerUrl;
	private Integer listenerTimeout;
	private Boolean multiBlockMode;
	private String loginUrl;
	private String dictionaryInstance;
	private boolean mailMultiBlockMode;

	/**
	 * конструктор, инициализирующий настройки
	 * @param reader - ридер настроек
	 */
	public CSAAdminGateConfigImpl(PropertyReader reader)
	{
		super(reader);
	}

	public String getListenerUrl()
	{
		return listenerUrl;
	}

	public int getListenerTimeout()
	{
		return listenerTimeout;
	}

	public boolean isMultiBlockMode()
	{
		return multiBlockMode;
	}

	public String getLoginUrl()
	{
		return loginUrl;
	}

	public String getDictionaryInstance()
	{
		return dictionaryInstance;
	}

	@Override
	public boolean isMailMultiBlockMode()
	{
		return mailMultiBlockMode;
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
		listenerUrl = getProperty(URL_KEY);
		listenerTimeout = getIntProperty(TIMEOUT_KEY);
		multiBlockMode = getBoolProperty(MULTI_BLOCK_MODE_KEY);
		loginUrl = getProperty(LOGIN_URL_KEY);
		dictionaryInstance = getProperty(DICTIONARY_INSTANCE_KEY);
		mailMultiBlockMode = getBoolProperty(MAIL_MULTI_BLOCK_MODE_KEY);
	}
}
