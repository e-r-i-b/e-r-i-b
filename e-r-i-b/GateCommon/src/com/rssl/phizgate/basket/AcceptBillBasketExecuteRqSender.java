package com.rssl.phizgate.basket;

import com.rssl.phizgate.basket.generated.AcceptBillBasketExecuteRq;
import com.rssl.phizic.BasketPaymentsListenerConfig;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.jms.JmsService;

import javax.jms.JMSException;

/**
 * @author vagin
 * @ created 29.04.14
 * @ $Author$
 * @ $Revision$
 * ������ ������� ������ ������������ �� ������������� �����.
 */
public class AcceptBillBasketExecuteRqSender
{
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);
	protected static final JmsService jmsService = new JmsService();

	/**
	 * �������� ���������.
	 * @param acceptBillBasketExecuteRequest - ������ �� ������ ������ ������������.
	 * @param rqUID - ���������� ������������� �������.
	 * @throws JMSException
	 */
	public static void send(String acceptBillBasketExecuteRequest, String rqUID) throws Exception
	{
		//���� ��������� ���������� - ������ ����������.
		if(!BasketProxyListenerActiveRestriction.check())
		{
			throw new GateLogicException("�������� �������� ����������. ��������� ������� �����.");
		}
		BasketProxyLoggerHelper.writeToLog(acceptBillBasketExecuteRequest, rqUID, AcceptBillBasketExecuteRq.class.getSimpleName());
		jmsService.sendMessageWithUserHeadersToQueue(acceptBillBasketExecuteRequest, getJMSQueueName(), getJMSQueueFactoryName(), null, null, new Pair<String,String>("ExtSystemCode","BP_ERIB_BASKET_EXEC"));
	}

	private static String getJMSQueueFactoryName()
	{
		BasketPaymentsListenerConfig config = ConfigFactory.getConfig(BasketPaymentsListenerConfig.class);
		return config.getAcceptBillBasketExecuteOutQueueFactory();
	}

	private static String getJMSQueueName()
	{
		BasketPaymentsListenerConfig config = ConfigFactory.getConfig(BasketPaymentsListenerConfig.class);
		return config.getAcceptBillBasketExecuteOutQueue();
	}
}
