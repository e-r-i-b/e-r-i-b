package com.rssl.phizic.web.ext.sbrf.persons;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * Форма генерации временного логина и пароля
 * @author Jatsky
 * @ created 10.01.14
 * @ $Author$
 * @ $Revision$
 */

public class GenerateDisposableLoginForm extends ActionFormBase
{
	private String disposableLogin;
	private Long personId;
	private String error;

	public String getDisposableLogin()
	{
		return disposableLogin;
	}

	public void setDisposableLogin(String disposableLogin)
	{
		this.disposableLogin = disposableLogin;
	}

	public Long getPersonId()
	{
		return personId;
	}

	public void setPersonId(Long personId)
	{
		this.personId = personId;
	}

	public String getError()
	{
		return error;
	}

	public void setError(String error)
	{
		this.error = error;
	}
}
