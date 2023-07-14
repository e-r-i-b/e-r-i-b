package com.rssl.phizic.gate.bankroll;

/**
 * @author Danilov
 * @ created 12.10.2007
 * @ $Author$
 * @ $Revision$
 */
public enum CardType
{

	debit("���������", "���������"),

	credit("���������", "���������"),

	overdraft("������������", "���������");

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
