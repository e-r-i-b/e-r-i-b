package com.rssl.phizic.payment;

import com.rssl.phizic.bankroll.BankrollProductLink;

import java.math.BigDecimal;

/**
 * @author Erkin
 * @ created 08.04.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ѕлатЄжна€ задача "ѕеревод между своими счетами"
 */
public interface InternalTransferTask extends PaymentTask
{
	/**
	 * «адать источник списани€.
	 */
	void setFromResourceCode(String fromResourceCode);

	/**
	 * код валюты источника списани€
	 * @param fromResourceCurrencyCode
	 */
	void setFromResourceCurrencyCode(String fromResourceCurrencyCode);

	/**
	 * «адать приЄмник зачислени€
	 */
	void setToResourceCode(String toResourceCode);

	/**
	 * код валюты приемника зачислени€
	 * @param toResourceCurrencyCode
	 */
	void setToResourceCurrencyCode(String toResourceCurrencyCode);

	/**
	 * «адать сумму перевода.
	 **/
	void setAmount(BigDecimal amount);

	/**
	 * €вл€етс€ ли перевод переводом с карты на счет
	 * @param isCardToAccountTransfer true, если €вл€етс€
	 */
	void setCardToAccountTransfer(boolean isCardToAccountTransfer);

	/**
	 * €вл€етс€ ли перевод переводом с карты на счет
	 * @return true, если €вл€етс€
	 */
	boolean isCardToAccountTransfer();

	/**
	 * измен€лс€ ли курс валют после отправки кода подтверждени€
	 * @return true, если измен€лс€
	 */
	boolean isCurrencyRatesChanged();

	/**
	 * установить линк ресурса списани€
	 * @param chargeOffLink - линк ресурса списани€
	 */
	public void setChargeOffLink(BankrollProductLink chargeOffLink);
}
