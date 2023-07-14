package com.rssl.auth.csa.wsclient.requests;

import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;

/**
 * Базовый класс запроса в CSABack
 * @author niculichev
 * @ created 30.08.2012
 * @ $Author$
 * @ $Revision$
 */
public abstract class RequestDataBase implements RequestData
{
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
