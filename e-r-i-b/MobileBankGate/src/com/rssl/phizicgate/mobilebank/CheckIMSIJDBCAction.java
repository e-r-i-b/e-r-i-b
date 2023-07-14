package com.rssl.phizicgate.mobilebank;

import com.rssl.phizgate.mobilebank.MobileBankConfig;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizgate.mobilebank.cache.techbreak.ImsiCheckResultCacheEntry;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Проверка IMSI без отправки СМС
 * @author Jatsky
 * @ created 16.01.15
 * @ $Author$
 * @ $Revision$
 */

public class CheckIMSIJDBCAction extends CachedJDBCAction<Integer, ImsiCheckResultCacheEntry>
{
	private static final String MB_IMSI_CHECK_IMSI = "mb_IMSI_CheckIMSI";

	private final String phone;
	private final String mbSystemId;

	public CheckIMSIJDBCAction(String phone, String mbSystemId) throws GateException
	{
		this.phone = phone;
		this.mbSystemId = mbSystemId;
	}

	public ImsiCheckResultCacheEntry doExecute(Connection con) throws SQLException, SystemException
	{
		CallableStatement cstmt =
				con.prepareCall("{? = call " + MB_IMSI_CHECK_IMSI + "(" +
						"@strPhoneNumber    = ?, " +
						"@iExternalSystemID = ?, " +
						"@iMessageID     = ?)}"
				);

		LogThreadContext.setProcName(MB_IMSI_CHECK_IMSI);
		try
		{
			cstmt.registerOutParameter(1, Types.INTEGER);
			cstmt.setString(2, phone);        //strPhoneNumber
			if (StringHelper.isEmpty(mbSystemId))
				cstmt.setInt(3, Integer.parseInt(ConfigFactory.getConfig(MobileBankConfig.class).getMbSystemId()));       //iExternalSystemID
			else
				cstmt.setInt(3, Integer.parseInt(mbSystemId));

			cstmt.registerOutParameter(4, Types.INTEGER); //iMobileOperator
			cstmt.execute();

			// анализ кода возврата
			int rc = cstmt.getInt(1);
			StandInUtils.registerMBOfflineEvent(rc);
			ImsiUtils.testReturnCode(rc);

			ImsiCheckResultCacheEntry result = new ImsiCheckResultCacheEntry();
			result.setPhoneNumber(phone);
			result.setMessageId(cstmt.getInt(4));
			return result;
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
	protected Integer processResult(ImsiCheckResultCacheEntry value) throws SystemException
	{
		return value.getMessageId();
	}

	@Override
	protected ImsiCheckResultCacheEntry getDbCached() throws SystemException
	{
		ImsiCheckResultCacheEntry lastMessage = cacheService.getImsiCheckResultCacheEntry(phone);
		return lastMessage;
	}

	@Override
	protected ImsiCheckResultCacheEntry getAppServerCached() throws SystemException
	{
		//отправка смс не берется из кеша без техперерывов
		return null;
	}
}

