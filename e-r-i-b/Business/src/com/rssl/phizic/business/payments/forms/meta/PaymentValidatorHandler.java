package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.AbstractPaymentDocument;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.utils.MockHelper;

import java.math.BigDecimal;

/**
 * @author Gainanov
 * @ created 13.06.2007
 * @ $Author$
 * @ $Revision$
 */
public class PaymentValidatorHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof AbstractPaymentDocument))
		{
			throw new DocumentException("Неверный тип платежа платежа id=" + ((BusinessDocument) document).getId() + " (Ожидается AbstractPaymentDocument)");
		}
		AbstractPaymentDocument payment = (AbstractPaymentDocument) document;
		Money amount = payment.getChargeOffAmount();
		// если не указана сумма
		if (amount == null)  return;

		Money comission = payment.getCommission();
		if (comission == null)
		{
			comission = new Money(BigDecimal.ZERO, amount.getCurrency());
		}

		Money accountAmount = getAccountAmount(payment.getChargeOffAccount());
		if (accountAmount.compareTo(amount.add(comission)) < 0)
			throw new DocumentLogicException("Сумма платежа превышает остаток по счету списания");
	}

	private Money getAccountAmount(String accountNumber) throws DocumentException, DocumentLogicException
	{
		try
		{
			PersonData data = PersonContext.getPersonDataProvider().getPersonData();
			AccountLink link = data.findAccount(accountNumber);
			if (link == null)
				throw new RuntimeException("Счет " + accountNumber + " для клиента id:" + data.getPerson().getId() + " не найден");

			Account account = link.getAccount();
			if(MockHelper.isMockObject(account))
				throw new DocumentLogicException("Операция временно недоступна. Повторите попытку позже.");
	
			return account.getBalance();
		}
		catch (BusinessException e)
		{
			throw new RuntimeException(e);
		}
		catch (BusinessLogicException e)
		{
			throw new RuntimeException(e);
		}
	}
}
