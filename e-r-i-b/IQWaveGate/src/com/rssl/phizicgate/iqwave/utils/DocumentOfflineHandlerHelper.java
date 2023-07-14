package com.rssl.phizicgate.iqwave.utils;

import com.rssl.phizicgate.iqwave.listener.*;
import com.rssl.phizicgate.iqwave.messaging.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gladishev
 * @ created 22.10.2013
 * @ $Author$
 * @ $Revision$
 */
public class DocumentOfflineHandlerHelper
{
	private static final Map<String, ExecutionResultProcessorBase> requestHandlers = new HashMap<String, ExecutionResultProcessorBase>();

	static
	{
		requestHandlers.put(Constants.SIMPLE_PAYMENT_RESPONSE, new WriteMonitoringLogResultProcessor());
		requestHandlers.put(Constants.SIMPLE_PAYMENT_ECOMMERCE_RESPONSE, new PaymentExecutionResultProcessor());
		requestHandlers.put(Constants.PAYMENT_GKH_RESPONSE, new PaymentExecutionResultProcessor());
		requestHandlers.put(Constants.PAYMENT_MOSENERGO_RESPONSE, new PaymentExecutionResultProcessor());
		requestHandlers.put(Constants.PAYMENT_FNS_RESPONSE, new PaymentExecutionResultProcessor());
		requestHandlers.put(Constants.PAYMENT_MGTS_RESPONSE, new PaymentExecutionResultProcessor());
		requestHandlers.put(Constants.PAYMENT_ROSTELECOM_RESPONSE, new PaymentExecutionResultProcessor());
		requestHandlers.put(Constants.FNS_FREE_PAYMENT_RESPONSE, new WriteMonitoringLogResultProcessor());
		// Обработчики сообщений для регистрации, редактирования и отмены автоплатежа
		requestHandlers.put(Constants.AUTO_PAY_REGISTER_RESPONSE, new AutoPaymentExecutionResultProcessor());
		requestHandlers.put(Constants.AUTO_PAY_CORRECTION_RESPONSE, new AutoPaymentExecutionResultProcessor());
		requestHandlers.put(Constants.AUTO_PAY_CANCEL_RESPONSE, new AutoPaymentExecutionResultProcessor());
		// ответ об исполнении платежа по оплате брони аэрофлота
		requestHandlers.put(Constants.PAYMENT_RES_RESPONSE, new AeroflotBookingExecutionResultProcessor());
		//отмена оплаты
		requestHandlers.put(Constants.REVERSAL_SIMPLE_PAYMENT_RESPONSE, new WithdrawExecutionResultProcessor());
		//возврат товара
		requestHandlers.put(Constants.REFUND_SIMPLE_PAYMENT_RESPONSE, new WithdrawExecutionResultProcessor());
	}

	/**
	 * Возвращает обработчик запроса на исполнение документа по типу сообщения
	 * @param requestType - тип сообщения
	 * @return обработчик запроса
	 */
	public static ExecutionResultProcessorBase getExecutionOfflineRequestHandler(String requestType)
	{
		return requestHandlers.get(requestType);
	}
}
