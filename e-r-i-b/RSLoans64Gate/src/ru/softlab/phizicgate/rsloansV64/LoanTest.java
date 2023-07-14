package ru.softlab.phizicgate.rsloansV64;

import junit.framework.TestCase;
import oracle.jdbc.driver.OracleConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

/**
 * @author Evgrafov
 * @ created 23.08.2007
 * @ $Author: Evgrafov $
 * @ $Revision: 4776 $
 */

public abstract class LoanTest extends TestCase
{
	protected OracleConnection connection;

	@Override protected void setUp() throws Exception
	{
		super.setUp();
		this.connection = (OracleConnection) openConnection();
	}

	@Override protected void tearDown() throws Exception
	{
		if(connection != null)
			connection.close();

		super.tearDown();
	}

	private Connection openConnection() throws SQLException
	{
		DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		return DriverManager.getConnection("jdbc:oracle:thin:@octopus:1521:octopus", "mossib", "mossib");
	}
}