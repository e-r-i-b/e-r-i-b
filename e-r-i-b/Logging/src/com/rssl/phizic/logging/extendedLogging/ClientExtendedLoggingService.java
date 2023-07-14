package com.rssl.phizic.logging.extendedLogging;

import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.cache.LogCacheProvider;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Calendar;

/**
 * @author gladishev
 * @ created 07.05.2013
 * @ $Author$
 * @ $Revision$
 *
 * Сервис для работы с записями расширенного логирования
 */
public class ClientExtendedLoggingService
{
	public static final String CACHE_NAME = "clientExtendLogCache";
	private static final Object LOCKER = new Object();
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static Cache cache = LogCacheProvider.getCache(CACHE_NAME);

	private static ClientExtendedLoggingService instance = null;

	/**
	 * @return инстанс сервиса
	 */
	public static ClientExtendedLoggingService getInstance()
	{
		ClientExtendedLoggingService service = instance;
		if (service == null)
		{
			synchronized (LOCKER)
			{
				if (instance == null)
					instance = new ClientExtendedLoggingService();

				service = instance;
			}
		}
		return service;
	}

	/**
	 * Добавить запись расширенного логирования
	 * @param entry запись
	 * @return сохраненная запись
	 */
	public ClientExtendedLoggingEntry addOrUpdate( final ClientExtendedLoggingEntry entry) throws SystemException
	{
		try
		{
			ClientExtendedLoggingEntry result = getExecutor().execute(new HibernateAction<ClientExtendedLoggingEntry>()
				{
					public ClientExtendedLoggingEntry run(Session session) throws Exception
					{
						session.saveOrUpdate(entry);
						session.flush();
						return entry;
					}
				}
			);

			clearCache(entry.getLoginId());
			return result;
		}
		catch (Exception e)
		{
			throw new SystemException(e);
		}
	}

	/**
	 * Удалить запись расширенного логирования
	 * + обновление кеша
	 * @param entry - удаляемая запись
	 */
	public void remove(final ClientExtendedLoggingEntry entry) throws SystemException
	{
		try
		{
			getExecutor().execute(new HibernateAction<ClientExtendedLoggingEntry>()
				{
					public ClientExtendedLoggingEntry run(Session session) throws Exception
					{
						session.delete(entry);
						session.flush();
						return entry;
					}
				}
			);

			clearCache(entry.getLoginId());
		}
		catch (Exception e)
		{
			throw new SystemException(e);
		}
	}

	/**
	 * Возвращает запись расширенного логирования
	 * Предварительно запись проверяется на актуальность (см. метод validateEntry)
	 * @param loginId - идентификатор логина, для которого ищем запись
	 * @return запись расширенного логирования
	 */
	public ClientExtendedLoggingEntry getValidEntry(final Long loginId)
	{
		if (loginId == null)
			return null;

		Element element = cache.get(loginId);
		if (element != null)
		{
			ClientExtendedLoggingEntry cachedEntry = (ClientExtendedLoggingEntry) element.getValue();
			if (validateEntry(cachedEntry))
				return cachedEntry;
		}

		try
		{
			ClientExtendedLoggingEntry entry = getExecutor().execute(new HibernateAction<ClientExtendedLoggingEntry>()
			{
				public ClientExtendedLoggingEntry run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.logging.extendedLogging.findByLoginId");
					query.setParameter("loginId", loginId);
					return (ClientExtendedLoggingEntry)query.uniqueResult();
				}
			});

			if (validateEntry(entry))
			{
				cache.put(new Element(loginId, entry));
				return entry;
			}

			if (entry != null)
				remove(entry);
			else
				cache.put(new Element(loginId, null));

			return null;

		}
		catch (Exception e)
		{
			log.error("Ошибка при получении записи расширенного логирования. loginId=" + loginId, e);
			return null;
		}
	}

	private boolean validateEntry(ClientExtendedLoggingEntry entry)
	{
		if (entry == null)
			return true;

		Calendar currentDate = Calendar.getInstance();
		return entry.getStartDate().before(currentDate) && (entry.getEndDate() == null || entry.getEndDate().after(currentDate));
	}

	private HibernateExecutor getExecutor()
	{
		return HibernateExecutor.getInstance();
	}

	public void clearCache(Long loginId)
	{
		if (loginId != null)
			cache.remove(loginId);
		else
			log.error("Не указан loginId при очистке кеша расширенного логирования");
	}
}
