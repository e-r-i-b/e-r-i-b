package com.rssl.phizic.web.gate.services.types;

import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.payments.owner.EmployeeInfo;
import com.rssl.phizic.gate.payments.owner.PersonName;

/**
 * @author osminin
 * @ created 26.12.2012
 * @ $Author$
 * @ $Revision$
 */
public class EmployeeInfoImpl implements EmployeeInfo
{
	private String guid;
	private String login;
	private PersonName personName;
	private Office employeeOffice;

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
		return login;
	}

	public void setLogin(String login)
	{
		this.login = login;
	}

	public PersonName getPersonName()
	{
		return personName;
	}

	public void setPersonName(PersonName personName)
	{
		this.personName = personName;
	}

	public Office getEmployeeOffice()
	{
		return employeeOffice;
	}

	public void setEmployeeOffice(Office employeeOffice)
	{
		this.employeeOffice = employeeOffice;
	}
}
