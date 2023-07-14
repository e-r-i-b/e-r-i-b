package com.rssl.phizic.utils.xml.jaxb;

import com.rssl.phizic.common.types.annotation.ThreadSafe;
import com.rssl.phizic.utils.StringHelper;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import javax.xml.bind.*;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;

/**
 * @author Erkin
 * @ created 24.03.2014
 * @ $Author$
 * @ $Revision$
 */
@ThreadSafe
public abstract class JAXBSerializerBase
{
	private final String charsetName;

	private final JAXBContext jaxbContext;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ctor
	 * @param knownBeanClasses
	 * @param charsetName
	 * @deprecated Используйте {@link JAXBSerializerBase#JAXBSerializerBase(JAXBContext jaxbContext, String charsetName)}
	 */
	@Deprecated
	protected JAXBSerializerBase(Class[] knownBeanClasses, String charsetName)
	{
		this.charsetName = charsetName;
		try
		{
			this.jaxbContext = JAXBContext.newInstance(knownBeanClasses);
		}
		catch (JAXBException e)
		{
			throw new RuntimeException("Сбой на загрузке JAXB-контекста по классам " + Arrays.toString(knownBeanClasses), e);
		}
	}

	protected JAXBSerializerBase(JAXBContext jaxbContext, String charsetName)
	{
		if (jaxbContext == null)
			throw new IllegalArgumentException("Не указан контекст JAXB");
		if (StringHelper.isEmpty(charsetName))
		    throw new IllegalArgumentException("Не указана кодировка");

		this.jaxbContext = jaxbContext;
		this.charsetName = charsetName;
	}

	/**
	 * Сериализация в строку
	 * @param bean - объект
	 * @return строка
	 */
	protected <B> String marshalBean(B bean) throws JAXBException
	{
		Marshaller m = jaxbContext.createMarshaller();
		m.setProperty(Marshaller.JAXB_ENCODING, charsetName);

		StringWriter writer = new StringWriter();

		m.marshal(bean, writer);

		return writer.toString();
	}

	/**
	 * Десериализация строки
	 * @param string строка
	 * @return объект
	 */
	protected <B> B unmarshalBean(String string, Class<B> beanClass) throws JAXBException
	{
		try
		{
			Unmarshaller um = jaxbContext.createUnmarshaller();
			InputStream is = new ByteArrayInputStream(string.getBytes(charsetName));
			JAXBElement<B> jaxbElement = um.unmarshal(new StreamSource(is), beanClass);
			return jaxbElement.getValue();
		}
		catch (UnsupportedEncodingException e)
		{
			throw new RuntimeException("Неподдерживаемая кодировка " + charsetName, e);
		}
	}



	/**
	 * Десериализация строки
	 * @param string строка
	 * @return объект
	 */
	protected <B> B unmarshalBean(String string) throws JAXBException
	{
		try
		{
			Unmarshaller um = jaxbContext.createUnmarshaller();
			InputStream is = new ByteArrayInputStream(string.getBytes(charsetName));
			//noinspection unchecked
			return (B) um.unmarshal(is);
		}
		catch (UnsupportedEncodingException e)
		{
			throw new RuntimeException("Неподдерживаемая кодировка " + charsetName, e);
		}
	}

	/**
	 * Десериализация строки с валидацией по xsd-схеме
	 * @param xml
	 * @param schema
	 * @param <B>
	 * @return
	 * @throws JAXBException
	 */
	protected  <B> B unmarshallBeanWithValidation(String xml, Schema schema) throws JAXBException
	{
		try
		{
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			unmarshaller.setSchema(schema);

			InputStream is = new ByteArrayInputStream(xml.getBytes(charsetName));

			//noinspection unchecked
			return (B) unmarshaller.unmarshal(is);
		}
		catch (UnsupportedEncodingException e)
		{
			throw new RuntimeException("Неподдерживаемая кодировка " + charsetName, e);
		}
	}

}
