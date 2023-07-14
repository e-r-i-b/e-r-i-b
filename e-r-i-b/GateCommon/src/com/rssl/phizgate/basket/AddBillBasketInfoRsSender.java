package com.rssl.phizgate.basket;

import com.rssl.phizgate.basket.generated.AddBillBasketInfoRs;
import com.rssl.phizic.BasketPaymentsListenerConfig;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.jms.JMSQueueSender;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.jms.JmsService;
import com.rssl.phizicgate.manager.routing.Adapter;
import com.rssl.phizicgate.manager.routing.AdapterService;

/**
 * @author vagin
 * @ created 29.04.14
 * @ $Author$
 * @ $Revision$
 * ������ ��������� � ������ ��������� AddBillBasketInfoRq
 */
public class AddBillBasketInfoRsSender
{
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);
	protected static final JmsService jmsService = new JmsService();

	/**
	 * �������� ���������.
	 * @param addBillBasketInfoRs - ���������.
	 * @param rqUID - RqUID ���������
	 * @param messageId - messageId ��������� ���������.
	 * @throws Exception
	 */
	public static void send(String addBillBasketInfoRs, String rqUID, String messageId) throws Exception
	{
		//���� ��������� ���������� - ������ ����������.
		if(!BasketProxyListenerActiveRestriction.check())
		{
			throw new GateLogicException("�������� �������� ����������. ��������� ������� �����.");
		}

		BasketProxyLoggerHelper.writeToLog(addBillBasketInfoRs, rqUID, AddBillBasketInfoRs.class.getSimpleName());
		jmsService.sendMessageWithUserHeadersToQueue(addBillBasketInfoRs, getJMSQueueName(), getJMSQueueFactoryName(), messageId, new Pair<String, String>("ExtSystemCode", "BP_ERIB_BASKET_INFO"));
	}

	private static String getJMSQueueFactoryName()
	{
		BasketPaymentsListenerConfig config = ConfigFactory.getConfig(BasketPaymentsListenerConfig.class);
		return config.getAddBillBasketInfoOutQueueFactory();
	}

	private static String getJMSQueueName()
	{
		BasketPaymentsListenerConfig config = ConfigFactory.getConfig(BasketPaymentsListenerConfig.class);
		return config.getAddBillBasketInfoOutQueue();
	}
}
