package com.rssl.phizic.credit;

import com.rssl.phizgate.common.credit.etsm.ETSMLoanClaimConstants;
import com.rssl.phizic.common.types.TextMessage;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.messaging.XmlMessage;
import com.rssl.phizic.messaging.XmlMessageParser;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.utils.xml.jaxb.JAXBSerializerBase;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19.*;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.validation.Schema;

/**
 * Получение существующих в ETSM заявок клиента по запросу из ЕРИБ (SearchApplicationRq)
 * @author Moshenko
 * @ created 10.03.15
 * @ $Author$
 * @ $Revision$
 */
public class LoanApplicationRelease19MessageParser extends JAXBSerializerBase implements XmlMessageParser
{
	private Schema messageSchema = null;

	private static final Class[] MESSAGE_CLASSES = {SearchApplicationRs.class, InitiateConsumerProductOfferRq.class, OfferResultTicket.class, StatusLoanApplicationRq.class, ERIBSendETSMApplRq.class};

	LoanApplicationRelease19MessageParser()
	{
		super(MESSAGE_CLASSES, "UTF-8");
		try
		{
			messageSchema = XmlHelper.schemaByResourceName(ETSMLoanClaimConstants.XSD_RELEASE_19);
		}
		catch (SAXException e)
		{
			throw new InternalErrorException("Сбой на загрузке XSD-схемы", e);
		}
	}

	public XmlMessage parseMessage(TextMessage textMessage) throws JAXBException
	{
		String text = textMessage.getText();
		Object data = unmarshallBeanWithValidation(text, messageSchema);
		if (data instanceof SearchApplicationRs)
			return new PhizProxyCreditEjbMessage((SearchApplicationRs)data, text);
		if (data instanceof InitiateConsumerProductOfferRq)
			return new PhizProxyCreditEjbMessage((InitiateConsumerProductOfferRq)data, text);
		if (data instanceof OfferResultTicket)
			return new PhizProxyCreditEjbMessage((OfferResultTicket)data, text);
		if (data instanceof StatusLoanApplicationRq)
			return new PhizProxyCreditEjbMessage((StatusLoanApplicationRq) data, text);
		if (data instanceof ERIBSendETSMApplRq)
			return new PhizProxyCreditEjbMessage((ERIBSendETSMApplRq) data, text);
		throw new JAXBException("Получен не известный тип сообщения");
	}
}
