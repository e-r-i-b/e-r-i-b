package com.rssl.phizic.limits.handlers;

import java.util.Calendar;

/**
 * @author osminin
 * @ created 21.01.14
 * @ $Author$
 * @ $Revision$
 *
 * ���������� � �������
 */
public class ProfileInfo
{
	private String firstName;
	private String surName;
	private String patrName;
	private String passport;
	private String tb;
	private Calendar birthDate;

	/**
	 * ����������
	 * @param firstName ���
	 * @param surName �������
	 * @param patrName ��������
	 * @param passport ���
	 * @param tb �������
	 * @param birthDate ���� ��������
	 */
	public ProfileInfo(String firstName, String surName, String patrName, String passport, String tb, Calendar birthDate)
	{
		this.firstName = firstName;
		this.surName = surName;
		this.patrName = patrName;
		this.passport = passport;
		this.tb = tb;
		this.birthDate = birthDate;
	}

	/**
	 * @return ���
	 */
	public String getFirstName()
	{
		return firstName;
	}

	/**
	 * @return �������
	 */
	public String getSurName()
	{
		return surName;
	}

	/**
	 * @return ��������
	 */
	public String getPatrName()
	{
		return patrName;
	}

	/**
	 * @return ���
	 */
	public String getPassport()
	{
		return passport;
	}

	/**
	 * @return �������
	 */
	public String getTb()
	{
		return tb;
	}

	/**
	 * @return ���� ��������
	 */
	public Calendar getBirthDate()
	{
		return birthDate;
	}
}
