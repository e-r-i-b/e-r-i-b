package com.rssl.phizic.errors;

import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.ConfigurationCheckedException;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

/**
 * @author gladishev
 * @ created 15.11.2007
 * @ $Author$
 * @ $Revision$
 */

public class ErrorMessagesService
{
	/**
	 * Добавить errorMessage
	 * @param errorMessage
	 * @throws ConfigurationCheckedException
	 */
	public ErrorMessage add(final ErrorMessage errorMessage) throws ConfigurationCheckedException
	{
		try
		{
			HibernateExecutor trnExecutor = HibernateExecutor.getInstance();

			return trnExecutor.execute(new HibernateAction<ErrorMessage>()
			{
				public ErrorMessage run(Session session) throws Exception
				{
					session.save(errorMessage);
					return errorMessage;
				}
			}
			);
		}
		catch (Exception e)
		{
			throw new ConfigurationCheckedException(e);
		}
	}

	/**
	 * Обновить errorMessage
	 * @param errorMessage
	 * @return
	 * @throws ConfigurationCheckedException
	 */
	public ErrorMessage update(final ErrorMessage errorMessage) throws ConfigurationCheckedException
	{
		try
		{
			HibernateExecutor trnExecutor = HibernateExecutor.getInstance();

			return trnExecutor.execute(new HibernateAction<ErrorMessage>()
			{
				public ErrorMessage run(Session session) throws Exception
				{
					session.update(errorMessage);
					return errorMessage;
				}
			}
			);
		}
		catch (Exception e)
		{
			throw new ConfigurationCheckedException(e);
		}
	}

	/**
	 * Удалить errorMessage
	 * @param errorMessage
	 */
	public void remove(final ErrorMessage errorMessage) throws ConfigurationCheckedException
	{
		try
		{
			HibernateExecutor trnExecutor = HibernateExecutor.getInstance();
			trnExecutor.execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					session.delete(errorMessage);
					return null;
				}
			}
			);
		}
		catch (Exception e)
		{
			throw new ConfigurationCheckedException(e);
		}
	}

	/**
	 * Найти список errorMessage
	 * @return список errorMessage
	 * @throws ConfigurationCheckedException
	 */
	public List<ErrorMessage> find(final String regExp, final ErrorType errorType, final ErrorSystem system, final String message) throws ConfigurationCheckedException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<ErrorMessage>>()
		    {
		        public List<ErrorMessage> run(Session session) throws Exception
		        {
			        Query query = session.getNamedQuery("com.rssl.phizic.errors.ErrorMessage.getMessages");
			        //noinspection unchecked
			        return (List<ErrorMessage>) query.setParameter("regExp", regExp).setParameter("errorType", errorType)
					        .setParameter("system", system).setParameter("message", message).list();
		        }
		    });
		}
		catch (Exception e)
		{
			throw new ConfigurationCheckedException(e);
		}
	}

	/**
	 * Найти сообщение об ошибке по Id
	 * @return список errorMessage
	 * @throws ConfigurationCheckedException
	 */
	public ErrorMessage findById(final Long id) throws ConfigurationCheckedException
	{
		List<ErrorMessage> results = find(DetachedCriteria.forClass(ErrorMessage.class).add(Expression.eq("id", id)));
		return results.size() == 1 ? results.get(0) : null;
	}

	/**
	 * Получить весь список сообщений
	 * @return список errorMessage
	 * @throws ConfigurationCheckedException
	 */
	public List<ErrorMessage> getAll() throws ConfigurationCheckedException
	{
		return find(DetachedCriteria.forClass(ErrorMessage.class));
	}

	public List<ErrorMessage> findBySystem(final ErrorSystem system) throws ConfigurationCheckedException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<ErrorMessage>>()
		    {
		        public List<ErrorMessage> run(Session session) throws Exception
		        {
			        Query query = session.getNamedQuery("com.rssl.phizic.errors.ErrorMessage.getMessagesBySystem");
			        //noinspection unchecked
			        return (List<ErrorMessage>) query.setParameter("system", system).list();
		        }
		    });
		}
		catch (Exception e)
		{
			throw new ConfigurationCheckedException(e);
		}
	}

	private <T> List<T> find(final DetachedCriteria detachedCriteria) throws ConfigurationCheckedException
	{
		HibernateExecutor lightExecutor = HibernateExecutor.getInstance();
		try
		{
			return lightExecutor.execute(new HibernateAction<List<T>>()
			{
				public List<T> run(Session session) throws Exception
				{
					//noinspection unchecked
					return detachedCriteria.getExecutableCriteria(session).list();
				}
			}
			);
		}
		catch (Exception e)
		{
			throw new ConfigurationCheckedException(e);
		}
	}
}
