package com.rssl.phizic;

import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

/**
 * @author Egorova
 * @ created 27.03.2008
 * @ $Author$
 * @ $Revision$
 */
public class BankContext extends BankContextConfig
{
	private String bankPhone;
	private String bankAddress;
	private String systemName;
	private String systemUrl;
	private String webUrl;
	private String nationalCurrencyCode;

	public BankContext(PropertyReader propertyReader)
	{
		super(propertyReader);
	}

	public String getNationalCurrencyCode()
	{
		return nationalCurrencyCode;
	}

	public String getBankBIC()
	{
		return getProperty(BankContextConfig.BANK_BIC);
	}

	public String getBankCorAcc()
	{
		return getProperty(BankContextConfig.BANK_CORACC);
	}

	public String getBankName()
	{
		return getProperty(BankContextConfig.BANK_NAME);
	}

	public String getBankPhone()
	{
		return bankPhone;
	}

	public String getBankAddress()
	{
		return bankAddress;
	}

	public String getSystemName()
	{
		return systemName;
	}

	public String getSystemUrl()
	{
		return systemUrl;
	}

	public String getWebUrl()
	{
		return webUrl;
	}

	public void doRefresh() throws ConfigurationException
	{
		bankPhone = getProperty(BankContextConfig.BANK_PHONE);
		bankAddress = getProperty(BankContextConfig.BANK_ADDRESS);
		systemName = getProperty(BankContextConfig.SYSTEM_NAME);
		systemUrl = getProperty(BankContextConfig.SYSTEM_URL);
		webUrl = getProperty(BankContextConfig.WEB_URL);
		nationalCurrencyCode = getProperty(BankContextConfig.NATIONAL_CURRENCY_CODE);
	}
}
