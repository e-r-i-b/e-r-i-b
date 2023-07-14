package com.rssl.phizic.business.payments.forms.meta.conditions;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderHelper;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderShort;
import com.rssl.phizic.business.documents.GateExecutableDocument;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.regions.RegionHelper;

/**
 * @author Balovtsev
 * @version 09.10.13 9:09
 */
public class BarCodeJurPaymentCondition extends BillingPaymentRequisitesSufficientCondition
{
	@Override
	public boolean accepted(StateObject stateObject, StateMachineEvent stateMachineEvent) throws BusinessException, BusinessLogicException
	{
		if (!(stateObject instanceof GateExecutableDocument))
		{
			throw new BusinessException("Неверный тип объекта "+stateObject.getClass().getName());
		}

		if (stateObject instanceof JurPayment)
		{
			ServiceProviderShort provider = ServiceProviderHelper.getServiceProvider(((JurPayment) stateObject).getReceiverInternalId());
			ServiceProviderShort regionProvider = RegionHelper.getBarCodeProvider();
			return provider != null && regionProvider != null && regionProvider.getSynchKey().equals(provider.getSynchKey());
		}

		return false;
	}
}
