package com.rssl.phizgate.common.documents.payments;

import com.rssl.phizic.gate.payments.owner.EmployeeInfo;
import com.rssl.phizic.gate.payments.owner.PersonName;
import com.rssl.phizic.gate.dictionaries.officies.Office;

/**
 * ���������� �� ����������
 * 
 * @author khudyakov
 * @ created 30.04.2013
 * @ $Author$
 * @ $Revision$
 */
public class EmployeeInfoImpl implements EmployeeInfo
{
	public EmployeeInfoImpl()
	{}

	public EmployeeInfoImpl(String guid, PersonName personName)
	{
		this.guid = guid;
		this.personName = personName;
	}


	private String guid;
	private PersonName personName;

	public String getGuid()
	{
		return guid;
	}

	public void setGuid(String guid)
	{
		this.guid = guid;
	}

	public String getLogin()
	{
		//� ���� ���������� guid ����������
		return null;
	}

	public Office getEmployeeOffice()
	{
		//� ���� ���������� guid ����������
		return null;
	}

	public PersonName getPersonName()
	{
		return personName;
	}

	public void setPersonName(PersonName personName)
	{
		this.personName = personName;
	}
}
