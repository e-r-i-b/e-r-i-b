package com.rssl.phizic.rapida.senders;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.systems.AccountPaymentSystemPayment;
import com.rssl.phizic.gate.payments.ErrorProcessor;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.errors.ErrorSystem;
import com.rssl.phizic.rapida.RapidaURLBuilder;
import com.rssl.phizic.rapida.RapidaService;
import com.rssl.phizic.rapida.RapidaFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author Krenev
 * @ created 26.11.2007
 * @ $Author$
 * @ $Revision$
 */
public class RapidaPaymentSender extends AbstractDocumentSender
{
	private static final RapidaService RAPIDA_ONLINE_SERVICE = RapidaFactory.service(RapidaService.class);
	private static final RapidaURLBuilder RAPIDA_URL_BUILDER = new RapidaURLBuilder();
	private static final String RESULT_OK = "OK";
	private static final String DESCRIPTION_TAG_NAME = "Description";
	private static final String RESULT_TAG_NAME = "Result";
	private static final ErrorProcessor errorProcessor = new ErrorProcessor(ErrorSystem.Rapida);

	public void send(GateDocument document) throws GateException, GateLogicException
	{
		if (!(document instanceof AccountPaymentSystemPayment))
			throw new GateException("Неверный тип платежа, должен быть - PaymentSystemPayment.");
		AccountPaymentSystemPayment PaymentSystemPayment = (AccountPaymentSystemPayment) document;
		String url;
		try
		{
			url = RAPIDA_URL_BUILDER.buildPaymentRequest(PaymentSystemPayment);
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
		sendMessage(url);
	}

	public void prepare(GateDocument document) throws GateException, GateLogicException
	{
		if (!(document instanceof AccountPaymentSystemPayment))
			throw new GateException("Неверный тип платежа, должен быть - AccountPaymentSystemPayment.");
		AccountPaymentSystemPayment PaymentSystemPayment = (AccountPaymentSystemPayment) document;
		errorProcessor.setDocument(document);
		String url;
		try
		{
			url = RAPIDA_URL_BUILDER.buildCheckPaymentRequest(PaymentSystemPayment);
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
		sendMessage(url);
	}

	private void sendMessage(String url) throws GateException,GateLogicException
	{
		Document response = RAPIDA_ONLINE_SERVICE.sendPayment(url);
		Element root = response.getDocumentElement();
		String result = XmlHelper.getSimpleElementValue(root, RESULT_TAG_NAME);
		if (!RESULT_OK.equals(result))
		{
			String descr = XmlHelper.getSimpleElementValue(root, DESCRIPTION_TAG_NAME);
			errorProcessor.parseError(descr);
		}
	}

}
