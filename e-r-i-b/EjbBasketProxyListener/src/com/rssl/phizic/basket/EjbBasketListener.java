package com.rssl.phizic.basket;

import com.rssl.phizgate.basket.BasketInvoiceHelper;
import com.rssl.phizgate.basket.BasketProxyListenerActiveRestriction;
import com.rssl.phizgate.basket.BasketProxyLoggerHelper;
import com.rssl.phizgate.basket.TicketStatusCode;
import com.rssl.phizgate.common.payments.offline.basket.BasketRouteInfo;
import com.rssl.phizgate.common.payments.offline.basket.BasketRouteInfoService;
import com.rssl.phizic.BasketPaymentsListenerConfig;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizicgate.esberibgate.ws.jms.ESBSegment;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.AddBillBasketInfoRq;
import com.rssl.phizicgate.wsgateclient.services.basket.BasketRouteServiceWrapper;

import javax.ejb.EJBException;
import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Ћистенер дл€ обработки сообщений очереди шины, касающихс€ корзины
 * @author niculichev
 * @ created 27.05.15
 * @ $Author$
 * @ $Revision$
 */
public class EjbBasketListener implements MessageDrivenBean, MessageListener
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);

	public void setMessageDrivenContext(MessageDrivenContext messageDrivenContext) throws EJBException
	{
	}

	public void ejbRemove() throws EJBException
	{
	}

	public void onMessage(Message message)
	{
		ApplicationInfo.setCurrentApplication(Application.BasketProxyListener);
		BasketPaymentsListenerConfig config = ConfigFactory.getConfig(BasketPaymentsListenerConfig.class);

		// включе режим получени€ сообщений напр€мую из автопэ€
	/*	if(BasketPaymentsListenerConfig.WorkingMode.esb != config.getWorkingMode())
			return;*/

		// выключен глобальный рубильник получени€ сообщений корзины
		if(!BasketProxyListenerActiveRestriction.check())
			return;

		TextMessage textMessage = (TextMessage) message;
		AddBillBasketInfoRq request = parseMessage(textMessage);
		if(request == null)
		    return;

		try
		{
			//получеение информации о маршрутизации
			BasketRouteInfo info = BasketRouteInfoService.getInfo(request.getOperUID());
			if(info == null)
			{
				sendErrorTicketResponse(request, textMessage);
				return;
			}

			BasketRouteServiceWrapper basketRouteService = new BasketRouteServiceWrapper(info.getBlockNumber());
			basketRouteService.addBillBasketInfo(textMessage.getText(), textMessage.getJMSMessageID());
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			sendErrorTicketResponse(request, textMessage);
			return;
		}
	}

	private AddBillBasketInfoRq parseMessage(TextMessage message)
	{
		try
		{
			AddBillBasketInfoRq request = ESBSegment.federal.getMessageParser().parseMessage(message);
			BasketProxyLoggerHelper.writeToLog(
					message.getText(), request.getRqUID(), AddBillBasketInfoRq.class.getSimpleName());
			return request;
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			return null;
		}
	}

	private void sendErrorTicketResponse(AddBillBasketInfoRq request, TextMessage message)
	{
		try
		{
			BasketInvoiceHelper.sendTicketResponse(
					request.getRqUID(), request.getOperUID(), TicketStatusCode.ERROR, "ќшибка обработки сообщени€.", message.getJMSMessageID());
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}
}
