package com.rssl.phizgate.messaging.internalws.client;

import org.apache.xml.utils.DOMBuilder;
import org.w3c.dom.Document;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import static com.rssl.phizgate.messaging.internalws.Constants.*;

/**
 * @author niculichev
 * @ created 28.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class ResponseHandler extends DefaultHandler
{
	private State state = State.START;
	private DOMBuilder bodyHandler;
	private int bodyLevel = 0;
	private String type;
	private StringBuilder tagText;
	private String errorCode;
	private String errorDescription;

	private enum State
	{
		START,
		MESSAGE_OPEN,
		UID_OPEN,
		UID_CLOSE,
		DATE_OPEN,
		DATE_CLOSE,
		STATUS_OPEN,
		STATUS_CODE_OPEN,
		STATUS_CODE_CLOSE,
		STATUS_DESCRIPTION_OPEN,
		STATUS_DESCRIPTION_CLOSE,
		STATUS_CLOSE,
		BODY,
		MESSAGE_CLOSE,
		END
	}

	public ResponseHandler() throws ParserConfigurationException
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		bodyHandler = new DOMBuilder(factory.newDocumentBuilder().newDocument());
	}

	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
	    tagText = new StringBuilder();

	    switch(state)
	    {
		    case START:
			    assertStartTag(qName, MESSAGE_TAG);
			    state = State.MESSAGE_OPEN;
			    break;

			case MESSAGE_OPEN:
			    assertStartTag(qName, UID_TAG);
			    state = State.UID_OPEN;
			    break;

			case UID_CLOSE:
			    assertStartTag(qName, DATE_TAG);
			    state = State.DATE_OPEN;
			    break;

			case DATE_CLOSE:
				assertStartTag(qName, STATUS_TAG);
			    state = State.STATUS_OPEN;
			    break;

			case STATUS_OPEN:
			    assertStartTag(qName, STATUS_CODE_TAG);
			    state = State.STATUS_CODE_OPEN;
			    break;

			case STATUS_CODE_CLOSE:
			    assertStartTag(qName, STATUS_DESCRIPTION_TAG);
			    state = State.STATUS_DESCRIPTION_OPEN;
			    break;

			case STATUS_CLOSE:
			    type = qName;
				bodyHandler.startDocument();
				bodyLevel = 0;
				state = State.BODY;

			case BODY:
				bodyLevel++;
				bodyHandler.startElement(uri, localName, qName, attributes);
				break;


			default:
				throw new SAXException("Ошибка разбора сообщения");
	    }
    }

	public void endElement(String uri, String localName, String qName) throws SAXException
    {
	    switch(state)
	    {
		    case UID_OPEN:
			    state = State.UID_CLOSE;
			    break;

		    case DATE_OPEN:
			    state = State.DATE_CLOSE;
			    break;


			case STATUS_CODE_OPEN:
				errorCode = getTagText();
				state = State.STATUS_CODE_CLOSE;
				break;

			case STATUS_DESCRIPTION_OPEN:
				errorDescription = getTagText();
				state = State.STATUS_DESCRIPTION_CLOSE;
				break;

			case STATUS_CODE_CLOSE:
			case STATUS_DESCRIPTION_CLOSE:
				assertStartTag(qName, STATUS_TAG);
				state = State.STATUS_CLOSE;
				break;

			case BODY:
				bodyLevel--;
				bodyHandler.endElement(uri, localName, qName);
				if (bodyLevel == 0)
				{
					bodyHandler.endDocument();
					state = State.MESSAGE_CLOSE;
				}
				break;

			case MESSAGE_CLOSE:
				state = State.END;
				break;

			default:
				throw new SAXException("Ошибка разбора сообщения");
	    }
    }

	public String getType()
	{
		return type;
	}

	public String getStatusCode()
	{
		return errorCode;
	}

	public String getStatusDescription()
	{
		return errorDescription;
	}

	public ResponseData getResponseData()
	{
		return new ResponseData((Document) bodyHandler.getRootDocument());
	}

	public void characters(char[] ch, int start, int length) throws SAXException
	{
		switch (state)
		{
			case BODY:
				bodyHandler.characters(ch, start, length);
				break;
			default:
				tagText.append(ch, start, length);
		}
	}

	private String getTagText()
	{
		return tagText.toString().trim();
	}

	private void assertStartTag(String qName, String tag) throws SAXException
	{
		if (!qName.equals(tag))
		{
			throw new SAXException("Ошибка разбора сообщения. Ожидается открывающий тег: " + tag + ". Найден тег:" + qName);
		}
	}
}
