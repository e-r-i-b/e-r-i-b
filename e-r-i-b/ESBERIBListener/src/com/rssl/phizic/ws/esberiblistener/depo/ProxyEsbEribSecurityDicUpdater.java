package com.rssl.phizic.ws.esberiblistener.depo;

import com.rssl.phizic.gate.csa.MQInfo;
import com.rssl.phizic.gate.csa.NodeInfo;
import com.rssl.auth.csa.wsclient.NodeInfoConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.jms.JmsService;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.ws.esberiblistener.depo.generated.BackServiceStub;
import com.rssl.phizic.ws.esberiblistener.depo.generated.BackService_ServiceLocator;
import com.rssl.phizic.gate.config.ESBEribConfig;
import org.apache.axis.client.Stub;
import org.w3c.dom.Document;

import javax.jms.JMSException;
import javax.xml.transform.TransformerException;

/**
 * Обработчик запроса на обновление справочника ценных бумаг.
 * Используется в прокси листенере
 * @author gladishev
 * @ created 28.10.13
 * @ $Author$
 * @ $Revision$
 */
public class ProxyEsbEribSecurityDicUpdater extends EsbEribSecurityDicUpdater
{
	public static final String SECURITY_DIC_XML_REQUEST = "SecurityDicDepo";

	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);
	private static final JmsService jmsService = new JmsService();

	public ProxyEsbEribSecurityDicUpdater(Document request)
	{
		super(request);
	}

	@Override
	public Document updateSecurityDictionary() throws TransformerException, GateException, GateLogicException
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
					jmsService.sendObjectToQueue(XmlHelper.convertDomToText(request), dictionaryMQ.getQueueName(), dictionaryMQ.getFactoryName(), SECURITY_DIC_XML_REQUEST, null);
				}
				catch (JMSException jmse)
				{
					log.error(jmse);
				}
			}
		}

		Document responseDocument = createResponseDocument(request);
		appendDocumentBlock(responseDocument, null);
		return responseDocument;
	}

	private void redirectToNode(String urlAddress) throws Exception
	{
		BackService_ServiceLocator service = new BackService_ServiceLocator();
		BackServiceStub stub = (BackServiceStub) service.getbackService();
		stub._setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY, urlAddress);
		stub.doIFX(XmlHelper.convertDomToText(request));
	}
}
