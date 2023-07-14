package com.rssl.phizicgate.esberibgate.documents.senders.InvoiceAcceptPayment;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.basket.InvoiceService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.basket.InvoiceAcceptPayment;
import com.rssl.phizicgate.esberibgate.basket.BasketRequestHelper;
import com.rssl.phizicgate.esberibgate.ws.jms.ESBSegment;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.OnlineMessageProcessor;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.OnlineMessageProcessorBase;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.Request;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.Response;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.AcceptBillBasketExecuteRq;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.AcceptBillBasketExecuteRs;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.StatusType;

/**
 * @author niculichev
 * @ created 01.06.15
 * @ $Author$
 * @ $Revision$
 */
class InvoiceAcceptPaymentProcessor extends OnlineMessageProcessorBase<AcceptBillBasketExecuteRs>
{
	private static final String REQUEST_NAME = "AcceptBillBasketExecuteRq";
	private BasketRequestHelper requestHelper;

	private final InvoiceAcceptPayment requestDataSource;
	private AcceptBillBasketExecuteRq request;

	/**
	 * конструктор
	 * @param factory гейтовая фабрика
	 * @param requestDataSource документ
	 * @param serviceName имя сервиса
	 */
	InvoiceAcceptPaymentProcessor(GateFactory factory, InvoiceAcceptPayment requestDataSource, String serviceName)
	{
		super(ESBSegment.federal, serviceName);
		this.requestHelper = new BasketRequestHelper(factory);
		this.requestDataSource = requestDataSource;
	}

	@Override
	protected String getRequestId()
	{
		return request.getRqUID();
	}

	@Override
	protected String getRequestSystemId()
	{
		return request.getSystemId();
	}

	@Override
	protected String getRequestMessageType()
	{
		return REQUEST_NAME;
	}

	@Override
	protected String getMonitoringDocumentType()
	{
		return Request.SKIP_MONITORING;
	}

	@Override
	protected Object initialize() throws GateException, GateLogicException
	{
		this.request = requestHelper.createESBAcceptRequest(requestDataSource);
		requestDataSource.setExternalId(request.getRqUID());
		return request;
	}

	@Override
	protected String getResponseId(AcceptBillBasketExecuteRs response)
	{
		return response.getRqUID();
	}

	@Override
	protected String getResponseErrorCode(AcceptBillBasketExecuteRs response)
	{
		return String.valueOf(response.getStatus().getStatusCode());
	}

	@Override
	protected String getResponseErrorMessage(AcceptBillBasketExecuteRs response)
	{
		return response.getStatus().getStatusDesc();
	}

	@Override
	protected void processResponse(Request<OnlineMessageProcessor<AcceptBillBasketExecuteRs>> request, Response<AcceptBillBasketExecuteRs> response) throws GateException, GateLogicException
	{
		InvoiceService service = GateSingleton.getFactory().service(InvoiceService.class);

		try
		{
			AcceptBillBasketExecuteRs acceptBillBasketExecuteRs = response.getResponse();

			StatusType status = acceptBillBasketExecuteRs.getStatus();
			service.updatePaymentStatus(acceptBillBasketExecuteRs.getRqUID(), status.getStatusCode(), status.getStatusDesc());
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}
}
