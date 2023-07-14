package com.rssl.phizicgate.iqwave.messaging.mock;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.iqwave.documents.FNSFreePaymentSender;
import com.rssl.phizicgate.iqwave.messaging.Constants;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.transform.TransformerException;

/**
 * @author gulov
 * @ created 15.12.2010
 * @ $Authors$
 * @ $Revision$
 */
public class FNSFreePaymentRequestHandler extends OfflineTicketResponseHandler
{
	public FNSFreePaymentRequestHandler() throws GateException
	{
		super();
	}

	public Document proccessRequest(Document document) throws GateException
	{
		validateParameters(document);

		return super.proccessRequest(document);
	}

	private void validateParameters(Document document) throws GateException
	{
		NodeList parameters;
		try
		{
			String tagName = Constants.BODY_TAG + Constants.DELIMITER + Constants.PARAMETERS_TAG +
				Constants.DELIMITER + Constants.PARAMETER_TAG + Constants.DELIMITER + Constants.SHORT_NAME_TAG;

			parameters = XmlHelper.selectNodeList(document.getDocumentElement(), tagName);
		}
		catch (TransformerException e)
		{
			throw new GateException("Ошибка получения списка элементов parameters", e);
		}

		for (String parameter : FNSFreePaymentSender.PARAMETERS.keySet())
		{
			Node node = getNode(parameters, parameter);

			Node childNode = getValueNode(node.getParentNode().getChildNodes(), parameter);
			if (StringHelper.isEmpty(childNode.getTextContent()) && !parameter.equals(Constants.RECEIVER_NUMBER_SHORT) && !parameter.equals(Constants.RECEIVER_BANK_SHORT)) // фнс для поля recipcode и recipbank допускается пустое значение Value
				throw new GateException("В сообщении значение параметра: " + parameter + " пустое");
		}
	}

	private Node getNode(NodeList parameters, String parameterName) throws GateException
	{
		for (int i = 0; i < parameters.getLength(); i++)
		{
			Node node = parameters.item(i);

			if (node.getTextContent().equals(parameterName))
				return node;
		}

		throw new GateException("В сообщении нет параметра: " + parameterName);
	}

	private Node getValueNode(NodeList nodeList, String parameterName) throws GateException
	{
		for (int i = 0; i < nodeList.getLength(); i++)
		{
			Node childNode = nodeList.item(i);
			if (childNode.getNodeName().equals(Constants.VALUE_TAG))
			{
				return childNode;
			}
		}
		throw new GateException("В сообщении нет тега со значением для параметра: " + parameterName);
	}
}