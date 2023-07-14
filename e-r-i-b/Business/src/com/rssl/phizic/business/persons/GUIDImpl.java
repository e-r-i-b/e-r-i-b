package com.rssl.phizic.business.persons;

import com.rssl.phizic.gate.clients.GUID;

import java.util.Calendar;

/**
 * @author osminin
 * @ created 08.12.14
 * @ $Author$
 * @ $Revision$
 *
 * ������� ������������� ������� (���+���+��+��)
 */
public class GUIDImpl implements GUID
{
	private String surName;
	private String firstName;
	private String patrName;
	private String passport;
	private Calendar birthDay;
	private String tb;

	/**
	 * ctor
	 */
	public GUIDImpl()
	{
	}

	/**
	 * ctor
	 * @param surName �������
	 * @param firstName ���
	 * @param patrName ��������
	 * @param passport �������
	 * @param birthDay ���� ��������
	 * @param tb �������
	 */
	public GUIDImpl(String surName, String firstName, String patrName, String passport, Calendar birthDay, String tb)
	{
		this.surName = surName;
		this.firstName = firstName;
		this.patrName = patrName;
		this.passport = passport;
		this.birthDay = birthDay;
		this.tb = tb;
	}

	public String getSurName()
	{
		return surName;
	}

	/**
	 * @param surName �������
	 */
	public void setSurName(String surName)
	{
		this.surName = surName;
	}

	public String getFirstName()
	{
		return firstName;
	}

	/**
	 * @param firstName ���
	 */
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getPatrName()
	{
		return patrName;
	}

	/**
	 * @param patrName ��������
	 */
	public void setPatrName(String patrName)
	{
		this.patrName = patrName;
	}

	public String getPassport()
	{
		return passport;
	}

	/**
	 * @param passport �������
	 */
	public void setPassport(String passport)
	{
		this.passport = passport;
	}

	public Calendar getBirthDay()
	{
		return birthDay;
	}

	/**
	 * @param birthDay ���� ��������
	 */
	public void setBirthDay(Calendar birthDay)
	{
		this.birthDay = birthDay;
	}

	public String getTb()
	{
		return tb;
	}

	/**
	 * @param tb ������� ������������ ������������
	 */
	public void setTb(String tb)
	{
		this.tb = tb;
	}
}
