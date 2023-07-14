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
	 * Сериализовать объект в XML представление
	 * @param object - объект
	 * @param converters дополнительные конверторы
	 * @return xml представление объекта
	 */
	public static String serialize(Object object, List<Converter> converters)
	{
		return getXStream(converters).toXML(object);
	}

	/**
	 * Десериализовать объект из XML представления
	 * @param xml = XML представление объекта
	 * @param converters дополнительные конверторы
	 * @return объект
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
	 * Сериализовать объект в XML представление
	 * @param object - объект
	 * @return xml представление объекта
	 */
	public static String serialize(Object object)
	{
		return getXStream().toXML(object);
	}

	/**
	 * Десериализовать объект из XML представления
	 * @param xml = XML представление объекта
	 * @return объект
	 */
	public static Object deserialize(String xml)
	{
		return getXStream().fromXML(xml);
	}
}
