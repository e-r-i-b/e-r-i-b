package com.rssl.phizic;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

/**
 * Конфиг для автообновляемых системных настроек
 * @author Puzikov
 * @ created 15.11.13
 * @ $Author$
 * @ $Revision$
 */

public class ApplicationAutoRefreshConfig extends Config
{
	private static final String MULTI_BLOCK_MODE_PROPERTY_KEY = "com.rssl.iccs.application.instance.multiBlockMode";
	private static final String NODE_ID_PROPERTY_KEY = "com.rssl.iccs.application.instance.node.number";
	private static final String PLATFORM_TYPE_KEY = "com.rssl.iccs.platform.type";
	private static final String DATA_MODIFICATIONS_KEY = "com.rssl.iccs.settings.asynchsearch.tracking.datamodification";
	private static final String GROUP_ID_KEY = "com.rssl.iccs.jms.group.id";
	private static final String USE_VAULT_ONLINE_KEY = "com.rssl.iccs.settings.use.vault.online";
	private static final String LOGIN_PAGE_MESSAGE_SECURE = "message.login.secure";
	public static final String MESSAGE_LOGIN_SLIDE_KEY = "message.login.slide.";

	private Boolean multiBlockModeProperty;
	private Long nodeIdProperty;
	private String platformType;
	private boolean dataModifications;
	private String groupId;
	private boolean useVaultOnline;
	private String loginPageMessageSecure;

	public ApplicationAutoRefreshConfig(PropertyReader reader)
	{
		super(reader);
	}

	protected void doRefresh() throws ConfigurationException
	{
		multiBlockModeProperty = getBoolProperty(MULTI_BLOCK_MODE_PROPERTY_KEY);
		nodeIdProperty = getLongProperty(NODE_ID_PROPERTY_KEY);
		platformType = getProperty(PLATFORM_TYPE_KEY);
		dataModifications = getBoolProperty(DATA_MODIFICATIONS_KEY);
		groupId = getProperty(GROUP_ID_KEY);
		useVaultOnline = getBoolProperty(USE_VAULT_ONLINE_KEY, false);
		loginPageMessageSecure = getProperty(LOGIN_PAGE_MESSAGE_SECURE);
	}

	/**
	 * @return Режим работы приложения - true - многоблочный, false - одноблочный
	 */
	public boolean isMultiblockMode()
	{
		return multiBlockModeProperty;
	}

	/**
	 * @return Номер текущего блока
	 */
	public Long getNodeNumber()
	{
		return nodeIdProperty;
	}

	public String getPlatformType()
	{
		return platformType;
	}

	public boolean isDataModifications()
	{
		return dataModifications;
	}

	public String getGroupId()
	{
		return groupId;
	}

	/**
	 * @return использовать ли скрипт https://vojs.group-ib.ru/vaultonline-2.js для сбора статистики на каждой странице
	 */
	public boolean isUseVaultOnline()
	{
		return useVaultOnline;
	}

	/**
	 * @return текст советов на странице входа
	 */
	public String getLoginPageMessageSecure()
	{
		return loginPageMessageSecure;
	}
}
