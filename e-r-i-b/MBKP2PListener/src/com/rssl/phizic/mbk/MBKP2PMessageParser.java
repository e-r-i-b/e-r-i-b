package com.rssl.phizic.mbk;

import com.rssl.phizic.common.types.TextMessage;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.gate.mobilebank.P2PRequest;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.messaging.ErmbXmlMessage;
import com.rssl.phizic.messaging.XmlMessage;
import com.rssl.phizic.messaging.XmlMessageParser;
import com.rssl.phizic.utils.xml.jaxb.JAXBSerializerBase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

/**
 * @author Rtischeva
 * @ created 11.12.14
 * @ $Author$
 * @ $Revision$
 */
class MBKP2PMessageParser extends JAXBSerializerBase implements XmlMessageParser
{
	private final Log log = LogFactory.getLog(LogModule.Web.toString());

	private static final JAXBContext jaxbContext;

	static
	{
		try
		{
			jaxbContext = JAXBContext.newInstance(P2PRequest.class);
		}
		catch (JAXBException e)
		{
			throw new ConfigurationException("Не удалось загрузить JAXB-контекст", e);
		}
	}

	MBKP2PMessageParser()
	{
		super(jaxbContext, "UTF-8");
	}

	public XmlMessage parseMessage(TextMessage message) throws JAXBException
	{
		if (!(message instanceof TextMessage))
			throw new IllegalArgumentException("Ожидается текстовое JMS-сообщение");

		log.info("Получен P2P-запрос: " + message);

		String text = message.getText();

		Object xmlMessage = unmarshalBean(text);
		if (xmlMessage != null)
			return buildXmlMessage(xmlMessage, text);
		return buildXmlMessage(unmarshalBean(text), text);
	}

	private XmlMessage buildXmlMessage(Object data, String text) throws JAXBException
	{
		Class requestClass = data.getClass();
		if (requestClass == P2PRequest.class)
			return new ErmbXmlMessage((P2PRequest) data, text);
		throw new JAXBException("Неизвестный формат сообщения: " + text);
	}
}
