package com.rssl.phizic.common.types;

/**
 * @ author: Vagin
 * @ created: 18.07.2013
 * @ $Author:
 * @ $Revision
 * Бизнес тип поля.
 */
@Deprecated
public enum BusinessFieldSubType
{
	phone,                  // номер телефона
	wallet,                 // номер интеренет кошелька
	ourCard,                // номер карты Сбербанка
	visaExternalCard,       // номер карты Visa
	masterCardExternalCard, // номер крты MasterCard
	externalAccount,        // номер счета другого банка
	ourAccount,             // номер счета в Сбербанке
	social                  // ИД в соцсети
}