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
 * ��������� ����������� ��������� ������� ������� ��������.
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
	 * @param phoneNumber ����� �������� ��� �������� ���������� �������� ������ ������� ��������.
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

			// ���� �����������:
			// PhoneNumber varchar(20)
			// QuickServices_Status int
			rs = JDBCUtils.executeStoredProcedureQuery(cstmt);
			if (rs == null)
				throw new SystemException("�� �� ������� ����������");
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
			 * � ������, ����� ������ ����������� ���, ���������� ������ ������
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
