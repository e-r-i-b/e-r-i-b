package com.rssl.phizic.business.documents.payments;

import com.rssl.phizic.gate.payments.owner.PersonName;
import com.rssl.phizic.person.Person;

/**
 * @author osminin
 * @ created 21.12.2012
 * @ $Author$
 * @ $Revision$
 */
public class PersonNameImpl implements PersonName
{
	private String lastName;
	private String firstName;
	private String middleName;

	public PersonNameImpl()
	{}

	public PersonNameImpl(Person person)
	{
		this.lastName = person.getSurName();
		this.firstName = person.getFirstName();
		this.middleName = person.getPatrName();
	}

	public PersonNameImpl(String lastName, String firstName, String middleName)
	{
		this.lastName   = lastName;
		this.firstName  = firstName;
		this.middleName = middleName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
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
