package com.rssl.phizic.business.payments;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.payments.ExchangeCurrencyTransferBase;
import com.rssl.phizic.business.rates.CurrencyRateHelper;

/**
 * @author: Pakhomova
 * @created: 22.09.2009
 * @ $Author$
 * @ $Revision$
 * проверяет, изменился ли курс валюты. Если да, вынуждает пользователя переввести данные.
 */
public class CheckRateDidntChangeHandler extends BusinessDocumentHandlerBase
{
    public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof ExchangeCurrencyTransferBase))
		{
			throw new DocumentException("Неверный тип платежа платежа id=" + ((BusinessDocument) document).getId() + " (Ожидается ExchangeCurrencyTransferBase)");
		}
		ExchangeCurrencyTransferBase payment = (ExchangeCurrencyTransferBase) document;
		if (payment.calcConvertionRates())
		{
			throw new DocumentLogicException(CurrencyRateHelper.getRateChangeMessage(document, stateMachineEvent));
		}
	}
}
