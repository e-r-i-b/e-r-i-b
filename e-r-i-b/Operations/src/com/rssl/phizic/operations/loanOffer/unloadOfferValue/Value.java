package com.rssl.phizic.operations.loanOffer.unloadOfferValue;

/**
 * @author gulov
 * @ created 13.07.2011
 * @ $Authors$
 * @ $Revision$
 */

/**
 * »нтерфейс, который реализуетс€ классами-пол€ми за€вок дл€ выгрузки в файл
 */
interface Value
{
	/**
	 * ¬озвращает значение пол€
	 * @return - значение пол€
	 */
	Object getValue();

	/**
	 * ѕризнак об€зательности пол€
	 * @return true - поле об€зательно, false - поле необ€зательно
	 */
	boolean isMandatory();

	/**
	 *  оличество зап€тых при выводе в файл после вывода значени€ пол€
	 * @return - количество зап€тых
	 */
	int getCommaCount();

	/**
	 * ѕризнак пустоты значени€ пол€
	 * @return - true - пустое поле, false - не пустое
	 */
	boolean isEmpty();

	/**
	 * —ообщение, которые пишетс€ в лог, если значение пол€ об€зательно, но не заполнено
	 * @return - строка сообщени€
	 */
	String getMessage();
}
