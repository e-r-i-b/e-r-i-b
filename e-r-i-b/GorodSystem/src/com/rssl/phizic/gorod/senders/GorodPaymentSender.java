package com.rssl.phizic.gorod.senders;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.payments.systems.AccountPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gorod.GorodRequestHelper;
import com.rssl.phizic.gorod.messaging.XMLMessagingService;
import com.rssl.phizic.gorod.messaging.Constants;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizgate.common.payments.BillingPaymentHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.transform.TransformerException;

/**
 * @author: Pakhomova
 * @created: 06.08.2009
 * @ $Author$
 * @ $Revision$
 */
public class GorodPaymentSender extends com.rssl.phizic.gorod.senders.sevb.GorodPaymentSender
{

	public GorodPaymentSender(GateFactory factory)
	{
		super(factory);
	}

	/**
	 * отсылает в город два сообщения
	 * ReqSaveEOrder(сохранение платежа) и
	 * ReqPayAdvice (проводка)
	 * @param gateDocument  платеж
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public void confirm(GateDocument gateDocument) throws GateLogicException, GateException
	{
		try
		{
			AccountPaymentSystemPayment doc = (AccountPaymentSystemPayment) gateDocument;
			XMLMessagingService service = XMLMessagingService.getInstance(getFactory());
			GorodRequestHelper requestHelper = new GorodRequestHelper(getFactory());

			// ищем в платеже поле, указываеюще главная услуга или нет
			Field mainServiceField = BillingPaymentHelper.getFieldById(doc.getExtendedFields(), Constants.NAME_MAIN_SERVICE_FIELD);
			boolean mainService = mainServiceField != null ? Boolean.valueOf((String)mainServiceField.getValue()) :
					requestHelper.isMainService(doc.getService().getCode());

			GateMessage payment = requestHelper.buildReqSaveEOrderRequest("", doc, requestHelper.isComplexService(doc.getExtendedFields()), mainService);
			Document saveResp = service.sendOnlineMessage(payment,null);

			Element ePay = XmlHelper.selectSingleNode(saveResp.getDocumentElement(), "EOrder/EPay");
			String unp = ePay.getAttribute("unp");

			GateMessage msg = service.createRequest("ReqPayAdvice");
			Document document = msg.getDocument();
			Element payAdvice = document.createElement("PayAdvice");
			document.getDocumentElement().appendChild(payAdvice);
			Element authAdvice = document.createElement("AuthAdvice");
			payAdvice.appendChild(authAdvice);
			Element lidM = document.createElement("lid-m");
			lidM.setAttribute("amount", doc.getChargeOffAmount().getDecimal().toString());
			lidM.setTextContent(unp);
			authAdvice.appendChild(lidM);
			Element authCode = document.createElement("authCode");
			authCode.setTextContent("0");
			authAdvice.appendChild(authCode);
			service.sendOnlineMessage(msg, null);
		}
		catch (TransformerException e)
		{
			throw new GateException(e);
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
	}

	protected void deletePaymentFromGorod(String paymentId)  throws GateException, GateLogicException
	{
		super.delete(paymentId);
	}

	protected void setReceiverAccountFromISPPN(AccountPaymentSystemPayment payment) throws GateException, TransformerException, GateLogicException
	{
		//do nothing			
	}
}
