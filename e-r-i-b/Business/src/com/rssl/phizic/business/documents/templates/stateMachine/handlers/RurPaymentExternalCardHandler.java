package com.rssl.phizic.business.documents.templates.stateMachine.handlers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentFieldsIndicateException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.templates.Constants;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.impl.IndividualTransferTemplate;
import com.rssl.phizic.business.payments.PaymentsConfig;
import com.rssl.phizic.config.ConfigFactory;

import java.util.Collections;

/**
 * @author akrenev
 * @ created 04.04.2014
 * @ $Author$
 * @ $Revision$
 *
 * ’ендлер провер€ющий возможность перевести на карту другого банка
 */

public class RurPaymentExternalCardHandler extends TemplateHandlerBase<TemplateDocument>
{
	private static final String VISA_MONEY_SEND_SERVICE_NAME = "VisaMoneySendService";
	private static final String MASTERCARD_MONEY_SEND_SERVICE_NAME = "MastercardMoneySendService";

	public void process(TemplateDocument document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof IndividualTransferTemplate))
			throw new DocumentException("Ќеверный тип платежа - ожидаетс€ IndividualTransferTemplate");

		IndividualTransferTemplate template = (IndividualTransferTemplate) document;
		boolean isVisaPayment = Constants.VISA_CARD_RECEIVER_SUB_TYPE_ATTRIBUTE_NAME.equals(template.getReceiverSubType());
		boolean isMasterCardPayment = Constants.MASTERCARD_CARD_RECEIVER_SUB_TYPE_ATTRIBUTE_NAME.equals(template.getReceiverSubType());

		if ((!isVisaPayment && !isMasterCardPayment) || template.isOurBankExternalCard())
			return;

		String serviceName = isVisaPayment ? VISA_MONEY_SEND_SERVICE_NAME : MASTERCARD_MONEY_SEND_SERVICE_NAME;
		try
		{
			if (!DocumentHelper.isAvailableService(template.getOwner().getLogin(), serviceName))
				throw new DocumentFieldsIndicateException(Collections.<String, String>emptyMap(), false, isVisaPayment ? ConfigFactory.getConfig(PaymentsConfig.class).getVisaErrorMessage() : ConfigFactory.getConfig(PaymentsConfig.class).getMastercardErrorMessage());
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}
}
