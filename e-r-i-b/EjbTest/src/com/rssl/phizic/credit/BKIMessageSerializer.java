package com.rssl.phizic.credit;

import com.rssl.phizic.utils.xml.jaxb.JAXBSerializerBase;
import com.rssl.phizicgate.esberibgate.bki.generated.EnquiryRequestERIB;
import com.rssl.phizicgate.esberibgate.bki.generated.EnquiryResponseERIB;

import javax.xml.bind.JAXBException;

/**
* @author Erkin
* @ created 21.11.2014
* @ $Author$
* @ $Revision$
*/
class BKIMessageSerializer extends JAXBSerializerBase
{
	private static final Class[] TRANSPORT_MESSAGE_CLASSES = { EnquiryRequestERIB.class, EnquiryResponseERIB.class };

	BKIMessageSerializer()
	{
		super(TRANSPORT_MESSAGE_CLASSES, "UTF-8");
	}

	String marshallBKIResponse(EnquiryResponseERIB bkiResponse) throws JAXBException
	{
		return marshalBean(bkiResponse);
	}

	EnquiryResponseERIB unmarshallBKIResponse(String bkiResponseXml) throws JAXBException
	{
		return unmarshalBean(bkiResponseXml);
	}
}
