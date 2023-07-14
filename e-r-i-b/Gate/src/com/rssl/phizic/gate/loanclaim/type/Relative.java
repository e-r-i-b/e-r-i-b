package com.rssl.phizic.gate.loanclaim.type;

import com.rssl.phizic.gate.loanclaim.dictionary.FamilyRelation;
import com.rssl.phizic.common.types.annotation.Immutable;
import org.apache.commons.lang.ObjectUtils;

import java.util.Calendar;

/**
 * @author Erkin
 * @ created 22.01.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * Родственник
 */
@Immutable
public class Relative
{
	private final FamilyRelation status;

	private final PersonName name;

	private final Calendar birthDay;

	private final boolean dependant;

	private final Boolean haveSBCredit;

	private final boolean isSBEmployee;

	private final String employeePlace;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ctor
	 * @param status - семейное родство (never null)
	 * @param name - ФИО родственника (never null)
	 * @param birthDay - дата рождения родственника (can be null)
	 * @param dependant - true, если находится на иждивении
	 * @param haveSBCredit - true, если есть кредит в СБ, null - не известно
	 * @param isSBEmployee - true, если родственник - сотрудник СБ
	 * @param employeePlace - место работы родственника (обязательно, если родственник сотрудник СБ)
	 */
	public Relative(FamilyRelation status, PersonName name, Calendar birthDay, boolean dependant, Boolean haveSBCredit, boolean isSBEmployee, String employeePlace)
	{
		if (status == null)
		    throw new IllegalArgumentException("Не указана степень родства родственника");
		if (name == null)
			throw new IllegalArgumentException("Не указаны ФИО родственника");

		this.status = status;
		this.name = name;
		this.birthDay = birthDay;
		this.dependant = dependant;
		this.haveSBCredit = haveSBCredit;
		this.isSBEmployee = isSBEmployee;
		this.employeePlace = employeePlace;
	}

	/**
	 * @return семейное родство (never null)
	 */
	public FamilyRelation getStatus()
	{
		return status;
	}

	/**
	 * @return ФИО родственника (never null)
	 */
	public PersonName getName()
	{
		return name;
	}

	/**
	 * @return дата рождения родственника (can be null)
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
	 * @return true, если родственник - сотрудник СБ
	 */
	public boolean isSBEmployee()
	{
		return isSBEmployee;
	}

	/**
	 * @return название организации, где работает родственник (null, если не сотрудник СБ)
	 */
	public String getEmployeePlace()
	{
		return employeePlace;
	}

	@Override
	public int hashCode()
	{
		int result = status.hashCode();
		result = 31 * result + name.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		Relative other = (Relative) o;

		boolean rc = name.equals(other.name);
		rc = rc && ObjectUtils.equals(birthDay, other.birthDay);
		rc = rc && status == other.status;
		return rc;
	}
}
