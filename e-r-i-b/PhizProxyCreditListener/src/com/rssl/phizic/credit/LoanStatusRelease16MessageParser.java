package com.rssl.phizic.credit;

import com.rssl.phizgate.common.credit.etsm.ETSMLoanClaimConstants;
import com.rssl.phizic.common.types.TextMessage;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.messaging.XmlMessage;
import com.rssl.phizic.messaging.XmlMessageParser;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.utils.xml.jaxb.JAXBSerializerBase;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16.ERIBSendETSMApplRq;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16.StatusLoanApplicationRq;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.validation.Schema;

/**
 * Парсер сообщения о статусе кредитной заявки (формат xsd 16 релиза)
 * @author Rtischeva
 * @ created 10.03.15
 * @ $Author$
 * @ $Revision$
 */
class LoanStatusRelease16MessageParser extends JAXBSerializerBase implements XmlMessageParser
{
	private final Schema release16MessageSchema;

	private static final Class[] MESSAGE_CLASSES = {StatusLoanApplicationRq.class, ERIBSendETSMApplRq.class};

	LoanStatusRelease16MessageParser()
	{
		super(MESSAGE_CLASSES, "UTF-8");
		try
		{
			release16MessageSchema = XmlHelper.schemaByResourceName(ETSMLoanClaimConstants.XSD_RELEASE_16);
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

		String text = message.getText();

		Object xmlMessage = unmarshallBeanWithValidation(text, release16MessageSchema);
		if (xmlMessage != null)
			return buildXmlMessage(xmlMessage, text);
		return buildXmlMessage(unmarshalBean(text), text);
	}

	private XmlMessage buildXmlMessage(Object data, String text) throws JAXBException
	{
		Class requestClass = data.getClass();
		if (requestClass == StatusLoanApplicationRq.class)
			return new PhizProxyCreditEjbMessage((StatusLoanApplicationRq)data, text);
		if (requestClass == ERIBSendETSMApplRq.class)
			return new PhizProxyCreditEjbMessage((ERIBSendETSMApplRq) data, text);
		throw new JAXBException("Неизвестный формат сообщения: " + text);
	}
}
