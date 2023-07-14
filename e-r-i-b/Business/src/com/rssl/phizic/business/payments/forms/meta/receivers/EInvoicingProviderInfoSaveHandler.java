package com.rssl.phizic.business.payments.forms.meta.receivers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizgate.common.payments.systems.recipients.FieldImpl;
import com.rssl.phizic.business.documents.exceptions.WrongDocumentTypeException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.FieldDataType;

import java.util.ArrayList;
import java.util.List;

/**
 * Хендлер используется для установки в платеж einvoicing-поставщику доп. полей.
 * @author gladishev
 * @ created 01.07.2015
 * @ $Author$
 * @ $Revision$
 */

public class EInvoicingProviderInfoSaveHandler extends BusinessDocumentHandlerBase
{
	private static final String UID_KEY = "ERIBUID";

	/**
	* Обработать документ
	* @param document документ
	* @param stateMachineEvent
	* @throws com.rssl.common.forms.DocumentLogicException неправильно заполнен документ, нужно исправить ошибки
	*/
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof JurPayment))
		{
			throw new WrongDocumentTypeException((BusinessDocument) document, JurPayment.class);
		}

		JurPayment payment = (JurPayment) document;
		List<Field> newExtendedFields = new ArrayList<Field>(payment.getExtendedFields());
		newExtendedFields.add(new FieldImpl(UID_KEY, null, FieldDataType.string, payment.getOrderUuid()));
		payment.setExtendedFields(newExtendedFields);
	}
}
