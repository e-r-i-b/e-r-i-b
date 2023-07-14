package com.rssl.phizic;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author vagin
 * @ created 05.05.14
 * @ $Author$
 * @ $Revision$
 * Конфиг работы с приемом сообщений из АС "AutoPay" в рамках корзины платежей.
 */
public class BasketPaymentsListenerConfig extends Config
{
	private static final String SERVICE_REDIRECT_URL = "com.rssl.phizic.service.basket.redirect.url.";
	private static final String Q_OUT_QUEUE = "com.rssl.phizic.service.basket.add.info.out.queue";
	private static final String Q_OUT_QUEUE_FACTORY = "com.rssl.phizic.service.basket.add.info.out.queue.factory";
	private static final String Q_REQUEST_QUEUE = "com.rssl.phizic.service.basket.accept.execute.out.queue";
	private static final String Q_REQUEST_QUEUE_FACTORY = "com.rssl.phizic.service.basket.accept.execute.out.queue.factory";
	private static final String THREAD_SLEEP_TIME = "com.rssl.phizic.service.basket.add.info.thread.sleep.time";
	private static final String BASKET_SERVICE_ADD_INFO_TIME_OUT = "com.rssl.phizic.service.basket.add.info.service.timeout";
	private static final String BASKET_SERVICE_ACCEPT_TICKET_TIME_OUT = "com.rssl.phizic.service.basket.accept.ticket.service.timeout";
	private static final String Q_IN_QUEUE = "com.rssl.phizic.service.basket.add.info.in.queue";
	private static final String Q_IN_QUEUE_FACTORY = "com.rssl.phizic.service.basket.add.info.in.queue.factory";
	private static final String Q_RESPONSE_QUEUE = "com.rssl.phizic.service.basket.accept.execute.in.queue";
	private static final String Q_RESPONSE_QUEUE_FACTORY = "com.rssl.phizic.service.basket.accept.execute.in.queue.factory";
	private static final String QUEUE_WORKING_MODE = "com.rssl.phizic.service.basket.queue.working.mode";

	private static final String ESB_ERIB_MQ_RESPONCE_QUERY = "com.rssl.phizic.service.basket.esb.erib.mq.responce.query";
	private static final String ESB_ERIB_MQ_RESPONCE_FACTORY = "com.rssl.phizic.service.basket.esb.erib.mq.responce.factory";

	private WorkingMode workingMode;
	private String jmsESBResponceQuery;
	private String jmsESBResponceFactory;

	public BasketPaymentsListenerConfig(PropertyReader reader)
	{
		super(reader);
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
		String workingModeStr = getProperty(QUEUE_WORKING_MODE);
		if(StringHelper.isNotEmpty(workingModeStr))
			workingMode = WorkingMode.valueOf(workingModeStr);

		jmsESBResponceQuery = getProperty(ESB_ERIB_MQ_RESPONCE_QUERY);
		jmsESBResponceFactory = getProperty(ESB_ERIB_MQ_RESPONCE_FACTORY);
	}

	public String getServiceRedirectUrl(Long nodeId)
	{
		return getProperty(SERVICE_REDIRECT_URL + nodeId);
	}
	//исходящие
	public String getAcceptBillBasketExecuteOutQueue()
	{
		return getProperty(Q_REQUEST_QUEUE);
	}

	public String getAcceptBillBasketExecuteOutQueueFactory()
	{
		return getProperty(Q_REQUEST_QUEUE_FACTORY);
	}

	public String getAddBillBasketInfoOutQueue()
	{
		return getProperty(Q_OUT_QUEUE);
	}

	public String getAddBillBasketInfoOutQueueFactory()
	{
		return getProperty(Q_OUT_QUEUE_FACTORY);
	}

	//входящие
	public String getAcceptBillBasketExecuteInQueue()
	{
		return getProperty(Q_RESPONSE_QUEUE);
	}

	public String getAcceptBillBasketExecuteInQueueFactory()
	{
		return getProperty(Q_RESPONSE_QUEUE_FACTORY);
	}

	public String getAddBillBasketInfoInQueue()
	{
		return getProperty(Q_IN_QUEUE);
	}

	public String getAddBillBasketInfoInQueueFactory()
	{
		return getProperty(Q_IN_QUEUE_FACTORY);
	}

	public int getBasketServiceAddInfoTimeOut()
	{
		return getIntProperty(BASKET_SERVICE_ADD_INFO_TIME_OUT);
	}

	public int getBasketServiceAcceptTicketTimeOut()
	{
		return getIntProperty(BASKET_SERVICE_ACCEPT_TICKET_TIME_OUT);
	}

	public int getThreadSleepTime()
	{
		return getIntProperty(THREAD_SLEEP_TIME);
	}

	public WorkingMode getWorkingMode()
	{
		return workingMode;
	}

	/**
	 * @return очедедь для тикетов в шину при обратном взаимодействии(от шины к ериб)
	 */
	public String getJMSESBResponceQuery()
	{
		return jmsESBResponceQuery;
	}

	/**
	 * @return фабрика соединений с очередью обратных сообщений(от ериб к шине)
	 */
	public String getJMSESBResponceFactory()
	{
		return jmsESBResponceFactory;
	}

	/**
	 * режим работы корзины
	 */
	public static enum WorkingMode
	{
		/**
		 * Работа через очереди автопэя
		 */
		autopay,

		/**
		 * Работа через шину
		 */
		esb

	}
}
