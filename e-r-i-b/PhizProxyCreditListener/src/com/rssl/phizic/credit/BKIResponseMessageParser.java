package com.rssl.phizic.credit;

import com.rssl.phizgate.common.credit.bki.BKIConstants;
import com.rssl.phizic.common.types.TextMessage;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.messaging.XmlMessage;
import com.rssl.phizic.messaging.XmlMessageParser;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.utils.xml.jaxb.JAXBSerializerBase;
import com.rssl.phizicgate.esberibgate.bki.generated.EnquiryResponseERIB;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.validation.Schema;

/**
 * Парсер ответов из БКИ
 * @author Rtischeva
 * @ created 10.03.15
 * @ $Author$
 * @ $Revision$
 */
class BKIResponseMessageParser extends JAXBSerializerBase implements XmlMessageParser
{
	private final Schema bkiMessageSchema;

	private static final Class[] MESSAGE_CLASSES = {EnquiryResponseERIB.class};

	BKIResponseMessageParser()
	{
		super(MESSAGE_CLASSES, "UTF-8");
		try
		{
			bkiMessageSchema = XmlHelper.schemaByResourceName(BKIConstants.XSD);
		}
		catch (SAXException e)
		{
			throw new InternalErrorException("Сбой на загрузке XSD-схемы", e);
		}
	}

	public XmlMessage parseMessage(TextMessage message) throws JAXBException
	{
		String text = message.getText();
		Object data = unmarshallBeanWithValidation(text, bkiMessageSchema);
		return new PhizProxyCreditEjbMessage((EnquiryResponseERIB)data, text);
	}
}
