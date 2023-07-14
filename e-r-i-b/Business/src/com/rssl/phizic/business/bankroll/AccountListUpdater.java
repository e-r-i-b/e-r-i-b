package com.rssl.phizic.business.bankroll;

import com.rssl.phizic.config.ConfigFactory;

import java.util.Calendar;

/**
 * @author Erkin
 * @ created 02.04.2013
 * @ $Author$
 * @ $Revision$
 */

class AccountListUpdater extends ProductListUpdaterBase
{
	AccountListUpdater(ProductChangeSet changeSet)
	{
		super(changeSet);
	}

	protected BankrollConfig getBankrollConfig()
	{
		return ConfigFactory.getConfig(BankrollRegistry.class).getAccountConfig();
	}

	protected Calendar getLastUpdate()
	{
		return getChangeSet().getAccountListLastUpdate();
	}

	protected void markOutdated()
	{
		getChangeSet().markAccountListOutdated();
	}
}
