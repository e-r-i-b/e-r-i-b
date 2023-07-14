package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.AbstractPaymentDocument;
import com.rssl.phizic.business.ext.sbrf.payments.forms.meta.ESBBillingPaymentCondition;
import com.rssl.phizic.business.resources.external.CardLink;

/**
 * Хендлер на запрет платежа по дополнительной карте шинному биллингу, если карта 99-WAY.
 *
 * @author bogdanov
 * @ created 26.10.2012
 * @ $Author$
 * @ $Revision$
 */

public class DisallowAdditionalCardPaymentHandler extends BusinessDocumentHandlerBase
{
	private static final String ERROR_MESSAGE = "Данная операция не может быть совершена по дополнительной карте. Пожалуйста, выберите основную карту в качестве источника списания средств.";

	private static final ESBBillingPaymentCondition isESBpayment = new ESBBillingPaymentCondition();

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		try
		{
			//Проверка должна выполняться только для шинного биллинга.
			if (!isESBpayment.accepted(document, stateMachineEvent))
				return;
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
		catch (BusinessLogicException e)
		{
			throw new DocumentLogicException(e);
		}

		if (!(document instanceof AbstractPaymentDocument))
			throw new DocumentException("Ожидается " + AbstractPaymentDocument.class.getName());

		AbstractPaymentDocument doc = (AbstractPaymentDocument) document;
		CardLink cardLink = (CardLink) doc.getChargeOffResourceLink();
		if (cardLink == null)
		{
			throw new DocumentException("Не найден линк-на-источник списания типа " + doc.getChargeOffResourceType());
		}
		
		String externalID = cardLink.getExternalId();
		//если карта дополнительная со свойством 99-WAY, то выводим клиенту сообщение.
		if (externalID.indexOf("99-way") >= 0 && !cardLink.isMain())
			throw new DocumentLogicException(ERROR_MESSAGE);
	}
}
