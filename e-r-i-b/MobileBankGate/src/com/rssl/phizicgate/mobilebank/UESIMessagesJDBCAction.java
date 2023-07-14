package com.rssl.phizicgate.mobilebank;

import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.dataaccess.jdbc.JDBCUtils;
import com.rssl.phizic.gate.mobilebank.UESIMessage;
import com.rssl.phizic.logging.messaging.System;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.rowset.CachedRowSet;

/**
 * Получение сообщений унифицированного интерфейса МБК
 * @author Pankin
 * @ created 26.12.14
 * @ $Author$
 * @ $Revision$
 */
public class UESIMessagesJDBCAction extends JDBCActionBase<List<UESIMessage>>
{
	private static final String UESI_MESSAGE_TEXT = "UESI_MessageText";
	private static final String UESI_MESSAGE_ID = "UESI_MessageID";
	private static final String UESI_MESSAGE_TYPE = "UESI_MessageType";

	private final JDBCMessageLogger messageLogger = new JDBCMessageLogger(System.mbk);

	protected List<UESIMessage> doExecute(Connection con) throws SQLException, SystemException
	{
		messageLogger.startEntry("UESI_MessagesFromMBK");
		messageLogger.setSQLStatement("? = call UESI_MessagesFromMBK");
		//TODO параметр хп?
		messageLogger.addInputParam("ExternalSystemID", "1");

		CachedRowSet rs = null;
		CallableStatement cstmt = con.prepareCall("{? = call UESI_MessagesFromMBK(@ExternalSystemID = 1)}");
		try
		{
			cstmt.registerOutParameter(1, Types.INTEGER); // RetCode
			rs = JDBCUtils.executeStoredProcedureQuery(cstmt);
			messageLogger.setResultSet(rs);
			return processResult(rs, cstmt);
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

	private List<UESIMessage> processResult(ResultSet rs, CallableStatement cstmt) throws SQLException
	{
		int rc = cstmt.getInt(1);
		messageLogger.setResultCode(rc);
		StandInUtils.checkStandInAndThrow(rc);

		List<UESIMessage> result = new ArrayList<UESIMessage>();
		if (rs != null)
		{
			while (rs.next())
			{
				UESIMessageImpl message = new UESIMessageImpl();
				message.setMessageId(rs.getLong(UESI_MESSAGE_ID));
				message.setMessageType(rs.getString(UESI_MESSAGE_TYPE));
				message.setMessageText(rs.getString(UESI_MESSAGE_TEXT));
				result.add(message);
			}
		}
		return result;
	}

	@Override
	public boolean isConnectionLogEnabled()
	{
		return false;
	}
}
