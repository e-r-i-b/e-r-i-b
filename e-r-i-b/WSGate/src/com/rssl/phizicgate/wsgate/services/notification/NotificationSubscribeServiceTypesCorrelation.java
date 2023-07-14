package com.rssl.phizicgate.wsgate.services.notification;

import java.util.Map;
import java.util.HashMap;

/**
 * @author: Pakhomova
 * @created: 28.05.2009
 * @ $Author$
 * @ $Revision$
 */
public class NotificationSubscribeServiceTypesCorrelation
{
	public static final Map<Class, Class> types= new HashMap<Class,Class>();

	static
	{
		types.put(com.rssl.phizic.gate.bankroll.Account.class, com.rssl.phizicgate.wsgate.services.notification.generated.Account.class);
		types.put(com.rssl.phizic.common.types.Money.class, com.rssl.phizicgate.wsgate.services.notification.generated.Money.class);
		types.put(com.rssl.phizicgate.wsgate.services.types.OfficeImpl.class, com.rssl.phizicgate.wsgate.services.notification.generated.Office.class);
		types.put(com.rssl.phizicgate.wsgate.services.types.CodeImpl.class, com.rssl.phizicgate.wsgate.services.notification.generated.Code.class);
		types.put(com.rssl.phizic.business.dictionaries.currencies.CurrencyImpl.class, com.rssl.phizicgate.wsgate.services.notification.generated.Currency.class);
		types.put(com.rssl.phizicgate.wsgate.services.types.CurrencyImpl.class, com.rssl.phizicgate.wsgate.services.notification.generated.Currency.class);
		types.put(com.rssl.phizic.gate.bankroll.AccountState.class, null);
	}

}
