package com.rssl.phizic.test;

import com.rssl.phizic.dataaccess.hibernate.Executor;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutorHelper;
import com.rssl.phizic.dataaccess.hibernate.HibernateUtil;
import com.rssl.phizic.dataaccess.query.ExecutorQuery;
import com.rssl.phizic.dataaccess.query.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Map;
import javax.naming.NameNotFoundException;

/**
 * User: Moshenko
 * Date: 10.09.13
 * Time: 10:42
 */
public class MbvMockHibernateExecutor  implements Executor
{
	private static final String SESSION_FACTORY_JNDI_NAME = "hibernate/session-factory/MBVMock";

	// ������ ������ �� ������ � ���������� ��� ����
	// ���� �� ����� ���� ������������ �� ��������� ������� execute
	private static final ThreadLocal<Map<String, Session>> sessionStorage = new ThreadLocal<Map<String, Session>>();
	private static final ThreadLocal<Map<String, Transaction>> transactionStorage = new ThreadLocal<Map<String, Transaction>>();

	private final HibernateExecutorHelper helper = new HibernateExecutorHelper();

	/**
	 * ��������� ����������� �������, �� ��� ��������� ������ �������� � ������������
	 * @param useTransaction ������������ �� ����������
	 * @param sessionStorage
	 * @param transactionStorage
	 * @throws javax.naming.NameNotFoundException
	 */
	private MbvMockHibernateExecutor(boolean useTransaction, ThreadLocal<Map<String, Session>> sessionStorage, ThreadLocal<Map<String, Transaction>> transactionStorage) throws NameNotFoundException
	{
		helper.setUseTransaction(useTransaction);
		helper.setSessionStorage(sessionStorage);
		helper.setTransactionStorage(transactionStorage);

		helper.setInstanceName("mbv");
		helper.setSessionFactory(HibernateUtil.lookupSessionFactory(SESSION_FACTORY_JNDI_NAME));
	}

	public static MbvMockHibernateExecutor getInstance(boolean useTransaction)
	{
		try
		{
			return new MbvMockHibernateExecutor(useTransaction, sessionStorage, transactionStorage);
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
