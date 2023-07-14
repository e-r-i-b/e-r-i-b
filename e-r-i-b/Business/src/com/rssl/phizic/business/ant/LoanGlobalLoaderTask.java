package com.rssl.phizic.business.ant;

import com.rssl.phizic.utils.test.SafeTaskBase;
import com.rssl.phizic.business.loans.LoanGlobalLoader;
import com.rssl.phizic.business.loans.LoanGlobal;
import com.rssl.phizic.business.loans.LoanGlobalSynchronizer;

/**
 *  Ант-таск загрузки общих данных для кредитов
 * @author gladishev
 * @ created 15.01.2008
 * @ $Author$
 * @ $Revision$
 */

public class LoanGlobalLoaderTask  extends SafeTaskBase
{
		private String root;

	public void safeExecute() throws Exception
	{
		if (root == null || root.length() <= 0)
			throw new Exception("Не установлен параметр 'root'");

		log("Updating loan products in path + " + root);

		LoanGlobalLoader loader = new LoanGlobalLoader(root);
		LoanGlobal global = loader.load();
		if(global!=null)
		{
			LoanGlobalSynchronizer globalSynchronizer = new LoanGlobalSynchronizer(global);
			globalSynchronizer.update();
		}
		else
			log("Can't load loan products in path + " + root);	
		log("global loan processed");
	}

	public void setRoot(String root)
	{
		this.root = root;
	}
}
