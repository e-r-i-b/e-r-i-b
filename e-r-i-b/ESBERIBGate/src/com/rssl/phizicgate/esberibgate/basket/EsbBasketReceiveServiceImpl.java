package com.rssl.phizicgate.esberibgate.basket;

import com.rssl.phizgate.basket.TicketStatusCode;
import com.rssl.phizic.common.types.basket.InvoiceState;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.basket.BasketRouteService;
import com.rssl.phizic.gate.basket.InvoiceService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizicgate.esberibgate.ws.jms.ESBSegment;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.*;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.AcceptBillBasketExecuteRs;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.AddBillBasketInfoRq;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.AddBillBasketInfoRs;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.ExecStatusType;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.PaymentInfoType;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.StatusType;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * @author niculichev
 * @ created 04.06.15
 * @ $Author$
 * @ $Revision$
 */
public class EsbBasketReceiveServiceImpl extends AbstractService implements BasketRouteService
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);

	/**
	 * —ервис обработки запросов от BasketProxy
	 * @param factory фабриа гейта
	 */
	public EsbBasketReceiveServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public void acceptBillBasketExecute(String message) throws GateException
	{
		InvoiceService service = GateSingleton.getFactory().service(InvoiceService.class);

		try
		{
			AcceptBillBasketExecuteRs acceptBillBasketExecuteRs = ESBSegment.federal.getMessageParser().parseMessage(message);

			StatusType status = acceptBillBasketExecuteRs.getStatus();
			service.updatePaymentStatus(acceptBillBasketExecuteRs.getRqUID(), status.getStatusCode(), status.getStatusDesc());
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}

	public void addBillBasketInfo(String message, String messageId) throws GateException
	{
		AddBillBasketInfoRq request = ESBSegment.federal.getMessageParser().parseMessage(message);

		try
		{
			List<PaymentListType> payments = request.getPayments();
			AddBillBasketInfoRs responce = BasketRequestHelper.createAddBillBasketInfoRs(request);

			if(CollectionUtils.isEmpty(payments))
			{
				BasketRequestHelper.sendResponce(responce, request.getRqUID(), messageId);
				return;
			}

			boolean isFirstNewInvoice = true;
			for(PaymentListType payment : payments)
			{
				PaymentInfoType paymentInfo = payment.getPaymentInfo();
				switch (paymentInfo.getPaymentStatus())
				{
					case NEW:
					{
						processInvoices(request, responce, payment, isFirstNewInvoice);
						isFirstNewInvoice = false;
						break;
					}
					case CANCELED:
					case DONE:
					{
						processDocuments(request, responce, payment);
						break;
					}
					case IN_EXEC:
					case REG_DEBT:
					case WAIT_APPROVE:
					{
						// данные статусы означают лишь, что платеж пока обрабатываетс€
						break;
					}
				}
			}

			// посылаем тикет
			responce.setStatus(BasketRequestHelper.createStatusType(TicketStatusCode.OK, null));
			BasketRequestHelper.sendResponce(responce, request.getRqUID(), messageId);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			//отправл€ем квитанцию с кодом -100
			AddBillBasketInfoRs rs = BasketRequestHelper.createAddBillBasketInfoRs(request);
			rs.setStatus(BasketRequestHelper.createStatusType(TicketStatusCode.ERROR, e.getMessage()));

			BasketRequestHelper.sendResponce(rs, request.getRqUID(), messageId);
		}
	}

	private void processInvoices(AddBillBasketInfoRq rq, AddBillBasketInfoRs rs, PaymentListType paymentType, boolean isUpdate)
	{
		InvoiceService service = GateSingleton.getFactory().service(InvoiceService.class);
		try
		{
			String autoSubId = Long.toString(rq.getAutoSubscriptionID().getAutopayId());
			if(isUpdate)
			{
				service.updateInvoice(BasketRequestHelper.convertInvoice(paymentType, autoSubId), rq.getOperUID());
			}
			else
			{
				service.addInvoice(BasketRequestHelper.convertInvoice(paymentType, autoSubId), rq.getOperUID());
			}

			rs.getPayments().add(BasketRequestHelper.createPaymentStatusInfo(paymentType, TicketStatusCode.OK, null));
		}
		catch (GateException e)
		{
			log.error(e.getMessage(), e);
			//обща€ ошибка обработки отправл€ем квитанцию с кодом -100
			rs.getPayments().add(BasketRequestHelper.createPaymentStatusInfo(paymentType, TicketStatusCode.ERROR, e.getMessage()));
		}
	}

	private void processDocuments(AddBillBasketInfoRq rq, AddBillBasketInfoRs rs, PaymentListType paymentType)
	{
		try
		{
			InvoiceService service = GateSingleton.getFactory().service(InvoiceService.class);

			InvoiceState state = InvoiceState.valueOf(paymentType.getPaymentInfo().getPaymentStatus().value().toUpperCase());
			String externalId = paymentType.getAutoPaymentId().getPaymentId();

			ExecStatusType execStatusType = paymentType.getPaymentInfo().getExecStatus();
			String nonExecReasonDesc = execStatusType.getNonExecReasonDesc();
			String nonExecReasonCode = execStatusType.getNonExecReasonCode();
			Calendar execPaymentDate = XMLDatatypeHelper.parseDateTime(execStatusType.getExecPaymentDate());

			service.updatePaymentState(externalId, state, nonExecReasonCode, nonExecReasonDesc, execPaymentDate, rq.getOperUID());
		}
		catch (GateLogicException e)
		{
			log.error(e.getMessage(), e);
			//не найден платеж дл€ обновлени€ -122
			rs.getPayments().add(BasketRequestHelper.createPaymentStatusInfo(paymentType, TicketStatusCode.PAYMENT_NOT_FOUND, e.getMessage()));
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			//обща€ ошибка обработки отправл€ем квитанцию с кодом -100
			rs.getPayments().add(BasketRequestHelper.createPaymentStatusInfo(paymentType, TicketStatusCode.ERROR, e.getMessage()));
		}
	}
}
