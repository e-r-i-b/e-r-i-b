package com.rssl.phizic.config.loyalty;

import com.rssl.phizic.config.*;

/**
 * User: Moshenko
 * Date: 15.07.2011
 * Time: 10:27:16
 * программа "Лояльность"
 */
public class LoyaltyConfig extends Config implements LoyaltyConfigMBean
{
	public static final String URL_KEY = "com.rssl.iccs.loyalty.base.url";
	public static final String CERT_KEY = "com.rssl.iccs.loyalty.pub.cert";
	public static final String PRIV_CERT_KEY = "com.rssl.iccs.loyalty.priv.cert";
	public static final String STORE_TYPE = "com.rssl.iccs.loyalty.store.type";
	public static final String STORE_PATH = "com.rssl.iccs.loyalty.store.path";
	public static final String STORE_PASSWORD = "com.rssl.iccs.loyalty.store.password";

	public LoyaltyConfig(PropertyReader reader)
	{
		super(reader);
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
	}

	/**
	 * Получить URL
	 * @return
	 */
	public  String getLoyaltyUrl()
	{
		return getProperty(URL_KEY);
	}

	/**
	 * Получить alias сертификата
	 * @return
	 */
	public String getLoyaltyPubCert()
	{
		return getProperty(CERT_KEY);
	}
	/**
	 * Получить alias сертификата
	 * @return
	 */
	public String getLoyaltyPrivCert()
	{
		return  getProperty(PRIV_CERT_KEY);
	}

    /**
	 * Получить тип хранилища
	 * @return
	 */
	public String getStoreType()
	{
		return  getProperty(STORE_TYPE);
	}

	public String getStorePath()
	{
		return  getProperty(STORE_PATH);
	}

	public String getStorePassword()
	{
		return  getProperty(STORE_PASSWORD);
	}
}
