package com.rssl.phizic.messaging.ermb;

import com.rssl.phizic.synchronization.types.CheckImsiResponse;
import com.rssl.phizic.synchronization.types.ServiceFeeResultRq;
import com.rssl.phizic.synchronization.types.ServiceStatusRes;
import com.rssl.phizic.utils.xml.jaxb.JAXBSerializerBase;

import javax.xml.bind.JAXBException;

/**
 * @author Erkin
 * @ created 24.03.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * Сериализатор + десериализатор сообщений транспортного канала ЕРМБ
 */
public class TransportMessageSerializer extends JAXBSerializerBase
{
	private static final Class[] TRANSPORT_MESSAGE_CLASSES
			= { SendSmsRequest.class, SendSmsWithImsiRequest.class, CheckImsiResponse.class,
			ServiceFeeResultRq.class, ServiceStatusRes.class};

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ctor
	 */
	public TransportMessageSerializer()
	{
		super(TRANSPORT_MESSAGE_CLASSES, "UTF8");
	}

	/**
	 * Сериализовать запрос транспортного канала в XML-строку
	 * @param request - запрос
	 * @return XML-строка
	 */
	public String marshallRequest(SendSmsRequest request) throws JAXBException
	{
		return super.marshalBean(request);
	}

	/**
	 * Сериализовать запрос транспортного канала в XML-строку
	 * @param request - запрос
	 * @return XML-строка
	 */
	public String marshallRequest(SendSmsWithImsiRequest request) throws JAXBException
	{
		return super.marshalBean(request);
	}

	/**
	 * Сериализовать ответ на отправку СМС с проверкой IMSI
	 * @param response - CheckImsiResponse
	 * @return XML-строка
	 */
	public String marshallCheckIMSIResponse(CheckImsiResponse response) throws JAXBException
	{
		return super.marshalBean(response);
	}

	/**
	 * Распарсить ответ на отправку СМС с проверкой IMSI
	 * @param responseXML - XML-строка с ответом
	 * @return CheckImsiResponse
	 */
	public CheckImsiResponse unmarshallCheckImsiResponse(String responseXML) throws JAXBException
	{
		return super.unmarshalBean(responseXML, CheckImsiResponse.class);
	}

	/**
	 * Сериализовать ответ о списании абонентской платы
	 * @param response - ServiceStatusRes
	 * @return XML-строка
	 * @throws JAXBException
	 */
	public String marshallServiceFeeResultResponse(ServiceStatusRes response) throws JAXBException
	{
		return super.marshalBean(response);
	}

	/**
	 * Распарсить ответ о списании абонентской платы
	 * @param responseXML - XML-строка с ответом
	 * @return ServiceStatusRes
	 */
	public ServiceStatusRes unmarshallServiceFeeResultResponse(String responseXML) throws JAXBException
	{
		return super.unmarshalBean(responseXML, ServiceStatusRes.class);
	}

	/**
	 * Сериализовать запрос о списании абонентской платы
	 * @param response - ServiceFeeResultRq
	 * @return XML-строка
	 * @throws JAXBException
	 */
	public String marshallServiceFeeResultRequest(ServiceFeeResultRq response) throws JAXBException
	{
		return super.marshalBean(response);
	}

	/**
	 * Распарсить запрос о списании абонентской платы
	 * @param responseXML - XML-строка с ответом
	 * @return ServiceFeeResultRq
	 */
	public ServiceFeeResultRq unmarshallServiceFeeResultRequest(String responseXML) throws JAXBException
	{
		return super.unmarshalBean(responseXML, ServiceFeeResultRq.class);
	}
}
