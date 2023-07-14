package com.rssl.phizic.test.ermb.cod;

import com.rssl.phizic.business.ermb.auxiliary.cod.message.generated.ERMBConnActionRq;
import com.rssl.phizic.business.ermb.auxiliary.cod.message.generated.ERMBConnActionRs;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.test.ermb.cod.generated.MBVEnableService;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.utils.xml.jaxb.JAXBUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLFilterImpl;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.StringReader;
import java.rmi.RemoteException;
import java.util.Calendar;
import javax.xml.bind.JAXBException;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;

/**
 * Заглушка сервиса для включения/отключения признака подключения к ЕРМБ (ЦОД)
 * @author Puzikov
 * @ created 12.11.13
 * @ $Author$
 * @ $Revision$
 */

public class CODWebServiceImpl implements MBVEnableService
{
	private static final String version = "1.0";
	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);
	private static final String XSD_FILE = "com/rssl/phizic/test/ermb/cod/erib-cod-ermb.xsd";

	public String sendMessage(String message) throws RemoteException
	{
		ERMBConnActionRq request = parseMessage(message, XSD_FILE);

		String action = "000".equals(request.getAction()) ? "отключение" : "включение";
		String name = request.getClient().getPersonName().getFirstName() + ' ' +
				request.getClient().getPersonName().getLastName() + ' ' +
				request.getClient().getPersonName().getMiddleName();
		log.info("Принят запрос на " + action + " признака подключения к ЕРМБ клиента " + name);

		return buildMessage();
	}

	private String buildMessage() throws RemoteException
	{
		ERMBConnActionRs result = new ERMBConnActionRs();
		result.setVersion(version);
		result.setRqUID(new RandomGUID().getStringValue());
		result.setRqTm(Calendar.getInstance());
		result.setResult(getResult());
		try
		{
			return JAXBUtils.marshalBean(result);
		}
		catch (JAXBException e)
		{
			throw new RemoteException("Ошибка создания строки ответа веб-сервиса для включения признака подключения к ЕРМБ", e);
		}
	}

	private String getResult()
	{
		int r = RandomUtils.nextInt(10);
		switch (r)
		{
			case 9:
				return "004_Exception";
			case 8:
				return "003_Already_Done";
			case 7:
				return "002_No_Connect";
			case 6:
				return "001_No_Parameter";
			default:
				return "000_Success";
		}
	}

	private ERMBConnActionRq parseMessage(String message, String xsdFile) throws RemoteException
	{
		try
		{
			Schema schema = XmlHelper.schemaByResourceName(xsdFile);
			Validator validator = schema.newValidator();
			SAXSource source = new SAXSource(
					new XMLNamespaceFilter(XMLReaderFactory.createXMLReader()),
					new InputSource(new StringReader(message)));
			validator.validate(source, null);
			return JAXBUtils.unmarshalBean(ERMBConnActionRq.class, message);
		}
		catch (Exception e)
		{
			throw new RemoteException("Ошибка парсинга сообщения при добавлении ЕРМБ профиля клиенту в COD", e);
		}
	}

	private static class XMLNamespaceFilter extends XMLFilterImpl
	{
		private static final String requiredNamespace = "http://service.cod.sbrf.ru";

		private XMLNamespaceFilter(XMLReader parent)
		{
			super(parent);
		}

		@Override
		public void startElement(String arg0, String arg1, String arg2, Attributes arg3) throws SAXException {
			if(!requiredNamespace.equals(arg0))
				arg0 = requiredNamespace;
			super.startElement(arg0, arg1, arg2, arg3);
		}
	}
}
