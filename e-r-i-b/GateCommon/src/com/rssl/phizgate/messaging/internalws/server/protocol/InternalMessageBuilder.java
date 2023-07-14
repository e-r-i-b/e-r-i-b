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
	private boolean end = false; //������� ��������� ��������� �������
	private final InnerSerializer serializer;
	private final StringWriter writer;

	public InternalMessageBuilder() throws SAXException
	{
		writer = new StringWriter();
		serializer = new InnerSerializer(writer, "utf-8");
		serializer.startDocument();
	}

	/**
	 * ������� ��� � ���� �������
	 * ��� ����������� ������ openTag � addParameter ����� ��������� �������� ������ ����� ����,
	 * �� ��� ��� ���� �� ����� ������ closeTag
	 * @param tagName ��� ����
	 * @return this
	 */
	public InternalMessageBuilder openTag(String tagName) throws SAXException
	{
		if (end)
		{
			throw new IllegalStateException("��������� ��������� ��� ���������");
		}
		tags.push(tagName);
		serializer.startElement("", "", tagName, emptyAttributes);
		return this;
	}

	/**
	 * ������� ���.
	 * ��� ����������� ������ openTag � addParameter ����� ��������� �������� ������ ������������� ����,
	 * @return this
	 */
	public InternalMessageBuilder closeTag() throws SAXException
	{
		if (end)
		{
			throw new IllegalStateException("��������� ��������� ��� ���������");
		}
		if (tags.isEmpty())
		{
			throw new SAXException("������ ����������� ���");
		}
		serializer.endElement("", "", tags.pop());
		return this;
	}

	/**
	 * �������� �������� (<name>value</name>) � ������� ��� (������������ ������� openTag).
	 * @param name ��� ���������
	 * @param value �������� ���������
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
	 * �������� �������� (<name>value</name>) � ������� ��� (������������ ������� openTag).
	 * @param name ��� ���������
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
	 * �������� �������� (<name>value</name>) � ������� ��� (������������ ������� openTag), ���� �������� �� �����.
	 * @param name ��� ���������
	 * @param value �������� ���������
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
	 * �������� �������� (<name>value</name>) � ������� ���(������������ ������� openTag).
	 * @param name ��� ���������
	 * @param value �������� ���������
	 * @return this
	 */
	public InternalMessageBuilder addParameter(String name, Enum value) throws SAXException
	{
		return addParameter(name, value.name());
	}

	/**
	 * �������� �������� (<name>value</name>) � ������� ���(������������ ������� openTag), ���� �������� �� null.
	 * @param name ��� ���������
	 * @param value �������� ���������
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
	 * �������� �������� (<name>value</name>) � ������� ���(������������ ������� openTag).
	 * @param name ��� ���������
	 * @param value �������� ���������
	 * @return this
	 */
	public InternalMessageBuilder addParameter(String name, long value) throws SAXException
	{
		return addParameter(name, String.valueOf(value));
	}

	/**
	 * �������� �������� (<name>value</name>) � ������� ���(������������ ������� openTag).
	 * @param name ��� ���������
	 * @param value �������� ���������
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
	 * �������� �������� (<name>value</name>) � ������� ���(������������ ������� openTag).
	 * @param name ��� ���������
	 * @param value �������� ���������
	 * @return this
	 */
	public InternalMessageBuilder addParameter(String name, Calendar value) throws SAXException
	{
		return addParameter(name, XMLDatatypeHelper.formatDateTimeWithoutTimeZone(value));
	}

	/**
	 * �������� �������� (<name>value</name>) � ������� ��� (������������ ������� openTag), ���� �������� �� �����.
	 * @param name ��� ���������
	 * @param value �������� ���������
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
	 * �������� �������� (<name>value</name>) � ������� ��� (������������ ������� openTag), ���� �������� �� �����.
	 * @param name ��� ���������
	 * @param value �������� ���������
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
	 * �������� �������� (<name>value</name>) � ������� ���(������������ ������� openTag).
	 * @param name ��� ���������
	 * @param value �������� ���������
	 * @return this
	 */
	public InternalMessageBuilder addParameter(String name, boolean value) throws SAXException
	{
		return addParameter(name, String.valueOf(value));
	}

	/**
	 * ������� ��������� � Document
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
			throw new SAXException("������� ���������� ����");

		serializer.endDocument();
		end = true;
	}
}
