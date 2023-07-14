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
 * ������� ��� ������ � JDBC
 */
public final class JDBCUtils
{
	/**
	 * �������� �� � ���������� ��������� ����������� ���������� ��.
	 *
	 * ���������.
	 * ����� � �������� ������ �� ��������� ��������� �������� � ��������.
	 * JDBC ���������� ���������� �������� � ���� ������ ������������, ������� ����� ������ � ������� getNextResult � getResultSet.
	 * � ���� ����� �������� ��� �������, ����� ���, ������� ����������� �� ��������� ���������� ��.
	 *
	 * ������:
	 * ��������� ������ ������ � JDBC:
	 * SELECT * FROM TABLE
	 * � ���� �� ������:
	 * SELECT @COUNT = COUNT(*) FROM TABLE
	 *
	 * ����� JBDC ���������� ���������� ���������� ��������.
	 *
	 * ����� ���������� ������� � ���������� ��������� ���������� ������
	 *
	 * @param statement
	 * @return ��������� ���������� ���������� ��� null, ���� �� �� ������� �� ������ �����������.
	 */
	public static CachedRowSet executeStoredProcedureQuery(PreparedStatement statement) throws SQLException
	{
		// ���� ����� ���������� ��������� ������ ���������� ����������
		CachedRowSet cachedRowSet = null;

		if (statement.execute())
		{
			// �������� � ��� ������ ����������
			cachedRowSet = cacheResultSet(statement);
		}

		// ���������� ���������� ���������� ��
		while (true)
		{
			// ��������� � ���������� ����������: ������� ��� �������
			if (statement.getMoreResults())
			{
				// ��������� ����������� �������� ������
				// �������� � ��� ��������� ����������
				if (cachedRowSet != null)
					cachedRowSet.close();
				cachedRowSet = cacheResultSet(statement);

				continue;
			}

			if (statement.getUpdateCount() != -1)
			{
				// ��������� ����������� �������� ������
				continue;
			}

			// ����������� ������ ���
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
