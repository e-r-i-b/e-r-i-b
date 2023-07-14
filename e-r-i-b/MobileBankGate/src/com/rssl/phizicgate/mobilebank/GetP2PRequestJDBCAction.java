package com.rssl.phizicgate.mobilebank;

import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.dataaccess.jdbc.JDBCUtils;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.mobilebank.P2PRequest;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.messaging.System;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.PhoneNumberFormat;

import java.sql.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.sql.rowset.CachedRowSet;

/**
 * @author Gulov
 * @ created 17.09.13
 * @ $Author$
 * @ $Revision$
 */

/**
 * Экшн для получения запросов МБК, по которым нужно определить карты клиентов (P2P)
 */
public class GetP2PRequestJDBCAction extends JDBCActionBase<List<P2PRequest>>
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);

	private final JDBCMessageLogger messageLogger = new JDBCMessageLogger(System.mbk);

	public List<P2PRequest> doExecute(Connection con) throws SQLException, SystemException
	{
		messageLogger.startEntry("ERMB_MobilePaymentCardRequests");
		messageLogger.setSQLStatement("? = call ERMB_MobilePaymentCardRequests");

		CachedRowSet rs = null;
		CallableStatement cstmt = con.prepareCall("{? = call ERMB_MobilePaymentCardRequests }");
		try
		{
			cstmt.registerOutParameter(1, Types.INTEGER); // RetCode
			rs = JDBCUtils.executeStoredProcedureQuery(cstmt);
			messageLogger.setResultSet(rs);
			return processResult(cstmt, rs);
		}
		catch (SQLException e)
		{
			if ("24000".equals(e.getSQLState()))  // проверка, что процедура не вернула выборку
				return Collections.emptyList();
			else
				throw e;
		}
		finally
		{
			messageLogger.finishEntry();
			if (rs != null)
				try { rs.close(); } catch (SQLException ignored) {}
			if (cstmt != null)
				try { cstmt.close(); } catch (SQLException ignored) {}
		}
	}

	private List<P2PRequest> processResult(CallableStatement cstmt, ResultSet rs) throws SQLException, GateException
	{
		int rc = cstmt.getInt(1);
		StandInUtils.registerMBOfflineEvent(rc);
		messageLogger.setResultCode(rc);

		StandInUtils.checkStandInAndThrow(rc);
		if (rc != 0)
			throw new SQLException("Хранимая процедура запросов из МБК на получение карт вернула ошибку");

		List<P2PRequest> result = new LinkedList<P2PRequest>();
		if (rs != null)
		{
			while (rs.next())
			{
				try
				{
					result.add(fillRequest(rs));
				}
				catch (NumberFormatException e)
				{
					log.error("ERMB_MobilePaymentCardRequests: неверный формат", e);
				}
			}
		}
		return result;
	}

	private P2PRequest fillRequest(ResultSet rs) throws SQLException
	{
		P2PRequest request = new P2PRequest();
		request.id       = rs.getInt("MobilePaymentCardRequestID");
		// Формат номера телефона: 10 цифр без семёрки.
		// См. BUG087532: [ЕРМБ] не работает p2p клиент МБК клиенту ЕРМБ
		request.phone    = PhoneNumberFormat.SIMPLE_NUMBER.parse(rs.getString("PhoneNumber"));
		request.tb       = rs.getInt("TerBank");
		request.branch   = rs.getInt("Branch");
		request.creation = DateHelper.toCalendar(rs.getDate("CreatedAt"));
		return request;
	}

	@Override
	public boolean isConnectionLogEnabled()
	{
		return false;
	}
}
