package com.rssl.phizic.gate.messaging.impl;

import com.rssl.phizic.gate.messaging.NotExpectedResponseException;
import org.apache.xml.utils.DOMBuilder;
import org.w3c.dom.Document;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.util.Set;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * @author Roshka
 * @ created 12.09.2006
 * @ $Author$
 * @ $Revision$
 */

public final class ResponseBodyHandler extends DOMBuilder
{

	private Set<String> validResponses;
	private State state;

	public ResponseBodyHandler(Set<String> validResponces )
	{
		super(createDOMDocument());
		this.validResponses = validResponces;
		this.state = State.message;
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
		if( !validResponses.contains(qName) )
			throw new NotExpectedResponseException("Неожиданный ответ : " + qName);
	}

	public Document getResult()
	{
		return (Document) getRootDocument();
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