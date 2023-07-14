package com.rssl.phizic.web.atm.security;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.StaticATMPersonData;
import com.rssl.phizic.business.clients.ClientResourceHelper;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.security.auth.ClientAthenticationCompleteAction;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.atm.AtmApiConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.ima.IMAccount;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;

import java.util.List;

/**
 * @author lukina
 * @ created 14.09.2011
 * @ $Author$
 * @ $Revision$
 */

public class ClientAthenticationCompleteATMAction extends ClientAthenticationCompleteAction
{
	private static final Log LOG = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	protected void updateContext(ActivePerson person)
	{
		StaticATMPersonData personData = new StaticATMPersonData(person);
		PersonContext.getPersonDataProvider().setPersonData(personData);

		try
		{
			List<Class> clientProductClassList = ClientResourceHelper.getClientsProducts(person, true).getFirst();
			personData.setNeedLoadAccounts(clientProductClassList.contains(Account.class));
			personData.setNeedLoadIMAccounts(clientProductClassList.contains(IMAccount.class));
			personData.setNeedLoadLoans(personData.isNeedLoadLoans() || clientProductClassList.contains(Loan.class));
			personData.setWayCardsReloaded(!ConfigFactory.getConfig(AtmApiConfig.class).getOnlyWayCards());
		}
		catch (BusinessException e)
		{
			LOG.error("Ошибка получения списка доступных клиенту продуктов", e);
		}
	}
}
