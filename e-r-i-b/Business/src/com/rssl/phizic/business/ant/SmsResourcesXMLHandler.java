package com.rssl.phizic.business.ant;

import com.rssl.phizic.business.sms.SMSResource;
import com.rssl.phizic.common.types.limits.ChannelType;
import com.rssl.phizic.utils.StringHelper;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.*;

/**
 *
 * »спользуетс€ дл€ чтени€ шаблонов смс из файлов sms-password-config.xml, sms-refuse-config.xml.
 *
 * @author  Balovtsev
 * @version 18.03.13 15:57
 */
public class SmsResourcesXMLHandler extends DefaultHandler
{
	private static final String DEFAULT_KEY_NAME        = "default";

	private static final String ELEMENT_DEFAULT_MESSAGE = "default-message";
	private static final String ELEMENT_MESSAGE         = "message";
	private static final String ELEMENT_TEXT            = "text";
	private static final String ELEMENT_KEY             = "key";
	private static final String ELEMENT_VARIABLES       = "variables";
	private static final String ELEMENT_DESCRIPTION     = "description";
	private static final String ELEMENT_PRIORITY        = "priority";
	private static final int    INITIAL_CAPACITY        = 5;

	private List<SMSResource>             resourcesList = new ArrayList<SMSResource>();
	private Map<ChannelType, SMSResource> currentMsgRes = new HashMap<ChannelType, SMSResource>(INITIAL_CAPACITY);

	private final Class<? extends SMSResource> smsResourcesClass;

	private boolean isKey;
	private boolean isText;
	private boolean isVariables;
	private boolean isDescription;
	private boolean isPriority;

	private StringBuffer innerText      = new StringBuffer();
	private ChannelType  currentChannel = ChannelType.INTERNET_CLIENT;

	/**
	 * @param smsResourcesClass указывает в этом случае на то, к какому типу относ€тс€ смс прочтенные из документа.
	 *
	 * <br/>¬ случае подтверждающих смс - com.rssl.phizic.business.sms.SMSConfirmationResource.
	 * <br/>¬ случае смс информирующих об отказе - com.rssl.phizic.business.sms.SMSRefusingResource.
	 * <br/>¬ случае смс информирующих - com.rssl.phizic.business.sms.SMSInformingResource.
	 */
	public SmsResourcesXMLHandler(Class<? extends SMSResource> smsResourcesClass)
	{
		this.smsResourcesClass = smsResourcesClass;
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
	{
		if (qName.equals(ELEMENT_MESSAGE))
		{
			currentMsgRes = createSMSTemplateInstances(false);
		}

		if (qName.equals(ELEMENT_DEFAULT_MESSAGE))
		{
			currentMsgRes = createSMSTemplateInstances(true);
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

			String attributeValue = attributes.getValue("channel");
			if (StringHelper.isNotEmpty(attributeValue))
			{
				if (attributeValue.equals(ChannelType.SELF_SERVICE_DEVICE.name()))
				{
					currentChannel = ChannelType.SELF_SERVICE_DEVICE;
				}

				if (attributeValue.equals(ChannelType.INTERNET_CLIENT.name()))
				{
					currentChannel = ChannelType.INTERNET_CLIENT;
				}

				if (attributeValue.equals(ChannelType.MOBILE_API.name()))
				{
					currentChannel = ChannelType.MOBILE_API;
				}

				if (attributeValue.equals(ChannelType.ERMB_SMS.name()))
				{
					currentChannel = ChannelType.ERMB_SMS;
				}

                if (attributeValue.equals(ChannelType.SOCIAL_API.name()))
                {
                    currentChannel = ChannelType.SOCIAL_API;
                }
			}
			else
			{
				currentChannel = ChannelType.INTERNET_CLIENT;
			}
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
			Iterator<ChannelType> iterator = currentMsgRes.keySet().iterator();
			while (iterator.hasNext())
			{
				ChannelType type = iterator.next();
				SMSResource resource = currentMsgRes.get(type);
				if (StringHelper.isEmpty(resource.getKey()))
				{
					resource.setKey(DEFAULT_KEY_NAME);
					continue;
				}

				if (type == ChannelType.INTERNET_CLIENT)
				{
					continue;
				}

				if (StringHelper.isEmpty(resource.getText()))
				{
					if (type == ChannelType.ERMB_SMS)
						iterator.remove();
					else
					{
						resource.setText(currentMsgRes.get(ChannelType.INTERNET_CLIENT).getText());
						resource.setCustom(false);
					}
				}
			}

			resourcesList.addAll(currentMsgRes.values());
			currentMsgRes.clear();
			return;
		}
		// удал€ем лишние пробелы и табул€цию
		String text = innerText.toString().trim().replaceAll("([ ]+)((?= )|(?=\\t))|([\\t]+)", "");

		if (isKey)
		{
			for (SMSResource resource : currentMsgRes.values())
			{
				resource.setKey(text.trim());
			}

			isKey = false;
		}

		if (isText)
		{
			SMSResource resource = currentMsgRes.get(currentChannel);
			resource.setChannel(currentChannel);

			if (StringHelper.isNotEmpty(text))
			{
				resource.setText(text);
				resource.setCustom(currentChannel != ChannelType.INTERNET_CLIENT);
			}

			isText = false;
		}

		if (isVariables)
		{
			currentMsgRes.get(ChannelType.INTERNET_CLIENT).setVariables(text.trim());
			isVariables = false;
		}

		if (isDescription)
		{
			currentMsgRes.get(ChannelType.INTERNET_CLIENT).setDescription(text.trim());
			isDescription = false;
		}

		if (isPriority)
		{
			//дл€ всех каналов необходимо выставить приоритет, если канал существует.
			for (ChannelType type : ChannelType.values())
			{
				if (currentMsgRes.containsKey(type))
				{
					currentMsgRes.get(type).setPriority(Long.valueOf(text.trim()));
				}
			}
			isPriority = false;
		}
		innerText.delete(0, innerText.length());
	}

	/**
	 *
	 * @return List&lt;SMSResource&gt;
	 */
	public List<SMSResource> getResourcesList()
	{
		//Ќе возвращать немодифицируемый список
		return resourcesList;
	}

	protected Map<ChannelType, SMSResource> createSMSTemplateInstances(boolean defaultMsg)
	{
		Map<ChannelType, SMSResource> res = new HashMap<ChannelType, SMSResource>();

		if (defaultMsg)
		{
			try
			{
				SMSResource template = smsResourcesClass.newInstance();
				template.setChannel(ChannelType.INTERNET_CLIENT);

				res.put(template.getChannel(), template);
			}
			catch (Exception e)
			{
				throw new RuntimeException(e);
			}

			return res;
		}

		for (int i = 0; i < INITIAL_CAPACITY; i++)
		{
			try
			{
				SMSResource template = smsResourcesClass.newInstance();
				switch (i)
				{
					case 1:
					{
						template.setChannel(ChannelType.MOBILE_API);
						break;
					}
					case 2:
					{
						template.setChannel(ChannelType.SELF_SERVICE_DEVICE);
						break;
					}
					case 3:
					{
						template.setChannel(ChannelType.ERMB_SMS);
						break;
					}
                    case 4:
                    {
                        template.setChannel(ChannelType.SOCIAL_API);
                        break;
                    }
					default:
					{
						template.setChannel(ChannelType.INTERNET_CLIENT);
					}
				}

				res.put(template.getChannel(), template);
			}
			catch (Exception e)
			{
				throw new RuntimeException(e);
			}
		}

		return res;
	}
}
