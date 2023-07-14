package com.rssl.phizicgate.mobilebank;

import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.mobile.GetRegistrationType;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizgate.mobilebank.cache.techbreak.GetRegistrations2CacheEntry;
import com.rssl.phizgate.mobilebank.cache.techbreak.GetRegistrations3CacheEntry;
import com.rssl.phizgate.mobilebank.cache.techbreak.GetRegistrationsCacheEntry;
import com.rssl.phizgate.mobilebank.cache.techbreak.GetRegistrationsCacheEntryBase;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

/**
 * @author Erkin
 * @ created 20.04.2010
 * @ $Author$
 * @ $Revision$
 */
class GetRegistrationsJDBCAction extends CachedJDBCAction<String, GetRegistrationsCacheEntryBase>
{
	private static final String UNKNOWN_SP_TYPE = "Неизвестный тип получения регистраций МБК";
	private final String cardNumber;
	private GetRegistrationType getRegistrationType = GetRegistrationType.FIRST;

	/**
	 * Конструктор
	 * @param cardNumber - номер карты по которой получаем регистрации
	 * @param getRegistrationType - тип хранимой процедуры
	 */
	GetRegistrationsJDBCAction(String cardNumber, GetRegistrationType getRegistrationType)
	{
		this.cardNumber = cardNumber;
		this.getRegistrationType = getRegistrationType;
	}

	public GetRegistrationsCacheEntryBase doExecute(Connection con) throws SQLException, GateException
	{
		CallableStatement cstmt =
				con.prepareCall("{? = call " + getRegistrationType.getDecription() + " " +
						"(@strCardNumber = ?, " +
						"@strRetStr = ?) }");
		LogThreadContext.setProcName(getRegistrationType.getDecription());
		try {
			cstmt.registerOutParameter(1, Types.INTEGER);
			cstmt.setString(2, cardNumber);
			cstmt.registerOutParameter(3, Types.VARCHAR);
			LogThreadContext.setProcName(getRegistrationType.getDecription());
			cstmt.execute();

			// анализ кода возврата
			int rc = cstmt.getInt(1);
			StandInUtils.registerMBOfflineEvent(rc);
			testReturnCode(rc);

			GetRegistrationsCacheEntryBase result;
			switch (getRegistrationType)
			{
				case FIRST:
					result = new GetRegistrationsCacheEntry();
					break;
				case SECOND:
					result = new GetRegistrations2CacheEntry();
					break;
				case THIRD:
					result = new GetRegistrations3CacheEntry();
					break;
				default:
					throw new IllegalArgumentException(UNKNOWN_SP_TYPE);
			}
			result.setStrCardNumber(cardNumber);
			result.setStrRetStr(cstmt.getString(3));
			return result;
		} finally {
			if (cstmt != null)
				try { cstmt.close(); } catch (SQLException ignored) {}
		}
	}

	@Override
	protected String processResult(GetRegistrationsCacheEntryBase value) throws SystemException
	{
		return value.getStrRetStr();
	}

	@Override
	protected GetRegistrationsCacheEntryBase getDbCached() throws SystemException
	{
		switch (getRegistrationType)
		{
			case FIRST:
				return cacheService.getRegistrationsCacheEntry(cardNumber);
			case SECOND:
				return cacheService.getRegistrations2CacheEntry(cardNumber);
			case THIRD:
				return cacheService.getRegistrations3CacheEntry(cardNumber);
			default:
				throw new IllegalArgumentException(UNKNOWN_SP_TYPE);
		}
	}

	@Override
	protected GetRegistrationsCacheEntryBase getAppServerCached() throws SystemException
	{
		String cacheName;
		switch (getRegistrationType)
		{
			case FIRST:
				cacheName = GetRegistrationsCacheEntry.CACHE_NAME;
				break;
			case SECOND:
				cacheName = GetRegistrations2CacheEntry.CACHE_NAME;
				break;
			case THIRD:
				cacheName = GetRegistrations3CacheEntry.CACHE_NAME;
				break;
			default:
				throw new IllegalArgumentException(UNKNOWN_SP_TYPE);
		}
		Cache cache = CacheProvider.getCache(cacheName);
		Element element = cache.get(cardNumber);
		if (element == null)
		{
			return null;
		}
		else
		{
			return (GetRegistrationsCacheEntryBase) element.getObjectValue();
		}
	}

	private void testReturnCode(int rc) throws SQLException
	{
		StandInUtils.checkStandInAndThrow(rc);
		switch (rc) {
			case 0: // all correct
				break;

			case 50001:
				throw new SQLException("не указан или указан заведомо неверный номер карты");

			case -5:
				throw new SQLException("номер карты содержит не только цифры");

			default:
				if (rc < 0)
					throw new SQLException("ошибка в БД. Причина по коду не определяется (" + rc + ")");
				throw new SQLException("неизвестный код возврата (" + rc + ")");
		}
	}
}
