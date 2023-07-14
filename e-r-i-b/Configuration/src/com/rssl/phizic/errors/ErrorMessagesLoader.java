package com.rssl.phizic.errors;

import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.util.*;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import com.rssl.phizic.utils.xml.XmlFileReader;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.utils.xml.ForeachElementAction;

/**
 * @author gladishev
 * @ created 21.11.2007
 * @ $Author$
 * @ $Revision$
 */

public class ErrorMessagesLoader
{
	public static final String ERROR_MESSAGES_FILE_NAME = "errorMessages.xml";
	private Document document;

	private List<ErrorMessage> errorMessages = new ArrayList<ErrorMessage>();

	public ErrorMessagesLoader(Document document)
	{
		this.document = document;
	}

	public List<ErrorMessage> getMessages()
	{
		return errorMessages;
	}

	public void load()
	{
		Element element = document.getDocumentElement();
		try
		{
			XmlHelper.foreach(element, "error-message", new ForeachElementAction()
			{
				public void execute(Element element) throws Exception
				{
					ErrorMessage errorMessage = new ErrorMessage();

					String value = getText(element, "reg-exp");
					errorMessage.setRegExp(value);

					String tagName = "";
					try
					{
						tagName = "error-type";
						value = getText(element, tagName);
						errorMessage.setErrorType(ErrorType.valueOf(value));

						tagName = "system";
						value = getText(element, tagName);
						errorMessage.setSystem(ErrorSystem.valueOf(value));
					}
					catch(IllegalArgumentException iae)
					{
						throw new RuntimeException("Неверное значение [" + value + "] в теге " + tagName, iae);
					}

					value = getText(element, "ikfl-message");
					errorMessage.setMessage(value);

					errorMessages.add(errorMessage);
				}
			}
			);
		}
		catch(Exception e)
		{
			throw new RuntimeException("Ошибка при загрузке сообщения из файла" + ERROR_MESSAGES_FILE_NAME, e);
		}
	}

	private String getText(Element element, String fieldName) throws Exception
	{
		Element node = XmlHelper.selectSingleNode(element, fieldName);
		String text = node.getTextContent().trim();
		if (text == null || text.equals(""))
		{
			throw new Exception("Не установлено значение в теге " + fieldName);
		}
		return text;
	}
}
