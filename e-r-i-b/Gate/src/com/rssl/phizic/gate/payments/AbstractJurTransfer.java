/***********************************************************************
 * Module:  JurTransfer.java
 * Author:  Egorova
 * Purpose: Defines the Interface JurTransfer
 ***********************************************************************/

package com.rssl.phizic.gate.payments;

/**
 * ѕеревод юридическому лицу в другой банк через платежную систему –оссии.
 */
public interface AbstractJurTransfer extends AbstractRUSPayment
{
	/**
	 * @return наименование получател€
	 */
	String getReceiverName();

	/**
	 * ”становить наименование получател€
	 * @param receiverName наименование получател€
	 */
	void setReceiverName(String receiverName);

	/**
	 * @return  ѕѕ получател€ платежа - может отсутствовать, в этом случае равен null
	 */
	String getReceiverKPP();

	/**
	 * ”станавливает номер реестра при успешном исполнении перевода
	 * @param registerNumber номер реестра
	 */
	void setRegisterNumber(String registerNumber);

	/**
	 * ”станавливает строку реестра при успешном исполнении перевода
	 * @param registerString строка реестра
	 */
	void setRegisterString(String registerString);
}