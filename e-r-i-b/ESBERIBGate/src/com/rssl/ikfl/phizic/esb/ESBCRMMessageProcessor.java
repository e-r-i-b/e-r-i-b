package com.rssl.ikfl.phizic.esb;

import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16.ERIBSendETSMApplRq;
import org.xml.sax.SAXException;

import java.io.Reader;
import java.io.StringReader;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;

/**
 * @ author: Gololobov
 * @ created: 13.03.15
 * @ $Author$
 * @ $Revision$
 */
public class ESBCRMMessageProcessor
{
	private static final String XML_NAME = "com/rssl/phizicgate/esberibgate/loanclaim/etsm/xsd/LoanApplicationRelease16.xsd";
	private JAXBContext jaxbContext;
	private final Schema loanXSDSchema;

	private static final Object LOCKER = new Object();
	private static volatile ESBCRMMessageProcessor INSTANCE = null;

	private static final Class[] CLASSES = new Class[]
			{
					ERIBSendETSMApplRq.class
			};

	/**
	 * @return инстанс ESBCRMMessageProcessor
	 */
	public static ESBCRMMessageProcessor getInstance()
	{
		if(INSTANCE != null)
			return INSTANCE;

		synchronized (LOCKER)
		{
			if (INSTANCE == null)
			{
				INSTANCE = new ESBCRMMessageProcessor();
			}
			return INSTANCE;
		}
	}

	private ESBCRMMessageProcessor()
	{
		try
		{
			jaxbContext = JAXBContext.newInstance(CLASSES);
			loanXSDSchema = XmlHelper.schemaByResourceName(XML_NAME);
		}
		catch (SAXException e)
		{
			throw new InternalErrorException("[ESB] —бой на загрузке XSD-схемы", e);
		}
		catch (JAXBException e)
		{
			throw new InternalErrorException("[ESB] —бой на загрузке JAXB-контекста", e);
		}
	}

	/**
	 * —оздаЄт Response
	 * @param message xml строка
	 * @return response
	 * @throws JAXBException
	 */
	public Object makeResponse(String message) throws JAXBException
	{
		return readRequest(message);
	}

	private Object readRequest(String requestXML) throws JAXBException
	{
		Reader reader = new StringReader(requestXML);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		unmarshaller.setSchema(loanXSDSchema);
		//noinspection unchecked
		return unmarshaller.unmarshal(reader);
	}
}
