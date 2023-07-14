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
 * ������������ + �������������� ��������� ���������� ������ ����
 * ������ ������������ ������ � ������������ P2P-�������� ���
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
	 * ������������� p2p ������ ��� � XML-������
	 * @param request - ������
	 * @return XML-������
	 */
	public String marshallRequest(P2PRequest request) throws JAXBException
	{
		return super.marshalBean(request);
	}

	/**
	 * ���������� p2p ������ ��� �� XML-������
	 * @param requestXML - XML-������ � ��������
	 * @return ������
	 */
	public P2PRequest unmarshallRequest(String requestXML) throws JAXBException
	{
		return super.unmarshalBean(requestXML);
	}
}
