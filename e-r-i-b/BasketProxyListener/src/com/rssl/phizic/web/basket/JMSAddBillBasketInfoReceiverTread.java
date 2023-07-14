package com.rssl.phizic.web.basket;

import com.rssl.phizgate.basket.BasketInvoiceHelper;
import com.rssl.phizgate.basket.BasketProxyListenerActiveRestriction;
import com.rssl.phizgate.basket.BasketProxyLoggerHelper;
import com.rssl.phizgate.basket.TicketStatusCode;
import com.rssl.phizgate.basket.generated.AddBillBasketInfoRq;
import com.rssl.phizgate.common.payments.offline.basket.BasketRouteInfo;
import com.rssl.phizgate.common.payments.offline.basket.BasketRouteInfoService;
import com.rssl.phizgate.common.payments.offline.processState.BasketInvoiceProcessState;
import com.rssl.phizic.BasketPaymentsListenerConfig;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.xml.jaxb.JAXBUtils;
import com.rssl.phizic.web.jms.JMSReceiverTreadBase;
import com.rssl.phizicgate.wsgateclient.services.basket.BasketRouteServiceWrapper;
import com.sun.xml.rpc.client.http.HttpClientTransport;
import com.sun.xml.rpc.soap.message.SOAPMessageContext;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.exception.LockAcquisitionException;

import java.io.IOException;
import java.net.HttpURLConnection;
import javax.jms.*;

/**
 * @author vagin
 * @ created 16.05.14
 * @ $Author$
 * @ $Revision$
 * Поток обработки входящего JMS сообщения из очереди добавления инвойсов(AddBillBasketInfoRq) в рамках корзины платежей.
 */
