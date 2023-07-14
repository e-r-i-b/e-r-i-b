package com.rssl.phizicgate.mobilebank;

import com.rssl.phizgate.mobilebank.cache.techbreak.GetCardsByPhoneCacheEntry;
import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.dataaccess.jdbc.JDBCUtils;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.apache.commons.lang.StringUtils;
import java.sql.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.sql.rowset.CachedRowSet;

/**
 * @author tisov
 * @ created 29.05.15
 * @ $Author$
 * @ $Revision$
 * Базовый класс получения карт по номеру телефона через хранимые процедуры
 */
public abstract class GetCardNumbersByPhoneJDBCActionBase  extends CachedJDBCAction<Set<String>, GetCardsByPhoneCacheEntry>
{
	private static final int INCORRECT_PHONE_NUMBER = 1307190001;
	private static final int MBK_NOT_CONNECT = 1307190002;
	private static final int ERR = 1307199999;
	private static final char RESULT_SET_DELIMITER = '|';

	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);
	private final JDBCMessageLogger messageLogger = new JDBCMessageLogger(com.rssl.phizic.logging.messaging.System.mbk);

	private String phone;

	protected GetCardNumbersByPhoneJDBCActionBase(String phone)
	{
		this.phone = phone;
	}

	protected abstract String getProcedureName();

	public GetCardsByPhoneCacheEntry doExecute(Connection con) throws SQLException, GateException
	{
		messageLogger.startEntry(getProcedureName());
		messageLogger.setSQLStatement("? = call " + getProcedureName());
		messageLogger.addInputParam("PhoneNumber", phone);

		CachedRowSet rs = null;
		CallableStatement cstmt = con.prepareCall("{? = call " + getProcedureName() + " (@PhoneNumber = ?)}");
		try
		{
			cstmt.registerOutParameter(1, Types.INTEGER); // RetCode
			cstmt.setString(2, phone);

			rs = JDBCUtils.executeStoredProcedureQuery(cstmt);
			messageLogger.setResultSet(rs);

			return processResult(cstmt, rs);
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

	@Override
	protected Set<String> processResult(GetCardsByPhoneCacheEntry value)
	{
		String resultSet = value.getResultSet();
		if (StringUtils.isEmpty(resultSet))
		{
			return Collections.emptySet();
		}
		String[] split = StringUtils.split(resultSet, RESULT_SET_DELIMITER);

		return new HashSet<String>(Arrays.asList(split));
	}

	@Override
	protected GetCardsByPhoneCacheEntry getDbCached() throws SystemException
	{
		return cacheService.getCardsByPhoneCacheEntry(phone);
	}

	@Override
	protected GetCardsByPhoneCacheEntry getAppServerCached() throws SystemException
	{
		Cache cache = CacheProvider.getCache(GetCardsByPhoneCacheEntry.CACHE_NAME);
		Element element = cache.get(phone);
		if (element == null)
		{
			return null;
		}
		else
		{
			return (GetCardsByPhoneCacheEntry) element.getObjectValue();
		}
	}

	protected GetCardsByPhoneCacheEntry processResult(CallableStatement cstmt, ResultSet rs) throws SQLException, GateException
	{
		int status = cstmt.getInt(1);
		messageLogger.setResultCode(status);
		StandInUtils.registerMBOfflineEvent(status);
		StandInUtils.checkStandInAndThrow(status);

		GetCardsByPhoneCacheEntry result = new GetCardsByPhoneCacheEntry();
		result.setPhoneNumber(phone);

		Set<String> cardNumbers = new HashSet<String>();

		if (rs != null)
		{
			try
			{
				getCardNumbersFromResultSet(rs,cardNumbers);
			}
			catch (Exception e)
			{
				throw new GateException(e);
			}
		}

		if (status == INCORRECT_PHONE_NUMBER)
		{
			throw new SQLException("Некорректный номер телефона");
		}
		else if (status == MBK_NOT_CONNECT)
		{
			log.error("Для телефона: " + phone + " нет подключений в МБК (карт не найдено)");
		}
		else if (status == ERR)
		{
			throw new SQLException("Произошла ошибка при вызове процедуры, попробуйте повторить запрос");
		}
		else if (status == 0)
		{
			result.setResultSet(StringUtils.join(cardNumbers, RESULT_SET_DELIMITER));
		}
		else
			throw new SQLException("Неизвестный статус ");

		return result;
	}

	@Override
	public boolean isConnectionLogEnabled()
	{
		return false;
	}

	protected void getCardNumbersFromResultSet(ResultSet rs, Set<String> cardNumbers) throws GateException, SQLException
	{
		while (rs.next())
		{
			cardNumbers.add(rs.getString(1));
		}
	}

}
