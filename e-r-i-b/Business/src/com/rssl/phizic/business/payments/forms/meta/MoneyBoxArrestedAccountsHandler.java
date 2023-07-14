package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.documents.payments.CreateMoneyBoxPayment;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.gate.bankroll.AccountState;

/**
 * @author akimov
 * Date: 26.05.15
 * Хэндлер для проверки счета создаваемой копилки.
 * Копилка на арестованные счета не создается (BUG089621).
 */
public class MoneyBoxArrestedAccountsHandler extends BusinessDocumentHandlerBase<CreateMoneyBoxPayment>
{
	private static final String ERROR_MESSAGE = "Данный вклад не допускает подключения сервиса Копилка.";

	public void process(CreateMoneyBoxPayment payment, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		AccountLink accountLink = (AccountLink) payment.getDestinationResourceLink();
		if (accountLink.getAccount().getAccountState() == AccountState.ARRESTED)
			throw new DocumentLogicException(ERROR_MESSAGE);
	}
}
