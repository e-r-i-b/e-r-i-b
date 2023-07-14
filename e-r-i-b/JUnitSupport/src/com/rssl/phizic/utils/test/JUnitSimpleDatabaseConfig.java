package com.rssl.phizic.utils.test;

/**
 * @author Omeliyanchuk
 * @ created 21.07.2008
 * @ $Author$
 * @ $Revision$
 */

public class JUnitSimpleDatabaseConfig implements JUnitDatabaseConfig
{
	private String dataSourceName;
	private String driver;
	private String login;
	private String password;
	private String URI;

	public String getDataSourceName()
	{
		return dataSourceName;
	}

	public void setDataSourceName(String dataSourceName)
	{
		this.dataSourceName = dataSourceName;
	}

	public String getDriver()
	{
		return driver;
	}

	public void setDriver(String driver)
	{
		this.driver = driver;
	}

	public String getLogin()
	{
		return login;
	}

	public void setLogin(String login)
	{
		this.login = login;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getURI()
	{
		return URI;
	}

	public void setURI(String URI)
	{
		this.URI = URI;
	}
}
