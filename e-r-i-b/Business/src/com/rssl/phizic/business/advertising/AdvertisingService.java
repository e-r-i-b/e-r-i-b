package com.rssl.phizic.business.advertising;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.productRequirements.AccTypesRequirement;
import com.rssl.phizic.business.dictionaries.productRequirements.ProductRequirement;
import com.rssl.phizic.business.locale.MultiLocaleQueryHelper;
import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizic.config.locale.MultiLocaleCache;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.query.BeanQuery;
import com.rssl.phizic.utils.DateHelper;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Calendar;
import java.util.List;

/**
 * —ервис дл€ работы с баннерами.
 * @author komarov
 * @ created 19.12.2011
 * @ $Author$
 * @ $Revision$
 */

public class AdvertisingService extends MultiInstanceAdvertisingService
{
	private static Cache advertisingCache;
	private static MultiLocaleCache advertisingListApiCache;

	static
	{
		advertisingCache = CacheProvider.getCache("advertising-cache");
		advertisingListApiCache = MultiLocaleCache.getCache("advertising-list-api-cache");
	}

	/**
	 * ѕоиск баннера по идентификатору.
	 * @param id идентификатор
	 * @return баннер
	 * @throws BusinessException
	 */
	public AdvertisingBlock findById(Long id) throws BusinessException
	{
		String key = id.toString();
		Element element = advertisingCache.get(key);
        if (element == null)
		{
			AdvertisingBlock advertisingBlock = super.findById(id, null);
			advertisingCache.put(new Element(key, advertisingBlock));
			return advertisingBlock;
		}
		return (AdvertisingBlock) element.getObjectValue();
	}

	/**
	 * ѕоиск баннеров по идентификаторам.
	 * @param ids идентификаторы
	 * @return баннеры
	 * @throws BusinessException
	 */
	public List<AdvertisingBlock> findByIds(List<Long> ids) throws BusinessException
	{
		return simpleService.findByIds(AdvertisingBlock.class, ids);
	}

	/**
	 * ѕолучение списка id баннеров в пор€дке их отображени€
	 * @param date - текуща€ дата
	 * @param tb - департамент клиента
	 * @return список id баннеров
	 * @throws BusinessException
	 */
	public List<Long> getBunnersList(final Calendar date, final String tb) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<Long>>()
			{
				public List<Long> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.advertising.AdvertisingService.getBunnerList");
					query.setParameter("currentDate", date);
					query.setParameter("TB", tb);
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
	 * ѕолучение списка баннеров в виде сущностей AdvertisingBlock в пор€дке их отображени€
	 * @param date - текуща€ дата
	 * @param tb - департамент клиента
	 * @return список баннеров
	 * @throws BusinessException
	 */
	public List<AdvertisingBlock> getApiBunnersList(final Calendar date, final String tb) throws BusinessException
	{
		try
		{
			String key = DateHelper.formatCalendar(date) + tb;
			Element cachedList = advertisingListApiCache.get(key);
			if(cachedList != null)
				return (List<AdvertisingBlock>)cachedList.getObjectValue();

			BeanQuery query = MultiLocaleQueryHelper.getQuery("com.rssl.phizic.business.advertising.AdvertisingService.getApiBunnerList");
			query.setParameter("currentDate", date);
			query.setParameter("departmentId", tb);
			List<AdvertisingBlock> resultList = query.executeListInternal();

			advertisingListApiCache.put(new Element(key,resultList));
			return resultList;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ѕолучение списка требований к продуктам клиента по id баннера
	 * @param bunnerId - id баннера
	 * @return список требований к продуктам клиента
	 * @throws BusinessException
	 */
	public List<ProductRequirement> getRequirementsList(final Long bunnerId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<ProductRequirement>>()
			{
				public List<ProductRequirement> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.advertising.AdvertisingService.getRequirementsList");
					query.setParameter("advertisingId", bunnerId);
					return (List<ProductRequirement>)query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ѕолучение списка требований к типам вклада по id баннера
	 * @param bunnerId - id баннера
	 * @return список требований к типам вклада
	 * @throws BusinessException
	 */
	public List<AccTypesRequirement> getAccTypesRequirementList(final Long bunnerId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<AccTypesRequirement>>()
			{
				public List<AccTypesRequirement> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.advertising.AdvertisingService.getAccTypesRequirementList");
					query.setParameter("advertisingId", bunnerId);
					return (List<AccTypesRequirement>)query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
