package com.rssl.phizic.dataaccess.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.*;

import java.util.Map;
import java.util.HashMap;
import java.sql.Statement;

/**
 * @author Kidyaev
 * @ created 06.10.2005
 * @ $Author: balovtsev $
 * @ $Revision: 25081 $
 */
public class HibernateExecutorHelper
{
    private static final Log log = LogFactory.getLog(HibernateExecutorHelper.class);

	private String                  instanceName     = null;
    private boolean                  useTransaction     = true;
    private ThreadLocal<Map<String,Session>>     sessionStorage;
    private ThreadLocal<Map<String,Transaction>> transactionStorage;
    private SessionFactory           sessionFactory;
	private String userName;
    
	public <T> T execute(HibernateAction<T> action) throws Exception
    {
        boolean needCloseSession       = false; // не закрываем сессию если она начата выше по стеку
        boolean needCommitTransaction = false; // не завершаем трензакцию если она начата выше по стеку

        T result;
        Session session = null;
        Transaction transaction = null;
        try
        {
	        if(sessionStorage.get()==null)
	            session = null;
	        else
                session = sessionStorage.get().get(instanceName);

            if(session == null)
            {
                session = sessionFactory.openSession();
	            needCloseSession = true;
	            if(userName != null)
	            {
		            Statement statementent = null;
		            try
		            {
			            statementent = session.connection().createStatement();
			            statementent.execute("ALTER SESSION SET CURRENT_SCHEMA = " + getUserName());
		            }
		            finally
		            {
			            if (statementent != null)
			            {
				           statementent.close();
			            }
		            }
	            }
                log.debug("Hibernate session opened");
	            Map<String,Session> map = sessionStorage.get();
	            if(map==null)
	                map=new HashMap<String,Session>();

	            map.put(instanceName,session);

                sessionStorage.set(map);

            }

            if(useTransaction)
            {
	            if(transactionStorage.get()==null)
	                transaction = null;
	            else
                    transaction = transactionStorage.get().get(instanceName);

                if (transaction == null)
                {
                    transaction = session.beginTransaction();
	                needCommitTransaction = true;
                    log.debug("Transaction started");
	                Map<String,Transaction> map = transactionStorage.get();
	                if(map==null)
	                    map = new HashMap<String,Transaction>();

	                map.put(instanceName,transaction);
                    transactionStorage.set(map);
                }
            }

            result = action.run(session);

	        if (needCloseSession)
	        {
		        session.flush();
		        log.debug("Hibernate session flushed");
	        }

            if(needCommitTransaction)
            {
                transaction.commit();
                log.debug("Transaction commited");
            }
        }
        catch (Exception ex)
        {
            if (transaction != null && needCommitTransaction)
            {
	            log.error("—бой при выполнении запроса к базе данных", ex);
	            try
	            {
		            transaction.rollback();
		            log.debug("Transaction rolled back");
	            }
	            catch (Exception e)
	            {
		            log.error("Transaction error rollback", e);
	            }
            }
            throw ex;
        }
        finally
        {
	        if (needCloseSession)
	        {
		        sessionStorage.get().remove(instanceName);
	        }
	        if (needCommitTransaction)
	        {
		        transactionStorage.get().remove(instanceName);
	        }
            if (needCloseSession && session != null && session.isOpen())
            {
                // состо€ние сесси должно быть "чистым"
                // не должно быть никаких повисших, несохраненных кривых объектов
                // возможно решение неправильное - требует дальнейшего уточнени€
	            try
	            {
					session.clear();
					log.debug("Hibernate session cleared");
	            }
	            finally
	            {
		            session.close();
		            log.debug("Hibernate session closed");
	            }
            }
        }

            return result;
    }

    public <T> T execute(HibernateActionStateless<T> action) throws Exception
    {
        T result;
        StatelessSession statelessSession = null;
        Transaction transaction = null;
        try
        {
                statelessSession = sessionFactory.openStatelessSession();
                log.debug("Hibernate statelessSession opened");
	            if(userName != null)
	            {
		            Query query = statelessSession.createSQLQuery("ALTER SESSION SET CURRENT_SCHEMA = " + getUserName() + ";");
	                query.executeUpdate();
	            }
                transaction = statelessSession.beginTransaction();
                log.debug("Transaction started");
	            result = action.run(statelessSession);

                transaction.commit();
                log.debug("Transaction commited");
        }
        catch (Exception ex)
        {
            if (transaction != null)
            {
	            try
	            {
	                transaction.rollback();
	                log.debug("Transaction rolled back");
	            }
	            catch (Exception e)
	            {
		            log.error("Transaction error rollback", e);
	            }

	            log.error("—бой при выполнении запроса к базе данных", ex);
            }
            throw ex;
        }
        finally
        {
            if (statelessSession != null)
            {
                statelessSession.close();
                log.debug("Hibernate statelessSession closed");
            }
        }

        return result;
    }

    public boolean isUseTransaction()
    {
        return useTransaction;
    }

    public void setUseTransaction(boolean useTransaction)
    {
        this.useTransaction = useTransaction;
    }

    public ThreadLocal<Map<String,Session>> getSessionStorage()
    {
        return sessionStorage;
    }

    public void setSessionStorage(ThreadLocal<Map<String,Session>> sessionStorage)
    {
        this.sessionStorage = sessionStorage;
    }

    public ThreadLocal<Map<String,Transaction>> getTransactionStorage()
    {
        return transactionStorage;
    }

    public void setTransactionStorage(ThreadLocal<Map<String,Transaction>> transactionStorage)
    {
        this.transactionStorage = transactionStorage;
    }

    public SessionFactory getSessionFactory()
    {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }

	public String getInstanceName()
	{
		return instanceName;
	}

	public void setInstanceName(String instanceName)
	{
		this.instanceName = instanceName;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}
}
