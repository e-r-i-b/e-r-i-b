package com.rssl.phizic.cryptoplugin;

import junit.framework.TestCase;
import com.rssl.phizic.security.crypto.CryptoProvider;

/**
 * @author Omeliyanchuk
 * @ created 28.02.2007
 * @ $Author$
 * @ $Revision$
 */

public class CryptoImplTest extends TestCase
{
/*	public void testCryptoLoad()
	{
		CryptoProviderFactoryImpl factory = new CryptoProviderFactoryImpl();
		CryptoProvider provider = factory.getProvider();
		factory.close();
	}
	public void testGeneralFunction()
	{
		CryptoProviderFactoryImpl factory = new CryptoProviderFactoryImpl();
		CryptoProvider agava = factory.getProvider();

		String s = agava.rand(8);

		assertNotNull(s);

		String h = agava.hashOld(s);

		assertNotNull(h);

		char[] chars123 = new char[]{'1', '2', '3'};

		String m = new String(chars123);
		String hash123 = agava.hashOld( m );

		assertNotNull(hash123);

		agava.hashOld("admin");

		factory.close();

		//agava.release(); //todo а нужно ли?
	}

	public void manualSeeBugs()
	{
		CryptoProviderFactoryImpl factory = new CryptoProviderFactoryImpl();
		CryptoProvider agava = factory.getProvider();

		String s = agava.rand(100000);
		assertNotNull(s);

		String h = agava.hashOld("");
		assertNotNull(h);

		s = agava.rand(-1);
		assertNotNull(s);

		h = agava.hashOld(null);
		assertNotNull(h);

		factory.close();
	}

	public  void manualTestConcurrencyThreadsUsing()
	{
		Thread t1 = new Thread(new AgavaJNIRunable());

		t1.start();

		new Thread(new AgavaJNIRunable()).start();
	}

	class AgavaJNIRunable implements Runnable
	{
		public void run()
		{
			CryptoProviderFactoryImpl factory = new CryptoProviderFactoryImpl();
			CryptoProvider agava = factory.getProvider();

			agava.rand(8);
			agava.hashOld("lkdchnkwencnwcjn");
			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}

			factory.close();
		}
	}
*/
}