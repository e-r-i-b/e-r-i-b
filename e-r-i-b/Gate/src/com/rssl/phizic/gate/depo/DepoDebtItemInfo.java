package com.rssl.phizic.gate.depo;

import java.io.Serializable;

/**
 * @author mihaylov
 * @ created 16.08.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Расширенная информация о пункте задолженности по счету ДЕПО
 * Включает в себя реквизиты для оплаты задолженности
 */
public interface DepoDebtItemInfo extends Serializable
{
	/**
	 * БИК банка получателя
	 * @return bankBIC
	 */
	String getBankBIC();

	/**
	 * Наименование банка получателя
	 * @return bankName
	 */
	String getBankName();

	/**
	 * Корсчет банка получателя
	 * @return bankCorAccount
	 */
	String getBankCorAccount();

	/**
	 * Наименование получателя платежа
	 * @return recipientName
	 */
	String getRecipientName();

	/**
	 * ИНН получателя
	 * @return recipientINN
	 */
	String getRecipientINN();

	/**
	 * КПП получателя
	 * @return recipientKPP
	 */
	String getRecipientKPP();

	/**
	 * Расчетный счет получателя
	 * @return recipientAccount
	 */
	String getRecipientAccount();
}
