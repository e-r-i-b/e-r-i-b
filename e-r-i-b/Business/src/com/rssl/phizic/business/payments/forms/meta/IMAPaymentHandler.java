package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.IMAOpeningClaim;
import com.rssl.phizic.business.documents.payments.ExchangeCurrencyTransferBase;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.util.ApplicationUtil;

/**
 * Проверки при покупке/продаже драг. металлов
 * @author Pankin
 * @ created 30.06.2011
 * @ $Author$
 * @ $Revision$
 */

public class IMAPaymentHandler extends BusinessDocumentHandlerBase
{
	private static final String CONVERTION_RATE_NOT_FOUND_MESSAGE = "По техническим причинам операция " +
			"временно недоступна. Повторите попытку позже.";
	private static final String ROUND_SUMM_MESSAGE1 = "За выполнение данной операции комиссия не взимается.";
	private static final String ROUND_SUMM_MESSAGE2 = "Обратите внимание! Введенная Вами сумма была пересчитана в соответствии с курсом конверсии.";

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof ExchangeCurrencyTransferBase))
			throw new DocumentException("Неверный тип платежа. Ожидается ExchangeCurrencyTransferBase");

		ExchangeCurrencyTransferBase transfer = (ExchangeCurrencyTransferBase) document;
		// Если не получили курс конверсии, не пропускаем платеж
		if (compareCurrency(transfer) && transfer.getConvertionRate() == null)
			throw new DocumentLogicException(CONVERTION_RATE_NOT_FOUND_MESSAGE);

		if (ApplicationUtil.isMobileApi())
		{
			if (transfer instanceof IMAOpeningClaim)
				stateMachineEvent.addMessage(ROUND_SUMM_MESSAGE1);
			// Если введенная клиентом сумма была пересчитана, нужно вывести соответствующее предупреждение
			if (transfer.getValueOfExactAmount().compareTo(transfer.getExactAmount().getDecimal()) != 0)
				stateMachineEvent.addMessage(ROUND_SUMM_MESSAGE2);
		}

	}

	private boolean compareCurrency(ExchangeCurrencyTransferBase transfer) throws DocumentException
	{
		try
		{
			return transfer.getChargeOffCurrency() != transfer.getDestinationCurrency();
		}
		catch (GateException e)
		{
			throw new DocumentException(e);
		}
	}
}
