package com.rssl.phizicgate.mobilebank;

import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.dataaccess.jdbc.JDBCUtils;
import com.rssl.phizic.gate.mobilebank.QuickServiceStatusCode;
import com.rssl.phizic.logging.messaging.System;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.rowset.CachedRowSet;

/**
 *
 * Реализует возможность получения статуса Быстрых сервисов.
 *
 * User: Balovtsev
 * Date: 24.05.2012
 * Time: 9:35:38
 */
class GetQuickServiceStatusJDBCAction extends JDBCActionBase<QuickServiceStatusCode>
{
	private final JDBCMessageLogger messageLogger = new JDBCMessageLogger(System.mbk);

	private final String phoneNumber;

	/**
	 * @param phoneNumber номер телефона для которого необходимо получить статус Быстрых сервисов.
	 */
	GetQuickServiceStatusJDBCAction(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	protected QuickServiceStatusCode doExecute(Connection con) throws SQLException, SystemException
	{
		messageLogger.startEntry("mb_WWW_GetPhoneQuickServices");
		messageLogger.setSQLStatement("call mb_WWW_GetPhoneQuickServices");
		messageLogger.addInputParam("PhoneNumber", phoneNumber);

		CachedRowSet rs = null;
		CallableStatement cstmt = con.prepareCall("{call mb_WWW_GetPhoneQuickServices(@PhoneNumber = ?)}");
		try
		{
			cstmt.setString(1, phoneNumber);

			// Поля резальтсета:
			// PhoneNumber varchar(20)
			// QuickServices_Status int
			rs = JDBCUtils.executeStoredProcedureQuery(cstmt);
			if (rs == null)
				throw new SystemException("ХП не вернула резальтсет");
			messageLogger.setResultSet(rs);

			if (rs.next())
			{
				int status = rs.getInt("QuickServices_Status");
				if (rs.wasNull())
					return QuickServiceStatusCode.QUICK_SERVICE_STATUS_UNKNOWN;
				return getStatusCode(status);
			}
			return QuickServiceStatusCode.QUICK_SERVICE_STATUS_UNKNOWN;
		}
		finally
		{
			messageLogger.finishEntry();
			if (cstmt != null)
				try { cstmt.close(); } catch (SQLException ignored) {}
			if (rs != null)
				try { rs.close(); } catch (SQLException ignored) {}
		}
	}

	private QuickServiceStatusCode getStatusCode(int code)
	{
		switch (code)
		{
			case 0:
				return QuickServiceStatusCode.QUICK_SERVICE_STATUS_OFF;

			case 1:
				return QuickServiceStatusCode.QUICK_SERVICE_STATUS_ON;

			/**
			 * в случае, когда пришел неизвестный код, возвращаем данный статус
			 */
			default:
				return QuickServiceStatusCode.QUICK_SERVICE_STATUS_UNKNOWN;
		}
	}

	@Override
	public boolean isConnectionLogEnabled()
	{
		return false;
	}
}
