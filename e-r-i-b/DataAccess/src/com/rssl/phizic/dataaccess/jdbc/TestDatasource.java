package com.rssl.phizic.dataaccess.jdbc;

import junit.framework.TestCase;
import oracle.jdbc.pool.OracleDataSource;

import java.sql.SQLException;
import java.sql.Connection;

/**
 * @author Erkin
 * @ created 12.09.2012
 * @ $Author$
 * @ $Revision$
 */

public class TestDatasource extends TestCase
{
	public void test() throws SQLException
	{
		OracleDataSource ds = new oracle.jdbc.pool.OracleDataSource();

		ds.setURL("jdbc:oracle:thin:@anur:1521:PhizIC");
		ds.setUser("SBRF_118");
		ds.setPassword("SBRF_118");
		ds.setDatabaseName("PhizIC");
		ds.setPortNumber(1521);
		ds.setServerName("anur");
		ds.setConnectionCacheName("PhizICConCache");
		ds.setConnectionCachingEnabled(true);
		Connection con = ds.getConnection();
		con.close();
	}
}
