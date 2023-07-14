package com.rssl.phizic.gate.claims;

import com.rssl.phizic.gate.documents.Claim;
import com.rssl.phizic.gate.payments.AccountOrIMATransferBase;

import java.math.BigDecimal;

/**
 * Заявка на изменение неснижаемого остатка вклада
 * @author Pankin
 * @ created 15.09.13
 * @ $Author$
 * @ $Revision$
 */
public interface AccountChangeMinBalanceClaim extends Claim, AccountOrIMATransferBase
{
	/**
	 * @return номер счета, для которого выполняется заявка
	 */
	String getAccountNumber();

	/**
	 * @return новый неснижаемый остаток
	 */
	BigDecimal getMinimumBalance();

	/**
	 * @return новая процентная ставка
	 */
	BigDecimal getInterestRate();
}