public class JMSAddBillBasketInfoReceiverTread extends JMSReceiverTreadBase
{
	private static final long IDLE_TIMEOUT = 1000;
	private static final String OFFLINE_DOC_DB_INSTANCE = "OfflineDoc";
	private static final String USER_HEADER_VALUE = "BP_ERIB_BASKET_INFO";
	protected static final String USER_HEADER_KEY = "ExtSystemCode";

	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);
	protected static final org.apache.commons.logging.Log apacheLog = LogFactory.getLog(JMSReceiverTreadBase.class);

	public JMSAddBillBasketInfoReceiverTread(long timeout, int batchSize, int flushTryCount, String queueName, String queueFactoryName)
	{
		super(timeout, batchSize, flushTryCount, queueName, queueFactoryName, null);
	}

	@Override
	protected boolean isIdle()
	{
		BasketPaymentsListenerConfig config = ConfigFactory.getConfig(BasketPaymentsListenerConfig.class, Application.BasketProxyListener);
		return BasketPaymentsListenerConfig.WorkingMode.autopay != config.getWorkingMode();
	}

	@Override
	protected void sleepIdle()
	{
		try
		{
			apacheLog.debug("Усыпаем на " + IDLE_TIMEOUT + "мс в режиме холостого хода");
			Thread.sleep(IDLE_TIMEOUT);
		}
		catch (InterruptedException ignored)
		{
		}
	}

	@Override
	protected void processMessage()
	{
		ApplicationInfo.setCurrentApplication(Application.BasketProxyListener);
		try
		{
			//если отключено настройкой - ничего не делаем.
			if(!BasketProxyListenerActiveRestriction.check())
			{
				BasketPaymentsListenerConfig config = ConfigFactory.getConfig(BasketPaymentsListenerConfig.class);
				Thread.sleep(config.getThreadSleepTime());
				return;
			}

			HibernateExecutor.getInstance(OFFLINE_DOC_DB_INSTANCE).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					//если какой то поток уже подхватил сообщение и обрабатывает его.
					if(isLock(session, new BasketInvoiceProcessState()))
						processInvoice(getMessage());
					else
					{
						BasketPaymentsListenerConfig config = ConfigFactory.getConfig(BasketPaymentsListenerConfig.class);
						Thread.sleep(config.getThreadSleepTime());
					}
					return null;
				}
			});
		}
		catch (Throwable e)
		{
			log.error(e.getMessage(), e);
		}
		finally
		{
			ApplicationInfo.setDefaultApplication();
		}

	}

	private void processInvoice(Message msg) throws Exception
	{
		BasketPaymentsListenerConfig config = ConfigFactory.getConfig(BasketPaymentsListenerConfig.class);
		if(msg == null)
		{
			Thread.sleep(config.getThreadSleepTime());
			return;
		}

		TextMessage textMessage = (TextMessage) msg;
		AddBillBasketInfoRq request = null;
		try
		{
			//определяем блок в который необходимо перенаправить запрос.
			request = JAXBUtils.unmarshalBean(AddBillBasketInfoRq.class, textMessage.getText().trim());
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			return;
		}
		//пишем в лог как только удалось разобрать сообщение.
		writeToMessageLog(textMessage, request.getRqUID(), AddBillBasketInfoRq.class.getSimpleName());

		String messageId = textMessage.getJMSMessageID();
		if(!msg.getStringProperty(USER_HEADER_KEY).equals(USER_HEADER_VALUE))
		{
			log.error("Неверный пользовательский заголовок: " + msg.getStringProperty(USER_HEADER_KEY));
			BasketInvoiceHelper.sendTicketResponse(request.getRqUID(), request.getOperUID(), TicketStatusCode.ERROR, "Неверный пользовательский заголовок.", messageId);
			return;
		}

		try
		{
			//поиск номера блока в БД
			BasketRouteInfo info = BasketRouteInfoService.getInfo(request.getOperUID());
			if (info == null)
			{
				BasketInvoiceHelper.sendTicketResponse(request.getRqUID(), request.getOperUID(), TicketStatusCode.ERROR, "Не удалось найти запись в базе оффлайн документов.", messageId);
				return;
			}

			BasketRouteServiceWrapper basketRouteService = new BasketRouteServiceWrapper(info.getBlockNumber());
			basketRouteService.addBillBasketInfo(textMessage.getText(), messageId);
		}
		catch (Exception e)
		{
			BasketInvoiceHelper.sendTicketResponse(request.getRqUID(), request.getOperUID(), TicketStatusCode.ERROR, "Ошибка обработки сообщения.", messageId);
			throw e;
		}
		finally
		{
			getSession().commit();
			setCurrentCommand(Command.CLOSE_SESSION);
		}

	}

	/**
	 * Проверка блокировки потоком
	 * @param session hibernate сессия
	 * @param object запись о процессинге.
	 * @return true - блокировка наложена текущим потоком
	 */
	private boolean isLock(org.hibernate.Session session, Object object)
	{
		try
		{
			session.lock(object, LockMode.UPGRADE_NOWAIT);
			return true;
		}
		catch (LockAcquisitionException ignore)
		{
			//каким-либо потоком уже установлена блокировка
			return false;
		}
	}

	private Message getMessage()
	{
		Message msg;
		try
		{
			msg = getReceiver().receive(getTimeOut());
		}
		catch (JMSException e)
		{
			log.error("Ошибка получения сообщения.", e);
			try
			{
				getSession().rollback();
			}
			catch (JMSException e1)
			{
				log.error("Ошибка получения сообщения", e1);
			}
			finally
			{
				setCurrentCommand(Command.CLOSE_SESSION);
			}
			return null;
		}
		return msg;
	}

	protected void writeToMessageLog(TextMessage textMessage, String rqUID, String messageType) throws JMSException
	{
		BasketProxyLoggerHelper.writeToLog(textMessage.getText(), rqUID, messageType);
	}

	private class TimeoutHttpTransport extends HttpClientTransport
	{
		private int timeout;

		TimeoutHttpTransport(int timeout)
		{
			this.timeout = timeout;
		}

		@Override
		protected HttpURLConnection createHttpConnection(String endpoint, SOAPMessageContext context) throws IOException
		{
			HttpURLConnection httpConn = super.createHttpConnection(endpoint, context);
			if (timeout > 0)
			{
				httpConn.setReadTimeout(timeout);
				httpConn.setConnectTimeout(timeout);
			}
			return httpConn;
		}
	}
}
