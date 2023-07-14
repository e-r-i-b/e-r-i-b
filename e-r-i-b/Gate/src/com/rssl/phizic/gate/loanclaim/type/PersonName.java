package com.rssl.phizic.gate.loanclaim.type;

import com.rssl.phizic.common.types.annotation.Immutable;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author Erkin
 * @ created 22.01.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * ФИО персоны
 */
@Immutable
public class PersonName
{
	private final String firstName;

	private final String lastName;

	private final String middleName;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ctor
	 * @param firstName - имя персоны (never null)
	 * @param lastName - фамилия персоны (never null)
	 * @param middleName - отчество персоны (can be null)
	 */
	public PersonName(String firstName, String lastName, String middleName)
	{
		if (StringHelper.isEmpty(firstName))
		    throw new IllegalArgumentException("Не указано имя");
		if (StringHelper.isEmpty(lastName))
			throw new IllegalArgumentException("Не указана фамилия");

		this.firstName = firstName;
		this.lastName = lastName;
		this.middleName = StringHelper.getNullIfEmpty(middleName);
	}

	/**
	 * @return имя персоны (never null)
	 */
	public String getFirstName()
	{
		return firstName;
	}

	/**
	 * @return фамилия персоны (never null)
	 */
	public String getLastName()
	{
		return lastName;
	}

	/**
	 * @return отчество персоны (can be null)
	 */
	public String getMiddleName()
	{
		return middleName;
	}

	@Override
	public int hashCode()
	{
		int result = StringHelper.normalizePersonName(firstName).hashCode();
		result = 31 * result + StringHelper.normalizePersonName(firstName).hashCode();
		return result;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		PersonName other = (PersonName) o;

		boolean rc = StringHelper.equalsAsPersonName(lastName, other.lastName);
		rc = rc && StringHelper.equalsAsPersonName(firstName, other.firstName);
		rc = rc && StringHelper.equalsAsPersonName(middleName, other.middleName);
		return true;
	}
}
