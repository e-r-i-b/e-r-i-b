package com.rssl.phizic.locale.services;

import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.dataaccess.DatabaseServiceBase;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.query.BeanQuery;
import com.rssl.phizic.locale.entities.ERIBStaticMessage;
import com.rssl.phizic.locale.utils.LocaleHelper;
import com.rssl.phizic.utils.ListTransformer;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.ArrayList;
import java.util.List;

/**
 * Сервис для работы с мультиязычными текстовками
 * @author komarov
 * @ created 25.11.2014
 * @ $Author$
 * @ $Revision$
 */
public class MultiInstanceEribStaticMessageService
{
	protected static final DatabaseServiceBase databaseService = new DatabaseServiceBase();

	/**
	 * Добавить запись
	 * @param message текстовка
	 * @param instance имя инстанса БД
	 * @return добавленная текстовка
	 * @throws SystemException
	 */
	public ERIBStaticMessage add(ERIBStaticMessage message, String instance) throws SystemException
	{
		try
		{
			return databaseService.add(message, instance);
		}
		catch (Exception e)
		{
			throw new SystemException(e);
		}
	}

	/**
	 * Добавить запись
	 * @param messages текстовки
	 * @param instance имя инстанса БД
	 * @return добавленная текстовка
	 * @throws SystemException
	 */
	public List<ERIBStaticMessage> add(List<ERIBStaticMessage> messages, String instance) throws SystemException
	{
		try
		{
			return databaseService.addList(messages, instance);
		}
		catch (Exception e)
		{
			throw new SystemException(e);
		}
	}

	/**
	 * Обновить запись
	 * @param message текстовка
	 * @param instance имя инстанса БД
	 * @return обновлённая текстовка
	 * @throws SystemException
	 */
	public ERIBStaticMessage update(ERIBStaticMessage message, String instance) throws SystemException
	{
		try
		{
			return databaseService.addOrUpdate(message, instance);
		}
		catch (Exception e)
		{
			throw new SystemException(e);
		}
	}

	/**
	 * Обновить запись
	 * @param message текстовка
	 * @param instance имя инстанса БД
	 * @return обновлённая текстовка
	 * @throws SystemException
	 */
	public List<ERIBStaticMessage> update(List<ERIBStaticMessage> message, String instance) throws SystemException
	{
		try
		{
			return databaseService.addOrUpdateList(message, instance);
		}
		catch (Exception e)
		{
			throw new SystemException(e);
		}
	}

	/**
	 * Удалить текстовку
	 * @param message текстовка
	 * @param instance имя инстанса БД
	 * @throws SystemException
	 */
	public void delete(ERIBStaticMessage message, String instance) throws SystemException
	{
		try
		{
			databaseService.delete(message, instance);
		}
		catch (Exception e)
		{
			throw new SystemException(e);
		}
	}

	/**
	 * Удалить текстовку
	 * @param message текстовка
	 * @param instance имя инстанса БД
	 * @throws SystemException
	 */
	public void delete(List<ERIBStaticMessage> message, String instance) throws SystemException
	{
		try
		{
			databaseService.deleteAll(message, instance);
		}
		catch (Exception e)
		{
			throw new SystemException(e);
		}
	}

	/**
	 * Возвращает все записи
	 * @param localeId идентификатор локали
	 * @param instance имя инстанса БД
	 * @return текстовка
	 * @throws SystemException
	 */
	public List<ERIBStaticMessage> getAll(String localeId, String instance) throws SystemException
	{
		try
		{
			DetachedCriteria criteria = DetachedCriteria.forClass(ERIBStaticMessage.class);
			criteria.add(Expression.eq("localeId", localeId));
			return databaseService.find(criteria, null, instance);
		}
		catch (Exception e)
		{
			throw new SystemException(e);
		}
	}

	/**
	 * Возвращает все записи для заданных локали и бандла
	 * @param localeId идентификатор локали
	 * @param bundle бандл
	 * @param instance имя инстанса БД
	 * @return текстовка
	 * @throws SystemException
	 */
	public List<ERIBStaticMessage> getAll(String localeId, String bundle, String instance) throws SystemException
	{
		try
		{
			DetachedCriteria criteria = DetachedCriteria.forClass(ERIBStaticMessage.class);
			criteria.add(Expression.eq("localeId", localeId))
					.add(Expression.eq("bundle", bundle));
			return databaseService.find(criteria, null, instance);
		}
		catch (Exception e)
		{
			throw new SystemException(e);
		}
	}

	/**
	 * Возвращает все изменяемые записи с учётом дефолтной локали
	 * @param localeId идентификатор локали
	 * @param instance имя инстанса БД
	 * @return текстовки
	 * @throws SystemException
	 */
	public List<ERIBStaticMessage> getMessagesForReplication(final String localeId, String instance) throws SystemException
	{
		if ( LocaleHelper.isDefaultLocale(localeId) )
			return getDefaultLocaleMessages(instance);
		return getMessagesForLocale(localeId, instance);

	}

	private List<ERIBStaticMessage> getDefaultLocaleMessages(String instance) throws SystemException
	{
		try
		{
			return HibernateExecutor.getInstance(instance).execute( new HibernateAction<List<ERIBStaticMessage>>()
			{
				public List<ERIBStaticMessage> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(ERIBStaticMessage.class.getName()+".getMessagesForLocaleRU");
					//noinspection unchecked
					return (List<ERIBStaticMessage>)query.list();
				}
			});
		}
		catch(Exception e)
		{
			throw new SystemException(e);
		}
	}

	/**
	 * Возвращает все возможные записи для локали
	 * @param localeId идентификатор локали
	 * @param instance имя инстанса БД
	 * @return текстовки
	 * @throws SystemException
	 */
	public List<ERIBStaticMessage> getMessagesForLocale(final String localeId, String instance) throws SystemException
	{
		BeanQuery query = new BeanQuery(new Object(), ERIBStaticMessage.class.getName()+".getMessagesForLocale", instance);
		query.setParameter("locale", localeId);
		query.setListTransformer(new ListTransformer<ERIBStaticMessage, Object[]>()
		{
			public List<ERIBStaticMessage> transform(List<Object[]> input) throws SystemException
			{
				List<ERIBStaticMessage> resultList = new ArrayList<ERIBStaticMessage>(input.size());
				for(Object resultElement: input)
				{
					Object[] res = (Object[])resultElement;
					ERIBStaticMessage message = new ERIBStaticMessage();
					message.setId((Long)res[0]);
					message.setLocaleId((String)res[1]);
					message.setBundle((String)res[2]);
					message.setKey((String)res[3]);
					message.setMessage((String)res[4]);
					resultList.add(message);
				}
				return resultList;
			}
		});
		try
		{
			return query.executeListInternal();
		}
		catch(Exception e)
		{
			throw new SystemException(e);
		}
	}

	/**
	 * Удаляет все статические сообщения для локали
	 * @param localeId идентификатор локали
	 * @param instance имя инстанса БД
	 */
	public void deleteLocaleMessages(final String localeId, String instance) throws Exception
	{
		HibernateExecutor.getInstance(instance).execute(new HibernateAction<Object>()
		{
			public Object run(Session session) throws Exception
			{
				Query query = session.getNamedQuery(ERIBStaticMessage.class.getName()+".deleteLocale");
				query.setParameter("localeId",localeId);
				return query.executeUpdate();
			}
		});
	}
}
