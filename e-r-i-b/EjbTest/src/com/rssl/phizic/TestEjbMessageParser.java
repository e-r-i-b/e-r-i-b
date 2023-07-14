package com.rssl.phizic;

import com.rssl.phizic.common.types.TextMessage;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.messaging.XmlMessage;
import com.rssl.phizic.messaging.XmlMessageParser;
import com.rssl.phizic.messaging.ermb.SendSmsRequest;
import com.rssl.phizic.messaging.ermb.SendSmsWithImsiRequest;
import com.rssl.phizic.synchronization.types.ResetIMSIRq;
import com.rssl.phizic.synchronization.types.ServiceStatusRes;
import com.rssl.phizic.synchronization.types.UpdateProfilesRq;
import com.rssl.phizic.utils.xml.jaxb.JAXBSerializerBase;
import com.rssl.phizicgate.esberibgate.bki.generated.EnquiryRequestERIB;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16.CRMNewApplRq;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16.ERIBUpdApplStatusRq;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19.ConsumerProductOfferResultRq;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19.OfferTicket;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19.SearchApplicationRq;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * @author Rtischeva
 * @ created 13.12.14
 * @ $Author$
 * @ $Revision$
 */
public class TestEjbMessageParser extends JAXBSerializerBase implements XmlMessageParser
{
	private final Log log = LogFactory.getLog(LogModule.Web.toString());

	private static final Class[] MESSAGE_CLASSES =
	{
			SendSmsRequest.class,
			SendSmsWithImsiRequest.class,
			com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16.ChargeLoanApplicationRq.class,
			//com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13.ChargeLoanApplicationRq.class,
			EnquiryRequestERIB.class,
			UpdateProfilesRq.class,
			ServiceStatusRes.class,
			ResetIMSIRq.class,
			CRMNewApplRq.class,
			ERIBUpdApplStatusRq.class
	};
	//≈сли пытатьс€ инициализировать jaxBContext классами в основе своей имеющие одинаковые типы в xsd, возникает ошибка
	//дл€ этой цели выносим их в дополнительный контекст
	Class[] ADD_MESSAGE_CLASSES = { SearchApplicationRq.class, OfferTicket.class, ConsumerProductOfferResultRq.class};

	public TestEjbMessageParser()
	{
		super(MESSAGE_CLASSES, "UTF-8");
	}

	public XmlMessage parseMessage(TextMessage message) throws JAXBException
	{
		log.trace("ѕолучено текстовое JMS-сообщение: " + message);

		String text = message.getText();
		Object object = null;
		try
		{
			object = unmarshalBean(text);
		}
		catch (JAXBException ex)
		{
			JAXBContext addJaxbContext = null;
			try
			{
				addJaxbContext = JAXBContext.newInstance(ADD_MESSAGE_CLASSES);

			}
			catch (JAXBException e)
			{
				throw new RuntimeException("—бой на загрузке JAXB-контекста по классам " + Arrays.toString(ADD_MESSAGE_CLASSES), e);
			}
			try
			{
				Unmarshaller um = addJaxbContext.createUnmarshaller();
				InputStream is = new ByteArrayInputStream(text.getBytes("UTF-8"));
				object = um.unmarshal(is);
			}
			catch (UnsupportedEncodingException e)
			{
				throw new JAXBException(ex.getMessage());
			}

		}
		return buildXmlMessage(object, text);
	}

	private XmlMessage buildXmlMessage(Object data, String text) throws JAXBException
	{
		Class requestClass = data.getClass();

		if (requestClass == SendSmsRequest.class)
			return new TestEjbMessage((SendSmsRequest)data, text);
		if (requestClass == SendSmsWithImsiRequest.class)
			return new TestEjbMessage((SendSmsWithImsiRequest) data, text);
		if (requestClass == com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16.ChargeLoanApplicationRq.class)
			return new TestEjbMessage((com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16.ChargeLoanApplicationRq) data, text);
		if (requestClass == com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13.ChargeLoanApplicationRq.class)
			return new TestEjbMessage((com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13.ChargeLoanApplicationRq) data, text);
		if (requestClass == EnquiryRequestERIB.class)
			return new TestEjbMessage((EnquiryRequestERIB) data, text);
		if (requestClass == UpdateProfilesRq.class)
			return new TestEjbMessage((UpdateProfilesRq) data, text);
		if (requestClass == ServiceStatusRes.class)
			return new TestEjbMessage((ServiceStatusRes) data, text);
		if (requestClass == ResetIMSIRq.class)
			return new TestEjbMessage((ResetIMSIRq) data, text);
		if (requestClass == CRMNewApplRq.class)
			return new TestEjbMessage((CRMNewApplRq) data, text);
		if (requestClass == ERIBUpdApplStatusRq.class)
			return new TestEjbMessage((ERIBUpdApplStatusRq) data, text);
		if (requestClass == SearchApplicationRq.class)
			return new TestEjbMessage((SearchApplicationRq) data, text);
		if (requestClass == OfferTicket.class)
			return new TestEjbMessage((OfferTicket) data, text);
		if (requestClass == ConsumerProductOfferResultRq.class)
			return new TestEjbMessage((ConsumerProductOfferResultRq) data, text);
		throw new JAXBException("Ќеизвестный формат сообщени€: " + text);
	}

}
