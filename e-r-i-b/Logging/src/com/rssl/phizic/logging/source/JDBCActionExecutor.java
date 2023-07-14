package com.rssl.phizic.logging.source;

import com.rssl.phizic.common.types.exceptions.DatabaseTimeoutException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.exceptions.TooManyDatabaseCursorsException;
import com.rssl.phizic.dataaccess.jdbc.JDBCAction;
import com.rssl.phizic.dataaccess.jdbc.LazyConnection;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.naming.NamingHelper;
import com.rssl.phizic.logging.messaging.System;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * @author Erkin
 * @ created 27.07.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * Сервис, работающий с (внешней) базой данных через датасорс
 */
public class JDBCActionExecutor
{
	private static final int TIMEOUT_EXCEPTION_CODE = 1013;
	private static final int MANY_CURSORS_EXCEPTION_CODE = 1000;
	protected static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	private final String dataSourceName;
	private volatile DataSource dataSource = null;
	private final Object dataSourceLock = new Object();
	private System system;

	public JDBCActionExecutor(String dataSourceName, System system)
	{
		this.dataSourceName = dataSourceName;
		this.system = system;
	}

	private DataSource getDataSource() throws SystemException
	{
		if (dataSource != null)
			return dataSource;

		synchronized (dataSourceLock)
		{
			if (dataSource == null)
				dataSource = initDataSource();
		}

		return dataSource;
	}

	private DataSource initDataSource() throws SystemException
	{
		if (StringHelper.isEmpty(dataSourceName))
			throw new SystemException("Не указано название datasource");

		try
		{
			return (DataSource) NamingHelper.getInitialContext().lookup(dataSourceName);
		}
		catch (NamingException ne)
		{
			log.fatal("Ошибка при инициализации дата-сорса " + dataSourceName, ne);
			throw new SystemException(ne);
		}
	}

	/**
	 * @return имя датасорца.
	 */
	public String getDataSourceName()
	{
		return dataSourceName;
	}

	/**
	 * Выполнить действие
	 * @param action собственно действие
	 * @param <T> тип возвращаемого результата
	 * @return результат
	 * @throws SystemException
	 */
	public <T> T execute(JDBCAction<T> action) throws SystemException
	{
		Connection con = null;
		try
		{
			con =  getConnection(action);
			return action.execute(con);
		}
		catch (SQLException ex)
		{
			log.error("Сбой при выполнении запроса к базе данных. " +
					"Класс: " + this.getClass().getName() + ". " +
					"Код ошибки: " + ex.getErrorCode() + ". " +
					"Сообщение: "  + ex.getMessage(),
					ex
			);

			if (ex.getErrorCode() == TIMEOUT_EXCEPTION_CODE)
				throw new DatabaseTimeoutException(ex);

			if (ex.getErrorCode() == MANY_CURSORS_EXCEPTION_CODE)
				throw new TooManyDatabaseCursorsException(ex);

			throw new SystemException(ex);
		}
		finally
		{
			if (con != null)
				try { con.close(); } catch (SQLException ignored) {}
		}
	}

	/**
	 * получить connect
	 * @return connect
	 * @throws SystemException
	 */
	protected Connection getConnection(JDBCAction action) throws SystemException
	{
		DataSource ds = getDataSource();
		Connection con = new LazyConnection(ds);
		if (action.isConnectionLogEnabled())
			return new ProxyConnection(con, system);
		else
			return con;

	}
}
