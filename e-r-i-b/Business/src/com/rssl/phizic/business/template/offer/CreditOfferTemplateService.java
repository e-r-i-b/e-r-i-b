package com.rssl.phizic.business.template.offer;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.dictionaries.synchronization.log.ChangeType;
import com.rssl.phizic.business.dictionaries.synchronization.log.DictionaryRecordChangeInfoService;
import com.rssl.phizic.cache.CacheHelper;
import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.DateHelper;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

import java.util.*;

/**
 * @author Balovtsev
 * @since 04.06.2015.
 */
public class CreditOfferTemplateService
{
	private static final SimpleService                     SIMPLE_SERVICE     = new SimpleService();
	private static final DictionaryRecordChangeInfoService DICTIONARY_SERVICE = new DictionaryRecordChangeInfoService();

	private static final Log LOG = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private final Cache cache;

	/**
	 * @param cacheName название кэша хранящего список шаблонов кредитных оферт
	 */
	public CreditOfferTemplateService(String cacheName)
	{
		cache = CacheProvider.getCache(cacheName);
	}

	/**
	 * Осуществляет поиск шаблона оферты по его идентификатору
	 * ИЗМЕНЯТЬ ВОЗВРАЩАЕМЫЙ ОБЪЕКТ В КЛИЕНТСКОМ ПРИЛОЖЕНИИ НЕЛЬЗЯ! ИСПОЛЬЗОВАТЬ КОПИЮ ПРИ РАБОТЕ!
	 *
	 * @param  id идентификатор шаблона
	 *
	 * @return шаблон оферты
	 * @throws BusinessException
	 */
	public CreditOfferTemplate find(Long id) throws BusinessException
	{
		CreditOfferTemplate cacheValue = findCached(id);

		if (cacheValue == null)
		{
			return SIMPLE_SERVICE.findById(CreditOfferTemplate.class, id);
		}

		return cacheValue;
	}

	/**
	 * Предназначен для поиска пдп оферты.
	 * ИЗМЕНЯТЬ ВОЗВРАЩАЕМЫЙ ОБЪЕКТ В КЛИЕНТСКОМ ПРИЛОЖЕНИИ НЕЛЬЗЯ! ИСПОЛЬЗОВАТЬ КОПИЮ ПРИ РАБОТЕ!
	 *
	 * @return возвращает пдп оферту
	 */
	public CreditOfferTemplate findPdp()
	{
		List<CreditOfferTemplate> cachedValues = getCachedValues(true);

		if (cachedValues.size() > 0)
		{
			CreditOfferTemplate template = cachedValues.get(cachedValues.size() - 1);

			if (template.getType() == CreditOfferTemplateType.PDP)
			{
				return template;
			}
		}

		return null;
	}

	/**

	/**
	 * Сохраняет/обновляет шаблон кредитной оферты.
	 * Действие метода распространяется не только на бд, но и на кэш (при этом кэш обновляется)
	 *
	 * @param template сохраняемый шаблон
	 * @param instance инстанс бд
	 *
	 * @return сохраненный шаблон
	 */
	public CreditOfferTemplate save(CreditOfferTemplate template, String instance) throws BusinessException, BusinessLogicException
	{
		CreditOfferTemplate       temporary    = null;
		List<CreditOfferTemplate> cachedValues = getCachedValues(true);
		Calendar currDate = DateHelper.clearSeconds(Calendar.getInstance());

		/*
		 * В кэше, список шаблонов лежит отсортированный таким образом, что первым элементом
		 * будет шаблон в статусе OPERATE, а последним шаблон ПДП
		 */
		boolean hasOperate = cachedValues.size() > 0 && (cachedValues.get(0).getStatus()  == Status.OPERATE);
		boolean isPdp      = template.getType() == CreditOfferTemplateType.PDP;

		if (!isPdp)
		{
			if (template.getTo() == null)
			{
				/*
				 * Если нет шаблонов в статусе OPERATE, то дата сохраняемого шаблона не может
				 * быть меньше текущей системной
				 */
				if (!hasOperate)
				{
					if (currDate.after(template.getFrom()))
					{
						throw new BusinessLogicException("credit.loan.offer.credit.offer.past.exception");
					}
				}

				/*
				 * Не может быть 2ух шаблонов с одинаковыми датами
				 * начала действия шаблона
				 */
				for (CreditOfferTemplate cached : cachedValues)
				{
					Calendar templateFrom = template.getFrom();
					Calendar cachedFrom   = cached.getFrom();

					if (templateFrom.equals(cachedFrom) && !cached.getId().equals(template.getId()))
					{
						throw new BusinessLogicException("credit.loan.offer.duplicate.exception");
					}
				}
			}
			else
			{
				/*
				 * Архивируемые записи не нужно проверять
				 */
				temporary = findCached(template.getId());
			}
		}

		Session     session     = null;
		Transaction transaction = null;

		try
		{
			session     = HibernateExecutor.getSessionFactory(instance).openSession();
			transaction = session.beginTransaction();

			if (template.getStatus() == Status.INTRODUCED)
			{
				Calendar fromDate = DateHelper.clearSeconds((Calendar) template.getFrom().clone());

				if (currDate.equals(fromDate))
				{
					// Необходимо дополнительно обновить шаблон бывший ранее в статусе OPERATE
					if (hasOperate)
					{
						temporary = cachedValues.get(0);
						temporary.setTo(currDate);
						session.saveOrUpdate(temporary);

						DICTIONARY_SERVICE.addChangesToLog(session, temporary, ChangeType.update);
					}
				}
			}

			session.saveOrUpdate(template);
			DICTIONARY_SERVICE.addChangesToLog(template, ChangeType.update);

			transaction.commit();
		}
		catch (Exception e)
		{
			if (temporary != null)
			{
				temporary.setStatus(Status.OPERATE);
				temporary.setTo(null);
			}

			if (transaction != null)
			{
				transaction.rollback();
			}

			throw new BusinessException(e);
		}
		finally
		{
			if (session != null)
			{
				session.close();
			}
		}

		/*
		 * в кэше обновляется только если там что-то было
		 */
		updateCached(template);
		return template;
	}

