
package com.rssl.phizic.operations.locale;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.locale.entities.ERIBStaticMessage;
import com.rssl.phizic.locale.services.MultiInstanceEribStaticMessageService;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.StringHelper;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.StringWriter;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * @author koptyaev
 * @ created 18.09.2014
 * @ $Author$
 * @ $Revision$
 */
public class UnloadEribMessagesOperation extends OperationBase
{
	private static final MultiInstanceEribStaticMessageService MESSAGE_SERVICE = new MultiInstanceEribStaticMessageService();
	private String localeId;

	/**
	 * Установить локаль для выгрузки
	 * @param id идентификатор локали
	 */
	public void setUnloadedId(String id)
	{
		localeId = id;
	}


	/**
	 * Получить текст файла выгрузки
	 * @return содержимое файла
	 * @throws BusinessException
	 */
	public String getFile() throws BusinessException
	{
		try
		{
			List<ERIBStaticMessage> list = getMessagesList();

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement(LocaleMessageTags.RESOURCES_TAG_NAME);
			rootElement.setAttribute(LocaleMessageTags.LOCALE_NAME_ATTRIBUTE_NAME, localeId);
			doc.appendChild(rootElement);

			String currBundle = "";
			Element bundle = null;
			for (ERIBStaticMessage eribMessage : list)
			{
				if(!StringHelper.equals(currBundle, eribMessage.getBundle()))
				{
					bundle = getBundleElement(doc, eribMessage);
					rootElement.appendChild(bundle);
					currBundle = eribMessage.getBundle();
				}
				if (bundle!=null)
					bundle.appendChild(getMessageElement(doc, eribMessage));
			}

			OutputFormat format = new OutputFormat(doc);
			format.setIndenting(true);
			format.setEncoding("windows-1251");
			StringWriter stringWriter = new StringWriter();
			XMLSerializer serializer = new XMLSerializer(stringWriter, format);
			serializer.serialize(doc);
			return stringWriter.toString();
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	private List<ERIBStaticMessage> getMessagesList() throws BusinessException
	{
		try
		{
			return MESSAGE_SERVICE.getMessagesForLocale(localeId, getInstanceName());
		}
		catch (SystemException e)
		{
			throw new BusinessException(e);
		}
	}

	private Element getBundleElement(Document document, ERIBStaticMessage staticMessage)
	{
		Element bundle = document.createElement(LocaleMessageTags.BUNDLE_TAG_NAME);
		bundle.setAttribute(LocaleMessageTags.BUNDLE_NAME_ATTRIBUTE_NAME, staticMessage.getBundle());
		return bundle;
	}

	private Element getMessageElement(Document document, ERIBStaticMessage staticMessage)
	{
		Element message = document.createElement(LocaleMessageTags.MESSAGE_TAG_NAME);
		message.setAttribute(LocaleMessageTags.MESSAGE_KEY_ATTRIBUTE_NAME, staticMessage.getKey());
		message.setTextContent(staticMessage.getMessage());
		return message;
	}
}
