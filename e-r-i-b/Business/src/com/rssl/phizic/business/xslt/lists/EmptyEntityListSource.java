package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;

/**
 * @author Krenev
 * @ created 08.09.2008
 * @ $Author$
 * @ $Revision$
 */
public class EmptyEntityListSource implements EntityListSource
{
	private static final Document document = getDocument();
	private static final Source source = new DOMSource(document);

	private static Document getDocument()
	{
		DocumentBuilder documentBuilder = XmlHelper.getDocumentBuilder();
		Document document = documentBuilder.newDocument();
		Element root = document.createElement("entity-list");
		document.appendChild(root);
		return document;
	}

	public Source getSource(Map<String, String> params) throws BusinessException
	{
		return source;
	}

	public Document getDocument(Map<String, String> params) throws BusinessException
	{
		return document;
	}
}
