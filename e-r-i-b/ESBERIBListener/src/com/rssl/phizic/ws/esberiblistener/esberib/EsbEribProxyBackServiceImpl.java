package com.rssl.phizic.ws.esberiblistener.esberib;

import com.rssl.phizic.ws.esberiblistener.esberib.generated.DocStateUpdateRq_Type;
import com.rssl.phizic.ws.esberiblistener.esberib.generated.IFXRq_Type;

/**
 * обработчика входящих сообщений от шины.
 * Используется в прокси листенере
 * @author gladishev
 * @ created 18.10.13
 * @ $Author$
 * @ $Revision$
 */
public class EsbEribProxyBackServiceImpl extends EsbEribBackServiceImpl
{
	public static final String REDIRECT_WS_NAME = "EsbEribBackService";

	@Override
	protected DocumentStateUpdaterBase getDocumentStateUpdater(DocStateUpdateRq_Type docStateUpdateRq)
	{
		return new ProxyDocumentStateUpdater(docStateUpdateRq);
	}

	@Override
	protected SecurityDicUpdater getSecurityDicUpdater(IFXRq_Type secDicInfoRq)
	{
		return new ProxySecurityDicUpdater(secDicInfoRq);
	}
}
