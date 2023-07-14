package com.rssl.auth.csa.back.protocol;

import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.utils.StringHelper;
import org.apache.xml.utils.DOMBuilder;
import org.w3c.dom.Document;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Calendar;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * @author krenev
 * @ created 22.08.2012
 * @ $Author$
 * @ $Revision$
 * "Разборщик" запроса.
 * Каждый запрос имеет следующий формат:
 * <message>
 *   <UID>идентифкатор запроса</UID>
 *   <date>Дата запроса</date>
 *   <source>код отправителя запроса</source>
 *   <version>версия протокола</version>
 *   <тип запроса>
 *     ...
 *   <тип запроса>
 * </message>
 * */

public class RequestMessageHandler extends DefaultHandler implements RequestInfo
{

	private String type;
	private String uid;
	private Calendar date;
	private String version;
	private String source;
	private String ip;

	private StringBuilder tagText;
	private State state = State.START;
	private int bodyLevel = 0;
	private DOMBuilder bodyHandler;

	public RequestMessageHandler() throws ParserConfigurationException
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		bodyHandler = new DOMBuilder(factory.newDocumentBuilder().newDocument());
	}

	private enum State
	{
		START,
		MESSAGE_OPEN,
		MESSAGE_CLOSE,
		UID_OPEN,
		UID_CLOSE,
		DATE_OPEN,
		DATE_CLOSE,
		SOURCE_OPEN,
		SOURCE_CLOSE,
		VERSION_OPEN,
		VERSION_CLOSE,
		IP_OPEN,
		IP_CLOSE,
		BODY,
		END
	}

	public String getType()
	{
		return type;
	}

	public String getUID()
	{
		return uid;
	}

	public Calendar getDate()
	{
		return date;
	}

	public String getVersion()
	{
		return version;
	}

	public String getSource()
	{
		return source;
	}


	public String getIP()
	{
		return ip;
	}

	public Document getBody()
	{
		return (Document) bodyHandler.getRootDocument();
	}

	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
	{
		tagText = new StringBuilder();
		switch (state)
		{
			case START:
				assertStartTag(qName, Constants.MESSAGE_TAG);
				state = State.MESSAGE_OPEN;
				break;
			case MESSAGE_OPEN:
				assertStartTag(qName, Constants.MESSAGE_UID_TAG);
				state = State.UID_OPEN;
				break;
			case UID_OPEN:
				throw new SAXException("Ошибка разбора сообщения. Ожидается закрывающий тег: " + Constants.MESSAGE_UID_TAG);
			case UID_CLOSE:
				assertStartTag(qName, Constants.MESSAGE_DATE_TAG);
				state = State.DATE_OPEN;
				break;
			case DATE_OPEN:
				throw new SAXException("Ошибка разбора сообщения. Ожидается закрывающий тег: " + Constants.MESSAGE_DATE_TAG);
			case DATE_CLOSE:
				assertStartTag(qName, Constants.MESSAGE_SOURCE_TAG);
				state = State.SOURCE_OPEN;
				break;
			case SOURCE_OPEN:
				throw new SAXException("Ошибка разбора сообщения. Ожидается закрывающий тег: " + Constants.MESSAGE_SOURCE_TAG);
			case SOURCE_CLOSE:
				assertStartTag(qName, Constants.MESSAGE_VERSION_TAG);
				state = State.VERSION_OPEN;
				break;
			case VERSION_OPEN:
				throw new SAXException("Ошибка разбора сообщения. Ожидается закрывающий тег: " + Constants.MESSAGE_VERSION_TAG);
			case VERSION_CLOSE:
				assertStartTag(qName, Constants.MESSAGE_IP_TAG);
				state = State.IP_OPEN;
				break;
			case IP_OPEN:
				throw new SAXException("Ошибка разбора сообщения. Ожидается закрывающий тег: " + Constants.MESSAGE_IP_TAG);
			case IP_CLOSE:
				type = qName;
				bodyHandler.startDocument();
				bodyLevel = 0;
				state = State.BODY;
			case BODY:
				bodyLevel++;
				bodyHandler.startElement(uri, localName, qName, attributes);
				break;
			case MESSAGE_CLOSE:
				throw new SAXException("Ошибка разбора сообщения. Ожидается конец документа");
			default:
				throw new SAXException("Ошибка разбора сообщения");
		}
	}

	public void endElement(String uri, String localName, String qName) throws SAXException
	{
		switch (state)
		{
			case MESSAGE_OPEN:
				throw new SAXException("Ошибка разбора сообщения. Ожидается тег: " + Constants.MESSAGE_UID_TAG);
			case UID_OPEN:
				uid = getNotEmptyText(Constants.MESSAGE_UID_TAG);
				state = State.UID_CLOSE;
				break;
			case UID_CLOSE:
				throw new SAXException("Ошибка разбора сообщения. Ожидается тег: " + Constants.MESSAGE_DATE_TAG);
			case DATE_OPEN:
				date = XMLDatatypeHelper.parseDateTime(getNotEmptyText(Constants.MESSAGE_DATE_TAG));
				state = State.DATE_CLOSE;
				break;
			case DATE_CLOSE:
				throw new SAXException("Ошибка разбора сообщения. Ожидается тег: " + Constants.MESSAGE_SOURCE_TAG);
			case SOURCE_OPEN:
				source = getNotEmptyText(Constants.MESSAGE_SOURCE_TAG);
				state = State.SOURCE_CLOSE;
				break;
			case SOURCE_CLOSE:
				throw new SAXException("Ошибка разбора сообщения. Ожидается тег: " + Constants.MESSAGE_VERSION_TAG);
			case VERSION_OPEN:
				version = getNotEmptyText(Constants.MESSAGE_VERSION_TAG);
				state = State.VERSION_CLOSE;
				break;
			case VERSION_CLOSE:
				throw new SAXException("Ошибка разбора сообщения. Ожидается тело сообщения");
			case IP_OPEN:
				ip = tagText.toString();
				state = State.IP_CLOSE;
				break;
			case IP_CLOSE:
				throw new SAXException("Ошибка разбора сообщения. Ожидается тело сообщения");
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

	private String getNotEmptyText(String tagName) throws SAXException
	{
		String result = tagText.toString();
		if (StringHelper.isEmpty(result))
		{
			throw new SAXException("Ошибка разбора сообщения. Oжидается значение для тега " + tagName);
		}
		return result;
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

	private void assertStartTag(String qName, String tag) throws SAXException
	{
		if (!qName.equals(tag))
		{
			throw new SAXException("Ошибка разбора сообщения. Ожидается открывающий тег: " + tag + ". Найден тег:" + qName);
		}
	}

	/**
	 * Получить информацию о запросе.
	 * @return информация о запросе.
	 */
	public RequestInfo getRequestInfo()
	{
		return this;
	}
}
