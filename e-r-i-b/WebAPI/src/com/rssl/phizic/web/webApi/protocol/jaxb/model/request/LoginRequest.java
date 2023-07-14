package com.rssl.phizic.web.webApi.protocol.jaxb.model.request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Запрос на аутентификацию пользователя
 * @author Jatsky
 * @ created 30.04.14
 * @ $Author$
 * @ $Revision$
 */

@XmlRootElement(name = "message")
public class LoginRequest extends Request
{
	private LoginRequestBody body;

	@Override
	@XmlElement(name = "body", required = true)
	public LoginRequestBody getBody()
	{
		return body;
	}

	public void setBody(LoginRequestBody body)
	{
		this.body = body;
	}
}
