package com.rssl.phizic.business.dictionaries.currencies;

import com.rssl.phizic.business.hibernate.DataBaseTypeQueryHelper;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.dictionaries.ReplicaDestination;
import com.rssl.phizic.gate.exceptions.GateException;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;
import javax.naming.NameNotFoundException;

/**
 * @author Omeliyanchuk
 * @ created 18.09.2006
 * @ $Author$
 * @ $Revision$
 */

public class CurrencyDestination implements ReplicaDestination
{
	private Session session;

	public void add(DictionaryRecord newValue) throws GateException
	{
		try
		{
			CurrencyImpl newCurrency = new CurrencyImpl();
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
		Query query = DataBaseTypeQueryHelper.getNamedQuery(session, "com.rssl.phizic.business.dictionaries.currencies.getAllCurrency");
		//noinspection unchecked
		List<CurrencyImpl> list = query.list();
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
