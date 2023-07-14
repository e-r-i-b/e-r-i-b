package com.rssl.phizic.utils.test;

import com.rssl.phizic.dataaccess.HibernateService;
import com.rssl.phizic.config.ConfigurationContext;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author Evgrafov
 * @ created 04.07.2006
 * @ $Author: komarov $
 * @ $Revision: 79898 $
 */

public class HibernateConfigurator implements JUnitConfigurator
{
	private boolean csaConfigure = false;
	private boolean csaAdminConfigure = false;
	private String SBRF_CONFIGURATION = "sbrf";

	public void configure()
	{
		configureHibernateService("hibernate/session-factory/PhizIC", "com.rssl.phizic.dataaccess.config.SimpleDataSourceConfig", getConfigs());
		configureHibernateService("hibernate/session-factory/PhizICLog", "com.rssl.phizic.dataaccess.config.LogDataSourceConfig", getConfigs());
		if(csaAdminConfigure)
			configureHibernateService("hibernate/session-factory/PhizICCSAAdmin", "com.rssl.phizic.dataaccess.config.CSAAdminDataSourceConfig", Collections.singletonList("csaadmin-business.cfg.xml"));
		if(csaConfigure)
			configureHibernateService("hibernate/session-factory/PhizICCSA", "com.rssl.phizic.dataaccess.config.CSADataSourceConfig", getCsaConfigs());

	}

	/**
	 * Выбрана конфигурация ЦСА
	 */
	public void csaConfigure(String name)
	{
		csaConfigure = "jdbc/CSA2".equals(name);
		csaAdminConfigure = "jdbc/CSAAdmin".equals(name);
	}

	private void configureHibernateService(String jndiName, String configClass, List<String> allConfigs)
	{
		HibernateService hibernateService = new HibernateService();
		hibernateService.setShowSqlEnabled("true");

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

		hibernateService.setJndiName(jndiName);
		hibernateService.setHibernateCfg("hibernate.cfg.xml");
		hibernateService.setDatasourceConfigClass(configClass);

		for (String config : allConfigs)
		{
			if (classLoader.getResourceAsStream(config) != null)
				hibernateService.addConfigResource(config);
		}

		hibernateService.start();
	}

	private List<String> getConfigs()
	{
		List<String> allConfigs = new ArrayList<String>(7);
		allConfigs.add("logging.cfg.xml");
		allConfigs.add("security.cfg.xml");
		allConfigs.add("business.cfg.xml");
		allConfigs.add("operations.cfg.xml");
		allConfigs.add("messaging.cfg.xml");
		allConfigs.add("gatemanager.cfg.xml");
		allConfigs.add("dataaccess.cfg.xml");
		allConfigs.add("com/rssl/phizic/locale/locale.cfg.xml");

		//todo заглушка, необходимо вынести конфигурацию hiberante в единый для тестов, тасков, серверов приложений файл
		String currentConfig = ConfigurationContext.getIntstance().getActiveConfiguration();
		if(SBRF_CONFIGURATION.equals(currentConfig))
		{
			allConfigs.add("com/rssl/phizic/business/ext/sbrf/business.cfg.xml");
			allConfigs.add("com/rssl/phizic/operations/ext/sbrf/operations.cfg.xml");
		}
		return allConfigs;
	}

	private List<String> getCsaConfigs()
	{
		List<String> allConfigs = new ArrayList<String>(2);
		allConfigs.add("com/rssl/phizic/business/ext/sbrf/csa/csa-business.cfg.xml");
		allConfigs.add("com/rssl/phizic/locale/locale.cfg.xml");
		return allConfigs;
	}
}