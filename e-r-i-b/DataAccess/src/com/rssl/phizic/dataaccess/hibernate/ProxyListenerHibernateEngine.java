package com.rssl.phizic.dataaccess.hibernate;

import com.rssl.phizic.dataaccess.HibernateLocalService;
import com.rssl.phizic.dataaccess.config.OfflineDocDataSourceConfig;
import com.rssl.phizic.dataaccess.config.SimpleDataSourceConfig;
import com.rssl.phizic.engine.EngineManager;

/**
 * @author Erkin
 * @ created 06.03.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * Движок Hibernate для прокси-листенеров
 * Любому прокси-листенеру база данных нужна для:
 *  - хранения инфы по обратному потоку
 */
public class ProxyListenerHibernateEngine extends HibernateEngineImpl
{
	private final HibernateLocalService offlineDocHibernateStarter = new HibernateLocalService();

	/**
	 * ctor
	 * @param manager - менеджер движков
	 */
	public ProxyListenerHibernateEngine(EngineManager manager)
	{
		super(manager);
	}

	@Override
	public void start()
	{
		super.start();

		offlineDocHibernateStarter.setInstanceName("OfflineDoc");
		offlineDocHibernateStarter.setDatasourceConfigClass(OfflineDocDataSourceConfig.class.getName());
		offlineDocHibernateStarter.setConfigResources("hibernate.cfg.xml");
		offlineDocHibernateStarter.addConfigResource("com/rssl/phizgate/common/payments/offline/offline-doc.cfg.xml");
		offlineDocHibernateStarter.start();
	}

	@Override
	public void stop()
	{
		offlineDocHibernateStarter.stop();

		super.stop();
	}
}
