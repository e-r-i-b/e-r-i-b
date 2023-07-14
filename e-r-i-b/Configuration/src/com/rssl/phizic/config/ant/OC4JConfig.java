package com.rssl.phizic.config.ant;

import com.rssl.phizic.config.ConfigurationException;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Erkin
 * @ created 13.01.2013
 * @ $Author$
 * @ $Revision$
 */

class OC4JConfig
{
	private static final String OC4J_PROP_FILE = "oc4j.properties";

	private final String host;

	private final String port;

	private final String adminLogin;

	private final String adminPassword;

	OC4JConfig(File confDir) throws ConfigurationException
	{
		Properties props = new Properties();
		FileInputStream propStream = null;
		try
		{
			//noinspection IOResourceOpenedButNotSafelyClosed
			propStream = new FileInputStream(new File(confDir, OC4J_PROP_FILE));
			props.load(propStream);
		}
		catch (IOException e)
		{
			throw new ConfigurationException("Ошибка при чтении файла свойств", e);
		}
		finally
		{
			IOUtils.closeQuietly(propStream);
		}

		host = props.getProperty("oc4j.host");
		port = props.getProperty("oc4j.port");
		adminLogin = props.getProperty("oc4j.admin.login");
		adminPassword = props.getProperty("oc4j.admin.password");
	}

	String getHost()
	{
		return host;
	}

	String getPort()
	{
		return port;
	}

	String getAdminLogin()
	{
		return adminLogin;
	}

	String getAdminPassword()
	{
		return adminPassword;
	}
}
