package com.rssl.phizic.web.basket;

import com.rssl.phizgate.basket.BasketProxyListenerActiveRestriction;
import com.rssl.phizgate.basket.generated.AcceptBillBasketExecuteRs;
import com.rssl.phizgate.common.payments.offline.OfflineDocumentInfo;
import com.rssl.phizgate.common.payments.offline.OfflineDocumentInfoService;
import com.rssl.phizic.BasketPaymentsListenerConfig;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.impl.TimeoutHttpTransport;
import com.rssl.phizic.utils.xml.jaxb.JAXBUtils;
import com.rssl.phizicgate.wsgateclient.services.basket.BasketRouteServiceWrapper;

import java.rmi.RemoteException;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

/**
 * @author vagin
 * @ created 16.05.14
 * @ $Author$
 * @ $Revision$
 * Поток обработки входящего JMS сообщения из очереди Q.RESPONSE корзины платежей.
 */
public class JMSAcceptBillBasketExecuteReceiverTread extends JMSAddBillBasketInfoReceiverTread
{
	private static final String USER_HEADER_VALUE = "BP_ERIB_BASKET_EXEC";

	public JMSAcceptBillBasketExecuteReceiverTread(long timeout, int batchSize, int flushTryCount, String queueName, String queueFactoryName)
	{
		super(timeout, batchSize, flushTryCount, queueName, queueFactoryName);
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
			Message msg = getReceiver().receive(getTimeOut());
			processAcceptTicket(msg);
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
			return;
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
		finally
		{
			ApplicationInfo.setDefaultApplication();
		}
	}

	private void processAcceptTicket(Message msg) throws Exception
	{
		BasketPaymentsListenerConfig config = ConfigFactory.getConfig(BasketPaymentsListenerConfig.class);

		if (msg != null)
		{
			if(!msg.getStringProperty(USER_HEADER_KEY).equals(USER_HEADER_VALUE))
			{
				log.error("Неверный пользовательский заголовок: "+msg.getStringProperty(USER_HEADER_KEY));
				return;
			}

			try
			{
				TextMessage textMessage = (TextMessage) msg;
				AcceptBillBasketExecuteRs request = JAXBUtils.unmarshalBean(AcceptBillBasketExecuteRs.class, textMessage.getText().trim());

				//пишем в лог как только удалось разобрать сообщение.
				writeToMessageLog(textMessage, request.getRqUID(), AcceptBillBasketExecuteRs.class.getSimpleName());
				//поиск номера блока в БД
				OfflineDocumentInfo offlineInfo = OfflineDocumentInfoService.getOfflineDocumentInfo(request.getRqUID());
				if(offlineInfo == null)
				{
					log.error("Не удалось найти запись в базе оффлайн документов. C RqUID = " + request.getRqUID());
					return;
				}

				BasketRouteServiceWrapper basketRouteService = new BasketRouteServiceWrapper(offlineInfo.getBlockNumber());
				basketRouteService.acceptBillBasketExecute(textMessage.getText());
			}
			catch (RemoteException e)
			{
				//таймаут не принципиален, у нас асинхронная обработка.
				if (e.getMessage().contains(TimeoutHttpTransport.SOCKET_TIMEOUT_EXCEPTION))
					return;
				log.error("Ошибка обработки сообщения.", e);
			}
			catch (Exception e)
			{
				log.error("Ошибка обработки сообщения.", e);
			}
			finally
			{
				setCurrentCommand(Command.CLOSE_SESSION);
				getSession().commit();
			}
		}
		else
		{
			Thread.sleep(config.getThreadSleepTime());
		}
	}
}
