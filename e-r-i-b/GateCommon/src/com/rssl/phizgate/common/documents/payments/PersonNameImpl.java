package com.rssl.phizgate.common.documents.payments;

import com.rssl.phizic.gate.payments.owner.PersonName;

/**
 * @author khudyakov
 * @ created 07.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class PersonNameImpl implements PersonName
{
	public PersonNameImpl()
	{}

	public PersonNameImpl(String surName, String firstName, String middleName)
	{
		fullName = surName + " " + firstName + " " + middleName;
	}


	private String fullName;

	public String getLastName()
	{
		//� ���� �� ����������
		return null;
	}

	public String getFirstName()
	{
		//� ���� �� ����������
		return null;
	}

	public String getMiddleName()
	{
		//� ���� �� ����������
		return null;
	}

	public String getFullName()
	{
		return fullName;
	}

	public void setFullName(String fullName)
	{
		this.fullName = fullName;
	}
}
