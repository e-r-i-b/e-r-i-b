package com.rssl.phizic.dataaccess.hibernate;

import com.rssl.phizic.dataaccess.query.ExecutorQuery;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.module.Module;
import com.rssl.phizic.context.ModuleContext;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.apache.commons.lang.StringUtils;

import java.util.Map;
import javax.naming.NameNotFoundException;

public final class HibernateExecutor implements Executor
{
	private static final String MAIN_INSTANCE_NAME = "main";

	private static final String SESSION_FACTORY_JNDI_NAME = "hibernate/session-factory/PhizIC";

    // ������ ������ �� ������ � ���������� ��� ����
    // ���� �� ����� ���� ������������ �� ��������� ������� execute
    private static final ThreadLocal<Map<String,Session>> sessionStorage     = new ThreadLocal<Map<String,Session>>();
    private static final ThreadLocal<Map<String,Transaction>> transactionStorage = new ThreadLocal<Map<String,Transaction>>();


    private final HibernateExecutorHelper helper = new HibernateExecutorHelper();

	/**
     * @return ��������� HibernateExecutor ������������ ����������
     */
    public static HibernateExecutor getInstance()
    {
        return getInstance(null);
    }

	/**
	 * @param instanceName ��� ��������� ��, ���� null, �� hibernate/session-factory/PhizIC
     * @return ��������� HibernateExecutor
     */
	public static HibernateExecutor getInstance(String instanceName)
	{
	    try
	    {
		    return new HibernateExecutor(sessionStorage, transactionStorage, instanceName);
	    }
	    catch (NameNotFoundException e)
	    {
		    throw new RuntimeException(e);
	    }
    }

    /**
     * ��������� ����������� �������, �� ��� ��������� ������ �������� � ������������
     * @param sessionStorage
     * @param transactionStorage
     * @param instanceName ��� ��������� ��, ���� null, �� hibernate/session-factory/PhizIC
     * @throws NameNotFoundException
     */
    private HibernateExecutor(ThreadLocal<Map<String,Session>>  sessionStorage, ThreadLocal<Map<String,Transaction>> transactionStorage, String instanceName) throws NameNotFoundException
    {
        helper.setUseTransaction(true);
        helper.setSessionStorage(sessionStorage);
        helper.setTransactionStorage(transactionStorage);
	    helper.setInstanceName(StringUtils.defaultIfEmpty(instanceName, MAIN_INSTANCE_NAME));
	    helper.setSessionFactory(getSessionFactory(instanceName));
    }

	/**
	 * ��������� ��������, ����� ����������� �������� ����������� ������
	 * � ���� ���� ���������� ����������. ����� ���������� �������� ��� ��� ���� ������� �����������.
	 *
	 * ��� ��������� ������� ���� �� ����� ����������� �������� ������ � ����������
	 * @param action ��������
	 * @return 
	 * @throws Exception
	 */
    public <T> T execute(HibernateAction<T> action) throws Exception
    {
        return helper.execute(action);
    }

	/**
	 * ���� �����, ��� �����������, ������ ������ ��� ��������� � "��������� ����������".
	 * @param action
	 * @return
	 * @throws Exception
	 */
    public <T> T execute(HibernateActionStateless<T> action) throws Exception
    {
        return helper.execute(action);
    }

	public Query getNamedQuery(String name)
	{
		return new ExecutorQuery(this, name);
	}

	/**
	 * ��������� SessionFactory ��� ������ � ������ ��
	 * @return SessionFactory
	 * @throws NameNotFoundException SessionFactory �� ����������������
	 */
    public static SessionFactory getSessionFactory() throws NameNotFoundException
    {
	    return getSessionFactory(null);
    }

	/**
	 * ��������� SessionFactory ��� ������ � ��
	 * @param instanceName ��� ��������� ��, ���� null, �� hibernate/session-factory/PhizIC
	 * @return SessionFactory
	 * @throws NameNotFoundException SessionFactory �� ����������������
	 */
    public static SessionFactory getSessionFactory(String instanceName) throws NameNotFoundException
    {
	    HibernateEngine engine = getHibernateEngine();
	    if (engine != null)
	    {
		    // ������ ���������� �������� � ������� ������ => ���������� ���
		    SessionFactory factory = engine.getSessionFactory(StringUtils.defaultIfEmpty(instanceName, MAIN_INSTANCE_NAME));
		    if (factory != null)
			    return factory;
	    }
	    // ������ ���������� �� �������� � ������� ������ => ���������� "�������" � jndi
	    return HibernateUtil.lookupSessionFactory(SESSION_FACTORY_JNDI_NAME + StringHelper.getEmptyIfNull(instanceName));
    }

	private static HibernateEngine getHibernateEngine()
	{
		Module module = ModuleContext.getModule();
		if (module == null)
			return null;
		return module.getHibernateEngine();
	}

	public String getInstanceName()
	{
		return helper.getInstanceName();
	}
}

