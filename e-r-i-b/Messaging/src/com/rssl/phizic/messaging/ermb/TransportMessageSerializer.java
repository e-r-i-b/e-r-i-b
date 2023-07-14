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
 * ������������ + �������������� ��������� ������������� ������ ����
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
	 * ������������� ������ ������������� ������ � XML-������
	 * @param request - ������
	 * @return XML-������
	 */
	public String marshallRequest(SendSmsRequest request) throws JAXBException
	{
		return super.marshalBean(request);
	}

	/**
	 * ������������� ������ ������������� ������ � XML-������
	 * @param request - ������
	 * @return XML-������
	 */
	public String marshallRequest(SendSmsWithImsiRequest request) throws JAXBException
	{
		return super.marshalBean(request);
	}

	/**
	 * ������������� ����� �� �������� ��� � ��������� IMSI
	 * @param response - CheckImsiResponse
	 * @return XML-������
	 */
	public String marshallCheckIMSIResponse(CheckImsiResponse response) throws JAXBException
	{
		return super.marshalBean(response);
	}

	/**
	 * ���������� ����� �� �������� ��� � ��������� IMSI
	 * @param responseXML - XML-������ � �������
	 * @return CheckImsiResponse
	 */
	public CheckImsiResponse unmarshallCheckImsiResponse(String responseXML) throws JAXBException
	{
		return super.unmarshalBean(responseXML, CheckImsiResponse.class);
	}

	/**
	 * ������������� ����� � �������� ����������� �����
	 * @param response - ServiceStatusRes
	 * @return XML-������
	 * @throws JAXBException
	 */
	public String marshallServiceFeeResultResponse(ServiceStatusRes response) throws JAXBException
	{
		return super.marshalBean(response);
	}

	/**
	 * ���������� ����� � �������� ����������� �����
	 * @param responseXML - XML-������ � �������
	 * @return ServiceStatusRes
	 */
	public ServiceStatusRes unmarshallServiceFeeResultResponse(String responseXML) throws JAXBException
	{
		return super.unmarshalBean(responseXML, ServiceStatusRes.class);
	}

	/**
	 * ������������� ������ � �������� ����������� �����
	 * @param response - ServiceFeeResultRq
	 * @return XML-������
	 * @throws JAXBException
	 */
	public String marshallServiceFeeResultRequest(ServiceFeeResultRq response) throws JAXBException
	{
		return super.marshalBean(response);
	}

	/**
	 * ���������� ������ � �������� ����������� �����
	 * @param responseXML - XML-������ � �������
	 * @return ServiceFeeResultRq
	 */
	public ServiceFeeResultRq unmarshallServiceFeeResultRequest(String responseXML) throws JAXBException
	{
		return super.unmarshalBean(responseXML, ServiceFeeResultRq.class);
	}
}
