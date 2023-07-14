package com.rssl.phizic.business.payments.forms.meta.handlers.basket.subscription;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.documents.payments.AutoSubType;
import com.rssl.phizic.business.documents.payments.CreateInvoiceSubscriptionPayment;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.gate.payments.basket.CreateInvoiceSubscription;
import org.apache.commons.collections.MapUtils;

import java.util.Map;

/**
 * Хендлер проверяеющий поддержку биллингом создания автопоиска(автоподписки по высталенному счету)
 * @author niculichev
 * @ created 23.08.14
 * @ $Author$
 * @ $Revision$
 */
public class CheckBillingSupportInvoiceSubscriptionHandler extends BusinessDocumentHandlerBase
{
	private static final String UNSUPPORT_SUBSCRIPTION_MESSAGE = "Поставщик услуг не поддерживает создание автопоиска счетов.";
	private static final ServiceProviderService serviceProviderService = new ServiceProviderService();

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		if(!(document instanceof CreateInvoiceSubscriptionPayment))
			throw new DocumentException("Ожидался CreateInvoiceSubscriptionPayment. Пришел : " + document.getClass().getName());

		if(!checkSupportInvoiceAutoPay((CreateInvoiceSubscriptionPayment) document))
			throw new DocumentLogicException(UNSUPPORT_SUBSCRIPTION_MESSAGE);
	}

	private boolean checkSupportInvoiceAutoPay(JurPayment payment) throws DocumentException
	{
		Map<AutoSubType,Object> autoSubSupport = payment.getAutoPaymentScheme();
		if(MapUtils.isNotEmpty(autoSubSupport))
			return autoSubSupport.get(AutoSubType.INVOICE) != null;

		try
		{
			return serviceProviderService.isSupportAutoPayType(payment.getReceiverInternalId(), AutoSubType.INVOICE);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}
}
