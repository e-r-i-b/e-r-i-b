package com.rssl.phizic.test;

import com.rssl.phizic.dataaccess.hibernate.*;
import com.rssl.phizic.dataaccess.query.ExecutorQuery;
import com.rssl.phizic.dataaccess.query.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Map;
import javax.naming.NameNotFoundException;

/**
 * Created by IntelliJ IDEA.
 * User: Egorovaa
 * Date: 21.11.2011
 * Time: 14:14:12
 */
public class MockHibernateExecutor implements Executor
{
	private static final String SESSION_FACTORY_JNDI_NAME = "hibernate/session-factory/ESBERIBMock";

	// храним ссылки на сессию и транзакцию для того
	// чтоб их можно было использовать во вложенных вызовах execute
	private static final ThreadLocal<Map<String, Session>> sessionStorage = new ThreadLocal<Map<String, Session>>();
	private static final ThreadLocal<Map<String, Transaction>> transactionStorage = new ThreadLocal<Map<String, Transaction>>();

	private final HibernateExecutorHelper helper = new HibernateExecutorHelper();

	/**
	 * Создается независимый враппер, он сам управляет своими сессиями и транзакциями
	 * @param useTransaction использовать ли транзакцию
	 * @param sessionStorage
	 * @param transactionStorage
	 * @throws javax.naming.NameNotFoundException
	 */
	private MockHibernateExecutor(boolean useTransaction, ThreadLocal<Map<String, Session>> sessionStorage, ThreadLocal<Map<String, Transaction>> transactionStorage) throws NameNotFoundException
	{
		helper.setUseTransaction(useTransaction);
		helper.setSessionStorage(sessionStorage);
		helper.setTransactionStorage(transactionStorage);

		helper.setInstanceName("main");
		helper.setSessionFactory(HibernateUtil.lookupSessionFactory(SESSION_FACTORY_JNDI_NAME));
	}

	public static MockHibernateExecutor getInstance(boolean useTransaction)
	{
		try
		{
			return new MockHibernateExecutor(useTransaction, sessionStorage, transactionStorage);
		}
		catch (NameNotFoundException e)
		{
			throw new RuntimeException(e);
		}
	}

	public <T> T execute(HibernateAction<T> action) throws Exception
	{
		return helper.execute(action);
	}

	public Query getNamedQuery(String name)
	{
		return new ExecutorQuery(this, name);
	}

	public String getInstanceName()
	{
		return helper.getInstanceName();
	}
}
