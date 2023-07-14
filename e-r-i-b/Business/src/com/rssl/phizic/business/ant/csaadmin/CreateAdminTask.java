package com.rssl.phizic.business.ant.csaadmin;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ant.csaadmin.mapping.CSAAdminAccessScheme;
import com.rssl.phizic.business.ant.csaadmin.mapping.CSAAdminAllowedDepartment;
import com.rssl.phizic.business.ant.csaadmin.mapping.CSAAdminLogin;
import com.rssl.phizic.business.schemes.AccessScheme;
import com.rssl.phizic.business.schemes.AccessSchemesConfig;
import com.rssl.phizic.business.schemes.DbAccessSchemesConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.HibernateService;
import com.rssl.phizic.dataaccess.config.CSAAdminDataSourceConfig;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.security.config.SecurityConfig;
import com.rssl.phizic.utils.test.JNDIUnitTestHelper;
import com.rssl.phizic.utils.test.JUnitSimpleDatabaseConfig;
import com.rssl.phizic.utils.test.SafeTaskBase;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.List;
import javax.naming.NamingException;

/**
 * @author akrenev
 * @ created 10.12.2013
 * @ $Author$
 * @ $Revision$
 *
 * Таск создания дефолтного админа в ЦСА Админ
 */

public class CreateAdminTask extends SafeTaskBase
{
	private final static String DEFAULT_CSA_HIBERNATE_JNDI        = "hibernate/session-factory/PhizICCSAAdmin";
	private final static String DEFAULT_CSA_DATASOURCE_CONF_CLASS = "com.rssl.phizic.dataaccess.config.CSAAdminDataSourceConfig";
	public static final String MAPPING_SOURCE                     = "com/rssl/phizic/business/ant/csaadmin/mapping/csaadmin-hibernate-mapping.cfg.xml";
	public static final String DB_INSTANCE_NAME                   = "CSAAdmin";

	private SecurityConfig initialize() throws Exception
	{
		return ConfigFactory.getConfig(SecurityConfig.class);
	}

	@Override
	public void safeExecute() throws Exception
	{
		SecurityConfig securityConfig = initialize();
		String loginAdmin = securityConfig.getDefaultAdminName();

		if ( loginAdmin == null )
			throw new BusinessException("Не задано USER ID для встроенной учетной записи администратора");

		AccessSchemesConfig schemesConfig = ConfigFactory.getConfig(DbAccessSchemesConfig.class);
		AccessScheme scheme = schemesConfig.getBuildinAdminAccessScheme();
		if(scheme == null)
			throw new BusinessException("Не найдена схема доступа для встроенной учетной записи администратора");

		createAdmin(loginAdmin, scheme);
	}

	private void createAdmin(String loginAdmin, AccessScheme scheme) throws Exception
	{
		Session session     = null;
		Transaction transaction = null;

		try
		{
			session = getSession();
			transaction = session.getTransaction();
			transaction.begin();

			if (isExistAdmin(loginAdmin, session))
				return;

			CSAAdminAccessScheme accessScheme = getAccessScheme(scheme, session);
			if (accessScheme == null)
			{
				accessScheme = new CSAAdminAccessScheme(scheme);
				session.save(accessScheme);
			}

			CSAAdminLogin login = new CSAAdminLogin(loginAdmin, accessScheme);
			session.save(login);

			session.save(new CSAAdminAllowedDepartment(login));

			transaction.commit();
		}
		catch (Exception e)
		{
			if (transaction != null && transaction.isActive())
				transaction.rollback();

			throw e;
		}
		finally
		{
			if (session != null)
			{
				try
				{
					session.flush();
				}
				finally
				{
					session.close();
				}
			}
		}
	}

	private CSAAdminAccessScheme getAccessScheme(AccessScheme scheme, Session session)
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(CSAAdminAccessScheme.class);
		criteria.add(Expression.eq("name", scheme.getName()));
		criteria.add(Expression.eq("category", scheme.getCategory()));
		Criteria executableCriteria = criteria.getExecutableCriteria(session);
		List<CSAAdminAccessScheme> schemes = executableCriteria.list();
		return CollectionUtils.isNotEmpty(schemes)? schemes.get(0): null;
	}

	private boolean isExistAdmin(String loginAdmin, Session session)
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(CSAAdminLogin.class);
		criteria.add(Expression.eq("name", loginAdmin));
		Criteria executableCriteria = criteria.getExecutableCriteria(session);
		return CollectionUtils.isNotEmpty(executableCriteria.list());
	}

	private void initializeDataSource() throws NamingException
	{
		JUnitSimpleDatabaseConfig config = new JUnitSimpleDatabaseConfig();
		CSAAdminDataSourceConfig dbConfig = new CSAAdminDataSourceConfig();
		config.setDataSourceName(dbConfig.getDataSourceName());
		config.setURI(dbConfig.getURI());
		config.setDriver(dbConfig.getDriver());
		config.setLogin(dbConfig.getLogin());
		config.setPassword(dbConfig.getPassword());
		JNDIUnitTestHelper.createJUnitDataSource(config);
	}

	protected HibernateService getHibernateService()
	{
		HibernateService hibernateService = new HibernateService();
		hibernateService.setJndiName(DEFAULT_CSA_HIBERNATE_JNDI);
		hibernateService.setShowSqlEnabled(String.valueOf(true));
		hibernateService.setDatasourceConfigClass(DEFAULT_CSA_DATASOURCE_CONF_CLASS);
		return hibernateService;
	}

	private void startCSAHibernateService() throws HibernateException
	{
		HibernateService hibernateService = getHibernateService();
		hibernateService.addConfigResource(MAPPING_SOURCE);
		hibernateService.start();
	}

	private Session getSession() throws BusinessException
	{
		try
		{
			initializeDataSource();
			startCSAHibernateService();
			return HibernateExecutor.getSessionFactory(DB_INSTANCE_NAME).openSession();
		}
		catch (Exception e)
		{
			throw new BusinessException("Ошибка при старте сервиса hibernate.", e);
		}
	}

	public Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}
}
