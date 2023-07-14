package com.rssl.phizic.web.webApi.protocol.jaxb.model.request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Тело запроса на аутентификацию пользователя
 * @author Jatsky
 * @ created 30.04.14
 * @ $Author$
 * @ $Revision$
 */

@XmlRootElement(name = "body")
public class LoginRequestBody extends SimpleRequestBody
{
	private String token;
	private Boolean isAuthenticationCompleted;

	@XmlElement(name = "authToken", required = true)
	public String getToken()
	{
		return token;
	}

	public void setToken(String token)
	{
		this.token = token;
	}

	@XmlElement(name = "isAuthenticationCompleted", required = true)
	public Boolean getAuthenticationCompleted()
	{
		return isAuthenticationCompleted;
	}

	public void setAuthenticationCompleted(Boolean authenticationCompleted)
	{
		isAuthenticationCompleted = authenticationCompleted;
	}
}

