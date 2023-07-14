package com.rssl.phizic.utils.xml.jaxb;

import org.w3c.dom.Document;

import java.io.*;
import java.util.List;
import javax.xml.bind.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;

/**
 * @author Erkin
 * @ created 02.01.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Утилиты для работы с JAXB
 */
public class JAXBUtils
{
	/**
	 * Упаковывает бин в строку
	 * @param bean - бин
	 * @return упакованный бин
	 */
	public static <T> String marshalBean(T bean) throws JAXBException
	{
		return marshalBean(bean, null, true);
	}

	/**
	 * Упаковывает бин в строку. При упаковке, валидирует по схеме.
	 * @param bean - бин
	 * @param schema - схема
	 * @param withTitle - должен ли быть заголовок xml-документа
	 * @return упакованный бин
	 */
	public static <T> String marshalBean(T bean, Schema schema, boolean withTitle) throws JAXBException
	{
		Class beanClass = bean.getClass();
		Thread currentThread = Thread.currentThread();
		ClassLoader currentClassLoader = currentThread.getContextClassLoader();
		try
		{
			currentThread.setContextClassLoader(beanClass.getClassLoader());
			JAXBContext jaxbContext = JAXBContext.newInstance(beanClass);
			Marshaller m = jaxbContext.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			if (!withTitle)
				m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
			if (schema != null)
				m.setSchema(schema);

			StringWriter writer = new StringWriter();
			m.marshal(bean, writer);
			return writer.toString();
		}
		finally
		{
			currentThread.setContextClassLoader(currentClassLoader);
		}
	}

	/**
	 * Упаковывает бин в строку, экранирует в cdata требуемые поля бина
	 * @param bean - бин
	 * @param fields - поля, которые необходимо экранировать в cdata
	 * @return упакованный бин
	 * @throws JAXBException
	 */
	public static <T> String marshalBeanWithCDATA(T bean, List<String> fields) throws JAXBException
	{
		Class beanClass = bean.getClass();
		Thread currentThread = Thread.currentThread();
		ClassLoader currentClassLoader = currentThread.getContextClassLoader();
		try
		{
			currentThread.setContextClassLoader(beanClass.getClassLoader());
			JAXBContext jaxbContext = JAXBContext.newInstance(beanClass);

			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			Document document = docBuilderFactory.newDocumentBuilder().newDocument();
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.marshal(bean, document);

			return marshalWithCDATA(document, fields);
		}
		catch (Exception e)
		{
			throw new JAXBException(e);
		}
		finally
		{
			currentThread.setContextClassLoader(currentClassLoader);
		}
	}

	private static String marshalWithCDATA(Document document, List<String> fields) throws TransformerException, UnsupportedEncodingException
	{
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		for (String field : fields)
		{
			transformer.setOutputProperty(OutputKeys.CDATA_SECTION_ELEMENTS, field);
		}
		transformer.setOutputProperty(OutputKeys.ENCODING, "windows-1251");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		transformer.transform(new DOMSource(document), new StreamResult(result));
		return result.toString();
	}

	/**
	 * Распаковывает бин заданного класса из строки
	 * @param beanClass - класс бина
	 * @param data - строка
	 * @return распакованный бин
	 */
	public static <T> T unmarshalBean(Class<T> beanClass, String data) throws JAXBException
	{
		return unmarshalBean(beanClass, data, null);
	}

	/**
	 * Распаковывает бин заданного класса из строки, При распаковке, валидирует по схеме.
	 * @param beanClass - класс бина
	 * @param data - строка
	 * @param schema - схема
	 * @return распакованный бин
	 */
	public static <T> T unmarshalBean(Class<T> beanClass, String data, Schema schema) throws JAXBException
	{
		Reader reader = null;
		Thread currentThread = Thread.currentThread();
		ClassLoader currentClassLoader = currentThread.getContextClassLoader();
		try
		{
			currentThread.setContextClassLoader(beanClass.getClassLoader());
			JAXBContext jaxbContext = JAXBContext.newInstance(beanClass);
			Unmarshaller um = jaxbContext.createUnmarshaller();
			if (schema != null)
				um.setSchema(schema);
			reader = new StringReader(data);
			JAXBElement<T> jaxbElement = um.unmarshal(new StreamSource(reader), beanClass);
			return jaxbElement.getValue();
		}
		finally
		{
			currentThread.setContextClassLoader(currentClassLoader);
			if (reader != null)
				try { reader.close(); } catch (IOException ignore) {}
		}
	}

	/**
	 * Распаковывает бин заданного класса из файла
	 * @param beanClass - класс бина
	 * @param file - файл
	 * @return распакованный бин
	 */
	public static <T> T unmarshalBean(Class<T> beanClass, File file) throws JAXBException
	{
		Thread currentThread = Thread.currentThread();
		ClassLoader currentClassLoader = currentThread.getContextClassLoader();
		try
		{
			currentThread.setContextClassLoader(beanClass.getClassLoader());
			JAXBContext jaxbContext = JAXBContext.newInstance(beanClass);
			Unmarshaller um = jaxbContext.createUnmarshaller();
			//noinspection unchecked
			return (T) um.unmarshal(file);
		}
		finally
		{
			currentThread.setContextClassLoader(currentClassLoader);
		}
	}
}
