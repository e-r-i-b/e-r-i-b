package com.rssl.phizic.business.deposits;

import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.business.BusinessException;
import org.xml.sax.SAXException;

import java.io.IOException;

/**
 * @author Roshka
 * @ created 10.05.2006
 * @ $Author$
 * @ $Revision$
 */

public class DepositGlobalSynchronizerTest extends BusinessTestCaseBase
{
	public void manualDepositGlobalSynchronizer() throws IOException, SAXException, BusinessException
	{
		DepositGlobal global = DepositGlobalLoaderTest.getDepositGlobal();
		DepositGlobalSynchronizer synchronizer = new DepositGlobalSynchronizer(global);
		synchronizer.update();
	}

	public void test()
	{
	}

}
