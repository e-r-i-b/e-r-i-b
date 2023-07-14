package com.rssl.phizic.credit;

import com.rssl.phizgate.common.credit.etsm.ETSMLoanClaimConstants;
import com.rssl.phizic.common.types.TextMessage;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.messaging.XmlMessage;
import com.rssl.phizic.messaging.XmlMessageParser;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.utils.xml.jaxb.JAXBSerializerBase;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13.StatusLoanApplicationRq;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.validation.Schema;
/**
 * ѕарсер сообщени€ о статусе кредитной за€вки (формат xsd 13 релиза)
 * @author Rtischeva
 * @ created 10.03.15
 * @ $Author$
 * @ $Revision$
 */
class LoanStatusRelease13MessageParser extends JAXBSerializerBase implements XmlMessageParser
{
	private final Schema release13MessageSchema;

	private static final Class[] MESSAGE_CLASSES = {StatusLoanApplicationRq.class};

	LoanStatusRelease13MessageParser()
	{
		super(MESSAGE_CLASSES, "UTF-8");
		try
		{
			release13MessageSchema = XmlHelper.schemaByResourceName(ETSMLoanClaimConstants.XSD_RELEASE_13);
		}
		catch (SAXException e)
		{
			throw new InternalErrorException("—бой на загрузке XSD-схемы", e);
		}
	}

	public XmlMessage parseMessage(TextMessage message) throws JAXBException
	{
		String text = message.getText();
		Object data = unmarshallBeanWithValidation(text, release13MessageSchema);
		return new PhizProxyCreditEjbMessage((StatusLoanApplicationRq)data, text);
	}
}

