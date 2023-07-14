package com.rssl.phizic.business.ext.sbrf.payments.forms.meta;

import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderHelper;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderShort;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.payments.PaymentsConfig;


/**
 * Кондишн для проверки билинговых платетежей в iqw, которые выполняются online
 * @author niculichev
 * @ created 06.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class OnlineIQWaveBillingPaymentCondition extends IQWaveBillingPaymentCondition
{
	public boolean accepted(BusinessDocument stateObject, StateMachineEvent stateMachineEvent) throws BusinessException, BusinessLogicException
	{
		if(!super.accepted(stateObject, stateMachineEvent))
			return false;

		if(!(stateObject instanceof JurPayment))
			return false;

		JurPayment jurPayment = (JurPayment) stateObject;
		ServiceProviderShort serviceProvider = ServiceProviderHelper.getServiceProvider(jurPayment.getReceiverInternalId());
		return ConfigFactory.getConfig(PaymentsConfig.class).getProviderCodes().contains(serviceProvider.getCode());
	}
}
