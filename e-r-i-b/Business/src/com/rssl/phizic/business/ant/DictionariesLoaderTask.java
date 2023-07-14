package com.rssl.phizic.business.ant;

import com.rssl.phizic.business.dictionaries.DictionariesSynchronizer;
import com.rssl.phizic.utils.annotation.PublicDefaultCreatable;
import com.rssl.phizic.utils.test.*;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.dataaccess.config.CSAAdminDataSourceConfig;

/** Created by IntelliJ IDEA.
 * User: Roshka
 * Date: 28.10.2005
 * Time: 18:30:56 */
@PublicDefaultCreatable
public class DictionariesLoaderTask extends SafeTaskBase implements ExternalDbSettingsTask
{
	private static Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private String login;
	private String password;
	private String initByParams;

	public String getInitByParams()
	{
		return initByParams;
	}

	public void setInitByParams(String initByParams)
	{
		this.initByParams = initByParams;
	}
	public String getLogin()
	{
		return login;
	}

	public void setLogin(String login)
	{
		this.login = login;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public void safeExecute() throws Exception
	{
		log.debug("Replicating dictionaries...");
		new DictionariesSynchronizer(GateSingleton.get().getFactory()).synchronizeAll();
		log.debug("Replicating processed.");
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
