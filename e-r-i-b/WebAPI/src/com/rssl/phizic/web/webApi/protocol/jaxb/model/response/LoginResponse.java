package com.rssl.phizic.web.webApi.protocol.jaxb.model.response;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.PersonTag;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Ответ на запрос аутентификации пользователя
 * @author Jatsky
 * @ created 30.04.14
 * @ $Author$
 * @ $Revision$
 */

@XmlType(propOrder = {"status", "loginCompleted", "person"})
@XmlRootElement(name = "message")
public class LoginResponse extends Response
{
	private boolean loginCompleted;
	private PersonTag person;

	@XmlElement(name = "loginCompleted", required = false)
	public boolean isLoginCompleted()
	{
		return loginCompleted;
	}

	public void setLoginCompleted(boolean loginCompleted)
	{
		this.loginCompleted = loginCompleted;
	}

	@XmlElement(name = "person", required = false)
	public PersonTag getPerson()
	{
		return person;
	}

	public void setPerson(PersonTag person)
	{
		this.person = person;
	}
}
