package com.rssl.phizic.business.payments.forms.meta.receivers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.gate.payments.systems.recipients.Field;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vagin
 * @ created 23.05.2012
 * @ $Author$
 * @ $Revision$
 * Хендлер удаляющий неключевые поля из платежа в случае создания автоподписки.
 * Если платеж создается по исполненному/полностью заполненному платежу или по подписке на инвойсы эти данные не удаляем.
 */
public class AutoSubNotKeyFieldCleanHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if(isCreateAutoSubscription((BusinessDocument) document))
		{
			JurPayment payment = (JurPayment) document;
			if(payment.getTemplateId() == null && payment.getInvoiceSubscriptionId() == null)
			{
				List<Field> extFields = payment.getExtendedFields();
				payment.setExtendedFields(null);
				payment.setExtendedFields(getOnlyKeyFields(extFields));
			}
		}
	}

	private List<Field> getOnlyKeyFields(List<Field> fields)
	{
		if(fields == null || fields.isEmpty())
			return fields;

		List<Field> result = new ArrayList<Field>();
		for(Field field : fields)
		{
			if(field.isKey())
				result.add(field);
		}

		return result;
	}

	private boolean isCreateAutoSubscription(BusinessDocument document)
	{
		return (FormConstants.CREATE_INVOICE_SUBSCRIPTION_PAYMENT.equals(document.getFormName()) || FormConstants.SERVICE_PAYMENT_FORM.equals(document.getFormName())) && document.isLongOffer();
	}
}
