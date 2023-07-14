package com.rssl.phizicgate.mobilebank;

import com.rssl.phizic.dataaccess.jdbc.JDBCAction;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.mobilebank.QuickServiceStatusCode;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.DateHelper;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Calendar;

/**
 *
 * Реализует возможность изменения статуса Быстрых сервисов.
 *
 * User: Balovtsev
 * Date: 24.05.2012
 * Time: 9:48:52
 */
public class SendQuickServiceStatusJDBCAction implements JDBCAction<QuickServiceStatusCode>
{
	private String phoneNumber;
	private QuickServiceStatusCode status;
	private static final String MB_PHONE_QUICK_SERVICES_SET_STATUS = "mb_Phone_QuickServices_SetStatus";
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);

	/**
	 * @param phoneNumber номер телефона для которого переключается статус
	 * @param status статус на который необходимо сменить текущий
	 */
	public SendQuickServiceStatusJDBCAction(String phoneNumber, QuickServiceStatusCode status)
	{
		this.status = status;
		this.phoneNumber = phoneNumber;
	}

	public QuickServiceStatusCode execute(Connection con) throws SQLException
	{
		Calendar timeStart = Calendar.getInstance();
		CallableStatement cstmt = con.prepareCall(
												  "{? = call "+ MB_PHONE_QUICK_SERVICES_SET_STATUS +"(" +
						                          "@strPhoneNumber = ?, " +
												  "@iExternalSystemID = 1,"+
												  "@QuickServices_NewStatus = ?)}"
												  );

		LogThreadContext.setProcName(MB_PHONE_QUICK_SERVICES_SET_STATUS);
		try
		{
			cstmt.registerOutParameter(1, Types.INTEGER);

			cstmt.setString(2,  phoneNumber );
			cstmt.setInt   (3,  status == QuickServiceStatusCode.QUICK_SERVICE_STATUS_ON ? 1 : 0);

			cstmt.execute();
			return returnStatusCode( cstmt.getInt(1) );
		}
		finally
		{
			if (cstmt != null)
			{
				try
				{
					cstmt.close();
				}
				catch (SQLException ignored) {}
			}
			try
			{
				StandInUtils.registerMBTimeOutEvent(DateHelper.diff(Calendar.getInstance(), timeStart));
			}
			catch (GateException e)
			{
				log.error(e);
			}
		}
	}

	private QuickServiceStatusCode returnStatusCode(int code)
	{
		switch (code)
		{
			case 0:
			{
				return status;
			}

			/**
			 * данный код возврата означает что Быстрые сервисы уже включены
			 */
			case 1204091010:
			{
				return QuickServiceStatusCode.QUICK_SERVICE_STATUS_ON;
			}

			/**
			 * данный код возврата означает что Быстрые сервисы уже выключены
			 */
			case 1209091020:
			{
				return QuickServiceStatusCode.QUICK_SERVICE_STATUS_OFF;
			}

			/**
			 * данный код возврата означает что превышен лимит нагрузки, либо это другая ошибка исправимая
			 * повторным запросом
			 */
			case 1204092000:
			{
				return QuickServiceStatusCode.QUICK_SERVICE_STATUS_REPEAT_QUERY;
			}

			/**
			 * ошибка не исправимая повторным запросом(например, ошибка во входных параметрах)
			 */
			default:
			{
				return QuickServiceStatusCode.QUICK_SERVICE_STATUS_UNKNOWN;
			}
		}
	}

	public boolean isConnectionLogEnabled()
	{
		return true;
	}
}
