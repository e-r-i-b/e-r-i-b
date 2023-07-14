package com.rssl.phizic.gate.owners.employee;

import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.payments.owner.EmployeeInfo;
import com.rssl.phizic.gate.payments.owner.PersonName;

/**
 * @author khudyakov
 * @ created 24.04.14
 * @ $Author$
 * @ $Revision$
 */
public class EmployeeInfoImpl implements EmployeeInfo
{
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
		//в ЕСУШ достаточно guid сотрудника
		return null;
	}

	public Office getEmployeeOffice()
	{
		//в ЕСУШ достаточно guid сотрудника
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

