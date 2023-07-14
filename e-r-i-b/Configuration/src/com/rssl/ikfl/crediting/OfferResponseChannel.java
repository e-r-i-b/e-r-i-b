package com.rssl.ikfl.crediting;

/**
 * @author Erkin
 * @ created 29.12.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * Канал отклика
 */
public enum OfferResponseChannel
{
	/**
	 * Сбербанк ОнЛ@йн
	 */
	SBOL ("ЕРИБ-СБОЛ"),

	/**
	 * Мобильное приложение СБОЛ
	 */
	MP ("ЕРИБ-МП"),

	/**
	 * Устройства самообслуживания
	 */
	US ("ЕРИБ-УС"),

	/**
	 * Мобильный банк
	 */
	MB ("ЕРИБ-МБ"),

	/**
	 * Гостевой СБОЛ
	 */
	SBOL_GUEST ("ЕРИБ-ГОСТЕВОЙ"),

	;

	/**
	 * Код канала в CRM
	 */
	public final String crmCode;

	private OfferResponseChannel(String crmCode)
	{
		this.crmCode = crmCode;
	}

	public String getCrmCode()
	{
		return crmCode;
	}
}
