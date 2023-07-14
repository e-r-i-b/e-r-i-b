package com.rssl.phizicgate.mobilebank;

import com.rssl.phizgate.mobilebank.MobileBankConfig;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.mobilebank.ImsiCheckResult;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizgate.mobilebank.cache.techbreak.ImsiCheckResultCacheEntry;

import java.sql.*;

/**
 * Запрос результатов проверки IMSI
 * @author Jatsky
 * @ created 16.01.15
 * @ $Author$
 * @ $Revision$
 */

public class GetCheckIMSIResultExJDBCAction extends CachedJDBCAction<ImsiCheckResult, ImsiCheckResultCacheEntry>
{
	private Integer messageId;
	private String phone;
	private static final String MB_IMSI_GET_VALIDATION_RESULT = "mb_IMSI_GetValidationResultEx";

	public GetCheckIMSIResultExJDBCAction(Integer messageId, String phone)
	{
		this.messageId = messageId;
		this.phone = phone;
	}

	public ImsiCheckResultCacheEntry doExecute(Connection con) throws SQLException, GateException
	{
		CallableStatement cstmt = con.prepareCall("{? = call " + MB_IMSI_GET_VALIDATION_RESULT + "(" +
				"@iMessageID    = ?, " +
				"@iExternalSystemID     = ?)}"
		);
		LogThreadContext.setProcName(MB_IMSI_GET_VALIDATION_RESULT);
		try
		{
			cstmt.registerOutParameter(1, Types.INTEGER);
			cstmt.setInt(2, messageId);
			cstmt.setInt(3, Integer.parseInt(ConfigFactory.getConfig(MobileBankConfig.class).getMbSystemId()));       //iExternalSystemID

			cstmt.execute();

			int result = cstmt.getInt(1);
			StandInUtils.registerMBOfflineEvent(result);
			StandInUtils.checkStandInAndThrow(result);

			ImsiCheckResultCacheEntry entry = new ImsiCheckResultCacheEntry();
			entry.setPhoneNumber(phone);
			entry.setMessageId(messageId);
			if (result == 1306068882)
			{
				entry.setValidationResult(result);
				return entry;
			}
			ImsiCheckResult imsiResult = ImsiUtils.getImsiResult(result);
			if (imsiResult != ImsiCheckResult.imsi_check_ok && imsiResult != ImsiCheckResult.imsi_ok)
				throw new GateException("При получении результата проверки IMSI для сообщения с id= " + messageId + " получен код возврата " + result);

			entry.setValidationResult(result);
			return entry;
		}
		finally
		{
			if (cstmt != null)
				try
				{
					cstmt.close();
				}
				catch (SQLException ignored)
				{
				}
		}
	}

	@Override
	protected ImsiCheckResult processResult(ImsiCheckResultCacheEntry value) throws GateException
	{
		Integer validationResult = value.getValidationResult();
		ImsiCheckResult imsiResult = ImsiUtils.getImsiResult(validationResult);
		if (imsiResult == ImsiCheckResult.UNKNOWN)
			throw new GateException("Получен неизвестный код результата проверки IMSI = " + validationResult + " для сообщения с id= " + messageId);

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
}
