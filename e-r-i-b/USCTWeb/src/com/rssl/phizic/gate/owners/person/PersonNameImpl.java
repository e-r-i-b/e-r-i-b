package com.rssl.phizic.gate.owners.person;

import com.rssl.phizic.gate.payments.owner.PersonName;

/**
 * @author khudyakov
 * @ created 24.04.14
 * @ $Author$
 * @ $Revision$
 */
public class PersonNameImpl implements PersonName
{
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

