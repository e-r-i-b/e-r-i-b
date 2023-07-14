package com.rssl.ikfl.crediting;

import com.rssl.phizic.common.types.TextMessage;
import com.rssl.phizic.messaging.XmlMessage;
import com.rssl.phizic.messaging.XmlMessageParser;
import com.rssl.phizicgate.esberibgate.AbstractESBERIBMessageMarshaller;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.GetCampaignerInfoRq;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.GetCampaignerInfoRs;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.RegisterRespondToMarketingProposeRq;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.RegisterRespondToMarketingProposeRs;

import javax.xml.bind.JAXBException;

/**
 * @author Erkin
 * @ created 29.12.2014
 * @ $Author$
 * @ $Revision$
 */
public class CRMMessageMarshaller extends AbstractESBERIBMessageMarshaller implements XmlMessageParser
{
	public String marshalOfferRequest(GetCampaignerInfoRq request) throws JAXBException
	{
		return marshalBean(request);
	}

	public String marshalOfferResponse(GetCampaignerInfoRs response) throws JAXBException
	{
		return marshalBean(response);
	}

	public GetCampaignerInfoRs unmarshalOfferResponse(String getOfferResponseXML) throws JAXBException
	{
		return unmarshallBeanWithValidation(getOfferResponseXML, eribAdapterXSDSchema);
	}

	public String marshallFeedbackRequest(RegisterRespondToMarketingProposeRq request) throws JAXBException
	{
		return marshalBean(request);
	}

	public XmlMessage parseMessage(TextMessage message) throws JAXBException
	{
		log.trace("Получено текстовое JMS-сообщение: " + message);

		String messageText = message.getText();
		Object messageBean = unmarshallBeanWithValidation(messageText, eribAdapterXSDSchema);
		return buildXmlMessage(messageBean, messageText);
	}

	private XmlMessage buildXmlMessage(Object data, String text) throws JAXBException
	{
		Class requestClass = data.getClass();

		if (requestClass == GetCampaignerInfoRq.class)
			return new CRMMessage((GetCampaignerInfoRq)data, text);

		if (requestClass == GetCampaignerInfoRs.class)
			return new CRMMessage((GetCampaignerInfoRs)data, text);

		if (requestClass == RegisterRespondToMarketingProposeRq.class)
			return new CRMMessage((RegisterRespondToMarketingProposeRq)data, text);

		if (requestClass == RegisterRespondToMarketingProposeRs.class)
			return new CRMMessage((RegisterRespondToMarketingProposeRs)data, text);

		throw new JAXBException("Неожиданный тип сообщения: " + requestClass);
	}
}
