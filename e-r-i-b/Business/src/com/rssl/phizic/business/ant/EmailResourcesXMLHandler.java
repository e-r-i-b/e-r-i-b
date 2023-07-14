package com.rssl.phizic.business.ant;

import com.rssl.phizic.business.email.EmailResource;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lukina
 * @ created 16.10.2013
 * @ $Author$
 * @ $Revision$
 */
public class EmailResourcesXMLHandler extends DefaultHandler
{
	private List<EmailResource> resourcesList = new ArrayList<EmailResource>();
	private EmailResource emailResource;

	private static final String ELEMENT_MESSAGE         = "message";
	private static final String ELEMENT_PLAIN_TEXT      = "plainText";
	private static final String ELEMENT_HTML_TEXT       = "htmlText";
	private static final String ELEMENT_KEY             = "key";
	private static final String ELEMENT_THEME           = "theme";
	private static final String ELEMENT_VARIABLES       = "variables";
	private static final String ELEMENT_DESCRIPTION     = "description";

	private boolean isKey;
	private boolean isTheme;
	private boolean isPlainText;
	private boolean isHtmlText;
	private boolean isVariables;
	private boolean isDescription;

	private StringBuffer innerText      = new StringBuffer();

	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
	{
		if (qName.equals(ELEMENT_MESSAGE))
		{
			emailResource = new EmailResource();
		}

		if (qName.equals(ELEMENT_KEY))
		{
			isKey = true;
		}
		if (qName.equals(ELEMENT_THEME))
		{
			isTheme = true;
		}

		if (qName.equals(ELEMENT_DESCRIPTION))
		{
			isDescription = true;
		}

		if (qName.equals(ELEMENT_HTML_TEXT))
		{
			isHtmlText = true;
		}
		if (qName.equals(ELEMENT_PLAIN_TEXT))
		{
			isPlainText = true;
		}

		if (qName.equals(ELEMENT_VARIABLES))
		{
			isVariables = true;
		}
	}


	public void characters(char[] ch, int start, int length) throws SAXException
	{
		innerText.append(new String(ch, start, length));
	}


	public void endElement(String uri, String localName, String qName) throws SAXException
	{
		if (qName.equals(ELEMENT_MESSAGE))
		{
			resourcesList.add(emailResource);
			return;
		}

		String text = innerText.toString().trim().replaceAll("([\\ ]{1,})((?=\\ )|(?=\\t))|([\\t]{1,})|([\\n])(?=\\ )", "");

		if (isKey)
		{
			emailResource.setKey(text);
			isKey = false;
		}

		if (isTheme)
		{
			emailResource.setTheme(text);
			isTheme = false;
		}

		if (isPlainText)
		{
			emailResource.setPlainText(text);
			isPlainText = false;
		}
		if (isHtmlText)
		{
			emailResource.setHtmlText(text);
			isHtmlText = false;
		}

		if (isVariables)
		{
			emailResource.setVariables(text.trim());
			isVariables = false;
		}

		if (isDescription)
		{
			emailResource.setDescription(text);
			isDescription = false;
		}

		innerText.delete(0, innerText.length());
	}

	public List<EmailResource> getResourcesList()
	{
		//Не возвращать немодифицируемый список
		return resourcesList;
	}

}
