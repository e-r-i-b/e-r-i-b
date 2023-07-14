package com.rssl.phizic.business.connectUdbo;

/**
 * Статусы для условий зявления о подключении УДБО
 * User: miklyaev
 * @ $Author$
 * @ $Revision$
 */
public enum UDBOClaimRulesStatus
{
	/**
	 * Активный
	 */
	ACTIVE(1),

	/**
	 * Введен
	 */
	ENTERED(2),

	/**
	 * Архивный
	 */
	ARCHIVAL(3);

	UDBOClaimRulesStatus(int value)
	{
		this.value = value;
	}

	/**
	 * Код статуса
	 */
	private final int value;

	public int toValue()
	{
		return value;
	}

	public static UDBOClaimRulesStatus fromValue(int value)
	{
		if (value == ACTIVE.value)
			return ACTIVE;
		if (value == ENTERED.value)
			return ENTERED;
		if (value == ARCHIVAL.value)
			return ARCHIVAL;

		throw new IllegalArgumentException("Неизвестный статус для условий заявления о подключении УДБО [" + value + "]");
	}
}
