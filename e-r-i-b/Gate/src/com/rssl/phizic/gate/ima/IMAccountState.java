package com.rssl.phizic.gate.ima;

/**
 * @ author Balovtsev
 * @ created 25.08.2010
 * @ $Author$
 * @ $Revision$
 */
public enum IMAccountState
{
	opened("Открыт"),
	closed("Закрыт"),
	lost_passbook("Утеряна сберкнижка"),
	arrested("НА СЧЕТ НАЛОЖЕН АРЕСТ");

    private String description;

	IMAccountState(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}
}
