package com.rssl.phizic.gate.commission;

/**
 * Тип перевода для установки тарифа комиссий
 * @author niculichev
 * @ created 18.04.2012
 * @ $Author$
 * @ $Revision$
 */
public enum TransferType
{
	/**
	 * Перевод на счет данного клиента в другой ТБ
	 */
	OTHER_TB_OWN_ACCOUNT,

	/**
	 * Перевод на счет другого клиента в другой ТБ 
	 */
	OTHER_TB_ANOTHER_ACCOUNT,

	/**
	 * Перевод в другом банке
	 */
	OTHER_BANK

}
