package com.rssl.phizic.dataaccess.jdbc;

import com.sun.rowset.CachedRowSetImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.rowset.CachedRowSet;

/**
 * @author Erkin
 * @ created 13.11.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * Утилиты для работы с JDBC
 */
public final class JDBCUtils
{
	/**
	 * Вызывает ХП и возвращает последний встреченный резальтсет ХП.
	 *
	 * Замечание.
	 * Часто в процессе работы ХП выполняет несколько селектов и апдейтов.
	 * JDBC возвращает результаты селектов в виде набора резальтсетов, которые можно обойти с помощью getNextResult и getResultSet.
	 * В этот набор попадают все селекты, кроме тех, которые сохраняются во временные переменные ХП.
	 *
	 * Пример:
	 * Следующий селект попадёт в JDBC:
	 * SELECT * FROM TABLE
	 * А этот не попадёт:
	 * SELECT @COUNT = COUNT(*) FROM TABLE
	 *
	 * Также JBDC возвращает результаты выполнения апдейтов.
	 *
	 * Метод пропускает апдейты и возвращает последний попавшийся селект
	 *
	 * @param statement
	 * @return последний попавшийся резальтсет или null, если ХП не вернула ни одного резальтсета.
	 */
	public static CachedRowSet executeStoredProcedureQuery(PreparedStatement statement) throws SQLException
	{
		// Сюда будем складывать последний удачно полученный резальтсет
		CachedRowSet cachedRowSet = null;

		if (statement.execute())
		{
			// Забираем в кеш первый резальтсет
			cachedRowSet = cacheResultSet(statement);
		}

		// Перебираем результаты выполнения ХП
		while (true)
		{
			// Переходим к следующему результату: селекту или апдейту
			if (statement.getMoreResults())
			{
				// Следующим результатом оказался селект
				// Забираем в кеш очередной резальтсет
				if (cachedRowSet != null)
					cachedRowSet.close();
				cachedRowSet = cacheResultSet(statement);

				continue;
			}

			if (statement.getUpdateCount() != -1)
			{
				// Следующим результатом оказался апдейт
				continue;
			}

			// Результатов больше нет
			return cachedRowSet;
		}
	}

	private static CachedRowSet cacheResultSet(PreparedStatement statement) throws SQLException
	{
		ResultSet rs = statement.getResultSet();
		try
		{
			if (rs != null)
			{
				CachedRowSet cachedRowSet = new CachedRowSetImpl();
				cachedRowSet.populate(rs);
				return cachedRowSet;
			}
			return null;
		}
		finally
		{
			if (rs != null)
				try { rs.close(); } catch (SQLException ignore) {}
		}
	}
}