	/**
	 * Удаляет шаблон кредитной оферты.
	 * Удаление осуществляется не только из бд, но и из кэша (при этом кэш обновляется)
	 *
	 * @param template удаляемый шаблон
	 * @param instance инстанс бд
	 */
	public void remove(CreditOfferTemplate template, String instance) throws BusinessException
	{
		SIMPLE_SERVICE.remove(template, instance);
		DICTIONARY_SERVICE.addChangesToLog(template, ChangeType.delete);

		/*
		 * из кэша удаляется только если там что-то было
		 */
		removeCached(template.getId());
	}

	private void updateCached(CreditOfferTemplate template) throws BusinessException
	{
		List<CreditOfferTemplate>         templates = new ArrayList<CreditOfferTemplate>(getCachedValues(false));
		ListIterator<CreditOfferTemplate> iterator  = templates.listIterator();

		while (iterator.hasNext())
		{
			CreditOfferTemplate next = iterator.next();

			if (next.getId().equals(template.getId()))
			{
				iterator.remove();
				break;
			}
		}

		templates.add(template);

		// Перевыставить статусы после изменения шаблона
		CollectionUtils.forAllDo(templates, new CreditOfferTemplateHandler());

		// Отсортировать элементы
		Collections.sort(templates, new CreditOfferTemplateSortComparator());

		CacheHelper.put2cache(cache, 1L, templates);
	}

	private void removeCached(Long id) throws BusinessException
	{
		List<CreditOfferTemplate> templates = new ArrayList<CreditOfferTemplate>(getCachedValues(false));
		if (templates.isEmpty())
		{
			return;
		}

		ListIterator<CreditOfferTemplate> iterator  = templates.listIterator();
		while (iterator.hasNext())
		{
			if (iterator.next().getId().equals(id))
			{
				iterator.remove();
				break;
			}
		}

		// Перевыставить статусы после изменения шаблона
		CollectionUtils.forAllDo(templates, new CreditOfferTemplateHandler());

		// Отсортировать элементы
		Collections.sort(templates, new CreditOfferTemplateSortComparator());

		CacheHelper.put2cache(cache, 1L, templates);
	}

	private CreditOfferTemplate findCached(Long id) throws BusinessException
	{
		for (CreditOfferTemplate value : getCachedValues(false))
		{
			if (value.getId().equals(id))
			{
				return value;
			}
		}

		return null;
	}

	/**
	 * Вызывыть в клиентском приложении или при крайней надобности
	 *
	 * @return Активный шаблон
	 * @throws BusinessException
	 */
	public CreditOfferTemplate getActiveTemplate() throws BusinessException
	{
		List<CreditOfferTemplate> offerTemplates = getCachedValues(true);

		if (offerTemplates.size() > 0)
		{
			return new CreditOfferTemplate(offerTemplates.get(0));
		}

		return null;
	}

	private List<CreditOfferTemplate> getCachedValues(boolean force)
	{
		try
		{
			Element element = cache.get(1L);

			if (element == null)
			{
				if (force)
				{
					List<CreditOfferTemplate> all = SIMPLE_SERVICE.getAll(CreditOfferTemplate.class);

					if (all.size() == 0)
					{
						return Collections.emptyList();
					}

					// Перевыставить статусы
					CollectionUtils.forAllDo(all, new CreditOfferTemplateHandler());

					// Отсортировать элементы
					Collections.sort(all, new CreditOfferTemplateSortComparator());

					return CacheHelper.put2cache(cache, 1L, all);
				}

				return Collections.emptyList();
			}

			//noinspection unchecked
			return (List<CreditOfferTemplate>) element.getValue();
		}
		catch (Exception e)
		{
			LOG.error("Ошибка при попытке получить список шаблонов из кэша", e);
		}

		return Collections.emptyList();
	}
}
