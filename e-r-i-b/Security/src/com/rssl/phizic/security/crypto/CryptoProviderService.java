package com.rssl.phizic.security.crypto;

import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.startup.StartupService;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.naming.NamingHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.management.MBeanException;
import javax.management.modelmbean.RequiredModelMBean;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;

/**
 * @author Roshka
 * @ created 29.08.2006
 * @ $Author$
 * @ $Revision$
 */

public class CryptoProviderService extends RequiredModelMBean implements StartupService
{
	private static final String PROPERTIES_FILE = "iccs.properties";

	private static final String CRYPTO_PROVIDER_KEY = "com.rssl.iccs.crypto-provider";

	private static final Log log = LogFactory.getLog(Constants.LOG_MODULE_CORE.toValue());

	private static final String DEFAULT_CRYPTO_PROVIDER_KEY = "com.rssl.iccs.crypto-provider.default";

	private static final PropertyReader propertyReader = ConfigFactory.getReaderByFileName(PROPERTIES_FILE);

	///////////////////////////////////////////////////////////////////////////

	/**
	 * default ctor
	 * @throws MBeanException
	 */
	public CryptoProviderService() throws MBeanException
	{
		log.trace("CryptoProviderService instance created");
	}

	public boolean isInitialized()
	{
		return getCryptoProviderFactoryPool() != null;
	}

	public void start() throws CryptoProviderServiceException
	{
		log.trace("������ CryptoProviderService");

		InitialContext initialContext = null;
		try
		{
			CryptoProviderFactoryPool pool = createCryptoProviderFactoryPool();
			initialContext = NamingHelper.getInitialContext();
			NamingHelper.bind(initialContext, Environment.CRYPTO_PROVIDER_FACTORY_POOL, pool);
		}
		catch (NamingException ex)
		{
			log.error("���� ��� ������� CryptoProviderService", ex);
			throw new CryptoProviderServiceException(ex);
		}
		catch (CryptoProviderServiceException ex)
		{
			log.error("���� ��� ������� CryptoProviderService", ex);
			throw ex;
		}
		finally
		{
			try
			{
				if (initialContext != null)
					initialContext.close();
			}
			catch (NamingException ignored) {}
		}

		log.trace("CryptoProviderService ������� ���������");
	}

	public void stop()
	{
		log.trace("������� CryptoProviderService");

		CryptoProviderFactoryPool pool = getCryptoProviderFactoryPool();
		if (pool != null)
			pool.close();

		log.trace("CryptoProviderService ����������");
	}

	public void postRegister(Boolean registrationDone)
	{
		super.postRegister(registrationDone);

		try
		{
			start();
		}
		catch (CryptoProviderServiceException ex)
		{
			log.error(ex.getMessage(), ex);
		}
	}

	public void preDeregister()
	{
		super.postDeregister();

		stop();
	}

	///////////////////////////////////////////////////////////////////////////

	private CryptoProviderFactoryPool getCryptoProviderFactoryPool()
	{
		InitialContext initialContext = null;
		try
		{
			initialContext = NamingHelper.getInitialContext();
			return (CryptoProviderFactoryPool) initialContext.lookup(Environment.CRYPTO_PROVIDER_FACTORY_POOL);
		}
		catch (NameNotFoundException ignored)
		{
			return null;
		}
		catch (NamingException ex)
		{
			throw new RuntimeException(ex);
		}
		finally
		{
			try
			{
				if (initialContext != null)
					initialContext.close();
			}
			catch (NamingException ignored) {}
		}
	}

	private CryptoProviderFactoryPool createCryptoProviderFactoryPool()
			throws CryptoProviderServiceException
	{
		String defaultFactoryClassName = propertyReader.getProperty(DEFAULT_CRYPTO_PROVIDER_KEY);
		if (defaultFactoryClassName == null)
			throw new CryptoProviderServiceException("�� ������ ������� ������-����������� \"��-���������\". " +
					"������� ��������� " + DEFAULT_CRYPTO_PROVIDER_KEY + " � " + PROPERTIES_FILE);

		// 1. �������� ���
		CryptoProviderFactoryPool pool = new CryptoProviderFactoryPool();
		// 1.1 ������� ��-��������� ��� ����� � � ���
		CryptoProviderFactory defaultFactory = createCryptoProviderFactory(defaultFactoryClassName);
		pool.setDefaultFactory(defaultFactory);
		pool.putFactory(defaultFactory);
		// 1.2 ���������� ���� ������
		for (int i=1; ; i++) {
			String factoryClassName = propertyReader.getProperty(CRYPTO_PROVIDER_KEY + "." + i);
			if (StringHelper.isEmpty(factoryClassName))
				break;
			pool.putFactory(createCryptoProviderFactory(factoryClassName));
		}
		return pool;
	}

	private static CryptoProviderFactory createCryptoProviderFactory(String factoryClassName)
			throws CryptoProviderServiceException
	{
		if (StringHelper.isEmpty(factoryClassName))
			throw new IllegalArgumentException("�������� 'factoryClassName' �� ����� ���� ������");

		try
		{
			Class<?> factoryClass = Class.forName(factoryClassName);
			return (CryptoProviderFactory) factoryClass.newInstance();
		}
		catch (ClassNotFoundException ex)
		{
			log.error("�� ������ ����� " + factoryClassName, ex);
			throw new CryptoProviderServiceException(ex);
		}
		catch (IllegalAccessException ex)
		{
			log.error("���� ��� ������� ������� ��������� " + factoryClassName, ex);
			throw new CryptoProviderServiceException(ex);
		}
		catch (InstantiationException ex)
		{
			log.error("���� ��� ������� ������� ��������� " + factoryClassName, ex);
			throw new CryptoProviderServiceException(ex);
		}
	}
}