package com.rssl.phizic.dataaccess.jdbc;

import com.rssl.phizic.common.types.exceptions.SystemException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Erkin
 * @ created 20.04.2010
 * @ $Author$
 * @ $Revision$
 */

public interface JDBCAction<ReturnType>
{
	/**
	 * Выполнить экшен
	 * @param con - коннекция к бд
	 * @return результат выполнения
	 */
	ReturnType execute(Connection con) throws SQLException, SystemException;

	/**
	 * @return включено ли логирование на уровне Connection.
	 */
	boolean isConnectionLogEnabled();
}
