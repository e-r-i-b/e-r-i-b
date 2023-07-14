package ru.softlab.phizicgate.rsloansV64.data;

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

/**
 * @author Omeliyanchuk
 * @ created 20.09.2007
 * @ $Author$
 * @ $Revision$
 */

public class RSLoansV64Executor implements Executor
{
	private static final String SESSION_FACTORY_JNDI_NAME = "hibernate/session-factory/RSLoansV64";

	// храним ссылки на сессию и транзакцию для того
	// чтоб их можно было использовать во вложенных вызовах execute
	private static final ThreadLocal<Map<String,Session>> sessionStorage     = new ThreadLocal<Map<String,Session>>();
    private static final ThreadLocal<Map<String,Transaction>> transactionStorage = new ThreadLocal<Map<String,Transaction>>();
	/**
	 * @return Экземпряр HibernateExecutor использующий транзакции
	 */
	public static RSLoansV64Executor getInstance()
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
	public static RSLoansV64Executor getInstance(boolean useTransaction)
	{
		try
		{
			return new RSLoansV64Executor(useTransaction, sessionStorage, transactionStorage);
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
	private RSLoansV64Executor(boolean useTransaction,
	                           ThreadLocal<Map<String,Session>> sessionStorage,
	                           ThreadLocal<Map<String,Transaction>> transactionStorage) throws NameNotFoundException
	{

		helper.setUseTransaction(useTransaction);
		helper.setInstanceName("main");
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
