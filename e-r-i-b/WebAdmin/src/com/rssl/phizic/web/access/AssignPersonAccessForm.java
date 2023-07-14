package com.rssl.phizic.web.access;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.web.actions.ActionFormBase;


/**
 * Created by IntelliJ IDEA.
 * User: Roshka
 * Date: 07.10.2005
 * Time: 12:55:54
 */
@SuppressWarnings({"JavaDoc"})
public class AssignPersonAccessForm extends ActionFormBase
{
	private Long person;
	private AssignAccessSubForm simpleAccess = new AssignAccessSubForm();
	private AssignAccessSubForm secureAccess = new AssignAccessSubForm();
	private AssignAccessSubForm smsBankingAccess = new AssignAccessSubForm();
	private Boolean             modified=false;
	private String userRegistrationMode;

	public Boolean getModified()
	{
		return modified;
	}

	public void setModified(Boolean modified)
	{
		this.modified = modified;
	}

	public Long getPerson()
	{
		return person;
	}

	public void setPerson(Long person)
	{
		this.person = person;
	}

	public ActivePerson getActivePerson() throws BusinessException
	{
		return (ActivePerson) new PersonService().findById(person);
	}

	public CommonLogin getLogin() throws BusinessException
	{
		return getActivePerson().getLogin();
	}

	public AssignAccessSubForm getSecureAccess()
	{
		return secureAccess;
	}

	public AssignAccessSubForm getSimpleAccess()
	{
		return simpleAccess;
	}

	public AssignAccessSubForm getSmsBankingAccess()
	{
		return smsBankingAccess;
	}

	public String getUserRegistrationMode()
	{
		return userRegistrationMode;
	}

	public void setUserRegistrationMode(String userRegistrationMode)
	{
		this.userRegistrationMode = userRegistrationMode;
	}
}
