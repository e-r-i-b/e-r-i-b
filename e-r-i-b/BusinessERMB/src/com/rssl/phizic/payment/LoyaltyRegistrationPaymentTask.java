package com.rssl.phizic.payment;

/**
 * Платежная задача "Регистрация программы лояльности Спасибо от Сбербанка"
 * @author Puzikov
 * @ created 07.11.13
 * @ $Author$
 * @ $Revision$
 */

public interface LoyaltyRegistrationPaymentTask extends PaymentTask
{
	/**
	 * Задать номер телефона
	 * @param phoneNumber текущий номер телефона
	 */
	public void setPhone(String phoneNumber);
}
