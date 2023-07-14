package com.rssl.phizic.dataaccess;

import com.rssl.phizic.dataaccess.hibernate.HibernateUtil;
import com.rssl.phizic.dataaccess.query.template.SQLQueryTemplateLoader;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.util.NamingHelper;

import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import javax.naming.Reference;

/**
 * @author Evgrafov
 * @ created 27.06.2006
 * @ $Author: mihaylov $
 * @ $Revision: 68499 $
 */

public class HibernateService extends HibernateStartupServiceBase implements HibernateServiceMBean
{
	public boolean isInitialized()
	{
		try
		{
			InitialContext context = NamingHelper.getInitialContext(buildProperties());
			Object service = context.lookup(getJndiName());
			return service !=null && !(service instanceof Reference);     //TODO костыль, откатить после проверки fix 8.5.0.2 для WebSphere. Гладышев.
		}
		catch(NameNotFoundException ignored)
		{
			return false;
		}
		catch (NamingException e)
		{
			throw new RuntimeException(e);
		}
	}

	public void start() throws HibernateException
	{
		log.info("Запуск HibernateService " + getJndiName());
		try
		{
			setMapResources(null);
			readDatasourceConfig();
			loadHibernateCfg();
			addMappings();

			Configuration configuration = buildConfiguration();
			configuration.buildSessionFactory();

			String boundName = getJndiName();

			if(HibernateUtil.lookupSessionFactory(boundName)!=null)
				log.info("Хибернейт фабрика " + boundName + " загружена");
			else
				log.error("Хибернейт фабрика " + boundName + " не загружена");

			SQLQueryTemplateLoader loader = new SQLQueryTemplateLoader(configuration.getSqlResultSetMappings().keySet(),boundName);
			loader.load();
			startLogging();

			log.info("Стартовал HibernateService " + getJndiName());
		}
		catch (HibernateException e)
		{
			log.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			throw new HibernateException(e);
		}
	}

	public void stop()
	{
		log.info("Останов HibernateService " + getJndiName());
		String boundName = getJndiName();

		stopLogging();
		try
		{
			HibernateUtil.removeSessionFactoryFromCache(boundName);
			InitialContext context = NamingHelper.getInitialContext(buildProperties());
			((SessionFactory) context.lookup(boundName)).close();
			//context.unbind(boundName);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}

	public boolean getStarted()
	{
		return getJndiName() != null;
	}

	public void setStarted(boolean value)
	{
		if(value)
			start();
		else
			stop();
	}

	public String getTransactionStrategy()
	{
		return getProperty(Environment.TRANSACTION_STRATEGY);
	}

	public void setTransactionStrategy(String txnStrategy)
	{
		setProperty(Environment.TRANSACTION_STRATEGY, txnStrategy);
	}

	public String getUserTransactionName()
	{
		return getProperty(Environment.USER_TRANSACTION);
	}

	public void setUserTransactionName(String utName)
	{
		setProperty(Environment.USER_TRANSACTION, utName);
	}

	public String getTransactionManagerLookupStrategy()
	{
		return getProperty(Environment.TRANSACTION_MANAGER_STRATEGY);
	}

	public void setTransactionManagerLookupStrategy(String lkpStrategy)
	{
		setProperty(Environment.TRANSACTION_MANAGER_STRATEGY, lkpStrategy);
	}

	public void dropSchema()
	{
		new SchemaExport(buildConfiguration()).drop(false, true);
	}

	public void createSchema()
	{
		new SchemaExport(buildConfiguration()).create(false, true);
	}

	public String getJndiName()
	{
		return getProperty(Environment.SESSION_FACTORY_NAME);
	}

	public void setJndiName(String jndiName)
	{
		setProperty(Environment.SESSION_FACTORY_NAME, jndiName);
	}

	public String getUserName()
	{
		return getProperty(Environment.USER);
	}

	public void setUserName(String userName)
	{
		setProperty(Environment.USER, userName);
	}

	public String getPassword()
	{
		return getProperty(Environment.PASS);
	}

	public void setPassword(String password)
	{
		setProperty(Environment.PASS, password);
	}

	public void setFlushBeforeCompletionEnabled(String enabled)
	{
		setProperty(Environment.FLUSH_BEFORE_COMPLETION, enabled);
	}

	public String getFlushBeforeCompletionEnabled()
	{
		return getProperty(Environment.FLUSH_BEFORE_COMPLETION);
	}

	public void setAutoCloseSessionEnabled(String enabled)
	{
		setProperty(Environment.AUTO_CLOSE_SESSION, enabled);
	}

	public String getAutoCloseSessionEnabled()
	{
		return getProperty(Environment.AUTO_CLOSE_SESSION);
	}
}