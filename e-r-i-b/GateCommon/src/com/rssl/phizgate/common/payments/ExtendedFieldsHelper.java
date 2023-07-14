package com.rssl.phizgate.common.payments;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.utils.DocumentConfig;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

import java.io.IOException;
import java.util.List;

import static com.rssl.phizgate.common.payments.ExtendedFieldsHelper.serialize;

/**
 * @author krenev
 * @ created 11.01.2011
 * @ $Author$
 * @ $Revision$
 * ������ ��� ������������ �������������� �����
 */
public class ExtendedFieldsHelper
{

	/**
	 * ������������ ������ ����� � ������ � ������� xml
	 * @param fields ������ �����
	 * @return ������ � ��������� �����
	 */
	public static String serialize(List<? extends Field> fields) throws DocumentException
	{
		return serialize(DocumentConfig.FORMAT.XML, fields);
	}

	/**
	 * ������������ ������ ����� � ������ � �������� �������
	 * ���� ������ �� �����, ������������ ������ �� ���������
	 * @param fields ������ �����
	 * @param format ������
	 * @return ������ � ��������� �����
	 */
	public static String serialize(DocumentConfig.FORMAT format, List<? extends Field> fields) throws DocumentException
	{
		if (fields == null)
		{
			return null;
		}
		if (format == null)
		{
			format = ConfigFactory.getConfig(DocumentConfig.class).getDocumentsExtendedFieldsFormat();
		}
		switch (format)
		{
			case XML:
				return new ExtendedFieldsXMLSerializer(fields).serialize();
			case GSON:
				return serializeGSON(fields);
			default:
				throw new DocumentException("����������� ������ �������� ����� "+fields);
		}
	}

	private static String serializeGSON(List<? extends Field> fields) throws DocumentException
	{
		try
		{
			return new ExtendedFieldsGSONSerializer(fields).serialize();
		}
		catch (IOException e)
		{
			throw new DocumentException(e);
		}
	}

	/**
	 * ��������������� ������ � ������ �����
	 * @param fields ������ � ������� � �����, ���������� ������� serialize
	 * @return ����������������� ������.
	 */
	public static List<Field> deserialize(String fields) throws DocumentException
	{
		if (StringHelper.isEmpty(fields))
		{
			return null;
		}

		switch (getFormat(fields))
		{
			case XML:
				return new ExtendedFieldsSAXSource(fields).getSource();
			case GSON:
				return parseGSON(fields);
			default:
				throw new DocumentException("����������� ������ �������� ����� " + fields);
		}
	}

	private static List<Field> parseGSON(String fields) throws DocumentException
	{
		try
		{
			return new ExtendedFieldsGSONParser(fields).parse();
		}
		catch (IOException e)
		{
			throw new DocumentException(e);
		}
	}

	/**
	 * ��������������� ������ � DOM
	 * @param fields ������ � ������� � �����, ���������� ������� serialize
	 * @return DOM. ����������������� ������.
	 */
	public static Document deserializeToDOM(String fields) throws DocumentException
	{
		if (StringHelper.isEmpty(fields))
		{
			return null;
		}
		try
		{
			DocumentConfig.FORMAT format = getFormat(fields);
			if (format == DocumentConfig.FORMAT.GSON)
			{
				//���� ������ ����� ������������/��������������... �����, ���� �����������, ������� �� ��������JSON���...
				fields = serialize(DocumentConfig.FORMAT.XML, deserialize(fields));
			}
			return XmlHelper.parse(fields);
		}
		catch (Exception e)
		{
			throw new DocumentException(e);
		}
	}

	/**
	 * ����������� �������� ����������� ����� � ������� ������... ��� �������������.
	 * @param fields ������ � ������� � �����, ���������� ������� serialize
	 * @return ������ � ������� � ����� � ������� ������� ���������.
	 * @throws DocumentException
	 */
	public static String rebuild(String fields) throws DocumentException
	{
		if (StringHelper.isEmpty(fields))
		{
			return fields; // �������� �� ���������.. ��������� ������� ���������
		}
		DocumentConfig.FORMAT fieldsFormat = getFormat(fields);
		DocumentConfig.FORMAT currentFormat = ConfigFactory.getConfig(DocumentConfig.class).getDocumentsExtendedFieldsFormat();
		if (fieldsFormat == currentFormat)
		{
			return fields; // �������� �� ���������.. ��������� ������� ���������
		}
		return serialize(currentFormat, deserialize(fields)); //��������������� � ������� ������..
	}

	/**
	 * �������� ������ ���������������� �������� �����.
	 * @param fields ������ � ������� � �����, ���������� ������� serialize
	 * @return ������ ������
	 */
	public static DocumentConfig.FORMAT getFormat(String fields)
	{
		if (StringHelper.isEmpty(fields))
		{
			return null;
		}
		if (fields.startsWith("<Attributes>"))
		{
			return DocumentConfig.FORMAT.XML;
		}
		return DocumentConfig.FORMAT.GSON;
	}
}
