package com.rssl.phizic.gate.cache.proxy.composers;

import com.rssl.phizic.gate.longoffer.autosubscription.AutoSubscription;

import java.io.Serializable;

/**
 * Композер для вычисления ключа в AutoSubscriptionService#getSubscriptionPayments
 * @author niculichev
 * @ created 27.03.2012
 * @ $Author$
 * @ $Revision$
 */
public class AutoSubPaymentCacheKeyComposer extends AbstractCacheKeyComposer
{
	private static final String DELEMITER = "|";

	public Serializable getKey(Object[] args, String params)
	{
		// получаем id логина клиента
		AutoSubscription autoSub = (AutoSubscription) args[0];
		// получаем идентификатор платежа
		Long id = (Long) args[getMainParamNum(params)];

		StringBuilder builder = new StringBuilder(autoSub.getExternalId());
		builder.append(DELEMITER).append(id);

		return builder.toString();
	}

}
