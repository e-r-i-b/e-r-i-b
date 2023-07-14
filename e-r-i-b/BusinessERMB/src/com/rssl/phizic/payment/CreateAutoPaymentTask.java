package com.rssl.phizic.payment;

import com.rssl.phizic.bankroll.BankrollProductLink;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.utils.PhoneNumber;

import java.math.BigDecimal;

/**
 * Платежная задача "Подключение автоплатежа"
 * @author Rtischeva
 * @ created 13.06.14
 * @ $Author$
 * @ $Revision$
 */
public interface CreateAutoPaymentTask extends PaymentTask
{
	/**
	 * установить номер телефона
	 * @param phoneNumber - номер телефона
	 */
	public void setPhoneNumber(PhoneNumber phoneNumber);

	/**
	 * установить сумму
	 * @param amount - сумма
	 */
	public void setAmount(BigDecimal amount);

	/**
	 * установить порог
	 * @param threshold - порог
	 */
	public void setThreshold(BigDecimal threshold);

	/**
	 * установить лимит
	 * @param limit - лимит
	 */
	public void setLimit(BigDecimal limit);

	/**
	 * установить линк карты списания
	 * @param cardLink - линк карты списания
	 */
	public void setCardLink(BankrollProductLink cardLink);

	/**
	 * установить поставщика услуг
	 * @param provider
	 */
	public void setProvider(BillingServiceProvider provider);

}
