package com.rssl.phizic.gate.dictionaries;

import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Iterator;
import javax.naming.NameNotFoundException;

/**
 * @author Balovtsev
 * @version 23.04.13 9:41
 */
public abstract class NamedQueryReplicaSource implements ReplicaSource
{
	protected String  namedQuery;
	protected Session session;

	protected NamedQueryReplicaSource(String namedQuery)
	{
		this.namedQuery = namedQuery;
	}

	public void initialize(GateFactory factory) throws GateException
	{
		if (session != null)
		{
			throw new RuntimeException("Oбнаружена незакрытая сессия, вызовите метод close()!");
		}

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

	protected Session getSession()
	{
		return session;
	}

	public Iterator iterator()
	{
		Query  query = session.getNamedQuery(namedQuery);
		return query.list().iterator();
	}

	public void close()
	{
		if(session == null)
		{
			return;
		}

		session.close();
		session = null;
	}

	protected String getInstanceName()
	{
		return null;
	}
}
