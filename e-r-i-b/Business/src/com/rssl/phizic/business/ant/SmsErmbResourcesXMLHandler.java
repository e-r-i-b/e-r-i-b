package com.rssl.phizic.business.ant;

import com.rssl.phizic.business.sms.SMSResource;
import com.rssl.phizic.common.types.limits.ChannelType;
import com.rssl.phizic.utils.StringHelper;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.*;

/**
 * Используется для чтения смс-шаблонов ЕРМБ из ermb-sms-texts.xml
 * @author Rtischeva
 * @created 01.08.13
 * @ $Author$
 * @ $Revision$
 */
public class SmsErmbResourcesXMLHandler extends DefaultHandler
{
	private static final String ELEMENT_MESSAGE         = "message";
	private static final String ELEMENT_TEXT            = "text";
	private static final String ELEMENT_KEY             = "key";
	private static final String ELEMENT_VARIABLES       = "variables";
	private static final String ELEMENT_DESCRIPTION     = "description";
	private static final String ELEMENT_PRIORITY        = "priority";

	private List<SMSResource> resourcesList = new ArrayList<SMSResource>();
	private SMSResource currentMsgRes;
	private final Class<? extends SMSResource> smsResourcesClass;

	private boolean isKey;
	private boolean isText;
	private boolean isVariables;
	private boolean isDescription;
	private boolean isPriority;

	private StringBuffer innerText      = new StringBuffer();
	private ChannelType  currentChannel = ChannelType.ERMB_SMS;

	public SmsErmbResourcesXMLHandler(Class<? extends SMSResource> smsResourcesClass)
	{
		this.smsResourcesClass = smsResourcesClass;
	}

	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
	{
		if (qName.equals(ELEMENT_MESSAGE))
        {
            currentMsgRes = createSMSTemplateInstance();
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
		if (qName.equals(ELEMENT_PRIORITY))
		{
			isPriority = true;
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
			currentMsgRes.setCustom(true);
			resourcesList.add(currentMsgRes);
			return;
		}

		// удаляем лишние пробелы и табуляцию
		String text = innerText.toString().trim().replaceAll("([ ]+)((?= )|(?=\\t))|([\\t]+)", "");

		if (isKey)
		{
			currentMsgRes.setKey(text.trim());
			isKey = false;
		}

		if (isText)
		{
			currentMsgRes.setChannel(currentChannel);

			if (StringHelper.isNotEmpty(text))
			{
				currentMsgRes.setText(text);
				currentMsgRes.setCustom(true);
			}

			isText = false;
		}

		if (isVariables)
		{
			currentMsgRes.setVariables(text.trim());
			isVariables = false;
		}

		if (isDescription)
		{
			currentMsgRes.setDescription(text.trim());
			isDescription = false;
		}
		if (isPriority)
		{
			currentMsgRes.setPriority(Long.valueOf(text.trim()));
			isPriority = false;
		}
		innerText.delete(0, innerText.length());
	}

	protected SMSResource createSMSTemplateInstance()
	{
		try
		{
			return smsResourcesClass.newInstance();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 *
	 * @return List&lt;SMSResource&gt;
	 */
	public List<SMSResource> getResourcesList()
	{
		//Не возвращать немодифицируемый список
		return resourcesList;
	}
}
