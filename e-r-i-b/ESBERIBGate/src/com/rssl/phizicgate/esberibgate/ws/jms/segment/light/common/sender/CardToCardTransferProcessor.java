package com.rssl.phizicgate.esberibgate.ws.jms.segment.light.common.sender;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.CardsTransfer;
import com.rssl.phizic.gate.payments.ReceiverCardType;
import com.rssl.phizic.logging.monitoring.MonitoringDocumentType;
import com.rssl.phizicgate.esberibgate.ws.jms.ESBSegment;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.OnlineMessageProcessor;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.OnlineMessageProcessorBase;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.Request;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.Response;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.light.ExternalIdGenerator;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.light.RequestHelper;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.light.generated.CardToCardRequest;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.light.generated.CardToCardResponse;

/**
 * @author akrenev
 * @ created 11.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * процессор для перевода с карты на карту
 */

class CardToCardTransferProcessor<D extends CardsTransfer> extends OnlineMessageProcessorBase<CardToCardResponse>
{
	private static final String OK_ERROR_CODE = "0";

	private static final String SB_CARD     = "00";
	private static final String MASTERCARD  = "01";
	private static final String VISA        = "02";

	private static final String INTERNAL_MESSAGE_TYPE    = "CardToCardRequest";
	private static final String EXTERNAL_MESSAGE_TYPE    = "CardToCardRequest";
	private static final String MONITORING_DOCUMENT_TYPE = MonitoringDocumentType.CARD_TRANSFER.name();
	private static final String SYSTEM_ID                = "light-esb";

	private final D document;
	private CardToCardRequest request;

	/**
	 * конструктор
	 * @param document документ
	 * @param serviceName имя сервиса
	 */
	CardToCardTransferProcessor(D document, String serviceName)
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
	protected String getResponseId(CardToCardResponse response)
	{
		return response.getHead().getMessUID().getMessageId();
	}

	@Override
	protected String getResponseErrorCode(CardToCardResponse response)
	{
		return response.getHead().getError().getErrCode();
	}

	@Override
	protected String getResponseErrorMessage(CardToCardResponse response)
	{
		return response.getHead().getError().getErrMes();
	}

	@Override
	protected void processResponse(Request<OnlineMessageProcessor<CardToCardResponse>> request, Response<CardToCardResponse> response) throws GateException, GateLogicException
	{
		if (!OK_ERROR_CODE.equals(response.getErrorCode()))
			processError(request, response);

		CardToCardResponse cardToCardResponse = response.getResponse();
		String authorizeCode = cardToCardResponse.getBody().getAuthorizeCode();
		document.setAuthorizeCode(authorizeCode);
	}

	private CardToCardRequest buildRequestObject(D document) throws GateException, GateLogicException
	{
		CardToCardRequest cardToCardRequest = new CardToCardRequest();

		cardToCardRequest.setHead(RequestHelper.getHeader(EXTERNAL_MESSAGE_TYPE));
		cardToCardRequest.setBody(getBody(document));
		cardToCardRequest.setSign(RequestHelper.getSign());

		return cardToCardRequest;
	}

	private CardToCardRequest.Body getBody(D document) throws GateException
	{
		Money amount = getAmount(document);

		CardToCardRequest.Body body = new CardToCardRequest.Body();

		body.setDebitCard(getDebitCard(document));
		body.setCurrCode(RequestHelper.getCurrency(amount));
		body.setCreditCard(document.getReceiverCard());
		body.setSumma(RequestHelper.getMoney(amount));
		body.setProperties(getProperties(document.getReceiverCardType()));

		return body;
	}

	private Money getAmount(D document)
	{
		return document.getChargeOffAmount() == null ? document.getDestinationAmount() : document.getChargeOffAmount();
	}

	private CardToCardRequest.Body.DebitCard getDebitCard(D document)
	{
		CardToCardRequest.Body.DebitCard debitCard = new CardToCardRequest.Body.DebitCard();
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
}
