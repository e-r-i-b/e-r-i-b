package com.rssl.phizicgate.esberibgate.documents.senders.LongOffer;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.RefuseLongOffer;
import com.rssl.phizicgate.esberibgate.documents.PaymentsRequestHelper;
import com.rssl.phizicgate.esberibgate.messaging.BackRefInfoRequestHelper;
import com.rssl.phizicgate.esberibgate.utils.EntityIdHelper;
import com.rssl.phizicgate.esberibgate.utils.LongOfferCompositeId;
import com.rssl.phizicgate.esberibgate.ws.jms.ESBSegment;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.OnlineMessageProcessor;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.Request;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.OnlineMessageProcessorBase;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.Response;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.ExternalIdGenerator;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.RequestHelper;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.SPNameType;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.SvcAcctDelRq;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.SvcAcctDelRs;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.SvcAcctId;

/**
 * @author akrenev
 * @ created 05.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * Ѕилдер запроса на отмену выполнени€ регул€рного платежа
 */

class RefuseLongOfferProcessor extends OnlineMessageProcessorBase<SvcAcctDelRs>
{
	private static final String OK_CODE = "0";

	private static final String REQUEST_TYPE = SvcAcctDelRq.class.getSimpleName();

	private final BackRefInfoRequestHelper requestHelper;

	private final RefuseLongOffer document;
	private SvcAcctDelRq request;

	/**
	 * конструктор
	 * @param factory гейтова€ фабрика
	 * @param serviceName им€ сервиса
	 */
	RefuseLongOfferProcessor(GateFactory factory, RefuseLongOffer document, String serviceName)
	{
		super(ESBSegment.federal, serviceName);
		requestHelper = new BackRefInfoRequestHelper(factory);
		this.document = document;
	}

	@Override
	protected String getRequestId()
	{
		return request.getRqUID();
	}

	@Override
	protected String getRequestSystemId()
	{
		return request.getSvcAcct().getSvcAcctId().getSystemId();
	}

	@Override
	protected String getRequestMessageType()
	{
		return REQUEST_TYPE;
	}

	@Override
	protected String getMonitoringDocumentType()
	{
		return Request.SKIP_MONITORING;
	}

	@Override
	protected Object initialize() throws GateException, GateLogicException
	{
		request = buildRequestObject(document);

		document.setExternalId(ExternalIdGenerator.generateExternalId(request));

		return request;
	}

	@Override
	protected String getResponseId(SvcAcctDelRs response)
	{
		return response.getRqUID();
	}

	@Override
	protected String getResponseErrorCode(SvcAcctDelRs response)
	{
		return String.valueOf(response.getStatus().getStatusCode());
	}

	@Override
	protected String getResponseErrorMessage(SvcAcctDelRs response)
	{
		return response.getStatus().getStatusDesc();
	}

	@Override
	protected void processResponse(Request<OnlineMessageProcessor<SvcAcctDelRs>> request, Response<SvcAcctDelRs> response) throws GateException, GateLogicException
	{
		if (!OK_CODE.equals(response.getErrorCode()))
			processError(request, response);
	}

	private SvcAcctDelRq buildRequestObject(RefuseLongOffer document) throws GateException, GateLogicException
	{
		LongOfferCompositeId compositId = EntityIdHelper.getLongOfferCompositeId(document.getLongOfferExternalId());

		SvcAcctDelRq svcAcctDelRq = new SvcAcctDelRq();
		svcAcctDelRq.setRqUID(PaymentsRequestHelper.generateUUID());
		svcAcctDelRq.setRqTm(PaymentsRequestHelper.generateRqTm());
		svcAcctDelRq.setOperUID(PaymentsRequestHelper.generateOUUID());
		svcAcctDelRq.setSPName(SPNameType.BP_ERIB);
		svcAcctDelRq.setSvcAcct(getSvcAcct(compositId));
		svcAcctDelRq.setBankInfo(RequestHelper.makeBankInfo(requestHelper.getRbTbBrch(compositId.getLoginId())));
		return svcAcctDelRq;
	}

	private SvcAcctDelRq.SvcAcct getSvcAcct(LongOfferCompositeId compositId) throws GateException
	{
		SvcAcctDelRq.SvcAcct svcAcct = new SvcAcctDelRq.SvcAcct();
		SvcAcctId svcAcctId = new SvcAcctId();
		svcAcctId.setSystemId(compositId.getSystemIdActiveSystem());
		svcAcctId.setSvcAcctNum(Long.parseLong(compositId.getEntityId()));
		svcAcctId.setSvcType(compositId.getSvcType());
		svcAcctId.setBankInfo(RequestHelper.getBankInfo(compositId));
		svcAcct.setSvcAcctId(svcAcctId);
		return svcAcct;
	}
}
