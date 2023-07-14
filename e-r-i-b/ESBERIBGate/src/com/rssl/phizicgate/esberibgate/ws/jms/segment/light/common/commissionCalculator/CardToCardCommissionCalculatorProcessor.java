package com.rssl.phizicgate.esberibgate.ws.jms.segment.light.common.commissionCalculator;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.CardsTransfer;
import com.rssl.phizic.gate.payments.ReceiverCardType;
import com.rssl.phizicgate.esberibgate.ws.jms.ESBSegment;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.OnlineMessageProcessor;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.OnlineMessageProcessorBase;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.Request;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.Response;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.light.ExternalIdGenerator;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.light.RequestHelper;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.light.generated.CardToCardInfoRequest;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.light.generated.CardToCardInfoResponse;

import java.math.BigDecimal;

/**
 * @author akrenev
 * @ created 15.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * процессор для запроса комисстт по переводу с карты на карту
 */

class CardToCardCommissionCalculatorProcessor<D extends CardsTransfer> extends OnlineMessageProcessorBase<CardToCardInfoResponse>
{
	private static final String OK_ERROR_CODE = "0";

	private static final String SB_CARD     = "00";
	private static final String MASTERCARD  = "01";
	private static final String VISA        = "02";

	private static final String INTERNAL_MESSAGE_TYPE    = CardToCardInfoRequest.class.getSimpleName();
	private static final String EXTERNAL_MESSAGE_TYPE    = "CardToCardInfoRequest";
	private static final String MONITORING_DOCUMENT_TYPE = Request.SKIP_MONITORING;
	private static final String SYSTEM_ID                = "light-esb";

	private final D document;
	private CardToCardInfoRequest request;

	/**
	 * конструктор
	 * @param document документ
	 * @param serviceName имя сервиса
	 */
	CardToCardCommissionCalculatorProcessor(D document, String serviceName)
	{
		super(ESBSegment.light, serviceName);
		this.document = document;
	}

	@Override
	protected String getRequestId()
	{
		return request.getHead().getMessUID().getMessageId();
	}

	@Override
	protected String getRequestSystemId()
	{
		return SYSTEM_ID;
	}

	@Override
	protected String getRequestMessageType()
	{
		return INTERNAL_MESSAGE_TYPE;
	}

	@Override
	protected String getMonitoringDocumentType()
	{
		return MONITORING_DOCUMENT_TYPE;
	}

	@Override
	protected Object initialize() throws GateException, GateLogicException
	{
		request = buildRequestObject(document);

		document.setExternalId(ExternalIdGenerator.generateExternalId(request));

		return request;
	}

	@Override
	protected String getResponseId(CardToCardInfoResponse response)
	{
		return response.getHead().getMessUID().getMessageId();
	}

	@Override
	protected String getResponseErrorCode(CardToCardInfoResponse response)
	{
		return response.getHead().getError().getErrCode();
	}

	@Override
	protected String getResponseErrorMessage(CardToCardInfoResponse response)
	{
		return response.getHead().getError().getErrMes();
	}

	@Override
	protected void processResponse(Request<OnlineMessageProcessor<CardToCardInfoResponse>> request, Response<CardToCardInfoResponse> response) throws GateException, GateLogicException
	{
		if (!OK_ERROR_CODE.equals(response.getErrorCode()))
			processError(request, response);

		document.setCommission(getCommission(response.getResponse(), CardToCardCommissionCalculatorProcessor.getAmount(document).getCurrency()));
	}

	private CardToCardInfoRequest buildRequestObject(D document) throws GateException, GateLogicException
	{
		CardToCardInfoRequest cardToCardInfoRequest = new CardToCardInfoRequest();

		cardToCardInfoRequest.setHead(RequestHelper.getHeader(EXTERNAL_MESSAGE_TYPE));
		cardToCardInfoRequest.setBody(getBody(document));
		cardToCardInfoRequest.setSign(RequestHelper.getSign());

		return cardToCardInfoRequest;
	}

	private CardToCardInfoRequest.Body getBody(D document) throws GateException
	{
		Money amount = getAmount(document);

		CardToCardInfoRequest.Body body = new CardToCardInfoRequest.Body();

		body.setDebitCard(getDebitCard(document));
		body.setCurrCode(RequestHelper.getCurrency(amount));
		body.setCreditCard(document.getReceiverCard());
		body.setSumma(RequestHelper.getMoney(amount));
		body.setProperties(getProperties(document.getReceiverCardType()));

		return body;
	}

	private CardToCardInfoRequest.Body.DebitCard getDebitCard(D document)
	{
		CardToCardInfoRequest.Body.DebitCard debitCard = new CardToCardInfoRequest.Body.DebitCard();
		debitCard.setCardNumber(document.getChargeOffCard());
		debitCard.setEndDate(RequestHelper.getDate(document.getChargeOffCardExpireDate()));
		return debitCard;
	}

	private String getProperties(ReceiverCardType receiverCardType) throws GateException
	{
		if (receiverCardType == null)
			return SB_CARD;

		switch (receiverCardType)
		{
			case SB:         return SB_CARD;
			case MASTERCARD: return MASTERCARD;
			case VISA:       return VISA;
			default: throw new GateException("Неизвестный тип карты " + receiverCardType);
		}
	}

	static Money getAmount(CardsTransfer transfer)
	{
		return transfer.getChargeOffAmount() == null ? transfer.getDestinationAmount() : transfer.getChargeOffAmount();
	}

	private Money getCommission(CardToCardInfoResponse response, Currency currency) throws GateException
	{
		String strMoney = response.getBody().getComission();
		//Устанавливаем валюту операции
		return new Money(new BigDecimal(strMoney), currency);
	}
}
