
package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.AbstractAccountsTransfer;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.account.AccountMessageConfig;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.CurrencyUtils;

import java.math.BigDecimal;

/**
 * Проверяет превышение максимальной суммы вклада
 * @author sergunin
 * @ created 27.12.2013
 * @ $Author$
 * @ $Revision$
 */

public class AccountCheckMaxBalanceAmountHandler extends BusinessDocumentHandlerBase
{

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		AbstractAccountsTransfer payment = (AbstractAccountsTransfer) document;

		if (payment.getDestinationResourceType() != ResourceType.ACCOUNT)
			return;

		AccountLink toAccount = (AccountLink) payment.getDestinationResourceLink();

		if (toAccount == null)
			return;

		BigDecimal maxBalance   = toAccount.getAccount().getMaxBalance();
		BigDecimal clearBalance = toAccount.getAccount().getClearBalance();

		if (maxBalance == null || clearBalance == null)
			return;

        if (maxBalance.compareTo(BigDecimal.ZERO) == 0)
            return;

		BigDecimal paymentAmount;
		if (payment.getDestinationAmount() != null)
			paymentAmount = payment.getDestinationAmount().getDecimal();
		else
			paymentAmount = payment.getChargeOffAmount().getDecimal();

		if (isEnoughMoney(paymentAmount, maxBalance, clearBalance))
			return;

		String currency = CurrencyUtils.getCurrencySign(getCurrencyCode(payment));
		stateMachineEvent.addMessage(createMessage(paymentAmount, maxBalance, clearBalance, currency));
	}

	private String getCurrencyCode(AbstractAccountsTransfer payment) throws DocumentException
	{
		try
		{
			return payment.getDestinationCurrency().getCode();
		}
		catch (GateException e)
		{
			throw new DocumentException(e);
		}
	}

	private String createMessage(BigDecimal payment, BigDecimal maxBalance, BigDecimal clearBalance, String currency)
	{
		String message = ConfigFactory.getConfig(AccountMessageConfig.class).getAccountMaxBallanceValidatorMessage();
		String admission = payment.setScale(2, BigDecimal.ROUND_HALF_UP).toString() + " \\" + currency;
		String excess = payment.subtract(maxBalance.subtract(clearBalance)).setScale(2, BigDecimal.ROUND_HALF_UP).toString() + " \\"
				+ (currency.endsWith(".") ? currency.substring(0, currency.length() - 1) : currency);
		message = message.replaceAll("\\{sumOfTransfer\\}", admission).replaceAll("\\{sumOfExceed\\}", excess);
		return message;
	}

	private boolean isEnoughMoney(BigDecimal addition, BigDecimal maxBalance, BigDecimal clearBalance)
	{
		if (maxBalance.subtract(clearBalance).compareTo(addition) > -1)
			return true;
		return false;
	}
}
