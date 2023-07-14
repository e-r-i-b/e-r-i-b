package com.rssl.phizic.business.ext.sbrf.technobreaks;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.phizgate.ext.sbrf.technobreaks.PeriodicType;
import com.rssl.phizgate.ext.sbrf.technobreaks.TechnoBreak;
import com.rssl.phizgate.ext.sbrf.technobreaks.TechnoBreakStatus;
import com.rssl.phizgate.ext.sbrf.technobreaks.TechnoBreaksChecker;
import com.rssl.phizgate.ext.sbrf.technobreaks.locale.LocaledTechnoBreak;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.ext.sbrf.technobreaks.event.UpdateTechnoBreaksEvent;
import com.rssl.phizic.business.locale.MultiLocaleQueryHelper;
import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizic.config.locale.MultiLocaleContext;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.query.BeanQuery;
import com.rssl.phizic.dataaccess.query.ExecutorQuery;
import com.rssl.phizic.events.EventSender;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizic.locale.entities.ERIBLocale;
import com.rssl.phizic.locale.utils.LocaleHelper;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Calendar;
import java.util.List;

/**
 * Сервис для технологических перерывов
 *
 * @author niculichev
 * @ created 10.10.2011
 * @ $Author$
 * @ $Revision$
 */
public class TechnoBreaksService
{
	private static final String QUERY_PREFIX = TechnoBreak.class.getName() + ".";

	private static final SimpleService simpleService = new SimpleService();

	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final Object LOCKER = new Object();
	private static final Cache CACHE = CacheProvider.getCache("techno-breaks-cache");
	private static final String DELIMITER = "|";

	private String getCacheKey(String key)
	{
		return key + DELIMITER + MultiLocaleContext.getLocaleId();
	}

	private List<TechnoBreak> getFromCache(String key)
	{
		Element element = CACHE.get(getCacheKey(key));
		if (element == null)
			return null;

		//noinspection unchecked
		return (List<TechnoBreak>) element.getObjectValue();
	}

	private List<TechnoBreak> addToCache(String key, List<TechnoBreak> technoBreaks)
	{
		CACHE.put(new Element(getCacheKey(key), technoBreaks));
		return technoBreaks;
	}

	static void doClearCache(String key)
	{
		synchronized (LOCKER)
		{
			for(ERIBLocale locale : LocaleHelper.getAllLocales())
				CACHE.remove(key + DELIMITER + locale.getId());
		}
	}

	private void clearCache(String externalSystemCode)
	{
		try
		{
			EventSender.getInstance().sendEvent(new UpdateTechnoBreaksEvent(externalSystemCode));
		}
		catch (Exception e)
		{
			log.error("Ошибка при отправке сообщения изменении соства тех. перерывов в системе.", e);
		}

	}

	/**
	 * Ищет технологический перерыв по идентификатору
	 * @param id ижентификатор
	 * @return технологический перерыв
	 * @throws BusinessException
	 */
	public TechnoBreak findById(Long id) throws BusinessException
	{
		return simpleService.findById(TechnoBreak.class, id);
	}

	/**
	 * Добавляет/Обновляет технологический перерыв
	 * @param technoBreak технологический перерыв
	 * @throws BusinessException
	 */
	public void saveOrUpdate(TechnoBreak technoBreak) throws BusinessException, BusinessLogicException
	{
		simpleService.addOrUpdate(technoBreak);
		if (ExternalSystemHelper.getMbkSystemCode().equals(technoBreak.getAdapterUUID()))
			csaSaveOrUpdateMbkTechnoBreak(technoBreak);
		clearCache(technoBreak.getAdapterUUID());
	}

	private List<TechnoBreak> getActualBreaksWithCache(final String externalSystemUUID) throws BusinessException
	{
		List<TechnoBreak> technoBreaks = getFromCache(externalSystemUUID);
		if (technoBreaks != null)
			return technoBreaks;

		synchronized (LOCKER)
		{
			List<TechnoBreak> technoBreakList = getFromCache(externalSystemUUID);
			if (technoBreakList != null)
				return technoBreakList;

			return addToCache(externalSystemUUID, getActualBreaks(externalSystemUUID));
		}
	}

