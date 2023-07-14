package com.rssl.phizic.business.ant;

import com.rssl.phizic.utils.files.FileHelper;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import java.io.*;
import java.util.Properties;

/**
 * @ author: filimonova
 * @ created: 14.04.2010
 * @ $Author$
 * @ $Revision$
 */
public class SwitchToStendDB extends Task
{
	private String root;
	private String dbUsername;
	private String dbPassword;
	private String config;
	private String isShadow;
	private String dbServerName;
	private String dbServerPort;
	private String dbServerDatabase;

	public void setRoot(String root)
	{
		this.root = root;
	}
	public void setDbUsername(String dbUsername)
	{
		this.dbUsername = dbUsername;
	}
	public void setDbPassword(String dbPassword)
	{
		this.dbPassword = dbPassword;
	}
	public void setConfig(String config)
	{
		this.config = config;
	}

	public void setIsShadow(String isShadow)
	{
		this.isShadow = isShadow;
	}

	public String getDbServerName()
	{
		return dbServerName;
	}

	public void setDbServerName(String dbServerName)
	{
		this.dbServerName = dbServerName;
	}

	public String getDbServerPort()
	{
		return dbServerPort;
	}

	public void setDbServerPort(String dbServerPort)
	{
		this.dbServerPort = dbServerPort;
	}

	public String getDbServerDatabase()
	{
		return dbServerDatabase;
	}

	public void setDbServerDatabase(String dbServerDatabase)
	{
		this.dbServerDatabase = dbServerDatabase;
	}

	public void execute() throws BuildException
	{
		String fileSeparator = File.separator;
		String confPath = root + fileSeparator +"AntBuilds" + fileSeparator + "configs" + fileSeparator;

		File confDir = new File(confPath);
		if (!confDir.isDirectory())
		{
			throw new IllegalArgumentException("Путь до папки PhizIC.image указан неверно");
		}

		File[] confFiles = confDir.listFiles(new FileFilter()
		{
			public boolean accept(File pathname)
			{
				return (pathname.getName().contains("properties") && pathname.getName().contains(config));
			}
		});

		if (confFiles == null)
		{
			return;
		}

		for ( File file : confFiles )
		{
			Properties properties = new Properties();
			try
			{
				FileHelper.loadProperties(file, properties);

				modifyProperty(properties, "dbserver.username", dbUsername);
				modifyProperty(properties, "dbserver.password", dbPassword);
				modifyProperty(properties, "dbserver.name",     dbServerName);
				modifyProperty(properties, "dbserver.port",     dbServerPort);
				modifyProperty(properties, "dbserver.database", dbServerDatabase);

				if (isShadow.equals("true"))
				{
					modifyProperty(properties, "dbserver.shadow.username", dbUsername+"_SHADOW");
					modifyProperty(properties, "dbserver.shadow.password", dbPassword+"_SHADOW");
				}

				FileHelper.storeProperties(file, properties);
			}
			catch (IOException e)
			{
				throw new BuildException(e);
			}
		}
	}
	
	private void modifyProperty(Properties properties, String fieldName, String value)
	{
		if (properties.containsKey(fieldName))
		{
			properties.setProperty(fieldName, value);
		}
	}
}
