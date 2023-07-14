package com.rssl.phizic.messaging;

import com.rssl.phizic.utils.xml.AllNamesFoundException;
import com.rssl.phizic.utils.xml.SAXSingleton;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * @author Gulov
 * @ created 01.07.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Парсер СМС-сообщения для получения UID и времени сообщения, для последующей записи в журнал сообщений
 */
class MessageParser
{
	private final SAXParser saxParser;

	MessageParser() throws SAXException
	{
		SAXParserFactory factory = SAXSingleton.getParserFactory();
		try
		{
			saxParser = factory.newSAXParser();
		}
		catch (ParserConfigurationException e)
		{
			throw new SAXException(e);
		}
	}

	RequestValues execute(String text) throws SAXException
	{
		return new RequestValues(parse(text));
	}

	private Map<String, String> parse(String text) throws SAXException
	{
		MessageHandler handler = new MessageHandler(RequestValues.UID_TAG_NAME, RequestValues.TIME_TAG_NAME);
		StringReader textReader = new StringReader(text);
		try
		{
			saxParser.parse(new InputSource(textReader), handler);
		}
		catch (IOException e)
		{
			throw new SAXException(e);
		}
		catch (AllNamesFoundException ignored)
		{
			// не обрабатыаем, только для прерывания работы парсера
		}
		finally
		{
			textReader.close();
		}
		return handler.getResult();
	}

	private class MessageHandler extends DefaultHandler
	{
		private final int length;
		private final Map<String, String> result;
		private StringBuilder textBuilder = null;
		private int counter = 0;

		MessageHandler(String ...tagNames)
		{
			length = tagNames.length;
			result = new HashMap<String, String>(length);
			for (String tagName : tagNames)
				result.put(tagName, null);
		}

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
		{
			isComplete();
			if (result.containsKey(qName))
				textBuilder = new StringBuilder();
		}

		@Override
		public void characters(char[] ch, int start, int length) throws SAXException
		{
			if (textBuilder != null)
				textBuilder.append(ch, start, length);
		}

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException
		{
			if (result.containsKey(qName))
			{
				result.put(qName, textBuilder.toString());
				textBuilder = null;
				counter++;
			}
			isComplete();
		}

		public Map<String, String> getResult()
		{
			return Collections.unmodifiableMap(result);
		}

		private void isComplete() throws AllNamesFoundException
		{
			if (counter == length)
				throw new AllNamesFoundException();
		}
	}
}
