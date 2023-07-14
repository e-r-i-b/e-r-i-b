package com.rssl.auth.csa.back.protocol;

import com.rssl.auth.csa.back.Utils;
import com.rssl.phizic.gate.messaging.impl.InnerSerializer;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import java.io.StringWriter;
import java.util.Calendar;
import java.util.Stack;

/**
 * @author krenev
 * @ created 23.08.2012
 * @ $Author$
 * @ $Revision$
 * ���������� �������
 */

public class ResponseBuilder implements ResponseInfo
{
	private Attributes emptyAttributes = new AttributesImpl();
	private Stack<String> tags = new Stack<String>();
	private final String bodyTagName;
	private final int errorCode;
	private final String errorDescription;
	private final String uid;
	private final Calendar date;
	private final InnerSerializer serializer;
	private final StringWriter writer;
	private boolean end = false; //������� ��������� ��������� �������

	public ResponseBuilder(String bodyTagName) throws SAXException
	{
		this(bodyTagName, 0, null);
	}

	public ResponseBuilder(String bodyTagName, int errorCode, String errorDescription) throws SAXException
	{
		this.bodyTagName = bodyTagName;
		this.errorCode = errorCode;
		this.errorDescription = errorDescription;
		uid = Utils.generateGUID();
		date = Calendar.getInstance();

		writer = new StringWriter();
		serializer = new InnerSerializer(writer, "utf-8");
		serializer.startDocument();

		openTag(Constants.MESSAGE_TAG);
		addParameter(Constants.MESSAGE_UID_TAG, uid);
		addParameter(Constants.MESSAGE_DATE_TAG, date);

		openTag(Constants.MESSAGE_STATUS_TAG);
		addParameter(Constants.MESSAGE_STATUS_CODE_TAG, errorCode);
		if (!StringHelper.isEmpty(errorDescription))
		{
			addParameter(Constants.MESSAGE_STATUS_DESCRIPTION_TAG, errorDescription);
		}
		closeTag();//Constants.MESSAGE_STATUS_TAG

		openTag(bodyTagName);
	}

	/**
	 * @return ���������� �� ������
	 */
	public ResponseInfo getResponceInfo()
	{
		if (!end)
		{
			throw new IllegalStateException("���������� ��������� ��������� ���������");
		}
		return this;
	}

	public String getType()
	{
		return bodyTagName;
	}

	public String getUID()
	{
		return uid;
	}

	public Calendar getDate()
	{
		return date;
	}

	public int getErrorCode()
	{
		return errorCode;
	}

	public String getErrorDescription()
	{
		return errorDescription;
	}

	public String asString()
	{
		if (!end)
		{
			throw new IllegalStateException("���������� ��������� ��������� ���������");
		}
		return writer.toString();
	}

	/**
	 * ������� ��� � ���� �������
	 * ��� ����������� ������ openTag � addParameter ����� ��������� �������� ������ ����� ����,
	 * �� ��� ��� ���� �� ����� ������ closeTag
	 * @param tagName ��� ����
	 * @return this
	 */
	public ResponseBuilder openTag(String tagName) throws SAXException
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
	public ResponseBuilder closeTag() throws SAXException
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
	public ResponseBuilder addParameter(String name, String value) throws SAXException
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
	 * �������� �������� (<name>value</name>) � ������� ��� (������������ ������� openTag), ���� �������� �� �����.
	 * @param name ��� ���������
	 * @param value �������� ���������
	 * @return this
	 */
	public ResponseBuilder addParameterIfNotEmpty(String name, String value) throws SAXException
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
	public ResponseBuilder addParameter(String name, Enum value) throws SAXException
	{
		return addParameter(name, value.name());
	}

	/**
	 * �������� �������� (<name>value</name>) � ������� ���(������������ ������� openTag), ���� �������� �� null.
	 * @param name ��� ���������
	 * @param value �������� ���������
	 * @return this
	 */
	public ResponseBuilder addParameterIfNotEmpty(String name, Enum value) throws SAXException
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
	public ResponseBuilder addParameter(String name, long value) throws SAXException
	{
		return addParameter(name, String.valueOf(value));
	}

	/**
	 * �������� �������� (<name>value</name>) � ������� ���(������������ ������� openTag).
	 * @param name ��� ���������
	 * @param value �������� ���������
	 * @return this
	 */
	public ResponseBuilder addParameterIfNotEmpty(String name, Long value) throws SAXException
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
	public ResponseBuilder addParameter(String name, Calendar value) throws SAXException
	{
		if(value == null)
		{
			return this;
		}

		return addParameter(name, XMLDatatypeHelper.formatDateTimeWithoutTimeZone(value));
	}

	/**
	 * �������� �������� (<name>value</name>) � ������� ��� (������������ ������� openTag), ���� �������� �� �����.
	 * @param name ��� ���������
	 * @param value �������� ���������
	 * @return this
	 */
	public ResponseBuilder addParameterIfNotEmpty(String name, Calendar value) throws SAXException
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
	public ResponseBuilder addParameter(String name, boolean value) throws SAXException
	{
		return addParameter(name, String.valueOf(value));
	}
	/**
	 * ��������� ��������� ������.
	 * @return this
	 */
	public ResponseBuilder end() throws SAXException
	{
		closeTag();//bodyTag
		closeTag();//Constants.MESSAGE_TAG
		if (!tags.isEmpty())
		{
			throw new SAXException("������� ���������� ����");
		}
		serializer.endDocument();
		end = true;
		return this;
	}
}
