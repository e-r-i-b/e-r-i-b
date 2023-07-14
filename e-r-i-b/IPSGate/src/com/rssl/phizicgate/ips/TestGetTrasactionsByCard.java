package com.rssl.phizicgate.ips;

import junit.framework.TestCase;
import oracle.jdbc.OracleTypes;
import oracle.jdbc.driver.OracleDriver;

import java.sql.*;

/**
 * @author Erkin
 * @ created 18.11.2011
 * @ $Author$
 * @ $Revision$
 */
public class TestGetTrasactionsByCard extends TestCase
{
	public void test() throws SQLException
	{
		DriverManager.registerDriver(new OracleDriver());
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@ACTINIA:1521:PHIZIC", "IPS", "IPS");
			ResultSet cursor = null;
			CallableStatement cstmt =
					con.prepareCall("{call getTransactionsByCards( " +
							"?, " + // pSeparator
							"?, " + // pCards
							"?, " + // pFromDate
							"?)}"   // pCursor
					);
		try
		{
			cstmt.setString(1, ",");
			cstmt.setString(2, "7010203183872724");
			cstmt.setTimestamp(3, new Timestamp(106, 11, 18, 0, 0, 0, 0));
			cstmt.registerOutParameter(4, OracleTypes.CURSOR);
			cstmt.execute();

			cursor = (ResultSet) cstmt.getObject(4);
			cursor.setFetchSize(1000);

			while (cursor.next())
				System.out.println(cursor.getRow());
		}
		finally
		{
			if (cursor != null)
				try { cursor.close(); } catch (SQLException ignored) {}

			if (cstmt != null)
				try { cstmt.close(); } catch (SQLException ignored) {}

			con.close();
		}
	}
}
