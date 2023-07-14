package com.rssl.phizic.dataaccess;

import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.*;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.Calendar;
import java.util.List;

/**
 * @author krenev
 * @ created 27.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class DatabaseServiceBase
{
	protected static final int BATCH_SIZE = 1000;
	/**
	 * �������� �������� � ��
	 * @param obj ����������� ������ ��� ����������
	 * @param instanceName ������� ��
	 * @param <T> ��� ��������
	 * @return ����������� ��������
	 * @throws Exception
	 */
	public <T> T add(final T obj, String instanceName) throws Exception
	{
		return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<T>()
		{
			public T run(Session session) throws Exception
			{
				session.save(obj);
				return obj;
			}
		}
		);
	}

	/**
	 * �������� �������� � ��.
	 * @param obj ����������� ������ ��� ����������
	 * @param instanceName ������� ��
	 * @param <T> ��� ��������
	 * @return ����������� ��������
	 * @throws Exception
	 */
	public <T> T update(final T obj, String instanceName) throws Exception
	{
		return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<T>()
		{
			public T run(Session session) throws Exception
			{
				session.update(obj);
				return obj;
			}
		}
		);
	}

	/**
	 * �������� �������� � ��.
	 * @param obj ����������� ������ ��� ����������
	 * @param instanceName ������� ��
	 * @param <T> ��� ��������
	 * @return ����������� ��������
	 * @throws Exception
	 */
	public <T> T addOrUpdate(final T obj, String instanceName) throws Exception
	{
		return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<T>()
		{
			public T run(Session session) throws Exception
			{
				session.saveOrUpdate(obj);
				return obj;
			}
		}
		);
	}

	/**
	 * ������� �������� �� ��.
	 * @param obj ����������� ������ ��� ����������
	 * @param instanceName ������� ��
	 * @param <T> ��� ��������
	 * @return ����������� ��������
	 * @throws Exception
	 */
	public <T> T  delete(final T obj, String instanceName) throws Exception
	{
		return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<T>()
		{
			public T run(Session session) throws Exception
			{
				session.delete(obj);
				return obj;
			}
		}
		);
	}

	/**
	 * ������� ������ ��������� �� ��.
	 * @param list ������
	 * @param instanceName ������� ��
	 * @throws Exception
	 */
	public void deleteAll(final List<?> list, String instanceName) throws Exception
	{
		HibernateExecutor trnExecutor = HibernateExecutor.getInstance(instanceName);
		trnExecutor.execute(new HibernateAction<Void>()
		{
			public Void run(Session session) throws Exception
			{
				for (Object obj : list)
				{
					session.delete(obj);
				}

				return null;
			}
		});
	}

	/**
	 * ����� ������ �������� �� ��������
	 * @param detachedCriteria �������� ������
	 * @param lockMode ��� ����������.
	 * @param instanceName ������� ��
	 * @param <T> ��� ��������
	 * @return ������ ���������.
	 * @throws Exception
	 */
	public <T> List<T> find(final DetachedCriteria detachedCriteria, final LockMode lockMode, String instanceName) throws Exception
	{
		return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<List<T>>()
		{
			public List<T> run(Session session) throws Exception
			{
				Criteria criteria = detachedCriteria.getExecutableCriteria(session);
				if (lockMode != null)
				{
					criteria.setLockMode(lockMode);
				}
				//noinspection unchecked
				return criteria.list();
			}
		}
		);
	}

	/**
	 * ����� ������������ ������ �� ��������
	 * @param detachedCriteria �������� ������
	 * @param lockMode ��� ����������.
	 * @param instanceName ������� ��
	 * @param <T> ��� ��������
	 * @return ������ ���������.
	 */
	public <T> T findSingle(final DetachedCriteria detachedCriteria, final LockMode lockMode, String instanceName) throws Exception
	{
		return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<T>()
		{
			public T run(Session session) throws Exception
			{
				Criteria criteria = detachedCriteria.getExecutableCriteria(session);
				if (lockMode != null)
					criteria.setLockMode(lockMode);
				//noinspection unchecked
				return (T) criteria.uniqueResult();
			}
		}
		);
	}

	/**
	 * ����� �������� �� ���������� �����.
	 * @param clazz ����� ��������
	 * @param id ������������
	 * @param lockMode ����� ���������
	 * @param instanceName ������� ��
	 * @param <T> ��� ��������
	 * @return ��������� ��������. ���� �� ������� ��� ������� ����� 1 ����������� null
	 * @throws Exception
	 */
	public <T> T findById(Class<T> clazz, Long id, LockMode lockMode, String instanceName) throws Exception
	{
		if (id == null)
			return null;

		return this.<T>findSingle(DetachedCriteria.forClass(clazz).add(Expression.eq("id", id)), lockMode, instanceName);
	}

	/**
	 * �������� ������� ���� � ����� ������� ���� ������ � ��� ���������
	 * @param instanceName ������� ��
	 * @return ���� � �����
	 * @throws Exception
	 */
	public Calendar getDatabaseSysDate(String instanceName) throws Exception
	{
		return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Calendar>()
		{
			public Calendar run(Session session) throws Exception
			{
				SQLQuery query = session.createSQLQuery("SELECT SYSTIMESTAMP systemdate FROM DUAL");
				query.addScalar("systemdate", Hibernate.CALENDAR);
				return (Calendar) query.uniqueResult();
			}
		});
	}

	/**
	 * �������� ������� ���� � ����� ������� ���� ������
	 * @param instanceName ������� ��
	 * @return ���� � �����
	 * @throws Exception
	 */
	public Calendar getDatabaseCurSysDate(String instanceName) throws Exception
	{
		return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Calendar>()
		{
			public Calendar run(Session session) throws Exception
			{
				SQLQuery query = session.createSQLQuery("SELECT SYSDATE systemdate FROM DUAL");
				query.addScalar("systemdate", Hibernate.CALENDAR);
				return (Calendar) query.uniqueResult();
			}
		});
	}

	/**
	 * �������� ������ ��������� � ��
	 * @param list ������ ���������
	 * @param instanceName ��� �������� ��
	 * @param <T> ��� ��������
	 * @return ����������� ������
	 * @throws Exception
	 */
	public <T> List<T> addList(final List<T> list, String instanceName) throws Exception
	{
		HibernateExecutor trnExecutor = HibernateExecutor.getInstance(instanceName);
		return trnExecutor.execute(new HibernateAction<List<T>>()
		{
			public List<T> run(Session session) throws Exception
			{
				int count = 0;
				for (T obj : list)
				{
					session.save(obj);
					count++;
					// ���� � ������ ���������� �������� ���������� ����� ������, ���������� �� � ��
					if (count >= BATCH_SIZE) {
						session.flush();
						session.clear();
						count = 0;
					}
				}

				return list;
			}
		}
		);
	}

	/**
	 * ����� ��� ����������/���������� ����� ������ � ��
	 * @param list ������ ����������� ��������
	 * @param instanceName �������� ����
	 * @param <T> ��� �������
	 * @return ������ ����������� ��������
	 * @throws Exception
	 */
	public <T> List<T> addOrUpdateList(final List<T> list, String instanceName) throws Exception
	{
		HibernateExecutor trnExecutor = HibernateExecutor.getInstance(instanceName);
		return trnExecutor.execute(new HibernateAction<List<T>>()
		{
			public List<T> run(Session session) throws Exception
			{
				int count = 0;
				for (T obj : list)
				{
					session.saveOrUpdate(obj);
					count++;

					// ���� � ������ ���������� �������� ���������� ����� ������, ���������� �� � ��
					if (count >= BATCH_SIZE) {
						session.flush();
						session.clear();
						count = 0;
					}
				}

				return list;
			}
		});
	}
}
