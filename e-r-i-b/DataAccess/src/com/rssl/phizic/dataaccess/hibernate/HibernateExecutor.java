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

    // храним ссылки на сессию и транзакцию для того
    // чтоб их можно было использовать во вложенных вызовах execute
    private static final ThreadLocal<Map<String,Session>> sessionStorage     = new ThreadLocal<Map<String,Session>>();
    private static final ThreadLocal<Map<String,Transaction>> transactionStorage = new ThreadLocal<Map<String,Transaction>>();


    private final HibernateExecutorHelper helper = new HibernateExecutorHelper();

	/**
     * @return Экземпряр HibernateExecutor использующий транзакции
     */
    public static HibernateExecutor getInstance()
    {
        return getInstance(null);
    }

	/**
	 * @param instanceName имя экземпляр БД, если null, то hibernate/session-factory/PhizIC
     * @return Экземпряр HibernateExecutor
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
     * Создается независимый враппер, он сам управляет своими сессиями и транзакциями
     * @param sessionStorage
     * @param transactionStorage
     * @param instanceName имя экземпляр БД, если null, то hibernate/session-factory/PhizIC
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
	 * Выполнить действие, перед выполнением действие открывается сессия
	 * и если надо начинается транзакция. После выполнения действие все что было открыто закрывается.
	 *
	 * При вложенных вызовах вниз по стеку наследуются открытые сессия и транзакция
	 * @param action действие
	 * @return 
	 * @throws Exception
	 */
    public <T> T execute(HibernateAction<T> action) throws Exception
    {
        return helper.execute(action);
    }

	/**
	 * Тоже самое, что одноименный, только сессия без состояния и "вложенных транзакций".
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
	 * Возвращее SessionFactory для работы с родной БД
	 * @return SessionFactory
	 * @throws NameNotFoundException SessionFactory не инициализирована
	 */
    public static SessionFactory getSessionFactory() throws NameNotFoundException
    {
	    return getSessionFactory(null);
    }

	/**
	 * Возвращее SessionFactory для работы с БД
	 * @param instanceName имя экземпляр БД, если null, то hibernate/session-factory/PhizIC
	 * @return SessionFactory
	 * @throws NameNotFoundException SessionFactory не инициализирована
	 */
    public static SessionFactory getSessionFactory(String instanceName) throws NameNotFoundException
    {
	    HibernateEngine engine = getHibernateEngine();
	    if (engine != null)
	    {
		    // Движок хибернейта доступен в текущем потоке => используем его
		    SessionFactory factory = engine.getSessionFactory(StringUtils.defaultIfEmpty(instanceName, MAIN_INSTANCE_NAME));
		    if (factory != null)
			    return factory;
	    }
	    // Движок хибернейта не доступен в текущем потоке => используем "заначку" в jndi
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

