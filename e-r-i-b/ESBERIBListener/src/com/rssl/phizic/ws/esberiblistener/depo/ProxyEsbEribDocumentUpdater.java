package com.rssl.phizic.ws.esberiblistener.depo;

import com.rssl.phizic.gate.csa.NodeInfo;
import com.rssl.auth.csa.wsclient.NodeInfoConfig;
import com.rssl.common.forms.doc.DocumentCommand;
import com.rssl.phizgate.common.payments.offline.OfflineDocumentInfo;
import com.rssl.phizgate.common.payments.offline.OfflineDocumentInfoService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.claims.DepoAccountClaimBase;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.utils.xml.ForeachElementAction;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.ws.esberiblistener.depo.generated.BackServiceStub;
import com.rssl.phizic.ws.esberiblistener.depo.generated.BackService_ServiceLocator;
import com.rssl.phizic.gate.config.ESBEribConfig;
import org.apache.axis.client.Stub;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.transform.TransformerException;

/**
 * Обработчик шинного запроса DocStateUpdateRq для депозитарных документов
 *
 * @author gladishev
 * @ created 21.10.13
 * @ $Author$
 * @ $Revision$
 */
public class ProxyEsbEribDocumentUpdater extends EsbEribDocumentUpdaterBase<OfflineDocumentInfo>
{
	//разбивка информации о документах по блокам для последующего редиректа запроса
	private Map<Long, List<String>> documentsByNode = new HashMap<Long, List<String>>();

	public ProxyEsbEribDocumentUpdater(Document request)
	{
		super(request);
	}

	@Override
	public Document updateDocumentsState() throws TransformerException
	{
		Document document = super.updateDocumentsState();

		redirectRequest();

		return document;
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
				Document request = createRequest(documentsByNode.get(nodeId));
				redirectToNode(nodeId, request);
			}
			catch (Exception e)
			{
				log.error("Ошибка при редиректе запроса изменения статусов документов в блок " + nodeId, e);
			}
		}
	}

	private void redirectToNode(Long nodeId, Document request)  throws Exception
	{
		ESBEribConfig config = ConfigFactory.getConfig(ESBEribConfig.class);

		BackService_ServiceLocator service = new BackService_ServiceLocator();
		BackServiceStub stub = (BackServiceStub) service.getbackService();
		String url = String.format(config.getRedirectServiceUrl(nodeId), EsbEribProxyBackServiceImpl.REDIRECT_WS_NAME);
		stub._setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY, url);
		stub.doIFX(XmlHelper.convertDomToText(request));
	}

	private Document createRequest(final List<String> elements) throws Exception
	{
		final Document document = (Document) request.cloneNode(true);
		final Element documentElement = document.getDocumentElement();
		XmlHelper.foreach(documentElement, "Document", new ForeachElementAction()
			{
				public void execute(Element element) throws Exception
				{
					String docNumber = XmlHelper.getSimpleElementValue(element, "DocNumber");
					if (!elements.contains(docNumber))
						documentElement.removeChild(element);
				}
			});

		return document;
	}

	@Override
	protected OfflineDocumentInfo getDocument(String documentNumber) throws GateException, GateLogicException
	{
		return OfflineDocumentInfoService.getOfflineDocumentInfo(documentNumber);
	}

	@Override
	protected void updateDocument(OfflineDocumentInfo document, DocumentCommand command) throws GateException, GateLogicException
	{
		document.fillState(command);
		OfflineDocumentInfoService.updateOfflineDocumentInfo(document);

		Long blockNumber = document.getBlockNumber();
		List<String> documentTypes = documentsByNode.get(blockNumber);
		if (documentTypes == null)
			documentTypes = new ArrayList<String>();
		documentTypes.add(document.getExternalId());
		documentsByNode.put(blockNumber, documentTypes);
	}

	@Override
	protected boolean checkDocument(OfflineDocumentInfo document)
	{
		try
		{
			Class<Object> clazz = ClassHelper.loadClass(document.getDocumentType());
			return document.getStateCode() == null && DepoAccountClaimBase.class.isAssignableFrom(clazz);
		}
		catch (ClassNotFoundException e)
		{
			log.error(e.getMessage(), e);
			return false;
		}
	}
}
