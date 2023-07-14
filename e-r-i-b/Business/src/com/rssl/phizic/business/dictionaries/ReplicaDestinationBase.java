package com.rssl.phizic.business.dictionaries;

import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.dictionaries.ReplicaDestination;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.hibernate.HibernateException;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.Session;

import java.util.List;
import java.util.ArrayList;
import javax.naming.NameNotFoundException;

/**
 * @author khudyakov
 * @ created 11.08.2010
 * @ $Author$
 * @ $Revision$
 */
public abstract class ReplicaDestinationBase<DR extends DictionaryRecord> implements ReplicaDestination<DR>
{
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	protected boolean needSort;
	protected boolean ignoreDublicate;//Игнорировать ли дубликаты при вставке и апдейте.

	private Session session;
	private boolean debug = log.isDebugEnabled();
	private List<String> errors = new ArrayList<String>();

	public void add(DR newValue) throws GateException
	{
		if (debug)
			log.debug("add: " + newValue.getSynchKey());
		try
		{
			session.save(newValue);
		}
		catch (NonUniqueObjectException e)
		{
			if (!ignoreDublicate)
			{
				throw e;
			}
			log.warn("Вставка дублирующейся записи проигнорирована(id=" + newValue.getSynchKey() + ")");
		}
		catch (HibernateException e)
		{
			throw new GateException(e);
		}
	}

	public void remove(DR oldValue) throws GateException
	{
		if (debug)
			log.debug("remove: " + oldValue.getSynchKey());

		try
		{
			session.delete(oldValue);
		}
		catch (HibernateException e)
		{
			throw new GateException(e);
		}
	}

	public void update(DR oldValue, DR newValue) throws GateException
	{
		if (debug)
			log.debug("update: " + oldValue.getSynchKey());

		try
		{
			oldValue.updateFrom(newValue);
			session.update(oldValue);
		}
		catch (NonUniqueObjectException e)
		{
			if (!ignoreDublicate)
			{
				throw e;
			}
			log.warn("Обновляение дублирующейся записи проигнорировано(id=" + newValue.getSynchKey() + ")", e);
		}
		catch (HibernateException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Cоздание сессии
	 */
	protected void initialize()
	{
		if (session != null)
			throw new RuntimeException("Oбнаружена незакрытая сессия, вызовите метод close()!");

		try
		{
			//noinspection HibernateResourceOpenedButNotSafelyClosed
			session = HibernateExecutor.getSessionFactory(getInstanceName()).openSession();
		}
		catch (NameNotFoundException e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * @return - сессия, с которой работаем
	 */
	public Session getSession()
	{
		return session;
	}

	public List<String> getErrors()
	{
		return errors;
	}

	/**
	 * Добавить запись об ошибке
	 * @param error - ошибка
	 */
	protected void addError(String error)
	{
		errors.add(error);
	}

	public void close()
	{
		if (session == null)
			return;

		try{
			session.flush();
		}finally {
			session.close();
			session = null;
		}
	}

	protected String getInstanceName()
	{
		return null;
	}
}
