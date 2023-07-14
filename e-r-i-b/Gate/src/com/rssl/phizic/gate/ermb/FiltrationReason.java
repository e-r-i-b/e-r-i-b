package com.rssl.phizic.gate.ermb;

/**
* @author Erkin
* @ created 10.11.2014
* @ $Author$
* @ $Revision$
*/

/**
 * Описание причины, по которой МБК передал связку в ЕРМБ
 * Задача «Обработка подключений к МБК в ЕРМБ» (миграция на лету)
 */
@SuppressWarnings("PackageVisibleField")
public enum FiltrationReason
{
	ERMB_PHONE("Телефон в ЕРМБ"),

	ERMB_CARD("Карта в ЕРМБ"),

	PILOT_ZONE("Карта в ПЗ"),       //по спеке "Пилотная зона", но МБК передает в таком виде

	;

	public final String mbkValue;

	private FiltrationReason(String mbkValue)
	{
		this.mbkValue = mbkValue;
	}

	public static FiltrationReason fromMBK(String mbkValue)
	{
		for (FiltrationReason reason : values())
		{
			if (reason.mbkValue.equals(mbkValue))
				return reason;
		}

		throw new IllegalArgumentException("Неожиданный FiltrationReason");
	}

	@Override
	public String toString()
	{
		return mbkValue;
	}
}
