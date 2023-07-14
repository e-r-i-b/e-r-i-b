package com.rssl.phizicgate.mobilebank;

import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.dataaccess.jdbc.JDBCUtils;
import com.rssl.phizic.logging.messaging.System;
import com.sun.rowset.CachedRowSetImpl;

import java.sql.*;
import javax.sql.rowset.CachedRowSet;

/**
 * Экшн, вызывающий хранимую процедуру получения подключений из МБК
 * Возвращает резалтсет, если подключений нет  - null
 * @author Rtischeva
 * @ created 07.07.14
 * @ $Author$
 * @ $Revision$
 */
class GetRegistrationsForErmbJDBCAction extends JDBCActionBase<ResultSet>
{
	private static final int NO_REGISTRATIONS_RET_CODE = -1;

	private static final int ERROR_RET_CODE = -2;

	private final JDBCMessageLogger messageLogger = new JDBCMessageLogger(System.mbk);

	public ResultSet doExecute(Connection con) throws SQLException, SystemException
	{
		messageLogger.startEntry("mb_BATCH_ERMB_GetRegistrations");
		messageLogger.setSQLStatement("? = call mb_BATCH_ERMB_GetRegistrations");

		CallableStatement cstmt = con.prepareCall("{? = call mb_BATCH_ERMB_GetRegistrations}");
		try
		{
			cstmt.registerOutParameter(1, Types.INTEGER); // RetCode
			ResultSet rs = JDBCUtils.executeStoredProcedureQuery(cstmt);
			//noinspection NestedTryStatement
			try
			{
				int returnCode = cstmt.getInt(1);
				StandInUtils.registerMBOfflineEvent(returnCode);
				messageLogger.setResultCode(String.valueOf(returnCode));
				StandInUtils.checkStandInAndThrow(returnCode);

				// A. Сбой МБК
				if (returnCode == ERROR_RET_CODE)
					throw new SystemException("ХП mb_BATCH_ERMB_GetRegistrations вернула ошибку " + ERROR_RET_CODE);

				// B. В МБК новых связок нет
				if (returnCode == NO_REGISTRATIONS_RET_CODE || rs == null)
					return null;

				// C. В МБК есть связки
				CachedRowSet rowSet = new CachedRowSetImpl();
				rowSet.populate(rs);
				messageLogger.setResultSet(rowSet);
				return rowSet;
			}
			finally
			{
				if (rs != null)
					try { rs.close(); } catch (SQLException ignore) {}
			}
		}
		finally
		{
			if (cstmt != null)
				try { cstmt.close(); } catch (SQLException ignored) {}

			messageLogger.finishEntry();
		}
	}

	@Override
	public boolean isConnectionLogEnabled()
	{
		return false;
	}
}
