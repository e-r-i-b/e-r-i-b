package com.rssl.phizic.ws.esberiblistener.depo;

import org.w3c.dom.Document;

import java.rmi.RemoteException;

/**
 * @author gladishev
 * @ created 18.10.13
 * @ $Author$
 * @ $Revision$
 */
public class EsbEribProxyBackServiceImpl extends EsbEribBackServiceImpl
{
	public static final String REDIRECT_WS_NAME = "backService";

	public EsbEribProxyBackServiceImpl() throws RemoteException
	{
		super();
	}

	@Override
	protected EsbEribDocumentUpdaterBase getDocumentStateUpdater(Document docStateUpdateRq)
	{
		return new ProxyEsbEribDocumentUpdater(docStateUpdateRq);
	}

	@Override
	protected EsbEribSecurityDicUpdater getSecurityDicUpdater(Document secDicInfoRq)
	{
		return new ProxyEsbEribSecurityDicUpdater(secDicInfoRq);
	}
}
