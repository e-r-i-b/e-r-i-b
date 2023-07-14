package com.rssl.phizic.ws.currency.sbrf;

import com.rssl.phizic.gate.csa.MQInfo;
import com.rssl.phizic.gate.csa.NodeInfo;
import com.rssl.auth.csa.wsclient.NodeInfoConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.utils.jms.JmsService;
import com.rssl.phizic.gate.config.ESBEribConfig;

import java.rmi.RemoteException;
import javax.jms.JMSException;

/**
 * Обработчик входящих запросов от шины по обновлению курсов валют для прокси шлюза
 *
 * @author gladishev
 * @ created 29.10.13
 * @ $Author$
 * @ $Revision$
 */
public class EsbEribRatesProxyBackServiceImpl extends EsbEribRatesBackServiceImpl
{
	public static final String RATES_DIC_REQUEST = "RatesDic";

	private static final JmsService jmsService = new JmsService();

	public String doIFX(String request) throws RemoteException
	{
		ESBEribConfig config = ConfigFactory.getConfig(ESBEribConfig.class);
		NodeInfoConfig nodeInfoConfig = ConfigFactory.getConfig(NodeInfoConfig.class);
		for (NodeInfo info : nodeInfoConfig.getNodes())
		{
			try
			{
				redirectToNode(config.getRatesRedirectServiceUrl(info.getId()), request);
			}
			catch (Exception e)
			{
				log.error("Ошибка при отправке запроса обновления справочника ценных бумаг в блок № " + info.getId(), e);
				try
				{
					MQInfo dictionaryMQ = info.getDictionaryMQ();
					jmsService.sendObjectToQueue(request, dictionaryMQ.getQueueName(), dictionaryMQ.getFactoryName(), RATES_DIC_REQUEST, null);
				}
				catch (JMSException jmse)
				{
					log.error(jmse);
				}
			}
		}

		return makeResult(ALL_OK_STATUS, handleMessage(request));
	}

	private void redirectToNode(String url, String request) throws Exception
	{
		EribRates_ServiceLocator locator = new EribRates_ServiceLocator();
		locator.seteribRatesEndpointAddress(url);
		EribRatesStub stub = (EribRatesStub) locator.geteribRates();
		stub.doIFX(request);
	}
}
