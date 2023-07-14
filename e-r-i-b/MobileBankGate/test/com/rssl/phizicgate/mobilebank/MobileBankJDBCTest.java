package com.rssl.phizicgate.mobilebank;

import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.mobile.GetRegistrationType;
import com.rssl.phizic.dataaccess.jdbc.JDBCAction;
import com.rssl.phizic.dataaccess.jdbc.JDBCUtils;
import com.rssl.phizic.gate.mobilebank.MbkCard;
import com.rssl.phizic.gate.mobilebank.MigrationCardType;
import com.rssl.phizic.gate.mobilebank.MigrationType;
import com.rssl.phizic.utils.resources.ResourceHelper;
import com.rssl.phizic.utils.test.JUnitDatabaseConfig;
import com.rssl.phizic.utils.test.JUnitSimpleDatabaseConfig;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Set;

/**
 * @author Erkin
 * @ created 06.08.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Тесты для проверки jdbc-экшенов МБК
 */
public class MobileBankJDBCTest extends JDBCActionTestBase
{
	@Override
	protected JUnitDatabaseConfig getDatabaseConfig()
	{
		JUnitSimpleDatabaseConfig config = new JUnitSimpleDatabaseConfig();
		config.setDataSourceName("jdbc/MobileBank");
		config.setURI("jdbc:jtds:sqlserver://actinia:1433/MobileBank");
		config.setDriver("net.sourceforge.jtds.jdbc.Driver");
		config.setLogin("ikfl");
		config.setPassword("12345678");
		return config;
	}

	/**
	 * Тест для ХП mb_WWW_GetRegistrations
	 */
	public void testGetRegistrations() throws Exception
	{
		JDBCAction action = new GetRegistrationsJDBCAction("6368116045973324", GetRegistrationType.FIRST);
		System.out.println(executeJDBCAction(action));
	}

	/**
	 * Тест для проверки возможности использования типа VARCHAR(MAX)
	 */
	public void testVarcharMax() throws Exception
	{
		final String inp = ResourceHelper.loadResourceAsString("long-text.xml");

		JDBCAction<String> action = new JDBCAction<String>()
		{
			public String execute(Connection con) throws SQLException, SystemException
			{
				CallableStatement cstmt = con.prepareCall("{call test_max(@inp = ?)}");
				try
				{
					cstmt.setString(1, inp);
					ResultSet rs = JDBCUtils.executeStoredProcedureQuery(cstmt);
					//noinspection NestedTryStatement
					try
					{
						if (rs != null && rs.next())
							return rs.getString(1);
						return "";
					}
					finally
					{
						try { cstmt.close(); } catch (SQLException ignored) {}
					}
				}
				finally
				{
					if (cstmt != null)
						try { cstmt.close(); } catch (SQLException ignored) {}
				}
			}

			public boolean isConnectionLogEnabled()
			{
				return true;
			}
		};

		System.out.println(executeJDBCAction(action));
	}

	/**
	 * Тест для проверки бегин-миграции ЕРМБ
	 */
	public void testERMBBeginMigration() throws Exception
	{
		Set<MbkCard> cardsToMigrate = Collections.singleton(new MbkCard("1", MigrationCardType.MAIN, false));
		Set<String> phoneListToMigrate = Collections.singleton("79111111111");
		Set<String> phoneListToDelete = null;

		JDBCAction action = new BeginMigrationJDBCAction(cardsToMigrate, phoneListToMigrate, phoneListToDelete, MigrationType.LIST);
		System.out.println(executeJDBCAction(action));
	}

	/**
	 * Тест для проверки коммит-миграции ЕРМБ
	 */
	public void testERMBCommitMigration() throws Exception
	{
		JDBCAction action = new CommitMigrationJDBCAction(7520873L);
		System.out.println(executeJDBCAction(action));
	}

	/**
	 * Тест для проверки получения связок миграции-на-лету
	 */
	public void testGetRegistrationsForErmb() throws Exception
	{
		GetRegistrationsForErmbJDBCAction action = new GetRegistrationsForErmbJDBCAction();
		System.out.println(executeJDBCAction(action));
	}

	/**
	 * Тест для проверки получения статуса Быстрых сервисов
	 */
	public void testGetQuickServiceStatus() throws Exception
	{
		GetQuickServiceStatusJDBCAction action = new GetQuickServiceStatusJDBCAction("79265215543");
		System.out.println(executeJDBCAction(action));
	}
}
