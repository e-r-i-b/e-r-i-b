package com.rssl.phizic.payment;

import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.utils.PhoneNumber;

import java.math.BigDecimal;

/**
 * Платёжная задача "Перевод по номеру телефона"
 * @author Dorzhinov
 * @ created 24.05.2013
 * @ $Author$
 * @ $Revision$
 */
public interface PhoneTransferTask extends PaymentTask
{
	/**
	 * Установить алиас карты списания
	 * @param fromResourceAlias    алиас карты списания (never null)
	 */
	void setFromResourceAlias(String fromResourceAlias);

	/**
	 * @return алиас карты списания
	 */
	String getFromResourceAlias();

	/**
	 * Установить код карты списания
	 * @param fromResourceCode - код карты списания (never null)
	 */
	void setFromResourceCode(String fromResourceCode);

	/**
	 * @return код карты списания
	 */
	String getFromResourceCode();
	
	/**
	 * Установить телефон получателя перевода.
	 * @param phoneNumber телефон получателя перевода (never null)
	 */
	void setPhone(PhoneNumber phoneNumber);

	/**
	 * Установить сумму перевода.
	 * exactAmount = сумма списания.
	 * @param amount - сумма списания в рублях (never null)
	 */
	void setAmount(BigDecimal amount);

	/**
	 * @return сумма перевода
	 */
	BigDecimal getAmount();

	/**
	 * установить линк карты списания
	 * @param chargeOffLink
	 */
	void setChargeOffLink(CardLink chargeOffLink);
}
