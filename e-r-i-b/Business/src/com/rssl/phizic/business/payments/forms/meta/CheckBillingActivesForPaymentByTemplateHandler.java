package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.OperationMessagesConfig;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.business.documents.templates.TemplateHelper;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.payments.systems.AbstractPaymentSystemPayment;

/**
 * ѕроверка активности оплаты по шаблону в адрес билинговой системы, дл€ которой выставлен статус дл€ шаблонов ќтключены.
 *
 * @author bogdanov
 * @ created 28.07.14
 * @ $Author$
 * @ $Revision$
 */

public class CheckBillingActivesForPaymentByTemplateHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		if (!(document instanceof JurPayment))
			return;

		JurPayment payment = (JurPayment) document;
		if (payment.isByTemplate() && !DocumentHelper.isBillingExternalPaymentTemplateSupported(payment))
			throw new DocumentLogicException(
					ConfigFactory.getConfig(OperationMessagesConfig.class).getOperationMessage(OperationMessagesConfig.NOT_ACTIVE_PROVIDER_MESSAGE)
			);
	}
}
