package com.rssl.phizic.mbk;

import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.mobilebank.MobileBankService;
import com.rssl.phizic.gate.mobilebank.P2PRequest;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.messaging.ermb.AuxMessageSerializer;
import com.rssl.phizic.utils.jms.JmsService;
import org.apache.commons.collections.CollectionUtils;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.StatefulJob;

import java.util.List;

/**
 * @author Gulov
 * @ created 17.09.13
 * @ $Author$
 * @ $Revision$
 */

/**
 * Маршрутизирует МБК-запросы "дай карту и ФИО по номеру ЕРМБ-телефона" в блоки ЕРИБ.
 */
public class P2PProxyJob implements StatefulJob, InterruptableJob
{
	private final Log log = PhizICLogFactory.getLog(LogModule.Scheduler);

	private final AuxMessageSerializer messageSerializer = new AuxMessageSerializer();

	private final JmsService jmsService = new JmsService();

	private volatile boolean isInterrupt = false;

	///////////////////////////////////////////////////////////////////////////

	public void execute(JobExecutionContext context)
	{
		ApplicationInfo.setCurrentApplication(Application.MBK_P2P_Proxy);
		try
		{
			List<P2PRequest> requests = getRequests();
			if (CollectionUtils.isEmpty(requests))
				return;

			for(P2PRequest request : requests)
			{
				if (isInterrupt)
					break;

				P2PConfig config = ConfigFactory.getConfig(P2PConfig.class);

				String requestXML = messageSerializer.marshallRequest(request);

				jmsService.sendMessageToQueue(requestXML, config.getProxyQueueName(), config.getProxyQCFName(), null, null);

				log.info("P2P-запрос МБК " + requestXML + " был отправлен в очередь " + config.getProxyQueueName());
			}
		}
		catch (Exception e)
		{
			log.error("Сбой на обработке P2P-запросов от МБК", e);
		}
		finally
		{
			ApplicationInfo.setDefaultApplication();
		}
	}

	private List<P2PRequest> getRequests() throws Exception
	{
		MobileBankService mobileBankService = GateSingleton.getFactory().service(MobileBankService.class);

		return mobileBankService.getMobilePaymentCardRequests();
	}

	public void interrupt()
	{
		isInterrupt = true;
	}
}
