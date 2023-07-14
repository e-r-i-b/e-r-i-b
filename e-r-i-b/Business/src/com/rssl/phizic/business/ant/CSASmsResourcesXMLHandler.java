package com.rssl.phizic.business.ant;

import com.rssl.phizic.business.sms.CSASmsResource;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author  Balovtsev
 * @version 03.04.13 14:04
 */
public class CSASmsResourcesXMLHandler extends DefaultHandler
{
	private static final String DEFAULT_KEY_NAME        = "default";

	private static final String ELEMENT_DEFAULT_MESSAGE = "default-message";
	private static final String ELEMENT_MESSAGE         = "message";
	private static final String ELEMENT_TEXT            = "text";
	private static final String ELEMENT_KEY             = "key";
	private static final String ELEMENT_VARIABLES       = "variables";
	private static final String ELEMENT_DESCRIPTION     = "description";

	private List<CSASmsResource> resources = new ArrayList<CSASmsResource>();
	private CSASmsResource       smsResource;

	private boolean isKey;
	private boolean isText;
	private boolean isVariables;
	private boolean isDescription;

	private StringBuffer innerText = new StringBuffer();

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
	{
		if (qName.equals(ELEMENT_MESSAGE))
		{
			smsResource = new CSASmsResource();
		}

		if (qName.equals(ELEMENT_DEFAULT_MESSAGE))
		{
			smsResource = new CSASmsResource();
			smsResource.setKey(DEFAULT_KEY_NAME);
		}

		if (qName.equals(ELEMENT_KEY))
		{
			isKey = true;
		}

		if (qName.equals(ELEMENT_DESCRIPTION))
		{
			isDescription = true;
		}

		if (qName.equals(ELEMENT_TEXT))
		{
			isText = true;
		}

		if (qName.equals(ELEMENT_VARIABLES))
		{
			isVariables = true;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException
	{
		innerText.append(new String(ch, start, length));
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException
	{
		if (qName.equals(ELEMENT_DEFAULT_MESSAGE) || qName.equals(ELEMENT_MESSAGE))
		{
			resources.add(smsResource);
			return;
		}

		String text = innerText.toString().trim().replaceAll("([\\ ]{1,})((?=\\ )|(?=\\t))|([\\t]{1,})|([\\n])(?=\\ )", "");

		if (isKey)
		{
			smsResource.setKey(text);
			isKey = false;
		}

		if (isText)
		{
			smsResource.setText(text);
			isText = false;
		}

		if (isVariables)
		{
			smsResource.setVariables(text.trim());
			isVariables = false;
		}

		if (isDescription)
		{
			smsResource.setDescription(text);
			isDescription = false;
		}

		innerText.delete(0, innerText.length());
	}

	/**
	 *
	 * Возвращает список сообщений полученных из xml
	 *
	 * @return List&lt;CSASmsResource&gt;
	 */
	public List<CSASmsResource> getResources()
	{
		//Не возвращать немодифицируемый список
		return resources;
	}
}
