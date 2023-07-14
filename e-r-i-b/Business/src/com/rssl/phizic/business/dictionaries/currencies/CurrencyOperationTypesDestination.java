package com.rssl.phizic.business.dictionaries.currencies;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.dictionaries.ReplicaDestination;
import com.rssl.phizic.gate.dictionaries.CurrencyOperationType;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Query;

import java.util.Iterator;
import java.util.List;
import java.util.Collections;
import javax.naming.NameNotFoundException;

/**
 * @author Egorova
 * @ created 07.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class CurrencyOperationTypesDestination implements ReplicaDestination
{
	private Session session;

	private String hqlQuery;

	public CurrencyOperationTypesDestination()
	{
		this.hqlQuery = " select type  from com.rssl.phizic.gate.dictionaries.CurrencyOperationType as type order by type.id";
	}

	public void add(DictionaryRecord newValue) throws GateException
	{
		try
		{
			CurrencyOperationType newCurrency = new CurrencyOperationType();
			newCurrency.updateFrom(newValue);

			session.save(newCurrency);
		}
		catch (HibernateException e)
		{
			throw new GateException(e);
		}
	}

	public void remove(DictionaryRecord oldValue) throws GateException
	{
		try
		{
			session.delete(oldValue);
		}
		catch (HibernateException e)
		{
			throw new GateException(e);
		}
	}

	public void update(DictionaryRecord oldValue, DictionaryRecord newValue) throws GateException
	{
		try
		{
			oldValue.updateFrom(newValue);
			session.update(oldValue);
		}
		catch (HibernateException e)
		{
			throw new GateException(e);
		}
	}

	public void initialize(GateFactory factory) throws GateException
	{
	}

	public Iterator iterator()
	{
		if(session != null)
			throw new RuntimeException("Oбнаружена незакрытая сессия, вызовите метод close()!");

		try
		{
			//noinspection HibernateResourceOpenedButNotSafelyClosed
			session = HibernateExecutor.getSessionFactory().openSession();
		}
		catch (NameNotFoundException e)
		{
			throw new RuntimeException(e);
		}
		String hql = hqlQuery;
		Query query = session.createQuery(hql);
		//noinspection unchecked
		List<CurrencyOperationType> list = query.list();
		Collections.sort(list, new CurrencyOperationTypesComparator());
		return list.iterator();
	}

	public void close()
	{
		if (session == null)
			return;

		session.flush();
		session.close();
		session = null;
	}

    public List<String> getErrors()
	{
		return null;
	}
}
