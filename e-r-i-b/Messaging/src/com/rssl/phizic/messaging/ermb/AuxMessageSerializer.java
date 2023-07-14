package com.rssl.phizic.messaging.ermb;

import com.rssl.phizic.gate.mobilebank.P2PRequest;
import com.rssl.phizic.utils.xml.jaxb.JAXBSerializerBase;

import javax.xml.bind.JAXBException;

/**
 * @author Erkin
 * @ created 24.03.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * Сериализатор + десериализатор сообщений служебного канала ЕРМБ
 * Сейчас используется только в сериализации P2P-запросов МБК
 */
public class AuxMessageSerializer extends JAXBSerializerBase
{
	private static final Class[] TRANSPORT_MESSAGE_CLASSES = { P2PRequest.class };

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ctor
	 */
	public AuxMessageSerializer()
	{
		super(TRANSPORT_MESSAGE_CLASSES, "UTF-8");
	}

	/**
	 * Сериализовать p2p запрос МБК в XML-строку
	 * @param request - запрос
	 * @return XML-строка
	 */
	public String marshallRequest(P2PRequest request) throws JAXBException
	{
		return super.marshalBean(request);
	}

	/**
	 * Распарсить p2p запрос МБК из XML-строки
	 * @param requestXML - XML-строка с запросом
	 * @return запрос
	 */
	public P2PRequest unmarshallRequest(String requestXML) throws JAXBException
	{
		return super.unmarshalBean(requestXML);
	}
}
