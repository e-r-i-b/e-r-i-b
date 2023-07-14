package com.rssl.phizic.jcpcryptoplugin;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

/**
 * @author bogdanov
 * @ created 24.01.14
 * @ $Author$
 * @ $Revision$
 */

public class CriptoProviderConfig extends Config
{
	/**
	 * Проперти, в которой обозначен путь к хранилищу сертификтов
	 */
	public static final String CERTIFICATE_STORE_PATH_KEY
			= "com.rssl.iccs.crypto-provider.jcp.certificate-store.path";

	/**
	 * Проперти, в которой обозначен пароль к контейнеру с закрытым ключом
	 */
	public static final String PRIVATE_PASSWORD_PROPERTY_KEY
			= "com.rssl.iccs.crypto-provider.jcp.private-keystore.password";

	/**
	 * Проперти, в которой обозначен пароль к хранилищу сертификатов
	 */
	public static final String CERTIFICATE_PASSWORD_PROPERTY_KEY
			= "com.rssl.iccs.crypto-provider.jcp.certificate-store.password";

	private static final String DEFAULT_CRYPTO_PROVIDER_KEY = "com.rssl.iccs.crypto-provider.default";

	/**
	 * Префикс имени свойства с именем алгоритма
	 */
	public static final String ALGORITHM_NAME_PREFIX = "com.rssl.iccs.crypto-provider.jcp.alg.name.";
	private String password;
	private String storePath;
	private String certificatePassword;
	private String defaultt;

	public CriptoProviderConfig(PropertyReader reader)
	{
		super(reader);
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
		password = getProperty(PRIVATE_PASSWORD_PROPERTY_KEY);
		storePath = getProperty(CERTIFICATE_STORE_PATH_KEY);
		certificatePassword = getProperty(CERTIFICATE_PASSWORD_PROPERTY_KEY);
		defaultt = getProperty(DEFAULT_CRYPTO_PROVIDER_KEY);
	}

	public String getAlgorithmName(String certId)
	{
		return getProperty(ALGORITHM_NAME_PREFIX + certId);
	}

	public String getCertificatePassword()
	{
		return certificatePassword;
	}

	public String getPassword()
	{
		return password;
	}

	public String getStorePath()
	{
		return storePath;
	}

	public String getDefaultt()
	{
		return defaultt;
	}
}
