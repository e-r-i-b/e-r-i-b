package com.rssl.ikfl.crediting;

import com.rssl.phizic.common.types.TextMessage;
import com.rssl.phizic.messaging.XmlMessage;
import com.rssl.phizic.messaging.XmlMessageParser;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.AbstractETSMMessageMarshallerR16;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16.CRMNewApplRq;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16.ERIBSendETSMApplRq;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16.ERIBUpdApplStatusRq;

import javax.xml.bind.JAXBException;

/**
 * �������� ��� �������� � CRM:
 * - ������� ��������� ������ (�������� ����� ������ �� ������)
 * - ��������� ������� ������������ ������
 * � ����� ��������� �������� ������ �� ���� � ETSM
 *
 * @ author: Gololobov
 * @ created: 16.02.15
 * @ $Author$
 * @ $Revision$
 */
public class CRMExtendedLoanClaimMessageMarshaller extends AbstractETSMMessageMarshallerR16 implements XmlMessageParser
{
	String marshalLoanClaimRequest(CRMNewApplRq request) throws JAXBException
	{
		return marshalBean(request);
	}

	String marshalUpdateLoanClaimStatusRequest(ERIBUpdApplStatusRq request) throws JAXBException
	{
		return marshalBean(request);
	}

	public String marshalInitiationNewLoanClaim(ERIBSendETSMApplRq request) throws JAXBException
	{
		return marshalBean(request);
	}

	/**
	 * �������������� XML-������ � ���������� �� xsd-�����
	 * @param eribSendETSMApplRqXML
	 * @return
	 * @throws JAXBException
	 */
	public ERIBSendETSMApplRq unmarshalInitiationNewLoanClaimRequest(String eribSendETSMApplRqXML) throws JAXBException
	{
		return unmarshallBeanWithValidation(eribSendETSMApplRqXML, loanApplicationXSDSchema);
	}

	public XmlMessage parseMessage(TextMessage message) throws JAXBException
	{
		log.trace("�������� ��������� JMS-���������: " + message);

		String messageText = message.getText();
		Object messageBean = unmarshallBeanWithValidation(messageText, loanApplicationXSDSchema);
		return buildXmlMessage(messageBean, messageText);
	}

	private XmlMessage buildXmlMessage(Object data, String text) throws JAXBException
	{
		Class requestClass = data.getClass();

		if (requestClass == CRMNewApplRq.class)
			return new CRMMessage((CRMNewApplRq)data, text);

		throw new JAXBException("����������� ��� ���������: " + requestClass);
	}
}
