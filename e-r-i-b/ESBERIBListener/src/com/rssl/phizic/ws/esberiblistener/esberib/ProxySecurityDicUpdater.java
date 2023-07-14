package com.rssl.phizic.ws.esberiblistener.esberib;

import com.rssl.phizic.gate.csa.MQInfo;
import com.rssl.phizic.gate.csa.NodeInfo;
import com.rssl.auth.csa.wsclient.NodeInfoConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.utils.jms.JmsService;
import com.rssl.phizic.ws.esberiblistener.esberib.generated.EsbEribBackService_BindingStub;
import com.rssl.phizic.ws.esberiblistener.esberib.generated.EsbEribBackService_ServiceLocator;
import com.rssl.phizic.ws.esberiblistener.esberib.generated.IFXRq_Type;
import com.rssl.phizic.ws.esberiblistener.esberib.generated.SecDicInfoRs_Type;
import com.rssl.phizic.gate.config.ESBEribConfig;
import org.apache.axis.client.Stub;

import javax.jms.JMSException;

/**
 * Обработчик запроса на обновление справочника ценных бумаг.
 * Используется в прокси листенере
 * @author gladishev
 * @ created 28.10.13
 * @ $Author$
 * @ $Revision$
 */
public class ProxySecurityDicUpdater extends SecurityDicUpdater
{
	public static final String SECURITY_DIC_IFX_REQUEST = "SecurityDicEsberib";

	private static final JmsService jmsService = new JmsService();

	public ProxySecurityDicUpdater(IFXRq_Type secDicInfoRq)
	{
		super(secDicInfoRq);
	}

	@Override
	protected SecDicInfoRs_Type getSecDicInfoRs_type()
	{
		ESBEribConfig config = ConfigFactory.getConfig(ESBEribConfig.class);
		NodeInfoConfig nodeInfoConfig = ConfigFactory.getConfig(NodeInfoConfig.class);
		for (NodeInfo info : nodeInfoConfig.getNodes())
		{
			try
			{
				redirectToNode(String.format(config.getRedirectServiceUrl(info.getId()), EsbEribProxyBackServiceImpl.REDIRECT_WS_NAME));
			}
			catch (Exception e)
			{
				log.error("Ошибка при отправке запроса обновления справочника ценных бумаг в блок № " + info.getId(), e);
				try
				{
					MQInfo dictionaryMQ = info.getDictionaryMQ();
					jmsService.sendObjectToQueue(secDicInfoRq, dictionaryMQ.getQueueName(), dictionaryMQ.getFactoryName(), SECURITY_DIC_IFX_REQUEST, null);
				}
				catch (JMSException jmse)
				{
					log.error(jmse);
				}
			}
		}

		return getSuccessfullResponse();
	}

	private void redirectToNode(String url) throws Exception
	{
		EsbEribBackService_ServiceLocator locator = new EsbEribBackService_ServiceLocator();
		EsbEribBackService_BindingStub stub = (EsbEribBackService_BindingStub) locator.getEsbEribBackService();
		stub._setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY, url);
		stub.doIFX(secDicInfoRq);
	}
}
