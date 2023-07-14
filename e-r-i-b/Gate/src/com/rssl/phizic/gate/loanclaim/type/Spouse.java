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
 * ������ � �������
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
	 * @param name - ��� (never null)
	 * @param birthDay - ���� �������� (never null)
	 * @param dependant - true, ���� ��������� �� ���������
	 * @param haveSBCredit - true, ���� ���� ������ � ��, null - �� ��������
	 * @param marriageContract - true, ���� ���� ������� ��������
	 */
	public Spouse(PersonName name, Calendar birthDay, boolean dependant, Boolean haveSBCredit, boolean marriageContract)
	{
		if (name == null)
		    throw new IllegalArgumentException("�� ������� ��� �������");
		if (birthDay == null)
		    throw new IllegalArgumentException("�� ������� �� �������");

		this.name = name;
		this.birthDay = birthDay;
		this.dependant = dependant;
		this.haveSBCredit = haveSBCredit;
		this.marriageContract = marriageContract;
	}

	/**
	 * @return ��� (never null)
	 */
	public PersonName getName()
	{
		return name;
	}

	/**
	 * @return ���� �������� (never null)
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
	 * @return true, ���� ���� ������� ��������
	 */
	public boolean isMarriageContract()
	{
		return marriageContract;
	}
}
