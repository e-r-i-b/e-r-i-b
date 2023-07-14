package com.rssl.phizic.dataaccess.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.impl.SessionFactoryObjectFactory;

import java.util.Map;
import java.util.HashMap;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.NameNotFoundException;
import javax.naming.Reference;

public final class HibernateUtil
{
    private static final Log                         log   = LogFactory.getLog(HibernateUtil.class);
	private static final Map<String, SessionFactory> cache = new HashMap<String, SessionFactory>();


	/**
	 * ����� SessionFactory � JNDI
	 * @param  jndiName JNDI ��� �������
	 * @return �������
	 * @throws NameNotFoundException ������� �� �������
	 */
	public static synchronized SessionFactory lookupSessionFactory(String jndiName) throws NameNotFoundException
	{
		SessionFactory sessionFactory = cache.get(jndiName);

		if(sessionFactory != null)
			return sessionFactory;

		InitialContext context = null;
		try
		{
			context = new InitialContext();
			Object obj = context.lookup(jndiName);
			if (obj instanceof Reference)
			{
				Reference ref = (Reference) obj;
				sessionFactory = ((SessionFactory) SessionFactoryObjectFactory.getInstance((String) ref.get(0).getContent()));
			}
			else
				sessionFactory = (SessionFactory) obj;

			if (sessionFactory == null)
			{
				//GIV ��� ���� - ���� ����� ���� ������ ������ - �������
				throw new NameNotFoundException("SessionFactory not found. JNDI name: " + jndiName);
			}
			cache.put(jndiName, sessionFactory);
			return sessionFactory;
		}
		catch (NameNotFoundException e)
		{
			throw e;
		}
		catch (NamingException e)
		{   //todo ������ �����, �������� ��� ���������� ������ ������. ����������� ����������!!!
			throw new NameNotFoundException(e.getMessage());
		}
		finally
		{
			try
			{
				if (context != null)
					context.close();
			}
			catch (NamingException e)
			{
				log.error("failed close context", e);
			}
		}
	}

	/**
	 * �������� SessionFactory �� ����
	 * @param jndiName JNDI ��� �������
	 */
	public static synchronized void removeSessionFactoryFromCache(String jndiName)
	{
		cache.remove(jndiName);
	}
}