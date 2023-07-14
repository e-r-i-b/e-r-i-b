package com.rssl.phizicgate.mobilebank;

import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.gate.mobilebank.DisconnectedPhoneResult;
import com.rssl.phizic.gate.mobilebank.PhoneDisconnectionReason;
import com.rssl.phizic.utils.DateHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Gulov
 * @ created 09.09.13
 * @ $Author$
 * @ $Revision$
 */

/**
 * Ёкшен получени€ отключенных телефонов
 */
public class ProviderDisconnectedPhoneJDBCAction extends JDBCActionBase<List<DisconnectedPhoneResult>>
{
	private static final String SQL_TEXT =
			"SELECT TOP(%d) " +
				"dp.DisconnectedPhoneID, dp.PhoneNumber, " +
				"dp.Reason, dp.DisconnectedPhoneSource, " +
				"dp.CreatedAt, dp.ReceivedAt, " +
				"dp.ERIBState, dp.ERIBProcTime " +
			"FROM ERMB_DisconnectedPhones dp " +
			"WITH (ROWLOCK, READPAST) " +
			"WHERE dp.ERIBState = 0";

	private final int maxResults;

	public ProviderDisconnectedPhoneJDBCAction(int maxResults)
	{
		this.maxResults = maxResults;
	}

	public List<DisconnectedPhoneResult> doExecute(Connection con) throws SQLException, SystemException
	{
		/*
		 * ROWLOCK - ”казывает, что вместо блокировки страниц или таблиц примен€ютс€ блокировки строк.
		 * READPAST - ”казывает, что сервер не считывает строки и страницы, заблокированные другими транзакци€ми.
		 */
		Statement statement = con.createStatement();
		try
		{
			ResultSet rs = statement.executeQuery(String.format(SQL_TEXT, maxResults));
			return processResult(rs);
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
	}

	private List<DisconnectedPhoneResult> processResult(ResultSet rs) throws SQLException, SystemException
	{
		List<DisconnectedPhoneResult> result = new LinkedList<DisconnectedPhoneResult>();
		while (rs.next())
			result.add(fillPhone(rs));
		rs.close();
		return result;
	}

	private DisconnectedPhoneResult fillPhone(ResultSet rs) throws SQLException
	{
		DisconnectedPhoneResult result = new DisconnectedPhoneResult();
		result.setId(rs.getInt("DisconnectedPhoneID"));
		result.setPhoneNumber(rs.getString("PhoneNumber"));
		result.setReason(PhoneDisconnectionReason.fromCode(rs.getInt("Reason")));
		result.setDisconnectedPhoneSource(rs.getInt("DisconnectedPhoneSource"));
		result.setCreatedAt(DateHelper.toCalendar(rs.getDate("CreatedAt")));
		result.setReceivedAt(DateHelper.toCalendar(rs.getDate("ReceivedAt")));
		result.setState(rs.getBoolean("ERIBState"));
		result.setProcessedTime(DateHelper.toCalendar(rs.getDate("ERIBProcTime")));
		return result;
	}
}
