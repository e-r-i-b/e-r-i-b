package com.rssl.phizic.business.dictionaries.pfp.personData;

/**
 * @author mihaylov
 * @ created 01.03.2012
 * @ $Author$
 * @ $Revision$
 */

/* Семейное положение */
public enum MaritalStatus
{
	MARRIED("Женат/Замужем"),
	NOT_MARRIED("Холост/Не замужем");

	private String description;

	MaritalStatus(String description)
	{
		this.description = description;
	}

	/**
	 * @return описание семейного положения
	 */
	public String getDescription()
	{
		return description;
	}
}
