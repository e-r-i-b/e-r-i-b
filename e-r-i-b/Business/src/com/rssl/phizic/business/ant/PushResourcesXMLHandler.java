package com.rssl.phizic.business.ant;

import com.rssl.phizic.messaging.mail.messagemaking.push.PushPrivacyType;
import com.rssl.phizic.messaging.mail.messagemaking.push.PushPublicityType;
import com.rssl.phizic.business.push.PushResource;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.*;

/**
 * распознает из файлов push-сообщения
 * @author basharin
 * @ created 07.10.13
 * @ $Author$
 * @ $Revision$
 */

public class PushResourcesXMLHandler extends DefaultHandler
{
	private static final String ELEMENT_MESSAGE         = "message";
	private static final String ELEMENT_KEY             = "key";
	private static final String ELEMENT_VARIABLES       = "variables";
	private static final String ELEMENT_DESCRIPTION     = "description";

	private static final String ELEMENT_SHORT_MESSAGE    = "shortMessage";
	private static final String ELEMENT_TEXT             = "text";
	private static final String ELEMENT_PRIORITY         = "priority";
	private static final String ELEMENT_CODE             = "code";
	private static final String ELEMENT_PRIVACY_TYPE     = "privacyType";
	private static final String ELEMENT_PUBLICITY_TYPE   = "publicityType";
	private static final String ELEMENT_SMS_BACKUP       = "smsBackup";
	private static final String ELEMENT_ATTRIBUTES       = "attributes";

	private List<PushResource> resourcesList = new ArrayList<PushResource>();
	private PushResource currentMsgRes;

	private final Class<? extends PushResource> pushResourcesClass;

	private boolean isKey;
	private boolean isText;
	private boolean isVariables;
	private boolean isDescription;


	private boolean isShortMessage;
	private boolean isPriority;
	private boolean isCode;
	private boolean isPrivacyType;
	private boolean isPublicityType;
	private boolean isSmsBackup;
	private boolean isAttributes;

	private StringBuffer innerText      = new StringBuffer();

	/**
	 * @param pushResourcesClass указывает в этом случае на то, к какому типу относятся push-сообщения прочтенные из документа.
	 *
	 * <br/>В случае подтверждающих push-сообщений - com.rssl.phizic.business.push.PushConfirmationResource.
	 * <br/>В случае push-сообщений информирующих - com.rssl.phizic.business.push.PushInformingResource.
	 */
	public PushResourcesXMLHandler(Class<? extends PushResource> pushResourcesClass)
	{
		this.pushResourcesClass = pushResourcesClass;
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
	{
		if (qName.equals(ELEMENT_MESSAGE))
		{
			currentMsgRes = createPushTemplateInstances();
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

		if (qName.equals(ELEMENT_SHORT_MESSAGE))
		{
			isShortMessage = true;
		}

		if (qName.equals(ELEMENT_CODE))
		{
			isCode = true;
		}
		if (qName.equals(ELEMENT_PRIVACY_TYPE))
		{
			isPrivacyType = true;
		}
		if (qName.equals(ELEMENT_PUBLICITY_TYPE))
		{
			isPublicityType = true;
		}
		if (qName.equals(ELEMENT_SMS_BACKUP))
		{
			isSmsBackup = true;
		}
		if (qName.equals(ELEMENT_ATTRIBUTES))
		{
			isAttributes = true;
		}
	}

	protected PushResource createPushTemplateInstances()
	{
		try
		{
			return pushResourcesClass.newInstance();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
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

		if (qName.equals(ELEMENT_MESSAGE))
		{
			resourcesList.add(currentMsgRes);
			currentMsgRes = null;
			return;
		}
		// удаляем лишние пробелы и табуляцию
		String text = innerText.toString().trim().replaceAll("([ ]+)((?= )|(?=\\t))|([\\t]+)", "");

		if (isKey)
		{
			if (currentMsgRes != null);
			{
				currentMsgRes.setKey(text.trim());
			}

			isKey = false;
		}

		if (isText)
		{
			if (currentMsgRes != null);
			{
				currentMsgRes.setText(text);
			}

			isText = false;
		}

		if (isVariables)
		{
			if (currentMsgRes != null);
			{
				currentMsgRes.setVariables(text.trim());
			}
			isVariables = false;
		}

		if (isDescription)
		{
			if (currentMsgRes != null);
			{
				currentMsgRes.setDescription(text.trim());
			}
			isDescription = false;
		}

		if (isPriority)
		{
			if (currentMsgRes != null);
			{
				currentMsgRes.setPriority(Long.valueOf(text.trim()));
			}
			isPriority = false;
		}

		if (isShortMessage)
		{
			if (currentMsgRes != null);
			{
				currentMsgRes.setShortMessage(text.trim());
			}
			isShortMessage = false;
		}

		if (isCode)
		{
			if (currentMsgRes != null);
			{
				currentMsgRes.setTypeCode(Integer.valueOf(text.trim()));
			}
			isCode = false;
		}

		if (isPrivacyType)
		{
			if (currentMsgRes != null);
			{
				currentMsgRes.setPrivacyType(PushPrivacyType.valueOf(text.trim()));
			}
			isPrivacyType = false;
		}

		if (isPublicityType)
		{
			if (currentMsgRes != null);
			{
				currentMsgRes.setPublicityType(PushPublicityType.valueOf(text.trim()));
			}
			isPublicityType = false;
		}

		if (isSmsBackup)
		{
			if (currentMsgRes != null);
			{
				currentMsgRes.setSmsBackup(text.trim().equals("1"));
			}
			isSmsBackup = false;
		}

		if (isAttributes)
		{
			if (currentMsgRes != null)
			{
				currentMsgRes.setAttributes(text);
			}
			isAttributes = false;
		}

		innerText.delete(0, innerText.length());

	}

	/**
	 *
	 * @return список шаблонов push-сообщений
	 */
	public List<PushResource> getResourcesList()
	{
		return resourcesList;
	}


}
