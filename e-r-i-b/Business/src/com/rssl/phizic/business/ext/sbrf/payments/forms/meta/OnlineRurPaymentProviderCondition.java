package com.rssl.phizic.business.ext.sbrf.payments.forms.meta;

import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderHelper;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderShort;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.payments.PaymentsConfig;

/**
 * Кондишн для проверки является ли платеж яндекс-поставщику online платежем
 * @author gladishev
 * @ created 11.11.2014
 * @ $Author$
 * @ $Revision$
 */

public class OnlineRurPaymentProviderCondition extends IQWaveBillingPaymentCondition
{
	public boolean accepted(BusinessDocument stateObject, StateMachineEvent stateMachineEvent) throws BusinessException, BusinessLogicException
	{
		if(!super.accepted(stateObject, stateMachineEvent))
			return false;

		if(!(stateObject instanceof RurPayment))
			return false;

		RurPayment payment = (RurPayment) stateObject;

		if(!payment.isServiceProviderPayment())
			return false;

		ServiceProviderShort serviceProvider = ServiceProviderHelper.getServiceProvider(payment.getReceiverInternalId());
		return ConfigFactory.getConfig(PaymentsConfig.class).getProviderCodes().contains(serviceProvider.getCode());

	}
}
