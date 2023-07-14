package com.rssl.phizic.gate.bankroll;

/**
 * @ author: filimonova
 * @ created: 16.08.2010
 * @ $Author$
 * @ $Revision$
 */
public enum AccountState
{
	/**
     * Открыт
     */
	OPENED(0),

	/**
     * Закрыт
     */
	CLOSED(1),

	/**
     * Арестован
     */
	ARRESTED(2),

	/**
     * Потеряна книжка. Заблокирован
     */
	LOST_PASSBOOK(3);

	private int id;
	private String description;

	AccountState(int id)
	{
		this.id = id;

		switch (id)
		{
			case 0:
				description = "Открыт";
			break;
			case 1:
				description = "Закрыт";
			break;
			case 2:
				description = "НА СЧЕТ НАЛОЖЕН АРЕСТ";
			break;
			case 3:
				description = "Утеряна сберкнижка";
			break;
		}
	}

	public int getId()
	{
		return id;
	}

	public String getDescription()
	{
		return description;
	}

	public static AccountState valueOf(int id)
	{
		switch (id)
		{
			case 0:
				return OPENED;
			case 1:
				return CLOSED;
			case 2:
				return ARRESTED;
			case 3:
				return LOST_PASSBOOK;
		}
		throw new IllegalArgumentException("Неизвестный статус счета [" + id + "]");
	}

}
