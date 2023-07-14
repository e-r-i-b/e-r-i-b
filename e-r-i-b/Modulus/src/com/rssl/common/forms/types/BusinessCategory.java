package com.rssl.common.forms.types;

/**
 * Бизнесс категории полей
 * @author niculichev
 * @ created 26.08.13
 * @ $Author$
 * @ $Revision$
 */
public enum BusinessCategory
{
	/**
	 * Номер телефона
	 */
	PHONE,

	/**
	 * Фамилия
	 */
	SURNAME,

	/**
	 * Абонентская часть номера телефона, состоящего из нескольких отдельных групп цифр
	 */
	EXTENDED_PHONE,

	/**
	 * Дата
	 */
	DATE,

	/**
	 * Полностью маскированная строка (максимальная длина - 255)
	 */
	FULL_MASKED,

	/**
	 * Улица
	 */
	STREET,

	/**
	 * Карта внешнего получателя
	 */
	EXTERNAL_CARD
}
