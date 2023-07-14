package com.rssl.phizic.gate.cache.proxy.composers;

import com.rssl.phizic.gate.longoffer.autosubscription.AutoSubscription;

import java.io.Serializable;

/**
 * �������� ��� ���������� ����� � AutoSubscriptionService#getSubscriptionPayments
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
		// �������� id ������ �������
		AutoSubscription autoSub = (AutoSubscription) args[0];
		// �������� ������������� �������
		Long id = (Long) args[getMainParamNum(params)];

		StringBuilder builder = new StringBuilder(autoSub.getExternalId());
		builder.append(DELEMITER).append(id);

		return builder.toString();
	}

}
