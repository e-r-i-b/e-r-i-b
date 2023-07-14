package com.rssl.phizic.business.dictionaries;

import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Collections;
import java.util.Iterator;

/**
 * Цель по справочнику ВСП с возможностью выдачи кредита
 * @author Moshenko
 * @ created 18.02.14
 * @ $Author$
 * @ $Revision$
 */
public class VSPLoanReplicaDestination  extends ReplicaDestinationBase
{
	private int         recCount;
	private Transaction transaction;

	public void initialize(GateFactory factory) throws GateException
	{
		super.initialize();

		if (transaction != null && transaction.isActive())
		{
			throw new RuntimeException("Обнаружена активная транзакция");
		}

		recCount       = 0;
		transaction    = getSession().beginTransaction();
	}

	public Iterator iterator() throws GateException, GateLogicException
	{
		return Collections.EMPTY_LIST.iterator();
	}

	public void add(DictionaryRecord newValue) throws GateException
	{
		try
		{
			VSPLoanReplicaDepartment dep = (VSPLoanReplicaDepartment) newValue;
			Session session = getSession();
			Query query = session.getNamedQuery("com.rssl.phizic.business.dictionaries.offices.extended.ExtendedDepartment.updateLoanAvailability");
			query.setParameter("loanAvailability", dep.isLoanAvalible());
			query.setParameter("tb", dep.getTb());
			query.setParameter("osb", dep.getOsb());
			query.setParameter("office", dep.getOffice());
			if (query.executeUpdate() == 0);
				addError("Не удалость обновить признак возможность выдачи  кредита для подрасдиления с ТБ:" + dep.getTb() + " ОСБ:" + dep.getOsb() + " ВСП:" + dep.getOffice());
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

	public void close()
	{
		try
		{
			if (transaction != null && !transaction.wasRolledBack())
			{
				transaction.commit();
			}
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
	public void remove(DictionaryRecord oldValue) throws GateException  {}
	public void update(DictionaryRecord oldValue, DictionaryRecord newValue) throws GateException {}
}
