package com.rssl.phizic.security.test;

import com.rssl.phizic.security.crypto.CryptoProviderService;
import com.rssl.phizic.security.crypto.CryptoProviderServiceException;
import com.rssl.phizic.utils.test.RSSLTestCaseBase;

import java.io.InvalidClassException;
import javax.management.MBeanException;

/**
 * @author Evgrafov
 * @ created 23.01.2006
 * @ $Author: bogdanov $
 * @ $Revision: 57189 $
 */

public abstract class SecurityTestBase extends RSSLTestCaseBase
{
	private static final Object SERVICE_LOCKER = new Object();
	private static volatile CryptoProviderService cryptoProviderService = null;

	protected void setUp() throws Exception
	{
		super.setUp();
		initialize1();
	}

	private static void initialize1() throws InvalidClassException, CryptoProviderServiceException, MBeanException
	{
		if(cryptoProviderService != null)
			return;

		synchronized(SERVICE_LOCKER)
		{
			if(cryptoProviderService != null)
				return;

			cryptoProviderService = new CryptoProviderService();
			cryptoProviderService.start();

			Runtime.getRuntime().addShutdownHook(new ShutdownCrypto(cryptoProviderService));
		}
	}


	private static class ShutdownCrypto extends Thread
	{
		private CryptoProviderService service;

		ShutdownCrypto(CryptoProviderService service)
		{
			this.service = service;
		}

		public void run()
		{
			service.stop();
		}
	}
}

