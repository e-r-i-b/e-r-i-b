package com.rssl.phizic.auth.pin;

import com.rssl.phizic.dataaccess.common.counters.CounterException;
import com.rssl.phizic.dataaccess.common.counters.Counters;
import com.rssl.phizic.dataaccess.common.counters.CounterService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

/**
 * @author Roshka
 * @ created 18.08.2006
 * @ $Author$
 * @ $Revision$
 */

public class PINService
{
	private static final CounterService counterService = new CounterService();

	/**
	 * Последний номер запроса
	 */
	public Long getLastRequestNumber()
	{
		try
		{
			return counterService.getNext(Counters.PIN_REQUEST_NUMBER);
		}
		catch(CounterException ex)
		{
			throw new SecurityException(ex);
		}
		catch(Exception ex)
		{
			throw new SecurityException(ex);
		}
	}

	/**
	 * Получить pin конверт по номеру и ид. пользователя
	 * @param requestNumber  номер запроса
	 * @param userId ид. пользователя
	 */
	public  PINEnvelope findEnvelope(final Long requestNumber, final String userId)
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<PINEnvelope>()
			{
				public PINEnvelope run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.auth.pin.findEnvelopeByRequestNumber");
					query.setParameter("requestNumber", requestNumber);
					query.setParameter("userId", userId);

					return (PINEnvelope) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new SecurityException(e);
		}
	}

	/**
	 * Поиск конверта по ид. пользователя.
	 * @param userId
	 */
	public PINEnvelope findEnvelope(final String userId)
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<PINEnvelope>()
			{
				public PINEnvelope run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.auth.pin.findEnvelope");
					query.setParameter("userId", userId);

					return (PINEnvelope) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new SecurityException(e);
		}
	}

	/**
	 * Конверты в конкретном состоянии.
	 * @param state статус
	 */
	public List<PINEnvelope> getEnvelopesForState(final String state)
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<PINEnvelope>>()
			{
				public List<PINEnvelope> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.auth.pin.getAllEnvelopesByState");
					query.setParameter("state", state);

					//noinspection unchecked
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new SecurityException(e);
		}
	}

	/**
	 * Создать
	 * @param envelope
	 */
	public void add(final PINEnvelope envelope) throws DuplicatePINException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					session.save(envelope);
					return null;
				}
			});
		}
		catch (ConstraintViolationException e)
		{
			throw new DuplicatePINException(e);
		}
		catch (Exception e)
		{
			throw new SecurityException(e);
		}
	}

	/**
	 * Сохранить
	 * @param envelope
	 */
	public void update(final PINEnvelope envelope) throws DuplicatePINException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					session.update(envelope);
					return null;
				}
			});
		}
		catch (ConstraintViolationException e)
		{
			throw new DuplicatePINException(e);
		}
		catch (Exception e)
		{
			throw new SecurityException(e);
		}
	}

	/**
	 * Удалить
	 * @param envelope
	 */
	public void remove(final PINEnvelope envelope)
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					session.delete(envelope);
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new SecurityException(e);
		}
	}
}