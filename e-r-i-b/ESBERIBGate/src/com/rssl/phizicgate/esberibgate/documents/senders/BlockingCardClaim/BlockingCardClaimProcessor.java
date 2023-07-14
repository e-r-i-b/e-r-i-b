package com.rssl.phizicgate.esberibgate.documents.senders.BlockingCardClaim;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.cms.claims.CardBlockingClaim;
import com.rssl.phizic.gate.config.ESBEribConfig;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.utils.EntityCompositeId;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizicgate.esberibgate.messaging.BackRefInfoRequestHelper;
import com.rssl.phizicgate.esberibgate.types.BlockReasonWrapper;
import com.rssl.phizicgate.esberibgate.utils.EntityIdHelper;
import com.rssl.phizicgate.esberibgate.ws.jms.ESBSegment;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.OnlineMessageProcessor;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.Request;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.OnlineMessageProcessorBase;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.Response;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.ExternalIdGenerator;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.RequestHelper;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.*;

/**
 * @author akrenev
 * @ created 19.05.2015
 * @ $Author$
 * @ $Revision$
 *
 * Билдер запроса на блокировку
 */

class BlockingCardClaimProcessor extends OnlineMessageProcessorBase<CardBlockRs>
{
	private static final String OK_CODE = "0";

	private static final String REQUEST_TYPE = CardBlockRq.class.getSimpleName();

	private final BackRefInfoRequestHelper requestHelper;

	private final CardBlockingClaim document;
	private CardBlockRq request;

	/**
	 * конструктор
	 * @param factory гейтовая фабрика
	 * @param document документ
	 * @param serviceName имя сервиса
	 */
	BlockingCardClaimProcessor(GateFactory factory, CardBlockingClaim document, String serviceName)
	{
		super(ESBSegment.federal, serviceName);
		requestHelper = new BackRefInfoRequestHelper(factory);
		this.document = document;
	}

	@Override
	protected Object initialize() throws GateException, GateLogicException
	{
		request = buildRequestObject(document);

		document.setExternalId(ExternalIdGenerator.generateExternalId(request));

		return request;
	}

	@Override
	protected String getRequestId()
	{
		return request.getRqUID();
	}

	@Override
	protected String getRequestSystemId()
	{
		return request.getCardAcctId().getSystemId();
	}

	public String getRequestMessageType()
	{
		return REQUEST_TYPE;
	}

	@Override
	protected String getMonitoringDocumentType()
	{
		return Request.SKIP_MONITORING;
	}

	@Override
	protected String getResponseId(CardBlockRs response)
	{
		return response.getRqUID();
	}

	@Override
	protected String getResponseErrorCode(CardBlockRs response)
	{
		return String.valueOf(response.getStatus().getStatusCode());
	}

	@Override
	protected String getResponseErrorMessage(CardBlockRs response)
	{
		return response.getStatus().getStatusDesc();
	}

	@Override
	protected void processResponse(Request<OnlineMessageProcessor<CardBlockRs>> request, Response<CardBlockRs> response) throws GateException, GateLogicException
	{
		if (!OK_CODE.equals(response.getErrorCode()))
			processError(request, response);
	}

	private CardBlockRq buildRequestObject(CardBlockingClaim document) throws GateException, GateLogicException
	{
		ExternalSystemHelper.check(ConfigFactory.getConfig(ESBEribConfig.class).getEsbERIBCardSystemId99Way());

		CardBlockRq cardBlockRq = new CardBlockRq();

		cardBlockRq.setRqUID(RequestHelper.generateUUID());
		cardBlockRq.setRqTm(RequestHelper.generateRqTm());

		cardBlockRq.setOperUID(RequestHelper.generateOUUID());
		cardBlockRq.setSPName(SPNameType.BP_ERIB);
		cardBlockRq.setBankInfo(RequestHelper.makeBankInfo(requestHelper.getRbTbBrch(document.getInternalOwnerId())));

		CardAcctIdType cardAcctId = new CardAcctIdType();
		cardAcctId.setCardNum(document.getCardNumber());

		EntityCompositeId cardCompositeId = EntityIdHelper.getCardOrAccountCompositeId(document.getCardExternalId());

		cardAcctId.setSystemId(cardCompositeId.getSystemId());
		BankInfoType bankInfo = new BankInfoType();
		bankInfo.setRbBrchId(cardCompositeId.getRbBrchId());
		cardAcctId.setBankInfo(bankInfo);
		cardBlockRq.setCardAcctId(cardAcctId);

		cardBlockRq.setBlockReason(BlockReasonType.valueOf(BlockReasonWrapper.getBlockReason(document.getBlockingReason())));

		return cardBlockRq;
	}
}
