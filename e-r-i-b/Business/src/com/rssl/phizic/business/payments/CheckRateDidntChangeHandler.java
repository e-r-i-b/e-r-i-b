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
 * ���������, ��������� �� ���� ������. ���� ��, ��������� ������������ ���������� ������.
 */
public class CheckRateDidntChangeHandler extends BusinessDocumentHandlerBase
{
    public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof ExchangeCurrencyTransferBase))
		{
			throw new DocumentException("�������� ��� ������� ������� id=" + ((BusinessDocument) document).getId() + " (��������� ExchangeCurrencyTransferBase)");
		}
		ExchangeCurrencyTransferBase payment = (ExchangeCurrencyTransferBase) document;
		if (payment.calcConvertionRates())
		{
			throw new DocumentLogicException(CurrencyRateHelper.getRateChangeMessage(document, stateMachineEvent));
		}
	}
}
