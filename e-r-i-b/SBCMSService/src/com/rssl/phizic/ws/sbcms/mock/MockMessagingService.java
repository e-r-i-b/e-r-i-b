package com.rssl.phizic.ws.sbcms.mock;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Map;
import java.util.HashMap;
import java.util.Calendar;

import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.gate.messaging.impl.DefaultErrorMessageHandler;
import com.rssl.phizicgate.sbcms.messaging.mock.XMLRequestValidator;

import javax.xml.transform.TransformerException;

/**
 * Обработка запроса и формирование ответа
 * @author Egorova
 * @ created 16.12.2008
 * @ $Author$
 * @ $Revision$
 */
public class MockMessagingService
{
	private Map<String, String> handlerMap;
	private String fileContainer = "com/rssl/phizicgate/sbcms/messaging/mock/xml/";

	public MockMessagingService()
	{
		handlerMap = new HashMap<String, String>();
		handlerMap.put("BALANCE",   "WAY4_BALANCE.xml");
		handlerMap.put("HISTORY",   "WAY4_HISTORY.xml");
		handlerMap.put("LOCK_LOST", "WAY4_LOCK_OK.xml");
		handlerMap.put("LOCK_STEAL", "WAY4_LOCK_OK.xml");
		handlerMap.put("LOCK_ATM",  "WAY4_LOCK_OK.xml");
		handlerMap.put("LOCK_OTHER", "WAY4_LOCK_OK.xml");	
	}

	public String makeResponse(String pqRequest)
	{
		try
		{
			Document documentRequest = XmlHelper.parse(pqRequest);
			XMLRequestValidator validator = new XMLRequestValidator();
			validator.validate(documentRequest);
			Element documentElement = documentRequest.getDocumentElement();
			String requestType = XmlHelper.getElementValueByPath(documentElement, "/POSGATE_MSG/WAY4_REQUEST/PRO_CODE");

			String responseFileName = handlerMap.get(requestType);

			Document responseDocument;

			if (responseFileName == null)
				responseDocument = makeValidationError("Не поддерживает запрос с типом " + requestType, DefaultErrorMessageHandler.ERROR_VALIDATE_CODE);
			else
				responseDocument = XmlHelper.loadDocumentFromResource(fileContainer + responseFileName);

			return XmlHelper.convertDomToText(responseDocument);
		}
		catch (Exception e)
		{
			return makeValidationErrorString(e.toString(), DefaultErrorMessageHandler.ERROR_VALIDATE_CODE);
		}
	}

	private String makeValidationErrorString(String errorCode, String errorMessage)
	{
		Document errorDocument = makeValidationError(errorCode,errorMessage);
		try
		{
			return XmlHelper.convertDomToText(errorDocument);
		}
		catch(TransformerException e)
		{
			throw new RuntimeException(e);
		}

	}

	public Document makeValidationError(String errorCode, String errorMessage)
	{
		try
		{
			Document response       = XmlHelper.loadDocumentFromResource(fileContainer + "WAY4_ERROR.xml");
			//todo. Если из ПЦ сообщение все таки с <message>...</message> придет, вместо /POSGATE_MSG пишем /message
			Element messageElement = XmlHelper.selectSingleNode(response.getDocumentElement(), "/POSGATE_MSG/WAY4_ERROR/ERR_MESS");
            Element codeElement    = XmlHelper.selectSingleNode(response.getDocumentElement(), "/POSGATE_MSG/WAY4_ERROR/ERR_CODE");
	        Element dateElement    = XmlHelper.selectSingleNode(response.getDocumentElement(), "/POSGATE_MSG/WAY4_ERROR/DATE_TIME");

			codeElement.setTextContent(errorMessage);
			messageElement.setTextContent(errorCode);
			Calendar calendar = Calendar.getInstance();
			dateElement.setTextContent(calendar.getTime().toString());

			return response;
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
	}
}
