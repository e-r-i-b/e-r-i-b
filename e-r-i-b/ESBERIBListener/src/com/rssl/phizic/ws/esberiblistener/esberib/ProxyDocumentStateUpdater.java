package com.rssl.phizic.ws.esberiblistener.esberib;

import com.rssl.phizic.gate.csa.NodeInfo;
import com.rssl.auth.csa.wsclient.NodeInfoConfig;
import com.rssl.common.forms.doc.DocumentCommand;
import com.rssl.phizgate.common.payments.offline.OfflineDocumentInfo;
import com.rssl.phizgate.common.payments.offline.OfflineDocumentInfoService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.claims.DepoAccountClaimBase;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.autosubscriptions.AutoSubscriptionClaim;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.ws.esberiblistener.esberib.generated.*;
import com.rssl.phizic.gate.config.ESBEribConfig;
import org.apache.axis.client.Stub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Обработчик шинного запроса DocStateUpdateRq для прокси шлюза
 *
 * @author gladishev
 * @ created 19.10.13
 * @ $Author$
 * @ $Revision$
 */
public class ProxyDocumentStateUpdater extends DocumentStateUpdaterBase<OfflineDocumentInfo>
{
	//разбивка информации о документах по блокам для последующего редиректа запроса
	private Map<Long, List<Document_Type>> documentsByNode = new HashMap<Long, List<Document_Type>>();

	public ProxyDocumentStateUpdater(DocStateUpdateRq_Type docStateUpdateType)
	{
		super(docStateUpdateType);
	}

	@Override
	public IFXRs_Type doIFX()
	{
		IFXRs_Type result = super.doIFX();

		redirectRequest();

		return result;
	}

	@Override
	protected OfflineDocumentInfo getDocument(String documentNumber) throws GateException, GateLogicException
	{
		return OfflineDocumentInfoService.getOfflineDocumentInfo(documentNumber);
	}

	@Override
	protected void updateDocument(OfflineDocumentInfo document, Document_Type documentInfo) throws GateException, GateLogicException
	{
		DocumentCommand documentCommand = getDocumentCommand(documentInfo);
		document.fillState(documentCommand);
		OfflineDocumentInfoService.updateOfflineDocumentInfo(document);

		Long blockNumber = document.getBlockNumber();
		List<Document_Type> documentTypes = documentsByNode.get(blockNumber);
		if (documentTypes == null)
			documentTypes = new ArrayList<Document_Type>();
		documentTypes.add(documentInfo);
		documentsByNode.put(blockNumber, documentTypes);
	}

	@Override
	protected boolean isUpdateUnavailable(OfflineDocumentInfo document)
	{
		try
		{
			Class<Object> clazz = ClassHelper.loadClass(document.getDocumentType());
			return !(AutoSubscriptionClaim.class.isAssignableFrom(clazz) || DepoAccountClaimBase.class.isAssignableFrom(clazz));
		}
		catch (ClassNotFoundException e)
		{
			log.error(e.getMessage(), e);
			return false;
		}
	}

	private boolean isNodeAvailable(Long nodeId)
	{
		NodeInfo node = ConfigFactory.getConfig(NodeInfoConfig.class).getNode(nodeId);
		return node.isExistingUsersAllowed() || node.isNewUsersAllowed() || node.isTemporaryUsersAllowed();
	}

	private void redirectRequest()
	{
		for (Long nodeId : documentsByNode.keySet())
		{
			if (!isNodeAvailable(nodeId))
				continue;

			try
			{
				IFXRq_Type request = createRequest(documentsByNode.get(nodeId));
				redirectToNode(nodeId, request);
			}
			catch (Exception e)
			{
				log.error("Ошибка при редиректе запроса изменения статусов документов в блок " + nodeId, e);
			}
		}
	}

	private void redirectToNode(Long nodeId, IFXRq_Type request) throws Exception
	{
		ESBEribConfig config = ConfigFactory.getConfig(ESBEribConfig.class);

		EsbEribBackService_ServiceLocator locator = new EsbEribBackService_ServiceLocator();
		EsbEribBackService_BindingStub stub = (EsbEribBackService_BindingStub) locator.getEsbEribBackService();
		String url = String.format(config.getRedirectServiceUrl(nodeId), EsbEribProxyBackServiceImpl.REDIRECT_WS_NAME);
		stub._setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY,  url);
		stub.doIFX(request);
	}

	private IFXRq_Type createRequest(List<Document_Type> documentTypes) throws Exception
	{
		DocStateUpdateRq_Type docStateUpdateRq = new DocStateUpdateRq_Type();
		docStateUpdateRq.setRqUID(docStateUpdateType.getRqUID());
		docStateUpdateRq.setRqTm(docStateUpdateType.getRqTm());
		docStateUpdateRq.setOperUID(docStateUpdateType.getOperUID());
		docStateUpdateRq.setSPName(docStateUpdateType.getSPName());
		docStateUpdateRq.setDocument(documentTypes.toArray(new Document_Type[documentTypes.size()]));

		IFXRq_Type rq = new IFXRq_Type();
		rq.setDocStateUpdateRq(docStateUpdateRq);
		return rq;
	}
}
