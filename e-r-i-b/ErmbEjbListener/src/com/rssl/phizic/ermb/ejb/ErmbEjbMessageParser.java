package com.rssl.phizic.ermb.ejb;

import com.rssl.phizic.common.types.TextMessage;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.messaging.ErmbXmlMessage;
import com.rssl.phizic.messaging.XmlMessage;
import com.rssl.phizic.messaging.XmlMessageParser;
import com.rssl.phizic.synchronization.types.*;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.utils.xml.jaxb.JAXBSerializerBase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.validation.Schema;

/**
 * @author Rtischeva
 * @ created 11.12.14
 * @ $Author$
 * @ $Revision$
 */
public class ErmbEjbMessageParser extends JAXBSerializerBase implements XmlMessageParser
{
	private final Schema ermbMessageSchema;

	private static final Log log = LogFactory.getLog(LogModule.Web.toString());

	private static final Class[] MESSAGE_CLASSES = {SMSRq.class, UpdateResourceRq.class, CheckImsiResponse.class, UpdateClientRq.class, ConfirmProfilesRq.class, ServiceFeeResultRq.class};

	ErmbEjbMessageParser()
	{
		super(MESSAGE_CLASSES, "UTF-8");
		try
		{
			ermbMessageSchema = XmlHelper.schemaByResourceNames("com/rssl/phizic/synchronization/mss.xsd");
		}
		catch (SAXException e)
		{
			throw new InternalErrorException("Сбой на загрузке XSD-схемы", e);
		}
	}

	public XmlMessage parseMessage(TextMessage message) throws JAXBException
	{
		if (!(message instanceof TextMessage))
			throw new IllegalArgumentException("Ожидается текстовое JMS-сообщение");

		log.trace("Получено текстовое JMS-сообщение: " + message);

		String text = message.getText();

		Object xmlMessage = unmarshallBeanWithValidation(text, ermbMessageSchema);
		if (xmlMessage != null)
			return buildXmlMessage(xmlMessage, text);
		return buildXmlMessage(unmarshalBean(text), text);
	}

	private XmlMessage buildXmlMessage(Object data, String text) throws JAXBException
	{
		Class requestClass = data.getClass();
		if (requestClass == SMSRq.class)
			return new ErmbXmlMessage((SMSRq)data, text);
		if (requestClass == UpdateResourceRq.class)
			return new ErmbXmlMessage((UpdateResourceRq) data, text);
		if (requestClass == CheckImsiResponse.class)
			return new ErmbXmlMessage((CheckImsiResponse) data, text);
		if (requestClass == UpdateClientRq.class)
			return new ErmbXmlMessage((UpdateClientRq) data, text);
		if (requestClass == ConfirmProfilesRq.class)
			return new ErmbXmlMessage((ConfirmProfilesRq) data, text);
		if (requestClass == ServiceFeeResultRq.class)
			return new ErmbXmlMessage((ServiceFeeResultRq) data, text);
		throw new JAXBException("Неизвестный формат сообщения: " + text);
	}
}
