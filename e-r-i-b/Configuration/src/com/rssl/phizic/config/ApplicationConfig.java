package com.rssl.phizic.config;

import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.config.build.BuildContextConfig;
import com.rssl.phizic.utils.StringHelper;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Omeliyanchuk
 * @ created 08.02.2008
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ ��� ��������� ��������
 */
public class ApplicationConfig
{
	/**
	 * ��������� ���������� � ��������� ������� ����������
	 */
	private static final String PHIZIC_APPLICATION_SERVER_PREFIX = "phizic.as.prefix";

	private static final PropertyReader propertyReader = new ResourcePropertyReader("iccs.properties");
	private static final String APPLICATION_INSTANCE = "com.rssl.iccs.application.instance.prefix";
	private static final String REPLACEMENT = "_";
	private static final String TARGET1 = ":";
	private static final String TARGET2 = "\\";

	private static String applicationInstanceName = null;
	private static InetAddress applicationHost = null;
	private static final Object lock = new Object();
	private static final Object lock_host = new Object();
	private ApplicationInfo applicationInfo;

	private static final ApplicationConfig thiS = new ApplicationConfig();

	public static ApplicationConfig getIt()
	{
		return thiS;
	}

	private ApplicationConfig()
	{
		this.applicationInfo = new ApplicationInfo();
	}

	/**
	 * ������� ����� ���������� ����������
	 * @return
	 */
	public String getApplicationPrefix()
	{
		return propertyReader.getProperty(APPLICATION_INSTANCE);
	}

	/**
	 * ������� ����� ���������� ����������
	 * @return
	 */
	public String getApplicationPrefixAdoptedToFileName()
	{
		String prefix = getApplicationPrefix();
		return prefix.replace(TARGET2, REPLACEMENT).replace(TARGET1, REPLACEMENT);
	}

	/**
	 * @return ��������� ���������� ����������: ���� : ����
	 */
	public String getApplicationInstanceName()
	{
		String instanceName = applicationInstanceName;
		if (StringHelper.isEmpty(instanceName))
		{
			synchronized (lock)
			{
				if (applicationInstanceName == null)
				{
					applicationInstanceName = System.getProperty(PHIZIC_APPLICATION_SERVER_PREFIX);
					if (StringHelper.isEmpty(applicationInstanceName))
					{
						try
						{
							String host = getApplicationHost().getHostName();

							BuildContextConfig buildConfig = ConfigFactory.getConfig(BuildContextConfig.class);
							applicationInstanceName = (host + ":" + buildConfig.getApplicationPort()).toLowerCase();
						}
						catch (UnknownHostException ignored)
						{
							applicationInstanceName = "";
						}
						catch (RuntimeException ignored)
						{
							applicationInstanceName = "";
						}
						catch (IOException ignored)
						{
							applicationInstanceName = "";
						}
					}
				}
				instanceName = applicationInstanceName;
			}
		}
		return instanceName;
	}

	/**
	 * ���� ����� ���������� ����������
	 * @return
	 */
	public int getApplicationPort()
	{
		BuildContextConfig buildConfig = ConfigFactory.getConfig(BuildContextConfig.class);
		return Integer.valueOf(buildConfig.getApplicationPort());
	}

	public Application getLoginContextApplication()
	{
		return ApplicationInfo.getCurrentApplication();
	}

	public ApplicationInfo getApplicationInfo()
	{
		return applicationInfo;
	}

	public InetAddress getApplicationHost() throws UnknownHostException
	{
		if (applicationHost == null)
		{
		   synchronized (lock_host)
		   {
			   applicationHost = InetAddress.getLocalHost();
		   }
		}
		return applicationHost;
	}
}
