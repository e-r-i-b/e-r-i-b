package com.rssl.phizic.business.dictionaries.departments;

import com.rssl.phizic.business.dictionaries.ReplicaDestinationBase;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.business.dictionaries.synchronization.log.ChangeType;
import com.rssl.phizic.dictionaries.synchronization.MultiBlockDictionaryRecord;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Collections;
import java.util.Iterator;

/**
 * Базовый класс для загрузки справочников, записи которых должны быть удалены перед новой загрузкой.
 * @author Balovtsev
 * @version 23.04.13 10:48
 */
public abstract class PreClearReplicaDestinationBase extends ReplicaDestinationBase
{
	private int         recCount;
	private boolean     beforeComplete;
	private boolean     transactionComplete;
	private Transaction transaction;

	public void initialize(GateFactory factory) throws GateException
	{
		super.initialize();

		if (transaction != null && transaction.isActive())
		{
			throw new RuntimeException("Обнаружена активная транзакция");
		}

		recCount       = 0;
		beforeComplete = false;
		transaction    = getSession().beginTransaction();
	}

	public Iterator iterator() throws GateException, GateLogicException
	{
		return Collections.EMPTY_LIST.iterator();
	}

	@Override
	public void add(DictionaryRecord newValue) throws GateException
	{
		try
		{
			Session session = getSession();

			if (!beforeComplete)
			{
				beforeAdding();
				beforeComplete = true;
			}

			session.save(getDestinationEntityName(), newValue);
			if (isMultiBlockEntity())
				addChangesToLog(session, (MultiBlockDictionaryRecord) newValue, ChangeType.update);

			if (++recCount % 500 == 0)
			{
				session.flush();
				session.clear();

				recCount = 0;
			}
		}
		catch (HibernateException e)
		{
			if (transaction != null && transaction.isActive())
			{
				transaction.rollback();
			}

			throw new GateException(e);
		}
	}

	protected void addChangesToLog(Session session, MultiBlockDictionaryRecord newValue, ChangeType changeType) {}

	@Override
	public void close()
	{
		try
		{
			if (transaction != null && !transaction.wasRolledBack())
			{
				transaction.commit();
			}

			transactionComplete = true;
		}
		catch (HibernateException e)
		{
			if (transaction != null && transaction.isActive())
			{
				transaction.rollback();
			}
		}
		finally
		{
			transaction = null;

			/*
			 * Необходимо очистить сессию во избежании завершения ожидающих задач
			 */
			if (getSession() == null)
			{
				return;
			}

			getSession().clear();
			super.close();
		}
	}

	public boolean isTransactionComplete()
	{
		return transactionComplete;
	}

	@Override
	public void remove(DictionaryRecord oldValue) throws GateException  {   /* Не удаляем */  }
	@Override
	public void update(DictionaryRecord oldValue, DictionaryRecord newValue) throws GateException {    /* и не обновляем */  }

	protected void beforeAdding()
	{
		getSession().getNamedQuery(getDestinationClearQuery()).executeUpdate();
	}

	protected abstract String getDestinationEntityName();
	protected abstract String getDestinationClearQuery();

	@Override protected String getInstanceName()
	{
		return  MultiBlockModeDictionaryHelper.getDBInstanceName();
	}

	protected boolean isMultiBlockEntity()
	{
		return true;
	}
}
