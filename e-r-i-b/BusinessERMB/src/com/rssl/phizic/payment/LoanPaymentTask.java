package com.rssl.phizic.payment;

import java.math.BigDecimal;

/**
 * ѕлатежна€ задача "ќплата кредита"
 * @author Rtischeva
 * @created 09.10.13
 * @ $Author$
 * @ $Revision$
 */
public interface LoanPaymentTask extends PaymentTask
{
	/**
	 * «адать алиас кредита
	 * @param loanAlias - алиас кредита
	 */
	void setLoanAlias(String loanAlias);

	/**
	 * ¬ернуть алиас кредита
	 */
	String getLoanAlias();

	/**
	 *  «адать код кредита
	 * @param loanLinkCode - код кредита
	 */
	void setLoanLinkCode(String loanLinkCode);

	/**
	 * «адать код ресурса списани€
	 * @param fromResourceCode - код ресурса списани€
	 */
	void setFromResourceCode(String fromResourceCode);

	/**
	 * задать сумму списани€
	 * @param amount - сумма списани€
	 */
	void setAmount(BigDecimal amount);

	/**
	 * вернуть сумму списани€
	 * @return  сумма списани€
	 */
	BigDecimal getAmount();

	/*
	 * ”становить алиас карты списани€
	 * @param fromResourceAlias - алиас карты списани€ (never null)
	 */
	void setFromResourceAlias(String fromResourceAlias);

	/**
	 * @return алиас карты списани€
	 */
	String getFromResourceAlias();

	/**
	 * установить остаток основной задолженности по кредиту
	 * @param balanceAmount
	 */
	void setLoanBalanceAmount(String balanceAmount);

	/**
	 * вернуть остаток основной задолженности по кредиту
	 * @return
	 */
	String getLoanBalanceAmount();

	/**
	 * вернуть валюту кредита в виде строки
	 * @return
	 */
	String getLoanCurrency();

	/**
	 * установить валюту кредита
	 * @param loanCurrency - валюта кредита
	 */
	void setLoanCurrency(String loanCurrency);
}
