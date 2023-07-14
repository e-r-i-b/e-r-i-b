package com.rssl.phizic.business.bankroll;

import com.rssl.phizic.config.ConfigFactory;

import java.util.Calendar;

/**
 * @author Erkin
 * @ created 02.04.2013
 * @ $Author$
 * @ $Revision$
 */

class LoanListUpdater extends ProductListUpdaterBase
{
	LoanListUpdater(ProductChangeSet changeSet)
	{
		super(changeSet);
	}

	protected BankrollConfig getBankrollConfig()
	{
		return ConfigFactory.getConfig(BankrollRegistry.class).getLoanConfig();
	}

	protected Calendar getLastUpdate()
	{
		return getChangeSet().getLoanListLastUpdate();
	}

	protected void markOutdated()
	{
		getChangeSet().markLoanListOutdated();
	}
}
