package com.rssl.phizic.business.ext.sbrf.payments.handlers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.CreationSourceType;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderState;
import com.rssl.phizic.business.documents.payments.AutoPaymentBase;
import com.rssl.phizic.business.documents.payments.ConvertableToGateDocument;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.documents.payments.RurPayment;

/**
 * @author krenev
 * @ created 29.06.2010
 * @ $Author$
 * @ $Revision$
 * Хендлер проверки доступности текущему пользователю поставщика, в адрес кторого совершается  платеж
 * (Поддерживает ли его ТБ переводы для даннного типа поставщика)
 */
public class CheckProviderServicePaymentHandler extends BusinessDocumentHandlerBase
{
	private static final ServiceProviderService serviceProviderService  = new ServiceProviderService();
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof ConvertableToGateDocument))
		{
			throw new DocumentException("document должен реализовывать ConvertableToGateDocument");
		}

		if (document instanceof AutoPaymentBase)
		{
			AutoPaymentBase autoPayment = (AutoPaymentBase) document;
			checkProvider(autoPayment.getReceiverInternalId(), autoPayment.getCreationSourceType());
			return;
		}

		if (document instanceof JurPayment)
		{
			JurPayment jurPayment = (JurPayment) document;
			checkProvider(jurPayment.getReceiverInternalId(), jurPayment.getCreationSourceType());
			return;
		}

		if (document instanceof RurPayment)
		{
			RurPayment rurPayment = (RurPayment) document;
			if (!rurPayment.isServiceProviderPayment())
				return;

			checkProvider(rurPayment.getReceiverInternalId(), rurPayment.getCreationSourceType());
			return;
		}

		throw new DocumentException("Oжидается JurPayment,RurPayment или AutoPayment");
	}

	private void checkProvider(Long receiverId, CreationSourceType paymentCreationSourceType) throws DocumentException, DocumentLogicException
	{
		if (receiverId == null)
		{
			return; //Для оплаты в адрес внешнего получателя проверять нечего.
		}

		try
		{
			ServiceProviderState state = serviceProviderService.findStateById(receiverId);
			if (state == null)
			{
				throw new DocumentLogicException("Оплата в адрес выбранного получателя недоступна");
			}
			if (ServiceProviderState.ACTIVE != state && ServiceProviderState.MIGRATION != state)
			{
				throw new DocumentLogicException("Оплата в адрес выбранного получателя недоступна");
			}
			if (ServiceProviderState.MIGRATION == state)
			{
				if (paymentCreationSourceType != CreationSourceType.template
						&& paymentCreationSourceType != CreationSourceType.mobiletemplate)
					throw new DocumentLogicException("Оплата в адрес выбранного получателя недоступна");
			}
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}
}
