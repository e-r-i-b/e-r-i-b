package com.rssl.phizic.gate.bankroll;

/**
 * @author Omeliyanchuk
 * @ created 19.11.2007
 * @ $Author$
 * @ $Revision$
 */

public enum CardState
{
	/**
	 * Активная
	 */
	active("Активная"),
	/**
	 * Закрытая
	 */
	closed("Закрытая"),
	/**
	 * Запрошен перевыпуск
	 */
	replenishment("Запрошен перевыпуск"),
	/**
	 * Карточка заказана в ПЦ
	 */
	ordered("Карточка заказана в ПЦ"),
	/**
	 * Подлежит выдаче
	 */
	delivery("Подлежит выдаче"),
	/**
	 * Заблокирована
	 */
	blocked("Заблокирована"),
	/**
	 * Изменение параметров карты в ПЦ
	 */
	changing("Изменение параметров карты в ПЦ"),
	/**
	 * Неизвестный, для всех не известных ИКФЛ статусов,
	 * т.е. если в шлюзе появилось значение,
	 * для которого не установлен соответствующий статус ИКФЛ, должен возвращаться этот.
	 */
	unknown("Неизвестно");

    private String description;

	CardState(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}
}
