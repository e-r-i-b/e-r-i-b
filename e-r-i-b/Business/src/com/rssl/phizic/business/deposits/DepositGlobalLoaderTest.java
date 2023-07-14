package com.rssl.phizic.business.deposits;

import com.rssl.phizic.test.BusinessTestCaseBase;
import org.xml.sax.SAXException;

import java.io.IOException;

/**
 * @author Roshka
 * @ created 10.05.2006
 * @ $Author$
 * @ $Revision$
 */

public class DepositGlobalLoaderTest extends BusinessTestCaseBase
{
	private static final String PATH = "Business/settings/deposits/globals";

	public void testDepositGlobalLoader() throws IOException, SAXException
	{
//		CryptoProviderFactory factory = new CryptoProviderHelper().lookupFactory();
//		CryptoProvider provider = factory.getProvider();
//		provider.rand(10);
//		factory.close();
		
		DepositGlobal global = getDepositGlobal();
		assertNotNull(global);
	}

	public static DepositGlobal getDepositGlobal() throws IOException, SAXException
	{
		DepositGlobalLoader loader = new DepositGlobalLoader(PATH);
		return loader.load();
	}
}
