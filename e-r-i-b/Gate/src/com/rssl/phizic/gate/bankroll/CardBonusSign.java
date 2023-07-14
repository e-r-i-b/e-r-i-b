package com.rssl.phizic.gate.bankroll;

/**
 * @ author: Vagin
 * @ created: 15.08.13
 * @ $Author
 * @ $Revision
 * Принадлежность к бонусной программе
 */
public enum CardBonusSign
{
	A("Аэрофлот бонус"),
	G("Золотая маска"),
	M("МТС"),
	Y("Молодежная"),
	Z("Подари жизнь"),
	O("Олимпийская благотворительная");

	private String description;

	CardBonusSign(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}
}


