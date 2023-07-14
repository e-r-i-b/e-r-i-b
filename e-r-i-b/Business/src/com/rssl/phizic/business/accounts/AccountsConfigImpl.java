package com.rssl.phizic.business.accounts;

import com.rssl.phizic.config.*;

/**
 @author Pankin
 @ created 23.11.2010
 @ $Author$
 @ $Revision$
 */
public class AccountsConfigImpl extends AccountsConfig
{
	private String accountNumberMask;
	private String accountNumberRegexp;

	public AccountsConfigImpl(PropertyReader propertyReader)
	{
		super(propertyReader);
	}

	public String getAccountNumberMask()
	{
		return accountNumberMask;
	}

	public String getAccountNumberRegexp()
	{
		return accountNumberRegexp;
	}

	public void doRefresh() throws ConfigurationException
	{
		accountNumberMask = getProperty(AccountsConfig.ACCOUNT_NUMBER_MASK);
		accountNumberRegexp = getProperty(AccountsConfig.ACCOUNT_NUMBER_REGEXP);
	}
}
