package com.rssl.phizic.business.ant.deposits;

import com.rssl.phizic.business.deposits.DepositGlobal;
import com.rssl.phizic.business.deposits.DepositGlobalSynchronizer;
import com.rssl.phizic.business.deposits.DepositGlobalLoader;
import com.rssl.phizic.dataaccess.config.CSAAdminDataSourceConfig;
import com.rssl.phizic.utils.test.JUnitDatabaseConfig;
import com.rssl.phizic.utils.test.JUnitSimpleDatabaseConfig;
import com.rssl.phizic.utils.test.SafeTaskBase;

/**
 * Ант-таск загрузки общих данных для депозитьв.
 * @author Roshka
 * @ created 10.05.2006
 * @ $Author$
 * @ $Revision$
 */

public class DepositGlobalLoaderTask extends SafeTaskBase
{
	private String root;

	public void safeExecute() throws Exception
	{
		if (root == null || root.length() <= 0)
			throw new Exception("Не установлен параметр 'root'");

		log("Updating deposit products in path + " + root);

		DepositGlobalLoader       loader = new DepositGlobalLoader(root);
		DepositGlobal             global = loader.load();
		DepositGlobalSynchronizer globalSynchronizer = new DepositGlobalSynchronizer(global);

		globalSynchronizer.update();
		log("global deposit processed");
	}

	public void setRoot(String root)
	{
		this.root = root;
	}

	protected JUnitDatabaseConfig getDatabaseConfig()
	{
		JUnitSimpleDatabaseConfig config = new JUnitSimpleDatabaseConfig();
		CSAAdminDataSourceConfig dbConfig = new CSAAdminDataSourceConfig();
		config.setDataSourceName(dbConfig.getDataSourceName());
		config.setURI(dbConfig.getURI());
		config.setDriver(dbConfig.getDriver());
		config.setLogin(dbConfig.getLogin());
		config.setPassword(dbConfig.getPassword());
		return config;
	}
}