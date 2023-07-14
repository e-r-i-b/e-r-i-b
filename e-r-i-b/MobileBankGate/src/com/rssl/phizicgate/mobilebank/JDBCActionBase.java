package com.rssl.phizicgate.mobilebank;

import com.rssl.phizgate.mobilebank.MobileBankConfig;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.jdbc.JDBCAction;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizic.utils.DateHelper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;

/**
 * Базовый класс для обычных (некешируемых) экшнов
 * @author Puzikov
 * @ created 30.10.14
 * @ $Author$
 * @ $Revision$
 */

public abstract class JDBCActionBase<T> implements JDBCAction<T>
{
	public final T execute(Connection con) throws SQLException, SystemException
	{
		Calendar timeStart = Calendar.getInstance();
		if (ConfigFactory.getConfig(MobileBankConfig.class).isSpCacheOn())
		{
			try
			{
				ExternalSystemHelper.check(ExternalSystemHelper.getMbkSystemCode());
			}
			catch (InactiveExternalSystemException e)
			{
				return processInactiveException(e);
			}
		}
		T result = doExecute(con);
		StandInUtils.registerMBTimeOutEvent(DateHelper.diff(Calendar.getInstance(), timeStart));
		return result;
	}

	protected abstract T doExecute(Connection con) throws SQLException, SystemException;;

	protected T processInactiveException(InactiveExternalSystemException e)
	{
		throw e;
	}

	public boolean isConnectionLogEnabled()
	{
		return true;
	}
}
