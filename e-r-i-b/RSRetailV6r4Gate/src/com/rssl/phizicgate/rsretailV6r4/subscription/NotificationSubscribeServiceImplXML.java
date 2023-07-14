package com.rssl.phizicgate.rsretailV6r4.subscription;

import com.rssl.phizgate.common.messaging.retail.RetailMessagingConfig;
import com.rssl.phizgate.common.messaging.retail.RetailMessagingConfigImpl;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizicgate.rsretailV6r4.data.RSRetailV6r4Executor;
import com.rssl.phizicgate.rsretailV6r4.dictionaries.currencies.CurrencyServiceImpl;
import com.rssl.phizicgate.rsretailV6r4.money.CurrencyHelper;
import org.w3c.dom.Document;
import org.hibernate.Session;
import org.hibernate.Query;

import java.util.Set;
import java.util.HashSet;

/**
 * @author Omeliyanchuk
 * @ created 20.01.2010
 * @ $Author$
 * @ $Revision$
 */

public class NotificationSubscribeServiceImplXML extends NotificationSubscribeServiceImpl
{
	public NotificationSubscribeServiceImplXML(GateFactory factory)
	{
		super(factory);
	}

	public void subscribeAccount(final Account account) throws GateException, GateLogicException
	{
		Document document = null;

		WebBankServiceFacade service = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		GateMessage message = service.createRequest("subscribeAccount_q");
		message.addParameter("accountReferenc", account.getId());

		document = service.sendOnlineMessage(message, null);
		//ничего не ждем поэтому ничего не зовем
	}
}
