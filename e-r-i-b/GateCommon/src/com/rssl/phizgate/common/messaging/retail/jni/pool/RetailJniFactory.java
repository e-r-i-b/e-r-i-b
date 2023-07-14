package com.rssl.phizgate.common.messaging.retail.jni.pool;

import com.rssl.api.retail.Retail;
import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.config.GateConnectionConfig;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.utils.libraries.LibraryLoader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.pool.BaseKeyedPoolableObjectFactory;

import java.io.File;
import java.io.IOException;

/**
 * @author Omeliyanchuk
 * @ created 24.11.2009
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������� ��� �������� retail jni
 */
public class RetailJniFactory extends BaseKeyedPoolableObjectFactory
{
	private static final Log log = LogFactory.getLog(Constants.LOG_MODULE_GATE.toValue());

	private static final String RETAIL_JNI = "retail_jni";

	private static volatile boolean nativeFactoryInitialized = false;
	private boolean isDebug;
	private boolean isRemote;

	/**
	 * ���������������� �������
	 * @param isRemote ��������� ����� ������
	 * @param isDebug ����� ������ jni
	 */
	public void init(Boolean isRemote, Boolean isDebug)
	{
		this.isRemote = isRemote;
		this.isDebug = isDebug;
		//��������� ���������� ������ ���� ���
		if(!nativeFactoryInitialized)
		{
			loadAndInitializeLibrary();
			nativeFactoryInitialized = true;
		}

	}

	/**
	 * ������������, ���� ������ ���� ��������.
	 */
	private synchronized void loadAndInitializeLibrary()
	{
		//���� ��� ���-�� ��������
		if(nativeFactoryInitialized)
			return;

		try
		{
			RetailJniFactory.log.debug("�������� ��������� ���������� ��� retail jni");

			ApplicationConfig appConfig = ApplicationConfig.getIt();
			//��������� ��������� �� ����� � ������� ��������������� ��� ����������
			String platformProperty=ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).getPlatformType();
			if (platformProperty.equals("i64"))
				Retail.Factory.setPlatform(Retail.NATIVE_PLATFORM.WINDOWS_I64);
			else if (platformProperty.equals("x64"))
					Retail.Factory.setPlatform(Retail.NATIVE_PLATFORM.WINDOWS_X64);
				else
					Retail.Factory.setPlatform(Retail.NATIVE_PLATFORM.WINDOWS_X86);

			File dir = LibraryLoader.saveResourceToTmp(Retail.Factory.mapLibraryName(), appConfig.getApplicationPrefixAdoptedToFileName() + File.separator + RetailJniFactory.RETAIL_JNI);
			LibraryLoader.saveResourceToTmp(Retail.Factory.mapExecuteName(), appConfig.getApplicationPrefixAdoptedToFileName() + File.separator + RetailJniFactory.RETAIL_JNI);

			Retail.Factory.setNativeLibraryPath(dir.getCanonicalPath());
			if (isDebug)
				Retail.Factory.setJabberLevel(Retail.JABBER_LEVEL.DEBUG);

			nativeFactoryInitialized = true;

			RetailJniFactory.log.debug("��������� ��������� ���������� ��� retail jni");

		}
		catch (IOException e)
		{
			throw new RuntimeException("������ ��� ���������� retail jni", e);
		}
	}

	/**
	 * ������� ������
	 * @param key ����, ��� ������� ����� ���� ���
	 * @return ��������� ������
	 * @throws Exception
	 */
	public Object makeObject(Object key) throws Exception
	{
		RetailJniFactory.log.debug("������ ��������� ����� ������� retail jni. ����:" + Thread.currentThread().getId());

		RetailWrapper wrapper = null;
		if (isRemote)
		{
			wrapper = new RemoteRetailWrapper(getHost(), getPort(), getVersion());
		}
		else
		{
			wrapper = new LocateRetailWrapper(getLocation(), getVersion());
		}

		RetailJniFactory.log.debug("��������� ��������� ����� ������� retail jni. ����:" + Thread.currentThread().getId());
		
		return wrapper;
	}

	/**
	 * ������� ������ � ���(���������� ���� �����������!!!!)
	 * @param key ����
	 * @param obj ������ ������� ���� �������
	 * @throws Exception
	 */
	public void destroyObject(Object key, Object obj) throws Exception
	{
		RetailJniFactory.log.debug("������� ������� retail jni. ����:" + Thread.currentThread().getId());
		Retail retail = (Retail)obj;
		retail.release();
		super.destroyObject(key, obj);
		RetailJniFactory.log.debug("������� ������� retail jni. ����:" + Thread.currentThread().getId());
	}

	/**
	 * ��������� ��������
	 * @param key - ����
	 * @param obj - ������� ��� ��������� �������
	 */
	public void activateObject(Object key, Object obj)
	{
		try
		{
			if (isRemote)
			{
				((RemoteRetailWrapper)obj).activate(getHost(), getPort(), getVersion());
			}
			else
			{
				((LocateRetailWrapper)obj).activate(getLocation(), getVersion());
			}
		}
		catch (Exception e)
		{
			log.error("��������� ������ ��� ��������� �������� ������: ", e);
		}
    }

	private String getHost()
	{
		return ConfigFactory.getConfig(GateConnectionConfig.class).getProperty("com.rssl.phizic.gate.rs-retail-v64.remote.host");
	}

	private int getPort()
	{
		return Integer.parseInt(ConfigFactory.getConfig(GateConnectionConfig.class).getProperty("com.rssl.phizic.gate.rs-retail-v64.remote.port"));
	}

	private String getLocation()
	{
		return ConfigFactory.getConfig(GateConnectionConfig.class).getProperty("com.rssl.phizic.gate.rs-retail-v64.location");
	}

	private int getVersion()
	{
		return Integer.parseInt(ConfigFactory.getConfig(GateConnectionConfig.class).getProperty("com.rssl.phizic.gate.rs-retail-v64.version"));
	}



}
