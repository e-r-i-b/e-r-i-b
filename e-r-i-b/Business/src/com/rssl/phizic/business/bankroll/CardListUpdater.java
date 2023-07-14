package com.rssl.phizic.business.bankroll;

import com.rssl.phizic.config.ConfigFactory;

import java.util.Calendar;

/**
 * @author Erkin
 * @ created 02.04.2013
 * @ $Author$
 * @ $Revision$
 */

class CardListUpdater extends ProductListUpdaterBase
{
	CardListUpdater(ProductChangeSet changeSet)
	{
		super(changeSet);
	}

	protected BankrollConfig getBankrollConfig()
	{
		return ConfigFactory.getConfig(BankrollRegistry.class).getCardConfig();
	}

	protected Calendar getLastUpdate()
	{
		return getChangeSet().getCardListLastUpdate();
	}

	protected void markOutdated()
	{
		getChangeSet().markCardListOutdated();
	}
}
