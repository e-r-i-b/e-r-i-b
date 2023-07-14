package com.rssl.phizicgate.esberibgate.ws.jms.common;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.utils.xml.jaxb.JAXBContextFactory;
import org.xml.sax.SAXException;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;

/**
 * @author akrenev
 * @ created 28.05.2015
 * @ $Author$
 * @ $Revision$
 *
 * ’елпер (билдер, прасер), работающий через JAXB
 */

public final class JAXBSegmentMessageHelper implements MessageBuilder, MessageParser
{
	private final String xsdSchemaName;
	private final String packageName;
	private volatile Schema xsdSchema = null;

	private JAXBSegmentMessageHelper(String packageName, String xsdSchemaName)
	{
		this.xsdSchemaName = xsdSchemaName;
		this.packageName = packageName;
	}

	/**
	 * @param packageName корневой пакет классов JAXB
	 * @param xsdSchemaName xsd схема
	 * @return инстанс билдера
	 */
	public static JAXBSegmentMessageHelper getInstance(String packageName, String xsdSchemaName)
	{
		return new JAXBSegmentMessageHelper(packageName, xsdSchemaName);
	}

	public <RqClass> String buildMessage(RqClass requestData) throws GateException
	{
		try
		{
			StringWriter writer = new StringWriter();
			Marshaller marshaller = getJAXBContext().createMarshaller();
			marshaller.marshal(requestData, writer);
			return writer.toString();
		}
		catch (JAXBException e)
		{
			throw new GateException("ќшибка преобразовани€ JAXB.", e);
		}
	}

	public <RsClass> RsClass parseMessage(TextMessage source) throws GateException
	{
		try
		{
			return (RsClass) parseMessage(source.getText());
		}
		catch (JMSException e)
		{
			throw new GateException("—бой при парсинге ответа.", e);
		}
	}

	public <RsClass> RsClass parseMessage(String body) throws GateException
	{
		try
		{
			Reader reader = new StringReader(body);
			Unmarshaller unmarshaller = getJAXBContext().createUnmarshaller();
			unmarshaller.setSchema(getXSDSchema());
			//noinspection unchecked
			return (RsClass) unmarshaller.unmarshal(reader);
		}
		catch (JAXBException e)
		{
			throw new GateException("—бой при парсинге ответа.", e);
		}
	}

	private JAXBContext getJAXBContext() throws JAXBException
	{
		return JAXBContextFactory.getInstance().getContext(packageName);
	}

	private Schema getXSDSchema() throws GateException
	{
		if (xsdSchema != null)
			return xsdSchema;

		synchronized (JAXBSegmentMessageHelper.class)
		{
			try
			{
				xsdSchema = XmlHelper.schemaByResourceName(xsdSchemaName);
			}
			catch (SAXException e)
			{
				throw new GateException("—бой на загрузке XSD-схемы", e);
			}
		}
		return xsdSchema;
	}
}
