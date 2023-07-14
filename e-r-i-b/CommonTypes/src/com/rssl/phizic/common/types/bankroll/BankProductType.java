package com.rssl.phizic.common.types.bankroll;

/**
 * @ author: filimonova
 * @ created: 15.09.2010
 * @ $Author$
 * @ $Revision$
 * enum для типов продуктов, перечень которых должен вернуться по GFL запросу
 */
public enum BankProductType
{
	/**
    * Счета
    */
	Deposit,
	/**
    * ОМС
    */
	IMA,
	/**
    * Карты
    */
	Card,
	/**
    * Кредиты
    */
	Credit,
	/**
    * Депо-счета
    */
	DepoAcc,
	/**
    * Длительные поручения
    */
	LongOrd,
	/**
	 * Карты, в случае получение информации по картам только из WAY (без карт. счета)
 	 */
	CardWay ,
	/**
	 * Сберегательные сертификаты
	 */
	Securities
}
