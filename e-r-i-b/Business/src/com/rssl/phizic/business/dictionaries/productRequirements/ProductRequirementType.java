package com.rssl.phizic.business.dictionaries.productRequirements;

/**
 * Тип требования для продуктов клиента
 * @author lepihina
 * @ created 11.01.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Полезное правило: поменял здесь, поменяй в
 * /com/rssl/phizic/web/dictionaries/productRequirements/resources.properties
 */
public enum ProductRequirementType
{
	//Кредитная карта
	CREDIT_CARD,
	//Дебетовая карта
	DEBIT_CARD,
	//Автоплатеж
	AUTOPAYMENT,
	//Вклад
	ACCOUNT,
	//Кредит
	LOAN,
	//Счет депо
	DEPO_ACCOUNT,
	//Металлический счет
	IMA,
	//ПФР
	PFR,
	// Отчёт по Кредитной Истории
	CREDIT_REPORT,
}
