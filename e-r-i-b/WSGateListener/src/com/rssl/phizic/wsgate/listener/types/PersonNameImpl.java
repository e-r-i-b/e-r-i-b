package com.rssl.phizic.wsgate.listener.types;

import com.rssl.phizic.gate.payments.owner.PersonName;

/**
 * @author osminin
 * @ created 28.12.2012
 * @ $Author$
 * @ $Revision$
 */
public class PersonNameImpl implements PersonName
{
	private String firstName;
	private String lastName;
	private String middleName;

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getMiddleName()
	{
		return middleName;
	}

	public void setMiddleName(String middleName)
	{
		this.middleName = middleName;
	}

	public String getFullName()
	{
		StringBuilder fullName = new StringBuilder();

		if (lastName != null)
		{
			fullName.append(lastName);
		}
		if (firstName != null)
		{
			if (fullName.length() != 0)
				fullName.append(" ");
			fullName.append(firstName);
		}
		if (middleName != null)
		{
			if (fullName.length() != 0)
				fullName.append(" ");
			fullName.append(middleName);
		}

		return fullName.toString();
	}
}
