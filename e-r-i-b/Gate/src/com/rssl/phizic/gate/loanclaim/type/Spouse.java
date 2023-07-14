package com.rssl.phizic.gate.loanclaim.type;

import com.rssl.phizic.common.types.annotation.Immutable;

import java.util.Calendar;

/**
 * @author Erkin
 * @ created 22.01.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * Данные о супруге
 */
@Immutable
public class Spouse
{
	private final PersonName name;

	private final Calendar birthDay;

	private final boolean dependant;

	private final Boolean haveSBCredit;

	private final boolean marriageContract;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ctor
	 * @param name - ФИО (never null)
	 * @param birthDay - дата рождения (never null)
	 * @param dependant - true, если находится на иждивении
	 * @param haveSBCredit - true, если есть кредит в СБ, null - не известно
	 * @param marriageContract - true, если есть брачный контракт
	 */
	public Spouse(PersonName name, Calendar birthDay, boolean dependant, Boolean haveSBCredit, boolean marriageContract)
	{
		if (name == null)
		    throw new IllegalArgumentException("Не указаны ФИО супруга");
		if (birthDay == null)
		    throw new IllegalArgumentException("Не указано ДР супруга");

		this.name = name;
		this.birthDay = birthDay;
		this.dependant = dependant;
		this.haveSBCredit = haveSBCredit;
		this.marriageContract = marriageContract;
	}

	/**
	 * @return ФИО (never null)
	 */
	public PersonName getName()
	{
		return name;
	}

	/**
	 * @return дата рождения (never null)
	 */
	public Calendar getBirthDay()
	{
		return birthDay;
	}

	/**
	 * @return true, если находится на иждивении
	 */
	public boolean isDependant()
	{
		return dependant;
	}

	/**
	 * @return true, если есть кредит в СБ, null - не известно
	 */
	public Boolean haveSBCredit()
	{
		return haveSBCredit;
	}

	/**
	 * @return true, если есть брачный контракт
	 */
	public boolean isMarriageContract()
	{
		return marriageContract;
	}
}
