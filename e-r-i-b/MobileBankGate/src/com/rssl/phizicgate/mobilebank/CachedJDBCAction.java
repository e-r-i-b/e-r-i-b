package com.rssl.phizicgate.mobilebank;

import com.rssl.phizgate.mobilebank.MobileBankConfig;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.common.types.exceptions.StandInExternalSystemException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.jdbc.JDBCAction;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizgate.mobilebank.cache.techbreak.MbkCacheEntry;
import com.rssl.phizgate.mobilebank.cache.techbreak.MobileBankCacheService;
import com.rssl.phizic.utils.DateHelper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;

/**
 * Экшн, результаты которого кешируется в БД
 * @author Puzikov
 * @ created 17.09.14
 * @ $Author$
 * @ $Revision$
 */

public abstract class CachedJDBCAction<ResultType, CacheType extends MbkCacheEntry> implements JDBCAction<ResultType>
{
	protected static final MobileBankCacheService cacheService = new MobileBankCacheService();

	public final ResultType execute(Connection con) throws SQLException, SystemException
	{
		Calendar timeStart = Calendar.getInstance();
		if (!ConfigFactory.getConfig(MobileBankConfig.class).isSpCacheOn())
		{
			//Кеширование выключено в настройках
			CacheType result = doExecute(con);
			if (result == null)
			{
				StandInUtils.registerMBTimeOutEvent(DateHelper.diff(Calendar.getInstance(), timeStart));
				return null;
			}
			StandInUtils.registerMBTimeOutEvent(DateHelper.diff(Calendar.getInstance(), timeStart));
			return processResult(result);
		}

		try
		{
			ExternalSystemHelper.check(ExternalSystemHelper.getMbkSystemCode());
		}
		catch (InactiveExternalSystemException e)
		{
			//Получение из кеша на таблицах в случае техперерыва
			return processInactiveException(e);
		}

		//получение из кеша СП
		CacheType cached = getAppServerCached();
		if (cached != null)
		{
			return processResult(cached);
		}

		//Получение по выполнению ХП
		try
		{
			CacheType result = doExecute(con);
			if (result == null)
			{
				StandInUtils.registerMBTimeOutEvent(DateHelper.diff(Calendar.getInstance(), timeStart));
				return null;
			}

			StandInUtils.registerMBTimeOutEvent(DateHelper.diff(Calendar.getInstance(), timeStart));
			cacheService.saveCacheEntry(result);
			return processResult(result);
		}
		catch (StandInExternalSystemException e)
		{
			//Если МБК в режиме Stand-In, брать из кеша
			return processInactiveException(e);
		}
	}

	//при неактивности МБК (тех-перерыв или Stand-In) делается попытка найти в кеше.
	private ResultType processInactiveException(InactiveExternalSystemException e) throws SystemException
	{
		CacheType cached = getDbCached();
		if (cached == null)
		{
			throw e;
		}

		return processResult(cached);
	}

	protected abstract CacheType doExecute(Connection con) throws SQLException, SystemException;

	protected abstract CacheType getDbCached() throws SystemException;

	protected abstract CacheType getAppServerCached() throws SystemException;

	protected abstract ResultType processResult(CacheType value) throws SystemException;

	public boolean isConnectionLogEnabled()
	{
		return true;
	}
}
