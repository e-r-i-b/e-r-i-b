package com.rssl.phizic.test;

import com.rssl.phizic.business.documents.AbstractPaymentDocument;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.jdbc.JDBCUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Evgrafov
 * @ created 27.01.2006
 * @ $Author: nady $
 * @ $Revision: 75782 $
 */

public class NativeSQLTest extends BusinessTestCaseBase
{
	protected void setUp() throws Exception
	{
		super.setUp();
		initializeRsV51Gate();
	}

	public void testNativeSQL() throws Exception
	{
		final String sql = "SELECT * FROM PAYMENTS WHERE ID < 100";
		final String hql = "select payment from AbstractPaymentDocument as payment where payment.id < 100";

		HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
		{
			public Void run(Session session) throws Exception
			{
				PreparedStatement statement = null;
				try
				{
					Query sqlQuery = session.createQuery(hql);
					List list = sqlQuery.list();

					for (Object aList : list)
					{
						AbstractPaymentDocument payment = (AbstractPaymentDocument) aList;

						System.out.println(BeanUtils.getNestedProperty(payment, "id"));
						System.out.println(BeanUtils.getNestedProperty(payment, "amount.decimal"));
					}

					statement = session.connection().prepareStatement(sql);
					printResult(statement);
				}
				finally
				{
					if (statement != null)
						statement.close();
				}

				return null;
			}
		});
	}

	private void printResult(PreparedStatement statement) throws SQLException
	{
		ResultSet resultSet = JDBCUtils.executeStoredProcedureQuery(statement);

		ResultSetMetaData metaData = statement.getMetaData();
		int columnCount = metaData.getColumnCount();

		String[] columnNames = new String[columnCount];

		for (int i = 1; i <= columnCount; i++)
		{
			columnNames[i - 1] = metaData.getColumnName(i);
		}
		if (resultSet != null)
		{
			while (resultSet.next())
			{
				System.out.print(resultSet.getRow());

				for (int i = 1; i <= columnCount; i++)
				{
					System.out.print(" ");
					System.out.print(columnNames[i - 1]);
					System.out.print("=");
					System.out.print(resultSet.getObject(i));
				}

				System.out.println();
			}
		}
	}
}
