package com.rssl.phizicgate.mobilebank;

import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.logging.LogThreadContext;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

/**
 * @author Erkin
 * @ created 21.04.2010
 * @ $Author$
 * @ $Revision$
 */
class UpdateSamplesJDBCAction extends JDBCActionBase<String>
{
	private final String cardNumber;

	private final String phoneNumber;

	private final String updateString;

	UpdateSamplesJDBCAction(String cardNumber, String phoneNumber, String updateString)
	{
		this.cardNumber = cardNumber;
		this.phoneNumber = phoneNumber;
		this.updateString = updateString;
	}

	public String doExecute(Connection con) throws SQLException, SystemException
	{
		LogThreadContext.setProcName("mb_WWW_UpdateSamples");

		CallableStatement cstmt = con.prepareCall("{? = call mb_WWW_UpdateSamples(?, ?, ?, ?, ?)}");
		try
		{
			cstmt.registerOutParameter(1, Types.INTEGER); // RetCode
			cstmt.setString(2, cardNumber.toString());
			cstmt.setString(3, phoneNumber);
			cstmt.setString(4, updateString);
			cstmt.registerOutParameter(5, Types.INTEGER); // iCounter (всегда имеет значение 1. Ќикакой информации не содержит)
			cstmt.registerOutParameter(6, Types.VARCHAR); // strErrorDescr
			cstmt.execute();
			// анализ кода возврата
			int rc = cstmt.getInt(1);
			StandInUtils.registerMBOfflineEvent(rc);
			StandInUtils.checkStandInAndThrow(rc);

			return cstmt.getString(6); // strErrorDescr
		}
		finally
		{
			if (cstmt != null)
				try { cstmt.close(); } catch (SQLException ignored) {}
		}
	}
}
