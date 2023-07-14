package com.rssl.phizicgate.iqwave.listener.proxy;

import com.rssl.phizic.gate.csa.NodeInfo;
import com.rssl.auth.csa.wsclient.NodeInfoConfig;
import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.phizgate.common.payments.offline.OfflineDocumentInfo;
import com.rssl.phizgate.common.payments.offline.OfflineDocumentInfoService;
import com.rssl.phizic.ListenerConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.iqwave.listener.MessageHelper;
import com.rssl.phizicgate.iqwave.listener.MesssageProcessor;
import com.rssl.phizicgate.iqwave.listener.proxy.generated.Esk4IQWaveProtType;
import com.rssl.phizicgate.iqwave.listener.proxy.generated.Esk4IQWaveService;
import com.rssl.phizicgate.iqwave.listener.proxy.generated.Esk4IQWaveService_Impl;
import com.rssl.phizicgate.iqwave.messaging.Constants;
import com.rssl.phizicgate.iqwave.messaging.MessageInfoContainer;
import com.rssl.phizgate.common.payments.PaymentCompositeId;
import com.rssl.phizicgate.wsgateclient.services.multiblock.adapterinfo.AdapterInfoHelper;

import java.rmi.RemoteException;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.Stub;
import javax.xml.transform.TransformerException;

/**
 * обработчик ответов результата исполения документа для прокси-листенера
 *
 * @author gladishev
 * @ created 16.10.13
 * @ $Author$
 * @ $Revision$
 */
public class ProxyExecutionResultProcessor implements MesssageProcessor
{
	private static final String LISTENER_EMPTY_ERROR = "Не указан адрес для редиректа запроса в блок № ";

	private static Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE);

	private String request;

	/**
	 * ctor
	 * @param request - обрабатываемое сообщение
	 */
	public ProxyExecutionResultProcessor(String request)
	{
		this.request = request;
	}

	public String process(MessageInfoContainer infoContainer) throws GateException, GateLogicException
	{
		PaymentCompositeId compositeId = new PaymentCompositeId(infoContainer.getParentMessageId(), XMLDatatypeHelper.formatDate(infoContainer.getParentMessageDate()));
		String documentExternalId = compositeId.toString();
		OfflineDocumentInfo offlineDocumentInfo = OfflineDocumentInfoService.getOfflineDocumentInfo(documentExternalId);

		if (offlineDocumentInfo == null)
			throw new GateException("Не найдена информация о документе " + documentExternalId);

		if (StringHelper.isNotEmpty(offlineDocumentInfo.getStateCode()))
			throw new GateException("Статус документа уже изменен " + documentExternalId); //статус уже заполнен, т.е. документ уже обработан

		saveOfflineInfo(offlineDocumentInfo, infoContainer);

		redirectRequest(offlineDocumentInfo);

		try
		{
			return XmlHelper.convertDomToText(MessageHelper.fillHeader(Constants.OFFLINE_TICKET, infoContainer, Constants.NO_ERROR, null));
		}
		catch (TransformerException e)
		{
			throw new GateException(e);
		}
	}

	private boolean isNodeAvailable(Long nodeId)
	{
		NodeInfo node = ConfigFactory.getConfig(NodeInfoConfig.class).getNode(nodeId);
		return node.isExistingUsersAllowed() || node.isNewUsersAllowed() || node.isTemporaryUsersAllowed();
	}

	private void redirectRequest(OfflineDocumentInfo offlineDocumentInfo) throws GateException
	{
		ListenerConfig listenerConfig = ConfigFactory.getConfig(ListenerConfig.class);
		try
		{
			if (!isNodeAvailable(offlineDocumentInfo.getBlockNumber()))
				return;

			String nodeAdapterInfoWebServiceUrl = listenerConfig.getNodeWebServiceUrl(offlineDocumentInfo.getBlockNumber());
			String adapterListenerUrl = AdapterInfoHelper.getAdapterListenerUrl(nodeAdapterInfoWebServiceUrl, offlineDocumentInfo.getAdapterUUID());
			if (StringHelper.isEmpty(adapterListenerUrl))
			{
				log.error(LISTENER_EMPTY_ERROR + offlineDocumentInfo.getBlockNumber());
				return;
			}

			Esk4IQWaveService service = new Esk4IQWaveService_Impl();
		    Esk4IQWaveProtType stub = service.getEsk4IQWave();
			String url = String.format(listenerConfig.getRedirectServiceUrl(), adapterListenerUrl);
			((com.sun.xml.rpc.client.StubBase) stub)._setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY, url);
		    stub.getXMLmessage(this.request);
		}
		catch (ServiceException e)
		{
			throw new GateException(e);
		}
		catch (RemoteException e)
		{
			throw new GateException(e);
		}
	}

	private void saveOfflineInfo(OfflineDocumentInfo offlineDocumentInfo, MessageInfoContainer infoContainer) throws GateException, GateLogicException
	{
		if (infoContainer.getErrorCode() == null)
		{
			processExecute(offlineDocumentInfo, infoContainer);
		}
		else
		{
			processReject(offlineDocumentInfo, infoContainer);
		}

		OfflineDocumentInfoService.updateOfflineDocumentInfo(offlineDocumentInfo);
	}

	private void processExecute(OfflineDocumentInfo offlineDocumentInfo, MessageInfoContainer infoContainer) throws GateException, GateLogicException
	{
		offlineDocumentInfo.setStateCode(DocumentEvent.EXECUTE.name());
		try
		{
			offlineDocumentInfo.setAdditInfo(XmlHelper.convertDomToText(infoContainer.getBodyContent()));
		}
		catch (TransformerException e)
		{
			log.error("Ошибка при разборе информации по документу " + offlineDocumentInfo.getExternalId(), e);
		}
	}

	private void processReject(OfflineDocumentInfo offlineDocumentInfo, MessageInfoContainer infoContainer) throws GateException, GateLogicException
	{
		offlineDocumentInfo.fillState(MessageHelper.error2State(infoContainer));
	}
}
