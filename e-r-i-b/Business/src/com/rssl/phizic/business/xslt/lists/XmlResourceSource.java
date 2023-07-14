package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.w3c.dom.Document;

import java.io.IOException;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;

/**
 * @author Kosyakov
 * @ created 03.11.2005
 * @ $Author: krenev $
 * @ $Revision: 6543 $
 */

public class XmlResourceSource implements EntityListSource
{
	public static final String PATH_PARAMETER = "path";

	private Document document;

	public XmlResourceSource(String fileName) throws IOException, ParserConfigurationException, SAXException
	{
		document = XmlHelper.loadDocumentFromResource(fileName);
	}

	public XmlResourceSource(Map parameters) throws IOException, ParserConfigurationException, SAXException
	{
		this((String) parameters.get(PATH_PARAMETER));
	}

	public Source getSource(final Map<String, String> params) throws BusinessException
	{
		return new DOMSource(getDocument(params));
	}

	public Document getDocument(Map<String, String> params) throws BusinessException
	{
		return document;
	}
}
