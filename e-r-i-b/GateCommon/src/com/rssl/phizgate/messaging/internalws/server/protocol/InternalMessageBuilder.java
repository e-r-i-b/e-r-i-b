package com.rssl.phizgate.messaging.internalws.server.protocol;

import com.rssl.phizic.gate.messaging.impl.InnerSerializer;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.Stack;

/**
 * @author gladishev
 * @ created 03.03.14
 * @ $Author$
 * @ $Revision$
 */
public class InternalMessageBuilder
{
	private Attributes emptyAttributes = new AttributesImpl();
	private Stack<String> tags = new Stack<String>();
	private boolean end = false; //признак окончания постоения запроса
	private final InnerSerializer serializer;
	private final StringWriter writer;

	public InternalMessageBuilder() throws SAXException
	{
		writer = new StringWriter();
		serializer = new InnerSerializer(writer, "utf-8");
		serializer.startDocument();
	}

	/**
	 * Открыть тег в теле запроса
	 * Все последующие вызовы openTag и addParameter будут вставлять элементы внутри этого тега,
	 * до тех пор пока не будет вызван closeTag
	 * @param tagName имя тега
	 * @return this
	 */
	public InternalMessageBuilder openTag(String tagName) throws SAXException
	{
		if (end)
		{
			throw new IllegalStateException("Постоение документа уже завершено");
		}
		tags.push(tagName);
		serializer.startElement("", "", tagName, emptyAttributes);
		return this;
	}

	/**
	 * Закрыть тег.
	 * Все последующие вызовы openTag и addParameter будут вставлять элементы внутри родительского тега,
	 * @return this
	 */
	public InternalMessageBuilder closeTag() throws SAXException
	{
		if (end)
		{
			throw new IllegalStateException("Постоение документа уже завершено");
		}
		if (tags.isEmpty())
		{
			throw new SAXException("Лишний закрывающий тег");
		}
		serializer.endElement("", "", tags.pop());
		return this;
	}

	/**
	 * Вставить параметр (<name>value</name>) в текущий тег (определяется вызовом openTag).
	 * @param name имя параметра
	 * @param value значение параметра
	 * @return this
	 */
	public InternalMessageBuilder addParameter(String name, String value) throws SAXException
	{
		openTag(name);
		if (!StringHelper.isEmpty(value))
		{
			char[] arr = value.toCharArray();
			serializer.characters(arr, 0, arr.length);
		}
		closeTag();
		return this;
	}

	/**
	 * Вставить параметр (<name>value</name>) в текущий тег (определяется вызовом openTag).
	 * @param name имя параметра
	 * @param value xml
	 * @return this
	 */
	public InternalMessageBuilder addParameter(String name, Element value) throws SAXException, IOException
	{
		openTag(name);
		if (value != null)
		{
			serializer.serializeElementOnly(value);
		}
		closeTag();
		return this;
	}

	/**
	 * Вставить параметр (<name>value</name>) в текущий тег (определяется вызовом openTag), если значение не пусто.
	 * @param name имя параметра
	 * @param value значение параметра
	 * @return this
	 */
	public InternalMessageBuilder addParameterIfNotEmpty(String name, String value) throws SAXException
	{
		if (StringHelper.isEmpty(value))
		{
			return this;
		}
		return addParameter(name, value);
	}

	/**
	 * Вставить патаметр (<name>value</name>) в текущий тег(определяется вызовом openTag).
	 * @param name имя параметра
	 * @param value значение параметра
	 * @return this
	 */
	public InternalMessageBuilder addParameter(String name, Enum value) throws SAXException
	{
		return addParameter(name, value.name());
	}

	/**
	 * Вставить патаметр (<name>value</name>) в текущий тег(определяется вызовом openTag), если значение не null.
	 * @param name имя параметра
	 * @param value значение параметра
	 * @return this
	 */
	public InternalMessageBuilder addParameterIfNotEmpty(String name, Enum value) throws SAXException
	{
		if (value == null)
		{
			return this;
		}
		return addParameter(name, value);
	}

	/**
	 * Вставить патаметр (<name>value</name>) в текущий тег(определяется вызовом openTag).
	 * @param name имя параметра
	 * @param value значение параметра
	 * @return this
	 */
	public InternalMessageBuilder addParameter(String name, long value) throws SAXException
	{
		return addParameter(name, String.valueOf(value));
	}

	/**
	 * Вставить патаметр (<name>value</name>) в текущий тег(определяется вызовом openTag).
	 * @param name имя параметра
	 * @param value значение параметра
	 * @return this
	 */
	public InternalMessageBuilder addParameterIfNotEmpty(String name, Long value) throws SAXException
	{
		if (value == null)
		{
			return this;
		}
		return addParameter(name, value);
	}

	/**
	 * Вставить патаметр (<name>value</name>) в текущий тег(определяется вызовом openTag).
	 * @param name имя параметра
	 * @param value значение параметра
	 * @return this
	 */
	public InternalMessageBuilder addParameter(String name, Calendar value) throws SAXException
	{
		return addParameter(name, XMLDatatypeHelper.formatDateTimeWithoutTimeZone(value));
	}

	/**
	 * Вставить параметр (<name>value</name>) в текущий тег (определяется вызовом openTag), если значение не пусто.
	 * @param name имя параметра
	 * @param value значение параметра
	 * @return this
	 */
	public InternalMessageBuilder addParameterIfNotEmpty(String name, Calendar value) throws SAXException
	{
		if (value == null)
		{
			return this;
		}
		return addParameter(name, value);
	}

	/**
	 * Вставить параметр (<name>value</name>) в текущий тег (определяется вызовом openTag), если значение не пусто.
	 * @param name имя параметра
	 * @param value значение параметра
	 * @return this
	 */
	public InternalMessageBuilder addParameterIfNotEmpty(String name, Boolean value) throws SAXException
	{
		if (value == null)
		{
			return this;
		}
		return addParameter(name, value);
	}

	/**
	 * Вставить патаметр (<name>value</name>) в текущий тег(определяется вызовом openTag).
	 * @param name имя параметра
	 * @param value значение параметра
	 * @return this
	 */
	public InternalMessageBuilder addParameter(String name, boolean value) throws SAXException
	{
		return addParameter(name, String.valueOf(value));
	}

	/**
	 * парсинг структуры в Document
	 * @return Document
	 */
	public Document toDocument() throws Exception
	{
		return XmlHelper.parse(writer.toString());
	}

	protected boolean isFinished()
	{
		return end;
	}

	protected StringWriter getWriter()
	{
		return writer;
	}

	protected void finished() throws SAXException
	{
		if (!tags.isEmpty())
			throw new SAXException("Имеются незакрытые теги");

		serializer.endDocument();
		end = true;
	}
}
