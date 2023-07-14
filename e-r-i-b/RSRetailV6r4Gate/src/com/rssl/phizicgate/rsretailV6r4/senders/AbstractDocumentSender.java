package com.rssl.phizicgate.rsretailV6r4.senders;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.documents.CommissionCalculator;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.SynchronizableDocument;
import com.rssl.phizic.gate.documents.WithdrawDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizgate.common.payments.documents.AbstractDocumentSenderBase;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.utils.NumericUtil;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.text.ParseException;
import java.math.BigDecimal;
import javax.xml.transform.TransformerException;

public abstract class AbstractDocumentSender extends AbstractDocumentSenderBase  implements CommissionCalculator
{
	public static final String PARAMETER_OPERATION_TYPE_NAME = "operation-type";

	protected AbstractDocumentSender(GateFactory factory)
	{
		super(factory);
	}

	protected abstract GateMessage createGateMessage(GateDocument gateDocument) throws GateException, GateLogicException;

	/**
	 * заполнить сообщение гейту данными из документа
	 *
	 * @param gateMessage сообщение
	 * @param gateDocument документ
	 */
	void fillGateMessage(GateMessage gateMessage, GateDocument gateDocument) throws GateLogicException, GateException
	{
		//Если платеж толдько создан, то у него нет AdmissionDate - даты отправки в банк (такое бывает при сохранении платежа для получения комиссии)
		if (gateDocument.getAdmissionDate() != null)
			gateMessage.addParameter("dateCreated", String.format("%1$td.%1$tm.%1$tY", gateDocument.getAdmissionDate().getTime()));
	}

	public void rollback(WithdrawDocument document) throws GateException, GateLogicException
	{
		String externalId = document.getWithdrawExternalId();

		String[] str = externalId.split(",");
		String keyString = str[0].trim();
		String kindString = str[1].trim();

		String key = keyString.split(":")[1].trim();
		String kind = kindString.split(":")[1].trim();

		WebBankServiceFacade service = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		GateMessage message = service.createRequest("recallDocument_q");

		Element documentNode = XmlHelper.appendSimpleElement(message.getDocument().getDocumentElement(), "document");
		XmlHelper.appendSimpleElement(documentNode, "applicationKind", kind);
		XmlHelper.appendSimpleElement(documentNode, "applicationKey", key);
		String sendId = new ExternalKeyCreator(kind, key).createId();

		Document answerFromRetail = service.sendOnlineMessage(message, null);

		String recalledDocumentId = getRecallExternalIdFromXml(answerFromRetail);

		if (!recalledDocumentId.equals(sendId))
			throw new GateLogicException("Невозможно отозвать документ");
	}

	/**
	 * послать документ
	 * @param gateDocument документ
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException
	 */
	public void send(GateDocument gateDocument) throws GateException, GateLogicException
	{
		GateMessage gateMessage = createGateMessage(gateDocument);

		fillGateMessage(gateMessage,gateDocument);

		WebBankServiceFacade service = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		Document answerFromRetail = service.sendOnlineMessage(gateMessage, null);

		if (gateDocument instanceof SynchronizableDocument)
			((SynchronizableDocument) gateDocument).setExternalId(getExternalIdFromXml(answerFromRetail));
	}

