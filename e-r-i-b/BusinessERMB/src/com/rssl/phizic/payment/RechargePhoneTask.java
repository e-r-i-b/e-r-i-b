package com.rssl.phizic.payment;

import java.math.BigDecimal;

/**
 * Платежная задача "Оплата телефона"
 * @author Rtischeva
 * @created 03.10.13
 * @ $Author$
 * @ $Revision$
 */
public interface RechargePhoneTask extends PaymentTask
{
	/**
	 * Задать код ресурса списания
	 * @param fromResourceCode
	 */
	void setFromResourceCode(String fromResourceCode);

	/**
	 * задать код поля с главной суммой во внешней системе
	 */
	void setAmountExternalId(String amountExternalId);

	/**
	 * задать сумму списания
	 * @param amount
	 */
	void setAmount(BigDecimal amount);

	/**
	 * true, если не нужно подтверждать
	 * @param needConfirm
	 */
	void setNotNeedConfirm(boolean needConfirm);

	/**
	 * задать номер телефона
	 * @param phoneNumber
	 */
	void setPhoneNumber(String phoneNumber);

	/**
	 * задать код поставщика услуг
	 * @param providerKey
	 */
	void setProviderKey(Long providerKey);

	/**
	 * установить код доп. поля поставщика, в котором хранится номер телефона
	 * @param fieldName
	 */
	void setRechargePhoneExternalId(String fieldName);

	/**
	 * Установить название мобильного оператора
	 * @param name
	 */
	public void setOperatorName(String name);
}
