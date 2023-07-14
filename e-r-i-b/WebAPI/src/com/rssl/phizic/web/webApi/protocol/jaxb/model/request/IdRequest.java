package com.rssl.phizic.web.webApi.protocol.jaxb.model.request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Запрос с параметром "id"
 * @author Jatsky
 * @ created 06.05.14
 * @ $Author$
 * @ $Revision$
 */

@XmlRootElement(name = "message")
public class IdRequest extends Request
{
	private IdRequestBody body;

	@Override
	@XmlElement(name = "body", required = true)
	public IdRequestBody getBody()
	{
		return body;
	}

	public void setBody(IdRequestBody body)
	{
		this.body = body;
	}
}
