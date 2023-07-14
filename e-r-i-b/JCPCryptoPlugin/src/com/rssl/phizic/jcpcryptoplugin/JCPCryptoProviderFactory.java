package com.rssl.phizic.jcpcryptoplugin;

import com.rssl.phizic.security.crypto.CryptoProviderFactory;
import com.rssl.phizic.security.crypto.CryptoProvider;

/**
 * @author Erkin
 * @ created 15.12.2010
 * @ $Author$
 * @ $Revision$
 */
public class JCPCryptoProviderFactory implements CryptoProviderFactory
{
	private CryptoProvider provider = null;

	private final Long lock = new Long(1);

	///////////////////////////////////////////////////////////////////////////

	public String getName()
	{
		return JCPConstants.PROVIDER_NAME;
	}

	public CryptoProvider getProvider()
	{
		if (provider == null) {
			synchronized (lock) {
				provider = new JCPCryptoProvider();
			}
		}
		return provider;
	}

	public void close()
	{
		// nothing to do
	}
}
