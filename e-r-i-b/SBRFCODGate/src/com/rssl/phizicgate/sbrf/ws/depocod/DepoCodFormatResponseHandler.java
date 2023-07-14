package com.rssl.phizicgate.sbrf.ws.depocod;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.GateTimeOutException;
import com.rssl.phizic.gate.messaging.ResponseHandler;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.util.Set;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * Парсер ответов от системы DepoCod
 * @author gladishev
 * @ created 18.04.2014
 * @ $Author$
 * @ $Revision$
 */

class DepoCodFormatResponseHandler extends DefaultHandler implements ResponseHandler
{
	private static final String COMPLETE_STATUS = "0";
	private static final String ERROR_TAG = "Error";
	private static final String ERROR_CODE_TAG = "Code";
	private static final String ERROR_MESSAGE_TAG = "Message";
	private static final String RESULTS_NODE_PATH = "//Results";
	private static final String RESULT_TAG = "Result";
	private static final String UUID_TAG = "UUID";
	private static final String RESULT_ERROR_MESSAGE_TAG = "ErrMessage";
	private static final String TIMEOUT_STATUS = "5";
	private static final String FAILURE_RESPONSE = "operationResultResponse";

	private String responceTag;
	private String resultCode;
	private String errorMessage;
	private boolean isSuccess;
	private Throwable exception;
	/**
	 * ctor
	 * @param responseData - ответ
	 * @param allowedRootTags разрешенные теги ответа
	 */
	DepoCodFormatResponseHandler(DepoCODResponseMessageData responseData, Set<String> allowedRootTags) throws GateException
	{
		try
		{
			Element docElement = XmlHelper.parse(responseData.getBody()).getDocumentElement();
			Element root = null;
			for (String tag : allowedRootTags)
			{
				root = XmlHelper.selectSingleNode(docElement, tag);
				if (root != null)
				{
					responceTag = tag;
					break;
				}
			}

			Element errorElement = XmlHelper.selectSingleNode(root, ERROR_TAG);
			resultCode = XmlHelper.selectSingleNode(errorElement, ERROR_CODE_TAG).getTextContent();
			isSuccess = COMPLETE_STATUS.equals(resultCode);
			if (!isSuccess)
			{
				errorMessage = XmlHelper.selectSingleNode(errorElement, ERROR_MESSAGE_TAG).getTextContent();
				exception = TIMEOUT_STATUS.equals(resultCode) ? new GateTimeOutException(errorMessage) : new GateException(errorMessage);
				return;
			}

			if (FAILURE_RESPONSE.equals(responceTag))
			{
				NodeList nodeList = XmlHelper.selectNodeList(docElement, RESULTS_NODE_PATH);
				if (nodeList.getLength() != 1)
					throw new GateException("Некорректный формат ответного сообщения");

				Element item = (Element) nodeList.item(0);
				String uuid = XmlHelper.selectSingleNode(item, UUID_TAG).getTextContent();
				if (!responseData.getMessageId().equals(uuid))
					throw new GateException("Некорректный идентификатор сообщения: " + uuid);

				isSuccess = Boolean.parseBoolean(XmlHelper.selectSingleNode(item, RESULT_TAG).getTextContent());
				if (!isSuccess)
				{
					errorMessage = XmlHelper.selectSingleNode(item, RESULT_ERROR_MESSAGE_TAG).getTextContent();
					exception = new GateLogicException(errorMessage);
				}
			}
		}
		catch (TransformerException e)
		{
			throw new GateException(e);
		}
		catch (ParserConfigurationException e)
		{
			throw new GateException(e);
		}
		catch (SAXException e)
		{
			throw new GateException(e);
		}
		catch (IOException e)
		{
			throw new GateException(e);
		}
	}

	public String getMessageId()
	{
		return null; //depoCod не возвращает идентификатор ответного сообщения
	}

	public String getResponceTag()
	{
		return responceTag;
	}

	public boolean isSuccess()
	{
		return isSuccess;
	}

	public boolean isVoid()
	{
		return false; //в depoCod ответы всегда онлайн
	}

	public void throwException() throws GateException, GateLogicException
	{
		if (exception instanceof GateException)
			throw (GateException) exception;
		else
			throw (GateLogicException) exception;
	}

	public Object getBody()
	{
	   return null;
	}

	public void reset()
	{
		throw new UnsupportedOperationException();
	}

	public String getErrorCode()
	{
		return resultCode;
	}

	public String getErrorText()
	{
		return errorMessage;
	}
}
