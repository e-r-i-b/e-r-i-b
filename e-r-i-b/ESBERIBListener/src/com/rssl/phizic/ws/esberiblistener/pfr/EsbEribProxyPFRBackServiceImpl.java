package com.rssl.phizic.ws.esberiblistener.pfr;

import com.rssl.phizgate.common.payments.offline.OfflineDocumentInfo;
import com.rssl.phizgate.common.payments.offline.OfflineDocumentInfoService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.ws.esberiblistener.pfr.generated.PfrDoneRqType;
import com.rssl.phizic.ws.esberiblistener.pfr.generated.PfrDoneRsType;
import com.rssl.phizic.ws.esberiblistener.pfr.generated.PfrDoneServiceImplLocator;
import com.rssl.phizic.ws.esberiblistener.pfr.generated.PfrDoneServiceSoapBindingStub;
import com.rssl.phizic.gate.config.ESBEribConfig;

/**
 * Реализация обработчика входящих сообщений по ПФР от шины
 * Используется в прокси-шлюзе
 * @author gladishev
 * @ created 18.10.13
 * @ $Author$
 * @ $Revision$
 */
public class EsbEribProxyPFRBackServiceImpl extends EsbEribPFRBackServiceBase<OfflineDocumentInfo>
{
	public static final String REDIRECT_WS_NAME = "PfrDoneServicePort";
	@Override
	protected PfrDoneRsType updateDocument(OfflineDocumentInfo document, PfrDoneRqType parameters)
	{

		try
		{
			return redirectToNode(document.getBlockNumber(), parameters);
		}
		catch (Exception e)
		{
			logException(e);
			return createResponse(-1L);
		}
	}

	private PfrDoneRsType redirectToNode(Long nodeId, PfrDoneRqType request) throws Exception
	{
		ESBEribConfig config = ConfigFactory.getConfig(ESBEribConfig.class);

		PfrDoneServiceImplLocator locator = new PfrDoneServiceImplLocator();
		String url = String.format(config.getRedirectServiceUrl(nodeId), EsbEribProxyPFRBackServiceImpl.REDIRECT_WS_NAME);
		locator.setPfrDoneServicePortEndpointAddress(url);
		PfrDoneServiceSoapBindingStub stub = (PfrDoneServiceSoapBindingStub) locator.getPfrDoneServicePort();
		return stub.pfrDone(request);
	}

	@Override
	protected OfflineDocumentInfo findDocument(String operationId)
	{
		try
		{
			return OfflineDocumentInfoService.getOfflineDocumentInfo(operationId);
		}
		catch (GateException e)
		{
			logException(e);
		}
		return null;
	}
}
