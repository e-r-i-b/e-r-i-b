package com.rssl.phizic.business.ermb.sms.parser;

/**
 * @author Erkin
 * @ created 23.11.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Лексемы
 */
enum Lexeme
{
	/**
	 * Комманда
	 */
	COMMAND,

	/**
	 * Ключевое слово комманды
	 */
	COMMAND_KEYWORD,

	/**
	 * Разделитель
	 */
	DELIMITER,

	/**
	 * Получатель
	 */
	RECIPIENT,

	/**
	 * Продукт
	 */
	PRODUCT,

	/**
	 * Чужая карта
	 */
	OTHER_CARD,

	/**
	 * Номер телефона
	 */
	PHONE,

	/**
	 * Алиас (поставщика, продукта)
	 */
	ALIAS,

	/**
	 * Название тарифа
	 */
	TARIFF,

	/**
	 * Сумма
	 */
	AMOUNT,

	/**
	 * Число
	 */
	NUMBER,

	/**
	 * Строка
	 */
	STRING,

	/**
	 * Конец текста
	 */
	EOF
}
