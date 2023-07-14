package com.rssl.phizicgate.rsV55.data;

import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutorHelper;
import com.rssl.phizic.dataaccess.hibernate.HibernateUtil;
import com.rssl.phizic.dataaccess.hibernate.Executor;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.dataaccess.query.ExecutorQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Map;
import javax.naming.NameNotFoundException;

public final class GateRSV55Executor implements Executor
{
	private static final String SESSION_FACTORY_JNDI_NAME = "hibernate/session-factory/RSBankV55";

	// храним ссылки на сессию и транзакцию для того
    // чтоб их можно было использовать во вложенных вызовах execute
	private static final ThreadLocal<Map<String,Session>> sessionStorage     = new ThreadLocal<Map<String,Session>>();
    private static final ThreadLocal<Map<String,Transaction>> transactionStorage = new ThreadLocal<Map<String,Transaction>>();


    /**
     * @return Экземпряр HibernateExecutor использующий транзакции
     */
    public static GateRSV55Executor getInstance()
    {
	    /*
	     для pervasive всегда обращаемся создавая транзакцию. Так как иначе создается транзакция
	     которая не закрывается, соответственно все изменение не проносятся. Если создавать
	     транзакцию явно, то транзакция по умолчанию не создается.
	     Транзакция работает без каких-либо lock'ов.
	     */
        return getInstance(true);
    }

    /**
     * @param useTransaction true - испольлизовать транзакции false не изпользовать
     * @return Экземпляр HibernateExecutor
     */
    public static GateRSV55Executor getInstance(boolean useTransaction)
    {
	    try
	    {
		    return new GateRSV55Executor(useTransaction, sessionStorage, transactionStorage);
	    }
	    catch (NameNotFoundException e)
	    {
		    throw new RuntimeException(e);
	    }
    }

    private final HibernateExecutorHelper helper = new HibernateExecutorHelper();

    /**
     * Создается независимый враппер, он сам управляет своими сессиями и транзакциями
     */
    private GateRSV55Executor(boolean useTransaction, ThreadLocal<Map<String,Session>>  sessionStorage, ThreadLocal<Map<String,Transaction>> transactionStorage) throws NameNotFoundException
    {
	    helper.setInstanceName("main");
        helper.setUseTransaction(useTransaction);
        helper.setSessionStorage(sessionStorage);
        helper.setTransactionStorage(transactionStorage);
        helper.setSessionFactory(getSessionFactory());
    }

    public <T> T execute(HibernateAction<T> action) throws Exception
    {
        return helper.execute(action);
    }

	public Query getNamedQuery(String name)
	{
		return new ExecutorQuery(this, name);
	}

	public static SessionFactory getSessionFactory() throws NameNotFoundException
	{
		return HibernateUtil.lookupSessionFactory(SESSION_FACTORY_JNDI_NAME);
	}

}
