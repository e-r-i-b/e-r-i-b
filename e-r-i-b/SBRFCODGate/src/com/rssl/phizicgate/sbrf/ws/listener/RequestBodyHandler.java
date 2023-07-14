package com.rssl.phizicgate.sbrf.ws.listener;

import com.rssl.phizic.gate.messaging.NotExpectedResponseException;
import com.sun.org.apache.xml.internal.utils.DOMBuilder;
import org.w3c.dom.Document;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 *
 * todo пока заглушка...
 * @author Roshka
 * @ created 23.10.2006
 * @ $Author$
 * @ $Revision$
 */

public class RequestBodyHandler extends DOMBuilder
{

	private String requestXml;
	private State state;

	public RequestBodyHandler(String request)
	{
		super(createDOMDocument());
		this.requestXml = request;
		this.state = State.message;
	}

	public String getRequestXml()
	{
		return requestXml;
	}

	private enum State
	{
		message,
		end,
	}

	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
	{
		switch (state)
		{
			case message:
				checkForExpectations(qName);
				state = State.end;
				break;
		}

		super.startElement(uri, localName, qName, attributes);
	}

	private void checkForExpectations(String qName) throws NotExpectedResponseException
	{
		//todo нужен список валидных к нам запросов..
		if (!requestXml.contains(qName))
			throw new NotExpectedResponseException("Неожиданный запрос из ЦОД: " + qName);
	}

	public Document getResult()
	{
		return (Document) getRootNode();
	}

	private static Document createDOMDocument()
	{
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			return factory.newDocumentBuilder().newDocument();
		}
		catch (ParserConfigurationException e)
		{
			throw new RuntimeException(e);
		}
	}

}
