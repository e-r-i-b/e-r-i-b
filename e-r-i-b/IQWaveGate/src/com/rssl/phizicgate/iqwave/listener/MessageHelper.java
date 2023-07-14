package com.rssl.phizicgate.iqwave.listener;

import com.rssl.common.forms.doc.DocumentCommand;
import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessagingClientException;
import com.rssl.phizic.gate.messaging.GateMessagingException;
import com.rssl.phizic.gate.messaging.GateMessagingValidationException;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.iqwave.ErrorProcessor;
import com.rssl.phizicgate.iqwave.messaging.Constants;
import com.rssl.phizicgate.iqwave.messaging.MessageInfoContainer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.TransformerException;

/**
 * @author Krenev
 * @ created 25.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class MessageHelper
{
	/**
	 * сформировать структуру заголовка
	 * @param messageType тип заголовка
	 * @param infoContainer информация о запросе
	 * @param errorCode код ошибки
	 * @param errorMessage текст ошибки
	 * @return документ с заполененным заголовком.
	 */
	public static Document fillHeader(String messageType, MessageInfoContainer infoContainer, String errorCode, String errorMessage)
	{
		DocumentBuilder documentBuilder = XmlHelper.getDocumentBuilder();

		Document document = documentBuilder.newDocument();
		Element root = document.createElement(messageType);
		document.appendChild(root);
		Element head = document.createElement(Constants.HEAD_TAG);
		root.appendChild(head);
		//MessageUID
		Element messageUID = document.createElement(Constants.MESSAGE_UID_TAG);
		head.appendChild(messageUID);
		XmlHelper.appendSimpleElement(messageUID, Constants.MESSAGE_ID_TAG, new RandomGUID().getStringValue());
		XmlHelper.appendSimpleElement(messageUID, Constants.MESSAGE_DATE_TAG, XMLDatatypeHelper.formatDateWithoutTimeZone(Calendar.getInstance()));
		XmlHelper.appendSimpleElement(messageUID, Constants.FROM_ABONENT_TAG, Constants.ABONENT_SBOL);

		//MessageType
		XmlHelper.appendSimpleElement(head, Constants.MESSAGE_TYPE_TAG, messageType);

		//Version
		XmlHelper.appendSimpleElement(head, Constants.VERSION_TAG, "1");

		if (errorCode != null)
		{
			Element error = document.createElement(Constants.ERROR_TAG);
			head.appendChild(error);
			XmlHelper.appendSimpleElement(error, Constants.ERROR_CODE_TAG, errorCode);
			if (errorMessage != null)
			{
				XmlHelper.appendSimpleElement(error, Constants.ERROR_MESSAGE_TAG, errorMessage);
			}
		}
		if (infoContainer != null)
		{
			//parentId
			Element parentId = document.createElement(Constants.PARENT_ID_TAG);
			head.appendChild(parentId);
			XmlHelper.appendSimpleElement(parentId, Constants.MESSAGE_ID_TAG, infoContainer.getMessageId());
			XmlHelper.appendSimpleElement(parentId, Constants.MESSAGE_DATE_TAG, XMLDatatypeHelper.formatDateWithoutTimeZone(infoContainer.getMessageDate()));
			XmlHelper.appendSimpleElement(parentId, Constants.FROM_ABONENT_TAG, infoContainer.getFromAbonent());
		}
		return document;
	}

	/**
	 * сформировать ответ об ошибке
	 * @param errorMessage текст ошибки
	 * @param infoContainer информация о запрове
	 * @return ответ
	 * @throws GateException
	 */
	public static String createErrorMessage(String errorMessage, MessageInfoContainer infoContainer) throws GateException
	{
		try
		{
			return XmlHelper.convertDomToText(fillHeader(Constants.OFFLINE_TICKET, infoContainer, "500", errorMessage));
		}
		catch (TransformerException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Конвертирует ошибку полученную из сообщения в статус документа
	 * @param infoContainer - контейнер сообщения
	 * @return - статус документа с описанием
	 * @throws GateLogicException
	 */
	public static DocumentCommand error2State(MessageInfoContainer infoContainer) throws GateLogicException
	{
		Map<String, Object> additionalFields = new HashMap<String, Object>();
		String errorCode = infoContainer.getErrorCode();
		DocumentEvent event = null;
		try
		{
			ErrorProcessor.processError(errorCode, infoContainer.getErrorDescription());
		}
		catch (GateMessagingClientException e)
		{
			event = DocumentEvent.REFUSE;
			additionalFields.put(DocumentCommand.ERROR_TEXT, e.getMessage());
		}
		catch (GateMessagingValidationException e)
		{
			event = DocumentEvent.ERROR;
			additionalFields.put(DocumentCommand.ERROR_TEXT, e.getMessage());
		}
		catch (GateMessagingException e)
		{
			event = DocumentEvent.ERROR;
			additionalFields.put(DocumentCommand.ERROR_TEXT, e.getMessage());
		}
		additionalFields.put(DocumentCommand.ERROR_CODE, errorCode);
		return new DocumentCommand(event, additionalFields);
	}
}
