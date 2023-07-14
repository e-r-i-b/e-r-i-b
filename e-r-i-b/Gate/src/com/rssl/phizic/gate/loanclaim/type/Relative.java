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
 * �����������
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
	 * @param status - �������� ������� (never null)
	 * @param name - ��� ������������ (never null)
	 * @param birthDay - ���� �������� ������������ (can be null)
	 * @param dependant - true, ���� ��������� �� ���������
	 * @param haveSBCredit - true, ���� ���� ������ � ��, null - �� ��������
	 * @param isSBEmployee - true, ���� ����������� - ��������� ��
	 * @param employeePlace - ����� ������ ������������ (�����������, ���� ����������� ��������� ��)
	 */
	public Relative(FamilyRelation status, PersonName name, Calendar birthDay, boolean dependant, Boolean haveSBCredit, boolean isSBEmployee, String employeePlace)
	{
		if (status == null)
		    throw new IllegalArgumentException("�� ������� ������� ������� ������������");
		if (name == null)
			throw new IllegalArgumentException("�� ������� ��� ������������");

		this.status = status;
		this.name = name;
		this.birthDay = birthDay;
		this.dependant = dependant;
		this.haveSBCredit = haveSBCredit;
		this.isSBEmployee = isSBEmployee;
		this.employeePlace = employeePlace;
	}

	/**
	 * @return �������� ������� (never null)
	 */
	public FamilyRelation getStatus()
	{
		return status;
	}

	/**
	 * @return ��� ������������ (never null)
	 */
	public PersonName getName()
	{
		return name;
	}

	/**
	 * @return ���� �������� ������������ (can be null)
	 */
	public Calendar getBirthDay()
	{
		return birthDay;
	}

	/**
	 * @return true, ���� ��������� �� ���������
	 */
	public boolean isDependant()
	{
		return dependant;
	}

	/**
	 * @return true, ���� ���� ������ � ��, null - �� ��������
	 */
	public Boolean haveSBCredit()
	{
		return haveSBCredit;
	}

	/**
	 * @return true, ���� ����������� - ��������� ��
	 */
	public boolean isSBEmployee()
	{
		return isSBEmployee;
	}

	/**
	 * @return �������� �����������, ��� �������� ����������� (null, ���� �� ��������� ��)
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
