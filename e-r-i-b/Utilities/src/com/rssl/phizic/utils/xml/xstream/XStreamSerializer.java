package com.rssl.phizic.utils.xml.xstream;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.util.List;

/**
 * @author mihaylov
 * @ created 12.11.13
 * @ $Author$
 * @ $Revision$
 */

public class XStreamSerializer
{

	private static XStream getXStream(List<Converter> converters)
	{
		XStream xStream = new XStream(new DomDriver());
		xStream.registerConverter(new DateConverter());
		for (Converter converter : converters)
			xStream.registerConverter(converter);
		return xStream;
	}

	/**
	 * ������������� ������ � XML �������������
	 * @param object - ������
	 * @param converters �������������� ����������
	 * @return xml ������������� �������
	 */
	public static String serialize(Object object, List<Converter> converters)
	{
		return getXStream(converters).toXML(object);
	}

	/**
	 * ��������������� ������ �� XML �������������
	 * @param xml = XML ������������� �������
	 * @param converters �������������� ����������
	 * @return ������
	 */
	public static Object deserialize(String xml, List<Converter> converters)
	{
		return getXStream(converters).fromXML(xml);
	}

	private static XStream getXStream()
	{
		return new XStream(new DomDriver());
	}

	/**
	 * ������������� ������ � XML �������������
	 * @param object - ������
	 * @return xml ������������� �������
	 */
	public static String serialize(Object object)
	{
		return getXStream().toXML(object);
	}

	/**
	 * ��������������� ������ �� XML �������������
	 * @param xml = XML ������������� �������
	 * @return ������
	 */
	public static Object deserialize(String xml)
	{
		return getXStream().fromXML(xml);
	}
}