	public void repeatSend(GateDocument document) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("Возможность повторной отправки платежа не реализована");
	}

	private String getRecallExternalIdFromXml(Document answerFromRetail) throws GateException
	{
		Element documentElem;
		try
		{
			documentElem = XmlHelper.selectSingleNode(answerFromRetail.getDocumentElement(), "document");
		}
		catch (TransformerException e)
		{
			throw new GateException(e);
		}

		String errorCode = XmlHelper.getSimpleElementValue(documentElem, "errorCode");
		if (!"0".equals(errorCode))
		{
			String errorText = XmlHelper.getSimpleElementValue(documentElem, "errorText");
			throw new GateException(errorText);
		}

		String key = XmlHelper.getSimpleElementValue(documentElem, "applicationKey");
		String kind = XmlHelper.getSimpleElementValue(documentElem, "applicationKind");
		ExternalKeyCreator externalKeyCreator = new ExternalKeyCreator(kind, key);
		return externalKeyCreator.createId();
	}

	public void prepare(GateDocument document) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("не реализовано");
	}

	public void confirm(GateDocument doc) throws GateException, GateLogicException
	{
		
	}

	private String getExternalIdFromXml(Document answerFromRetail) throws GateException
	{
		String key = XmlHelper.getSimpleElementValue(answerFromRetail.getDocumentElement(), "applicationKey");
		String kind = XmlHelper.getSimpleElementValue(answerFromRetail.getDocumentElement(), "applicationKind");
		if(key == null || kind == null)
			throw new GateException("Ошибка вставки платежа: key:"+key+", kind:"+kind);
		ExternalKeyCreator externalKeyCreator = new ExternalKeyCreator(kind, key);
		return externalKeyCreator.createId();
	}

	public void calcCommission(GateDocument document) throws GateException, GateLogicException
	{
		WebBankServiceFacade webBankServiceFacade = GateSingleton.getFactory().service(WebBankServiceFacade.class);

		GateMessage message = webBankServiceFacade.createRequest("commissionPay_q");
		GateMessage msg = createGateMessage(document);

		msg.addParameter("dateCreated", String.format("%1$td.%1$tm.%1$tY", document.getClientCreationDate().getTime()));

		fillGateMessage(msg,document);

		Node node  = message.getDocument().importNode(msg.getDocument().getDocumentElement(), true);
		
		message.getDocument().getDocumentElement().appendChild(node);
		Document response = webBankServiceFacade.sendOnlineMessage(message, null);

		Money commission = getDocumentCommission(response);
		document.setCommission(commission);
	}

	protected Money getDocumentCommission(Document document) throws GateException, GateLogicException
	{
		try
		{
			Element curElem =  XmlHelper.selectSingleNode(document.getDocumentElement(), "commission/currency");
			Element sumElem1 = XmlHelper.selectSingleNode(document.getDocumentElement(), "commission/value");
			String sumStr = sumElem1.getTextContent();
			String currISO = curElem.getTextContent();
			CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
			Currency cur = currencyService.findByAlphabeticCode(currISO);
			if(cur==null)
				cur = currencyService.getNationalCurrency();
			return new Money(NumericUtil.parseBigDecimal(sumStr), cur);
		}
		catch (ParseException e)
		{
			throw new GateException(e);
		}
		catch (TransformerException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * прнцип работы туп как валенок: отдаем в ритейл платеж, он его валидирует
	 * (в нашем случае на разрешение операций по видам счетов списания и зачисления)
	 * и отдает нам пустой документ(в случае если все ок) или код и текст ошибки.
	 * Далее ошибка (если есть) показывается на экран пользователю
	 * @param document платеж, отправляемый в ритейл
	 * @throws GateException
	 * @throws GateLogicException - ошибка валидирования
	 */
	public void validate(GateDocument document) throws GateException, GateLogicException
	{
		try
		{
			WebBankServiceFacade webBankServiceFacade = GateSingleton.getFactory().service(WebBankServiceFacade.class);

			GateMessage message = webBankServiceFacade.createRequest("validatePayment_q");
			GateMessage msg = createGateMessage(document);

			fillGateMessage(msg,document);

			Node node  = message.getDocument().importNode(msg.getDocument().getDocumentElement(), true);
			//хитрая схема включения комиссии в сумму платежа
			if(document.getCommission() != null)
			{
				Element chargeOffAmountNode = XmlHelper.selectSingleNode((Element)node, "chargeOffAmount/value");
				if (chargeOffAmountNode != null)
				{
					BigDecimal sum = NumericUtil.parseBigDecimal(chargeOffAmountNode.getTextContent());
					BigDecimal totalSum = sum.add(document.getCommission().getDecimal());
					chargeOffAmountNode.setTextContent(totalSum.toString());
				}
			}

			message.getDocument().getDocumentElement().appendChild(node);


			webBankServiceFacade.sendOnlineMessage(message, null);
		}
		catch(TransformerException e)
		{
			throw new GateException(e);
		}
		catch (ParseException e)
		{
			throw new GateException(e);
		}
	}
}