package com.rssl.phizicgate.mobilebank;

import com.rssl.phizic.common.types.exceptions.SystemException;
import org.apache.commons.lang.StringUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author Gulov
 * @ created 10.09.13
 * @ $Author$
 * @ $Revision$
 */

/**
 * Экшен обновления статуса отключенных телефонов
 */
public class UpdaterStateDisconnectedPhoneJDBCAction extends JDBCActionBase<Void>
{
	private static final String SQL_TEXT =
			"UPDATE ERMB_DisconnectedPhones " +
			"WITH (ROWLOCK) SET ERIBState = 1 " +
			"WHERE DisconnectedPhoneID IN (%s)";

	private final List<Integer> phones;

	public UpdaterStateDisconnectedPhoneJDBCAction(List<Integer> phones)
	{
		this.phones = phones;
	}

	public Void doExecute(Connection con) throws SQLException, SystemException
	{
		Statement statement = con.createStatement();
		try
		{
			statement.executeUpdate(String.format(SQL_TEXT, StringUtils.join(phones, ", ")));
		}
		finally
		{
			if (statement != null)
				try
				{
					statement.close();
				}
				catch (SQLException ignored)
				{
				}
		}

		return null;
	}
}
