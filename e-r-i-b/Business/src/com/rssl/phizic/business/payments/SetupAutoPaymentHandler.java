package com.rssl.phizic.business.payments;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderShort;
import com.rssl.phizic.business.documents.payments.AutoPaymentBase;

/**
 * @author Erkin
 * @ created 04.05.2011
 * @ $Author$
 * @ $Revision$
 */
public class SetupAutoPaymentHandler extends BusinessDocumentHandlerBase
{
	private static final ServiceProviderService providerService = new ServiceProviderService();

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof AutoPaymentBase))
			throw new DocumentException("Неверный тип платежа. Ожидается com.rssl.phizic.business.documents.payments.AutoPaymentBase");
		AutoPaymentBase autoPayment = (AutoPaymentBase) document;

		try
		{
			// 1. Сохраняем имя услуги поставщика в платеже 
			ServiceProviderShort provider = providerService.findShortProviderBySynchKey(autoPayment.getReceiverPointCode());
			if (provider != null) {
				autoPayment.setNameService(provider.getNameService());
			}
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}
}
