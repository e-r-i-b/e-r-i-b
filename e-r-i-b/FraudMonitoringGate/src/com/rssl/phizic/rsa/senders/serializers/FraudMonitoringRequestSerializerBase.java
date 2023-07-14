package com.rssl.phizic.rsa.senders.serializers;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.rsa.senders.enumeration.FraudMonitoringRequestType;
import com.rssl.phizic.utils.StringHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import static com.rssl.phizic.rsa.senders.serializers.support.RSARequestXMLDocumentConstants.*;

/**
 * @author tisov
 * @ created 06.07.15
 * @ $Author$
 * @ $Revision$
 * Базовый класс сериализатора фрод-запроса в xml-документ
 */
public abstract class FraudMonitoringRequestSerializerBase<RQ>
{
	private static DocumentBuilder documentBuilder;

	private Document xmlDocument;
	private RQ request;

	private static DocumentBuilder createDocumentBuilder() throws ParserConfigurationException
	{
		return DocumentBuilderFactory.newInstance().newDocumentBuilder();
	}

	protected abstract void fillRootNode(Node rootNode);

	protected abstract FraudMonitoringRequestType getRequestType();

	protected RQ getRequest()
	{
		return this.request;
	}

	private DocumentBuilder getDocumentBuilder() throws ParserConfigurationException
	{
		if (documentBuilder == null)
		{
			documentBuilder = createDocumentBuilder();
		}
		return documentBuilder;
	}

	protected Node createElement(String elementName)
	{
		return this.xmlDocument.createElement(elementName);
	}

	private Node createTextNode(String text)
	{
		return this.xmlDocument.createTextNode(text);
	}

	protected Node createSimpleTag(String elementName, String text)
	{
		Node result = createElement(elementName);
		result.appendChild(createTextNode(text));

		return result;
	}

	protected void appendSimpleTagIfNotNull(Node rootNode, String childName, String childText)
	{
		if (!StringHelper.isEmpty(childText))
		{
			rootNode.appendChild(createSimpleTag(childName, childText));
		}
	}

	private String convertDocumentToString()
	{
		DOMImplementationLS domImplementation = (DOMImplementationLS) this.xmlDocument.getImplementation();
		LSSerializer lsSerializer = domImplementation.createLSSerializer();
		return lsSerializer.writeToString(this.xmlDocument);
	}

	/**
	 * Сериализовать фрод-запрос в xml-документ
	 * @param request
	 * @return
	 */
	public String serialize(RQ request) throws GateException
	{
		try
		{
			this.request = request;
			this.xmlDocument = getDocumentBuilder().newDocument();

			Node rootNode = this.xmlDocument.createElement(DOCUMENT_NAME);
			this.xmlDocument.appendChild(rootNode);

			rootNode.appendChild(createSimpleTag(REQUEST_KIND, getRequestType().name()));

			fillRootNode(rootNode);

			return convertDocumentToString();
		}
		catch (ParserConfigurationException e)
		{
			throw new GateException(e);
		}
	}

}