	/**
	 * Получение актуальных технологических перерывов(текущая дата входит в диапазон действия тех перерыва)
	 * @param externalSystemUUID идентификатор внешней системы
	 * @return список технологических перерыво
	 */
	private List<TechnoBreak> getActualBreaks(final String externalSystemUUID) throws BusinessException
	{
		try
		{
			BeanQuery query = MultiLocaleQueryHelper.getQuery(QUERY_PREFIX + "findByAdapterCode");
			Calendar currentDate = Calendar.getInstance();
			query.setParameter("adapterCode", externalSystemUUID);
			query.setParameter("fromDate", currentDate);
			query.setParameter("toDate", currentDate);
			query.setParameter("status", TechnoBreakStatus.ENTERED.name());
			query.setParameter("autoBreaks", TechnoBreaksChecker.isOnlyManualRemoveAutoTechnoBreak());
			return query.executeListInternal();
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Возвращает первый активный, действующий в данный момент технологический перерыв
	 * @param externalSystemUUID uuid внешней системы
	 * @return первый активный, действующий в данный момент технологический перерыв
	 * @throws BusinessException
	 */
	public TechnoBreak getActiveBreak(String externalSystemUUID) throws BusinessException
	{
		List<TechnoBreak> technoBreaks = getActualBreaksWithCache(externalSystemUUID);
		return TechnoBreaksChecker.getActiveBreak(technoBreaks);
	}

	/**
	 * Получить все действующие в данный момент технологические перерывы
	 * @param externalSystemUUID uuid внешней системы
	 * @return список активных, действующих в данный момент технологических перерывов
	 * @throws BusinessException
	 */
	public List<TechnoBreak> getActiveBreaks(String externalSystemUUID) throws BusinessException
	{
		List<TechnoBreak> technoBreaks = getActualBreaksWithCache(externalSystemUUID);
		return TechnoBreaksChecker.getActiveBreaks(technoBreaks);
	}

	/**
	 * Возвращает список активных тех перерывов и список, тех тех. перерывов, которые перешли в статус Deleted
	 * за время между предудещей проверки и текущим временем.
	 *
	 * @param lastTestDate время предыдущей проверки.
	 * @return список тех. перерыввов.
	 * @throws BusinessException
	 */
	public List<TechnoBreak> getActiveOrJustStoppedTechnoBreaks(final Calendar lastTestDate) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<TechnoBreak>>()
			{
				public List<TechnoBreak> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + "listOfActive");
					query.setParameter("singlePeriodicType",    PeriodicType.SINGLE);
					query.setBoolean("availableRemoveAutoBreaks", !TechnoBreaksChecker.isOnlyManualRemoveAutoTechnoBreak());
					query.setCalendar("lastTestDate",    lastTestDate);
					query.setCalendar("currentTestDate", Calendar.getInstance());
					//noinspection unchecked
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * добавить автоматически выставленный тех. перерыв
	 * @param technoBreak тех. перерыв
	 * @throws BusinessException
	 */
	public void addAutoTechnoBreak(final TechnoBreak technoBreak) throws BusinessException
	{
		try
		{
			ExecutorQuery executorQuery = new ExecutorQuery(HibernateExecutor.getInstance(), QUERY_PREFIX + "addAutoTechnoBreak");

			executorQuery.setParameter("adapterUUID", technoBreak.getAdapterUUID());
			executorQuery.setParameter("periodic", technoBreak.getPeriodic().name());
			executorQuery.setParameter("defaultMessage", technoBreak.isDefaultMessage());
			executorQuery.setParameter("fromDate", technoBreak.getFromDate());
			executorQuery.setParameter("toDate", technoBreak.getToDate());
			executorQuery.setParameter("message", technoBreak.getMessage());
			executorQuery.setParameter("status", technoBreak.getStatus().name());
			executorQuery.setParameter("autoEnabled", technoBreak.isAutoEnabled());
			executorQuery.setParameter("allowOfflinePayments", technoBreak.isAllowOfflinePayments());
			executorQuery.setParameter("onlyManualRemove", TechnoBreaksChecker.isOnlyManualRemoveAutoTechnoBreak());
			executorQuery.setParameter("technoBreakUUID", technoBreak.getUuid().toString());

			executorQuery.executeUpdate();

			clearCache(technoBreak.getAdapterUUID());
		}
		catch (DataAccessException e)
		{
			throw new BusinessException("Ошибка добавления автоматического тех. перерыва.", e);
		}
	}

	private void csaSaveOrUpdateMbkTechnoBreak(TechnoBreak technoBreak) throws BusinessException, BusinessLogicException
	{
		try
		{
			CSABackRequestHelper.saveOrUpdateMbkTechnoBreak(technoBreak);
		}
		catch (BackLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (BackException e)
		{
			throw new BusinessException(e);
		}
	}
}
