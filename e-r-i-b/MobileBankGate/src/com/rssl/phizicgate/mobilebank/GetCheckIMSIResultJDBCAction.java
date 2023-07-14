package com.rssl.phizicgate.mobilebank;

import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.dataaccess.jdbc.JDBCUtils;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.mobilebank.ImsiCheckResult;
import com.rssl.phizic.logging.messaging.System;
import com.rssl.phizgate.mobilebank.cache.techbreak.ImsiCheckResultCacheEntry;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import javax.sql.rowset.CachedRowSet;

import static com.rssl.phizicgate.mobilebank.MBKConstants.MBK_PHONE_NUMBER_FORMAT;

/**
 * @author gladishev
 * @ created 18.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class GetCheckIMSIResultJDBCAction extends CachedJDBCAction<ImsiCheckResult, ImsiCheckResultCacheEntry>
{
	private Integer messageId;
	private String phone;
	private final JDBCMessageLogger messageLogger = new JDBCMessageLogger(System.mbk);

	public GetCheckIMSIResultJDBCAction(Integer messageId, String phone)
	{
		this.messageId = messageId;
		this.phone = phone;
	}

	public ImsiCheckResultCacheEntry doExecute(Connection con) throws SQLException, GateException
	{
		messageLogger.startEntry("mb_IMSI_GetValidationResult");
		messageLogger.setSQLStatement("? = call mb_IMSI_GetValidationResult");

		CachedRowSet rs = null;
		CallableStatement cstmt = con.prepareCall("{? = call mb_IMSI_GetValidationResult( @iMessageID = ? )}");
		try
		{
			cstmt.registerOutParameter(1, Types.INTEGER);
			cstmt.setInt(2, messageId);
			messageLogger.addInputParam("iMessageID", messageId);

			rs = JDBCUtils.executeStoredProcedureQuery(cstmt);
			int result = cstmt.getInt(1);
			messageLogger.setResultCode(result);
			messageLogger.setResultSet(rs);
			StandInUtils.registerMBOfflineEvent(result);
			StandInUtils.checkStandInAndThrow(result);

			return analyzeData(rs, result);
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

	private ImsiCheckResultCacheEntry analyzeData(ResultSet rs, int result) throws SQLException, GateException
	{
		Map<String, Integer> phoneIMSICheckResults = new HashMap<String, Integer>();
		if (rs != null)
		{
			while (rs.next()) {
				phoneIMSICheckResults.put(rs.getString(1), rs.getInt(2));
			}
		}

		ImsiCheckResultCacheEntry entry = new ImsiCheckResultCacheEntry();
		entry.setPhoneNumber(phone);
		entry.setMessageId(messageId);
		if (result == 1202084009)
		{
			entry.setValidationResult(result);
			return entry;
		}

		if (result != 0)
			throw new GateException("При получении результата проверки IMSI для сообщения с id= " + messageId  + " получен код возврата " + result);

		Integer phoneIMSICheckResult = phoneIMSICheckResults.get(MBK_PHONE_NUMBER_FORMAT.translate(phone));
		if (phoneIMSICheckResult == null || phoneIMSICheckResult == 0)
			throw new GateException("Не удалось получить результат проверки IMSI для сообщения с id= " + messageId);

		entry.setValidationResult(phoneIMSICheckResult);
		return entry;
	}

	@Override
	protected ImsiCheckResult processResult(ImsiCheckResultCacheEntry value) throws GateException
	{
		Integer validationResult = value.getValidationResult();
		ImsiCheckResult imsiResult = SendSmsUtils.getImsiResult(validationResult);
		if (imsiResult == ImsiCheckResult.UNKNOWN)
			throw new GateException("Получен неизвестный код результата проверки IMSI = " + validationResult  + " для сообщения с id= " + messageId);

		return imsiResult;
	}

	@Override
	protected ImsiCheckResultCacheEntry getDbCached() throws SystemException
	{
		ImsiCheckResultCacheEntry cacheEntry = cacheService.getImsiCheckResultCacheEntry(phone);
		if (cacheEntry.getValidationResult() == null)
			return null;
		if (!messageId.equals(cacheEntry.getMessageId()))
			return null;

		return cacheEntry;
	}

	@Override
	protected ImsiCheckResultCacheEntry getAppServerCached() throws SystemException
	{
		//отправка смс не берется из кеша без техперерывов
		return null;
	}

	@Override
	public boolean isConnectionLogEnabled()
	{
		return false;
	}
}
