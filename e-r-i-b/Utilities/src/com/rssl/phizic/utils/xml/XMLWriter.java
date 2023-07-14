package com.rssl.phizic.utils.xml;

import com.rssl.phizic.common.types.collection.LinkedListStack;
import com.rssl.phizic.common.types.collection.Stack;
import org.apache.commons.lang.StringUtils;
import org.dom4j.io.OutputFormat;
import org.dom4j.tree.DefaultComment;
import org.dom4j.tree.DefaultDocumentType;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.Writer;
import java.util.EmptyStackException;

/**
 * @author Erkin
 * @ created 15.01.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * XML-писатель (на базе DOM4J)
 * ВАЖНО! В конструкторе наследника необходимо вызвать метод construct()
 */
public abstract class XMLWriter implements Closeable, Flushable
{
	private org.dom4j.io.XMLWriter serializer;

	private final Stack<String> tagStack = new LinkedListStack<String>();

	///////////////////////////////////////////////////////////////////////////

	protected void construct(Writer writer, OutputFormat outputFormat)
	{
		this.serializer = new org.dom4j.io.XMLWriter(writer, outputFormat);
	}

	/**
	 * Начать запись XML
	 */
	public void startDocument()
	{
		try
		{
			serializer.startDocument();
		}
		catch (SAXException e)
		{
			throw new WriteXMLException(e);
		}
	}

	/**
	 * Окончить запись XML
	 */
	public void endDocument()
	{
		if (!tagStack.isEmpty())
			throw new WriteXMLException("Есть незакрытые теги: " + StringUtils.join(tagStack, ","));

		try
		{
			serializer.endDocument();
		}
		catch (SAXException e)
		{
			throw new WriteXMLException(e);
		}
	}

	/**
	 * Записать DTD
	 * @param elementName
	 * @param publicID
	 * @param systemID
	 */
	public void writeDTD(String elementName, String publicID, String systemID)
	{
		try
		{
			serializer.write(new DefaultDocumentType(elementName, publicID, systemID));
		}
		catch (IOException e)
		{
			throw new WriteXMLException(e);
		}
	}

	/**
	 * Начать запись сложного элемента (с вложениями)
	 * @param tagName - имя тега элемента
	 * @param attributes - атрибуты
	 * @return this
	 */
	public XMLWriter startElement(String tagName, Attributes attributes)
	{
		if (attributes == null)
			//noinspection AssignmentToMethodParameter
			attributes = new AttributesImpl();

		try
		{
			serializer.startElement("", "", tagName, attributes);
			tagStack.push(tagName);
			return this;
		}
		catch (SAXException e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * Начать запись сложного элемента (с вложениями)
	 * @param tagName - имя тега элемента
	 * @return this
	 */
	public XMLWriter startElement(String tagName)
	{
		return startElement(tagName, null);
	}

	/**
	 * Начать запись сложного элемента (с вложениями) с одним атрибутом
	 * @param tagName - имя тега элемента
	 * @param attributeName - имя атрибута
	 * @param attributeValue - значение атрибута
	 * @return this
	 */
	public XMLWriter startElement(String tagName, String attributeName, String attributeValue)
	{
		return startElement(tagName, new XMLElementAttributes(attributeName, attributeValue));
	}

	/**
	 * Закончить запись сложного элемента (с вложениями)
	 * @return this
	 */
	public XMLWriter endElement()
	{
		try
		{
			String tagName = tagStack.pop();
			serializer.endElement("", "", tagName);
			return this;
		}
		catch (SAXException e)
		{
			throw new RuntimeException(e);
		}
		catch (EmptyStackException e)
		{
			throw new WriteXMLException("Сначала должен быть вызван startElement()", e);
		}
	}

	/**
	 * Записать простой текстовый элемент
	 * @param tagName - имя тега элемента
	 * @param text - текст
	 * @return this
	 */
	public XMLWriter writeTextElement(String tagName, String text)
	{
		if (text == null)
			//noinspection AssignmentToMethodParameter
			text = "";

		try
		{
			serializer.startElement("", "", tagName, new AttributesImpl());
			serializer.characters(text.toCharArray(), 0, text.length());
			serializer.endElement("", "", tagName);
			return this;
		}
		catch (SAXException e)
		{
			throw new WriteXMLException(e);
		}
	}

	/**
	 * Записать комментарий
	 * @param comment - комментарий
	 * @return this
	 */
	public XMLWriter writeComment(String comment)
	{
		try
		{
			serializer.write(new DefaultComment(comment));
			return this;
		}
		catch (IOException e)
		{
			throw new WriteXMLException(e);
		}
	}

	public void flush() throws IOException
	{
		serializer.flush();
	}

	public void close() throws IOException
	{
		serializer.close();
	}
}
