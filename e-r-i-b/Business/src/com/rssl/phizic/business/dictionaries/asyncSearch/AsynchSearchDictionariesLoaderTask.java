package com.rssl.phizic.business.dictionaries.asyncSearch;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.dataaccess.HibernateService;
import com.rssl.phizic.dataaccess.config.AsyncSearchDataSourceConfig;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.test.JUnitDatabaseConfig;
import com.rssl.phizic.utils.test.JUnitSimpleDatabaseConfig;
import com.rssl.phizic.utils.test.SafeTaskBase;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Arrays;
import java.util.List;
import javax.naming.NameNotFoundException;

/**
 * ���������� ������������ �� �������� ���� � ���� ��� ������������ ������
 *
 * @ author: Gololobov
 * @ created: 12.10.2012
 * @ $Author$
 * @ $Revision$
 */
public class AsynchSearchDictionariesLoaderTask extends SafeTaskBase
{
	private static final String[] configResourcesList = {"com/rssl/phizic/business/ext/sbrf/asyncsearch/business.cfg.xml",
														 "com/rssl/phizic/operations/ext/sbrf/asyncsearch/operations.cfg.xml"}; 
	private static final String jndiName = "hibernate/session-factory/PhizICAsyncSearch";
	private static final String datasourceConfigClass = "com.rssl.phizic.dataaccess.config.AsyncSearchDataSourceConfig";
	private static final String hibernateCfg = "hibernate.cfg.xml";

	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);
	private static final AsynchSearchDictionariesService asynchSearchDictionariesService = new AsynchSearchDictionariesService();

	private static final String ASYNC_SEARCH_INSTANCE_NAME = "AsyncSearch";

	private static final String ERROR_REPLICATE_TO_ASYNC_DB = "������ ��� ���������� ������ � ���� ������ ������.";
	private static final String ERROR_DELETE_RECORDS = "��� �������� ������� �� ������� %s ��������� ������";

	/**
	 * ��������� HibernateService ��� ���� ������ ������
	 */
	private void configureHibernateServiceForAsyncSearch()
	{
		ClassLoader oldClassLoader = Thread.currentThread().getContextClassLoader();
		try{
			Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());

			HibernateService hibernateService = new HibernateService();
			hibernateService.setShowSqlEnabled("true");

			hibernateService.setJndiName(jndiName);
			hibernateService.setHibernateCfg(hibernateCfg);
			hibernateService.setDatasourceConfigClass(datasourceConfigClass);

			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			List<String> allConfigs = Arrays.asList(configResourcesList);
			for (String config : allConfigs)
			{
				if (classLoader.getResourceAsStream(config) != null)
					hibernateService.addConfigResource(config);
			}
			hibernateService.start();
		}
		finally
		{
			Thread.currentThread().setContextClassLoader(oldClassLoader);
		}
	}

	public void safeExecute() throws Exception
	{
		Transaction transaction = null;
		Session session = null;
		try
		{
			try
			{
				session = getSession();
				transaction = session.beginTransaction();
				//�������� ���� ����������� �����
				deleteRecordsByClass(session, AsynchSearchServiceProvider.class, "����������� (service_providers_async_search)");
				//���������� ����������� ����������� �����
				asynchSearchDictionariesService.replicateServiceProviders(session);
				transaction.commit();
			}
			catch (Exception e)
			{
				//���������� ����������
				if (transaction != null && transaction.isActive())
					transaction.rollback();
				log.error(ERROR_REPLICATE_TO_ASYNC_DB, e);
			}
		}
		finally {
			//��������� ������
			if (session != null)
				try{
					session.flush();
				}
				finally {
					session.close();
					session = null;
				}
		}
	}

	private void deleteRecordsByClass(Session session, Class clazz, String errorString) throws BusinessException
	{
		if (session != null)
		{
			try
			{
				Query query = session.createQuery("delete "+clazz.getSimpleName());
				int recCount = query.executeUpdate();
				log.info(String.format("������� ������� '"+clazz.getSimpleName()+"' %s", String.valueOf(recCount)));
			}
			catch (Exception e)
			{
				throw new BusinessException(String.format(ERROR_DELETE_RECORDS, errorString),e);
			}
		}
	}

	/**
	 * �������� ������ ��� �� ������ ������
	 * @return
	 */
	private Session getSession()
	{
		try
		{   //��������� HibernateService ��� ���� ������ ������
			configureHibernateServiceForAsyncSearch();
			//noinspection HibernateResourceOpenedButNotSafelyClosed
			return HibernateExecutor.getSessionFactory(ASYNC_SEARCH_INSTANCE_NAME).openSession();
		}
		catch (NameNotFoundException e)
		{
			throw new RuntimeException(e);
		}
	}

	protected JUnitDatabaseConfig getDatabaseConfig()
	{
		JUnitSimpleDatabaseConfig config = new JUnitSimpleDatabaseConfig();
		AsyncSearchDataSourceConfig dbConfig = new AsyncSearchDataSourceConfig();
		config.setDataSourceName(dbConfig.getDataSourceName());
		config.setURI(dbConfig.getURI());
		config.setDriver(dbConfig.getDriver());
		config.setLogin(dbConfig.getLogin());
		config.setPassword(dbConfig.getPassword());
		return config;
	}
}
