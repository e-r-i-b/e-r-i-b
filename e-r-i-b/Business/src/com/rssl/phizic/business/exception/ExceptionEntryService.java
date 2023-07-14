package com.rssl.phizic.business.exception;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.business.dictionaries.synchronization.log.ChangeType;
import com.rssl.phizic.business.dictionaries.synchronization.log.DictionaryRecordChangeInfoService;
import com.rssl.phizic.business.locale.MultiLocaleQueryHelper;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.query.BeanQuery;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.exceptions.ExceptionEntry;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.cache.Cache;
import com.rssl.phizic.utils.cache.OnCacheOutOfDateListener;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;

import java.util.List;

/**
 * @author mihaylov
 * @ created 12.04.2013
 * @ $Author$
 * @ $Revision$
 */
public class ExceptionEntryService
{
	private static final String QUERY_NAME = ExceptionMapping.class.getName() + ".getMessage";
	private static final String GET_BY_HASH_QUERY_NAME = ExceptionMapping.class.getName() + ".getByHash";
	private static final String GET_BY_HASH_AND_GROUP_QUERY_NAME = ExceptionMapping.class.getName() + ".getByHashAndGroup";

	private static SimpleService simpleService = new SimpleService();
	private static DictionaryRecordChangeInfoService changeInfoService = new DictionaryRecordChangeInfoService();

	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static final Cache<ExceptionMessageKey, String> CACHE = new Cache<ExceptionMessageKey, String>(new OnCacheOutOfDateListener<ExceptionMessageKey, String>()
	{
		public String onRefresh(ExceptionMessageKey key)
		{
			try
			{
				BeanQuery query = MultiLocaleQueryHelper.getQuery(QUERY_NAME);
				query.setParameter("hash", key.getHash());
				query.setParameter("application", key.getApplication());
				query.setParameter("tb", key.getTb());
				return query.executeUnique();
			}
			catch (Exception e)
			{
				log.error("Ошибка получения текстовки для исключения " + key.getHash(), e);
				return StringUtils.EMPTY;
			}
		}
	}, 15L);

	/**
	 * Добавить или обновить запись об ошибке
	 * @param list записи об ошибке
	 * @return сохраненная запись
	 * @throws BusinessException
	 */
	public List<ExceptionMapping> addExceptionMappings(List<ExceptionMapping> list) throws BusinessException
	{
		List<ExceptionMapping> mappings = simpleService.addList(list, MultiBlockModeDictionaryHelper.getDBInstanceName());
		addToLog(mappings, ChangeType.update);

		return mappings;
	}

	/**
	 * @param id - идентификатор ошибки
	 * @return сообщение об ошибке
	 * @throws BusinessException
	 */
	public ExceptionEntry getById(Long id) throws BusinessException
	{
		return simpleService.findById(ExceptionEntry.class, id, Constants.DB_INSTANCE_NAME);
	}

	/**
	 * получить сообщение для исключения из БД логов
	 * @param hash хеш исключения
	 * @param exceptionEntryApplication приложение в рамках которого ищем сообщение
	 * @param tb тербанк в рамках которого ищем исключение
	 * @return сообщение
	 */
	public String getMessage(final String hash, final ExceptionEntryApplication exceptionEntryApplication, final String tb)
	{
		return CACHE.getValue(new ExceptionMessageKey(hash, tb, exceptionEntryApplication.name()));
	}

	/**
	 * Получить список сообщений об ошибке по хэшу
	 * @param hash хэш
	 * @return список сообщений об ошибке
	 * @throws BusinessException
	 */
	public List<ExceptionMapping> getByHash(final String hash) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(MultiBlockModeDictionaryHelper.getDBInstanceName()).execute(new HibernateAction<List<ExceptionMapping>>()
			{
				public List<ExceptionMapping> run(Session session) throws Exception
				{
					return (List<ExceptionMapping>) session.getNamedQuery(GET_BY_HASH_QUERY_NAME)
							.setParameter("hash", hash)
							.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Удаляет ExceptionMapping по хешу
	 * @param hash хэш
	 * @throws BusinessException
	 */
	public void removeExceptionMapping(String hash) throws BusinessException
	{
		List<ExceptionMapping> mappings = getByHash(hash);

		try
		{
			if (CollectionUtils.isNotEmpty(mappings))
			{
				simpleService.deleteAll(mappings, MultiBlockModeDictionaryHelper.getDBInstanceName());
				addToLog(mappings, ChangeType.delete);
			}
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получить маппинг по хешу и группе
	 * @param hash хеш
	 * @param group группа
	 * @return маппинг
	 * @throws BusinessException
	 */
	public ExceptionMapping getByHashAndGroup(final String hash, final Long group) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<ExceptionMapping>()
			{
				public ExceptionMapping run(Session session) throws Exception
				{
					return (ExceptionMapping) session.getNamedQuery(GET_BY_HASH_AND_GROUP_QUERY_NAME)
							.setParameter("hash", hash)
							.setParameter("group_id", group)
							.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	private void addToLog(List<ExceptionMapping> resources, ChangeType type) throws BusinessException
	{
		changeInfoService.addChangesToLog(resources, type);
	}
}
