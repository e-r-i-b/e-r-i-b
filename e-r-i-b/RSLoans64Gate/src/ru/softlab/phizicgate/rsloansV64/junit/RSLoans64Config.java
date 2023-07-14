package ru.softlab.phizicgate.rsloansV64.junit;

import com.rssl.phizic.config.*;

/**
 * @author Omeliyanchuk
 * @ created 10.12.2007
 * @ $Author$
 * @ $Revision$
 */

/**
 *  
 */
public class RSLoans64Config extends Config
{
	private static String RS_LOANS_DATASOURCE_NAME = "com.rssl.iccs.loans.datasourceName";
	private static String RS_LOANS_DB_DRIVER       = "com.rssl.iccs.loans.jdbc.driver";
	private static String RS_LOANS_SERVER_NAME     = "com.rssl.iccs.loans.dbserver.name";
	private static String RS_LOANS_SERVER_PORT     = "com.rssl.iccs.loans.dbserver.port";
	private static String RS_LOANS_DB              = "com.rssl.iccs.loans.database";
	private static String RS_LOANS_DB_LOGIN        = "com.rssl.iccs.loans.jdbc.login";
	private static String RS_LOANS_DB_PASSWORD     = "com.rssl.iccs.loans.jdbc.password";

	private String datasorceName;
	private String driver;
	private String login;
	private String password;
	private String serverName;
	private String serverPort;
	private String db;

	public RSLoans64Config(PropertyReader reader)
	{
		super(reader);
	}

	public void doRefresh() throws ConfigurationException
	{
		datasorceName = getProperty(RS_LOANS_DATASOURCE_NAME);
		driver = getProperty(RS_LOANS_DB_DRIVER);
		serverName = getProperty(RS_LOANS_SERVER_NAME);
		serverPort = getProperty(RS_LOANS_SERVER_PORT);
		db = getProperty(RS_LOANS_DB);
		login = getProperty(RS_LOANS_DB_LOGIN);
		password = getProperty(RS_LOANS_DB_PASSWORD);
	}

	public String getDatasorceName()
	{
		return datasorceName;
	}

	public String getDriver()
	{
		return driver;
	}

	public String getLogin()
	{
		return login;
	}

	public String getPassword()
	{
		return password;
	}

	public String getServerName()
	{
		return serverName;
	}

	public String getServerPort()
	{
		return serverPort;
	}

	public String getDb()
	{
		return db;
	}
}

