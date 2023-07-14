package com.rssl.phizic.web.webApi.protocol.jaxb.model.request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/** Запрос на редактирование группы операций
 * @author Jatsky
 * @ created 13.08.14
 * @ $Author$
 * @ $Revision$
 */

@XmlRootElement(name = "message")
public class AlfEditOperationGroupRequest extends Request
{
	private AlfEditOperationGroupRequestBody body;

	@XmlElement(name = "body", required = true)
	public AlfEditOperationGroupRequestBody getBody()
	{
		return body;
	}

	public void setBody(AlfEditOperationGroupRequestBody body)
	{
		this.body = body;
	}
}
