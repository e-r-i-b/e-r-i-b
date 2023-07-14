package com.rssl.phizicgate.sbrf.ws.listener.handler;

import com.rssl.phizic.gate.documents.AbstractAccountTransfer;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.AbstractJurTransfer;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author Egorova
 * @ created 04.08.2008
 * @ $Author$
 * @ $Revision$
 */
public class JurPaymentConfirmationOfflineHandler extends PaymentConfirmationOfflineHandlerBase
{
	public void fillPaymentData(AbstractAccountTransfer document, String messageText) throws GateException
	{
		AbstractJurTransfer payment = (AbstractJurTransfer) document;

		try
		{
			Document doc = XmlHelper.parse(messageText);
			//номер реестра
			Element registerNumberElement = XmlHelper.selectSingleNode(doc.getDocumentElement(),"/message/confirmation_offline_a/register");
			if(registerNumberElement != null && registerNumberElement.getTextContent() != null)
				payment.setRegisterNumber(registerNumberElement.getTextContent());
			else
				payment.setRegisterNumber("");
			//строка реестра
			Element registerStringElement = XmlHelper.selectSingleNode(doc.getDocumentElement(),"/message/confirmation_offline_a/line");
			if(registerStringElement != null && registerStringElement.getTextContent()!= null)
				payment.setRegisterString(registerStringElement.getTextContent());
			else
				payment.setRegisterString("");
		}
		catch(Exception ex)
		{
			throw new GateException("Ошибка при разборе offline сообщения", ex);
		}

		super.fillPaymentData(document, messageText);
	}

	protected boolean isConvertionPayment(AbstractAccountTransfer document)
	{
		return false;
	}

	protected String getDestinationAccount(AbstractAccountTransfer document)
	{
		AbstractJurTransfer payment = (AbstractJurTransfer) document;
		return payment.getReceiverAccount();
	}
}
