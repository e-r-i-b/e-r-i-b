package com.rssl.phizicgate.mobilebank;

import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.dataaccess.jdbc.JDBCAction;
import com.rssl.phizic.logging.source.JDBCActionExecutor;
import com.rssl.phizic.logging.messaging.System;
import com.rssl.phizic.utils.test.JNDIUnitTestHelper;
import com.rssl.phizic.utils.test.JUnitDatabaseConfig;
import junit.framework.TestCase;

import javax.naming.NamingException;

/**
 * @author Erkin
 * @ created 06.08.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Базовый класс тестов для jdbc-экшенов
 */
public abstract class JDBCActionTestBase extends TestCase
{
	private JUnitDatabaseConfig databaseConfig;

	private JDBCActionExecutor executor;

	///////////////////////////////////////////////////////////////////////////

	protected abstract JUnitDatabaseConfig getDatabaseConfig();

	protected <T> T executeJDBCAction(JDBCAction<T> action) throws Exception
	{
		return executor.execute(action);
	}

	protected void setUp() throws Exception
	{
		super.setUp();

		ApplicationInfo.setCurrentApplication(Application.JUnitTest);

		databaseConfig = getDatabaseConfig();

		initializeDataSource();

		executor = new JDBCActionExecutor(databaseConfig.getDataSourceName(), System.mbk);
	}

	private void initializeDataSource()
	{
		try
		{
			JNDIUnitTestHelper.createJUnitDataSource(databaseConfig);
		}
		catch (NamingException ne)
		{
			ne.printStackTrace();
			fail("NamingException thrown on Init : " + ne.getMessage());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail("Exception thrown on Init : " + e.getMessage());
		}
	}
}
