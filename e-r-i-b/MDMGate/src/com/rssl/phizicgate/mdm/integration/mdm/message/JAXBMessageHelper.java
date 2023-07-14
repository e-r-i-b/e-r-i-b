package com.rssl.phizicgate.mdm.integration.mdm.message;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.utils.xml.jaxb.JAXBContextFactory;
import com.rssl.phizicgate.mdm.integration.mdm.MDMSegmentInfo;
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
 * @ created 14.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * ’елпер (билдер, прасер), работающий через JAXB
 */

public class JAXBMessageHelper
{
	private final String xsdSchemaName;
	private final String packageName;
	private volatile Schema xsdSchema = null;

	private static final JAXBMessageHelper instance = new JAXBMessageHelper(MDMSegmentInfo.JAXB_CLASSES_PACKAGE_NAME, MDMSegmentInfo.XSD_SCHEMA_NAME);

	private JAXBMessageHelper(String packageName, String xsdSchemaName)
	{
		this.xsdSchemaName = xsdSchemaName;
		this.packageName = packageName;
	}

	/**
	 * @return инстанс хелпера
	 */
	public static JAXBMessageHelper getInstance()
	{
		return instance;
	}

	/**
	 * —формировать запрос
	 * @param requestData информаци€, необходима€ дл€ запроса
	 * @return запрос
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 */
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

	/**
	 * разобрать jms сообщение
	 * @param source jms сообщение
	 * @param <RsClass> класс результата
	 * @return результат
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 */
	public <RsClass> RsClass parseMessage(TextMessage source) throws GateException
	{
		try
		{
			Reader reader = new StringReader(source.getText());
			Unmarshaller unmarshaller = getJAXBContext().createUnmarshaller();
			unmarshaller.setSchema(getXSDSchema());
			//noinspection unchecked
			return (RsClass) unmarshaller.unmarshal(reader);
		}
		catch (JMSException e)
		{
			throw new GateException("—бой при парсинге ответа.", e);
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

		synchronized (JAXBMessageHelper.class)
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
