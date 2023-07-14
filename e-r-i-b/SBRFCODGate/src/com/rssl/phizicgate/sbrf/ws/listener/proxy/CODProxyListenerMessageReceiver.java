package com.rssl.phizicgate.sbrf.ws.listener.proxy;

import com.rssl.phizic.gate.csa.NodeInfo;
import com.rssl.auth.csa.wsclient.NodeInfoConfig;
import com.rssl.common.forms.doc.DocumentCommand;
import com.rssl.phizgate.common.payments.offline.OfflineDocumentInfo;
import com.rssl.phizgate.common.payments.offline.OfflineDocumentInfoService;
import com.rssl.phizic.ListenerConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.InvalidTargetException;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.sbrf.ws.listener.CODListenerMessageReceiver;
import com.rssl.phizicgate.sbrf.ws.listener.InternalMessageInfoContainer;
import com.rssl.phizicgate.sbrf.ws.listener.OfflineRequestHandler;
import com.rssl.phizicgate.sbrf.ws.listener.proxy.generated.OfflineSrvLocator;
import com.rssl.phizicgate.sbrf.ws.listener.proxy.generated.OfflineSrvSoap_PortType;
import com.rssl.phizicgate.wsgateclient.services.multiblock.adapterinfo.AdapterInfoHelper;

import java.rmi.RemoteException;
import javax.xml.rpc.ServiceException;

/**
 * Инстанс обработчика сообщений от COD для прокси-шлюза
 *
 * @author gladishev
 * @ created 16.10.13
 * @ $Author$
 * @ $Revision$
 */
public class CODProxyListenerMessageReceiver extends CODListenerMessageReceiver
{
	private static final String LISTENER_EMPTY_ERROR = "Не указан адрес для редиректа запроса в блок № ";

	/**
	 * ctor
	 * @param factory - гейтовая фабрика
	 */
	public CODProxyListenerMessageReceiver(GateFactory factory) throws GateException
	{
		super(factory);
	}

	protected boolean handlePayment(InternalMessageInfoContainer container, boolean isSuccess) throws GateException, GateLogicException
	{
		String documentExternalId = composeDocumentExternalId(container);
		OfflineDocumentInfo offlineDocumentInfo = OfflineDocumentInfoService.getOfflineDocumentInfo(documentExternalId);

		if (offlineDocumentInfo == null)
			throw new InvalidTargetException("Не найдена информация о документе " + documentExternalId);

		if (StringHelper.isNotEmpty(offlineDocumentInfo.getStateCode()))
			throw new GateException("Статус документа уже изменен " + documentExternalId); //статус уже заполнен, т.е. документ уже обработан

		saveOfflineInfo(offlineDocumentInfo, container, isSuccess);

		redirectRequest(container, offlineDocumentInfo);

		return true;
	}

	protected void finalizeHandle()
	{
		//nothing
	}

	private boolean isNodeAvailable(Long nodeId)
	{
		NodeInfo node = ConfigFactory.getConfig(NodeInfoConfig.class).getNode(nodeId);
		return node.isExistingUsersAllowed() || node.isNewUsersAllowed() || node.isTemporaryUsersAllowed();
	}

	private void redirectRequest(InternalMessageInfoContainer container, OfflineDocumentInfo offlineDocumentInfo) throws GateException
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

			OfflineSrvLocator codLocator = new OfflineSrvLocator();
			String url = String.format(listenerConfig.getRedirectServiceUrl(), adapterListenerUrl);
	        codLocator.setOfflineSrvSoapEndpointAddress(url);
	        OfflineSrvSoap_PortType portType = codLocator.getOfflineSrvSoap();
	        portType.getXMLmessage(container.getMessage().getBody());
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

	private void saveOfflineInfo(OfflineDocumentInfo offlineDocumentInfo, InternalMessageInfoContainer container, boolean isSuccess) throws GateException
	{
		Class docClass;
		try
		{
			docClass = ClassHelper.loadClass(offlineDocumentInfo.getDocumentType());
		}
		catch (ClassNotFoundException e)
		{
			throw new GateException(e);
		}
		OfflineRequestHandler handler = getPaymentHandler(isSuccess, docClass);
		DocumentCommand updateCommand = handler.getUpdateCommand(container);

		if (isSuccess)
		{
			offlineDocumentInfo.setStateCode(updateCommand.getEvent().name());
			offlineDocumentInfo.setAdditInfo(container.getMessage().getBody());
		}
		else
		{
			offlineDocumentInfo.fillState(updateCommand);
		}

		OfflineDocumentInfoService.updateOfflineDocumentInfo(offlineDocumentInfo);
	}
}