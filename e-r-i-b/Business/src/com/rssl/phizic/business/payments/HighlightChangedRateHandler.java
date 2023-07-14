package com.rssl.phizic.business.payments;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.payments.ExchangeCurrencyTransferBase;
import com.rssl.phizic.utils.StringHelper;

/**
 *
 *  ласс служит дл€ проверки измени€ курса валют и регистрации изменений.
 * ƒополнительно посредством параметра highlightedField можно указать им€ пол€ которое необходимо посветить в случае
 * изменившегос€ курса.
 *
 * User: Balovtsev
 * Date: 09.02.2012
 * Time: 8:54:39
 */
public class HighlightChangedRateHandler extends BusinessDocumentHandlerBase
{
	private static final String PARAMETER_HIGHLIGHTED_FIELD = "highlightedField";
	private static final String ERROR_MESSAGE = "»зменилс€ курс конверсии банка.";

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof ExchangeCurrencyTransferBase))
		{
			throw new DocumentException("Ќеверный тип платежа платежа id=" + ((BusinessDocument) document).getId() + " (ќжидаетс€ ExchangeCurrencyTransferBase)");
		}
		//пересчет курсов конверсии "calcConvertionRates()" тут уже не нужен, т.к. он происходит в при обновлении документа введенными данными
		if (((ExchangeCurrencyTransferBase) document).isRateChanged())
		{
			stateMachineEvent.addErrorMessage(ERROR_MESSAGE);
			String parameter = getParameter(PARAMETER_HIGHLIGHTED_FIELD);
			if ( StringHelper.isEmpty(parameter) )
			{
				stateMachineEvent.registerChangedField("confirmCourse");
			}
			else
			{
				stateMachineEvent.registerChangedField(parameter);
			}
		}
	}
}
