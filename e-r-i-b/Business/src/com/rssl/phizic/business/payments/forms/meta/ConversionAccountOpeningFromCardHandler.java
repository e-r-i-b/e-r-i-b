package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.AccountOpeningClaim;
import com.rssl.phizic.business.documents.payments.ExchangeCurrencyTransferBase;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.PaymentAbilityERL;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * Хендлер для проверки запрещены/разрешены валютообменные операции с карты на вклад для форм открытия вклада.
 * @author Jatsky
 * @ created 19.03.14
 * @ $Author$
 * @ $Revision$
 */

public class ConversionAccountOpeningFromCardHandler extends ConversionFromCardToAccountHandler
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		if (!(document instanceof AccountOpeningClaim))
			throw new DocumentException("Ожидается наследник AccountOpeningClaim");

		AccountOpeningClaim accountOpeningClaim = (AccountOpeningClaim) document;

		if (!accountOpeningClaim.isNeedInitialFee())
			return;

		try
		{
			Currency toResourceCurrency = accountOpeningClaim.getDestinationCurrency();
			ExchangeCurrencyTransferBase transfer = (ExchangeCurrencyTransferBase) document;
			PaymentAbilityERL fromResource = transfer.getChargeOffResourceLink();

			if (toResourceCurrency == null || fromResource == null || fromResource.getCurrency() == null)
				return;

			if (fromResource instanceof CardLink)
			{
				showMessage(fromResource.getCurrency().getCode(), toResourceCurrency.getCode(), stateMachineEvent);
			}
		}
		catch (GateException e)
		{
			throw new DocumentException(e);
		}

	}
}
