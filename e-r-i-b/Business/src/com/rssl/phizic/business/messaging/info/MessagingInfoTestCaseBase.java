package com.rssl.phizic.business.messaging.info;

import com.rssl.phizic.security.crypto.CryptoProviderService;
import com.rssl.phizic.utils.test.RSSLTestCaseBase;

/** Created by IntelliJ IDEA. User: Evgrafov Date: 02.09.2005 Time: 15:22:41 */
public abstract class MessagingInfoTestCaseBase extends RSSLTestCaseBase
{

	private static CryptoProviderService cryptoProviderService = null;
	private static boolean initialized = false;

	public MessagingInfoTestCaseBase(String string)
	{
		super(string);
	}

	public MessagingInfoTestCaseBase()
	{
	}

	protected void setUp() throws Exception
	{
		super.setUp();
		initialize();
	}

	private void initialize() throws Exception
	{
		if (initialized)
			return;

		cryptoProviderService = new CryptoProviderService();
		cryptoProviderService.start();

		initialized = true;
	}

	protected void tearDown() throws Exception
	{
		try
		{
			if (initialized)
				cryptoProviderService.stop();
		}
		finally
		{
			super.tearDown();
		}
	}

}
