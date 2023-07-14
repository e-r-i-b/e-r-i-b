package com.rssl.phizic.business.persons;

import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.person.Person;

/**
 * @author Omeliyanchuk
 * @ created 12.12.2006
 * @ $Author$
 * @ $Revision$
 */

public class InvalidPersonStateException extends BusinessLogicException
{
	private final Person person;

	public InvalidPersonStateException(Person person, String message)
	{
		super(message);
		this.person = person;
	}

	/**
	 * @return Персона
	 */
	public Person getPerson()
	{
		return person;
	}
}
