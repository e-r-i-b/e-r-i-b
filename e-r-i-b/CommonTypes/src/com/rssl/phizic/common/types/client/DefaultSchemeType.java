package com.rssl.phizic.common.types.client;

import com.rssl.phizic.common.types.csa.ProfileType;

/**
 * @author komarov
 * @ created 18.07.2014
 * @ $Author$
 * @ $Revision$
 *  Тип схемы прав по умолчанию
 */
public enum DefaultSchemeType
{
	//схема прав СБОЛ.
	SBOL,
	//схкема прав карточного клиента.
	CARD,
	//схема прав для УДБО.
	UDBO,
	//схема прав УДБО клиента в резервном блоке.
	UDBO_TEMPORARY,
	//схема прав карточного клиента в резервном блоке.
	CARD_TEMPORARY,
	//схема прав гостевого входа для оформления заявки на кредит, кредитную карту
	GUEST;

	/**
	 * Возвращает тип схемы прав по умолчанию по типу договора, по которому подключен клиент
	 * @param type Тип договора, по которому подключен клиент
	 * @return тип схемы прав по умолчанию
	 */
	public static DefaultSchemeType getDefaultSchemeType(CreationType type)
	{
		switch (type)
		{
			case UDBO: return DefaultSchemeType.UDBO;
			case SBOL: return DefaultSchemeType.SBOL;
			case CARD: return DefaultSchemeType.CARD;
			default: throw new IllegalArgumentException("Неверное значение аргумента type:" + type);
		}
	}

	/**
	 * Возвращает тип схемы прав по умолчанию по типу договора, по которому подключен клиент и типу профиля
	 * @param type тип договора, по которому подключен клиент
	 * @param profileType тип профиля
	 * @return тип схемы прав по умолчанию
	 */
	public static DefaultSchemeType getDefaultSchemeType(CreationType type, ProfileType profileType)
	{
		if(profileType == ProfileType.TEMPORARY)
			return type == CreationType.UDBO ? UDBO_TEMPORARY: CARD_TEMPORARY;
		return getDefaultSchemeType(type);
	}
}
