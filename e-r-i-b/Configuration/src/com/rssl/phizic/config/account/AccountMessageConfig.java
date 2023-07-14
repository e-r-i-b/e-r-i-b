package com.rssl.phizic.config.account;

import com.rssl.phizic.config.*;

/**
 * Конфиг для насройки сообщений для вклада
 *
 * @ author: sergunin
 * @ created: 27.12.13
 * @ $Author$
 * @ $Revision$
 */
public class AccountMessageConfig extends Config
{
	public static final String ACCOUNT_MAX_BALANCE_MESSAGE = "com.rssl.iccs.account.max.balance.message";
	public static final String ACCOUNT_MAX_BALANCE_VALIDATOR_MESSAGE = "com.rssl.iccs.account.max.balance.validator.message";
	public static final String ACCOUNT_CLOSE_DATE_DISCLAIM_MESSAGE = "com.rssl.iccs.account.сlose.date.disclaim.message";

	private String accountMaxBallanceMessage;
	private String accountMaxBallanceValidatorMessage;
	private String accountCloseDateDisclaimMessage;


	public AccountMessageConfig(PropertyReader reader)
	{
		super(reader);
	}

	protected void doRefresh() throws ConfigurationException
	{
		accountMaxBallanceMessage = getProperty(ACCOUNT_MAX_BALANCE_MESSAGE);
		accountMaxBallanceValidatorMessage = getProperty(ACCOUNT_MAX_BALANCE_VALIDATOR_MESSAGE);
        accountCloseDateDisclaimMessage = getProperty(ACCOUNT_CLOSE_DATE_DISCLAIM_MESSAGE);
	}

	public String getAccountMaxBallanceMessage() {
		return accountMaxBallanceMessage;
	}

	public String getAccountMaxBallanceValidatorMessage()
	{
		return accountMaxBallanceValidatorMessage;
	}

    public String getAccountCloseDateDisclaimMessage()
    {
        return accountCloseDateDisclaimMessage;
	}
}
