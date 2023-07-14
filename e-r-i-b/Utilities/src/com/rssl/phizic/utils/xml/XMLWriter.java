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
 * XML-�������� (�� ���� DOM4J)
 * �����! � ������������ ���������� ���������� ������� ����� construct()
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
	 * ������ ������ XML
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
	 * �������� ������ XML
	 */
	public void endDocument()
	{
		if (!tagStack.isEmpty())
			throw new WriteXMLException("���� ���������� ����: " + StringUtils.join(tagStack, ","));

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
	 * �������� DTD
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
	 * ������ ������ �������� �������� (� ����������)
	 * @param tagName - ��� ���� ��������
	 * @param attributes - ��������
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
	 * ������ ������ �������� �������� (� ����������)
	 * @param tagName - ��� ���� ��������
	 * @return this
	 */
	public XMLWriter startElement(String tagName)
	{
		return startElement(tagName, null);
	}

	/**
	 * ������ ������ �������� �������� (� ����������) � ����� ���������
	 * @param tagName - ��� ���� ��������
	 * @param attributeName - ��� ��������
	 * @param attributeValue - �������� ��������
	 * @return this
	 */
	public XMLWriter startElement(String tagName, String attributeName, String attributeValue)
	{
		return startElement(tagName, new XMLElementAttributes(attributeName, attributeValue));
	}

	/**
	 * ��������� ������ �������� �������� (� ����������)
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
			throw new WriteXMLException("������� ������ ���� ������ startElement()", e);
		}
	}

	/**
	 * �������� ������� ��������� �������
	 * @param tagName - ��� ���� ��������
	 * @param text - �����
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
	 * �������� �����������
	 * @param comment - �����������
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
