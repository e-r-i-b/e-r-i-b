package com.rssl.phizic.sbrfcryptoplugin;

import com.rssl.phizic.security.crypto.CryptoProviderFactory;
import com.rssl.phizic.security.crypto.CryptoProvider;

/**
 * @author Omeliyanchuk
 * @ created 24.03.2008
 * @ $Author$
 * @ $Revision$
 */

public class SBRFCryptoProviderFactoryImpl implements CryptoProviderFactory
{
	private final static String GOST_PROVIDER_NAME = "SBRF";

	///////////////////////////////////////////////////////////////////////////

	public String getName()
	{
		return GOST_PROVIDER_NAME;
	}

	public CryptoProvider getProvider()
	{
		return new SBRFCryptoProvider();
	}

	public void close()
	{

	}
}
