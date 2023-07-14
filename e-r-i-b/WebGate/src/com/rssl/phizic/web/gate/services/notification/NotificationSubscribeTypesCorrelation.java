package com.rssl.phizic.web.gate.services.notification;

import java.util.Map;
import java.util.HashMap;

/**
 * @author: Pakhomova
 * @created: 28.05.2009
 * @ $Author$
 * @ $Revision$
 */
public class NotificationSubscribeTypesCorrelation
{
	public static final Map<Class, Class> types= new HashMap<Class,Class>();

	static
	{
		types.put(com.rssl.phizic.web.gate.services.notification.generated.Account.class, com.rssl.phizic.gate.bankroll.Account.class);
		types.put(com.rssl.phizic.web.gate.services.notification.generated.Currency.class, com.rssl.phizic.web.gate.services.types.CurrencyImpl.class);
		types.put(com.rssl.phizic.web.gate.services.notification.generated.Money.class, com.rssl.phizic.common.types.Money.class);
		types.put(com.rssl.phizic.web.gate.services.notification.generated.Office.class, com.rssl.phizgate.common.services.types.OfficeImpl.class);
		types.put(com.rssl.phizic.web.gate.services.notification.generated.Code.class, com.rssl.phizgate.common.services.types.CodeImpl.class);
		types.put(com.rssl.phizic.gate.bankroll.AccountState.class, null);
	}
}
