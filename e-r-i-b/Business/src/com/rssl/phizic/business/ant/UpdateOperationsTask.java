package com.rssl.phizic.business.ant;

import com.rssl.phizic.business.ant.services.groups.ServicesGroupLoader;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.test.OperationsLoader;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.test.JUnitConfig;
import com.rssl.phizic.utils.test.JUnitDatabaseConfig;
import com.rssl.phizic.utils.test.JUnitSimpleDatabaseConfig;
import com.rssl.phizic.utils.test.SafeTaskBase;

/**
 * @author Evgrafov
 * @ created 03.07.2006
 * @ $Author: krenev_a $
 * @ $Revision: 66104 $
 */

public class UpdateOperationsTask extends SafeTaskBase implements ExternalDbSettingsTask
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private String deleteUknownServices;
	private String deleteUknownOperations;
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

	public String getDeleteUknownOperations()
	{
		return deleteUknownOperations;
	}

	public void setDeleteUknownOperations(String deleteUknownOperations)
	{
		this.deleteUknownOperations = deleteUknownOperations;
	}

	public String getDeleteUknownServices()
	{
		return deleteUknownServices;
	}

	public void setDeleteUknownServices(String deleteUknownServices)
	{
		this.deleteUknownServices = deleteUknownServices;
	}

	public void safeExecute() throws Exception
	{
		OperationsLoader loader = new OperationsLoader();

		loader.setDeleteUnknownOperations(Boolean.valueOf(deleteUknownOperations));
		loader.setDeleteUnknownServices  (Boolean.valueOf(deleteUknownServices));

		log.debug("PARAMS");
		log.debug("deleteUknownServices   " + loader.isDeleteUnknownServices());
		log.debug("deleteUknownOperations " + loader.isDeleteUnknownOperations());
		log.debug("initByParams " + initByParams);
		if(StringHelper.isNotEmpty(login))
			log.debug("login " + login);
		if(StringHelper.isNotEmpty(password))
			log.debug("password " + password);
		loader.initAndLoad();

		log("Начинаем обновление группы сервисов");
		new ServicesGroupLoader().load();
		log("Закончили обновление группы сервисов");
	}

	protected JUnitDatabaseConfig getDatabaseConfig()
	{
		if(initByParams!=null && Boolean.valueOf(initByParams))
		{
			JUnitSimpleDatabaseConfig config = new JUnitSimpleDatabaseConfig();
			config.setLogin(login);
			config.setPassword(password);

			JUnitConfig dbConfig = new JUnitConfig();
			config.setDataSourceName(dbConfig.getDataSourceName());
			config.setURI(dbConfig.getURI());
			config.setDriver(dbConfig.getDriver());
			return config;
		}
		else return null;
	}
}