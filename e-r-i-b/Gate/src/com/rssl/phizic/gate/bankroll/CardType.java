package com.rssl.phizic.gate.bankroll;

/**
 * @author Danilov
 * @ created 12.10.2007
 * @ $Author$
 * @ $Revision$
 */
public enum CardType
{

	debit("дебетовая", "Дебетовая"),

	credit("кредитная", "Кредитная"),

	overdraft("овердрафтная", "Дебетовая");

	private String description;
	private String displayDescription;

	CardType(String description, String displayDescription)
	{
		this.description = description;
		this.displayDescription = displayDescription;
	}

	public String getDescription()
	{
		return description;
	}

	public String getType()
	{
		return name();
	}

	public String getDisplayDescription()
	{
		return displayDescription;
	}
}
