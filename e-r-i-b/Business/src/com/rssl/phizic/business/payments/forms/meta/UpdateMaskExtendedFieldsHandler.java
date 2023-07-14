package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.payments.CreateInvoiceSubscriptionPayment;
import com.rssl.phizic.common.types.transmiters.Pair;

import java.util.List;

/**
 * обновляем маски для полей со скрытыми реквизитами(например номер паспорта)
 * @author basharin
 * @ created 06.08.14
 * @ $Author$
 * @ $Revision$
 */

public class UpdateMaskExtendedFieldsHandler extends DefaultCommissionSaveHandler
{
	private static final String PASSPORT_SERIAS_AND_NUMBER_KEY = "REGULAR_PASSPORT_RF@seriesAndNumber";
	private static final String PASSPORT_SERIAS_AND_NUMBER_MASK = "####****##";

	@Override
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof CreateInvoiceSubscriptionPayment))
			throw new DocumentException("document должен быть CreateInvoiceSubscriptionPayment");

		CreateInvoiceSubscriptionPayment payment = (CreateInvoiceSubscriptionPayment) document;

		List<Pair<String, String>> chosenFields = payment.getChosenDocumentsFields();
		for (Pair<String, String> chosenField : chosenFields)
		{
			if (PASSPORT_SERIAS_AND_NUMBER_KEY.equals(chosenField.getSecond()))
			{
				payment.updateMaskFieldByKey(chosenField.getFirst(), PASSPORT_SERIAS_AND_NUMBER_MASK);
			}
		}
	}
}