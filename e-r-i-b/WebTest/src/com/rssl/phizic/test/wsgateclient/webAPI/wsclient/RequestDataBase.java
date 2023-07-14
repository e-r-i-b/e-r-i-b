package com.rssl.phizic.test.wsgateclient.webAPI.wsclient;

import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;

/**
 * Базовый класс запорса в WebAPI
 * @author Jatsky
 * @ created 13.11.13
 * @ $Author$
 * @ $Revision$
 */

public abstract class RequestDataBase implements RequestData
{
	private static final String REQUEST_NAME = "body";

	public String getName()
	{
		return REQUEST_NAME;
	}

	protected Document createRequest()
	{
		DocumentBuilder builder = XmlHelper.getDocumentBuilder();
		Document document = builder.newDocument();
		document.appendChild(document.createElement(getName()));
		return document;
	}

	protected Element createTag(Document request, String tagName, String tagValue)
	{
		Element tag = request.createElement(tagName);
		tag.appendChild(request.createTextNode(tagValue));
		return tag;
	}
}
