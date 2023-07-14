package com.rssl.phizic.business.dictionaries.regions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.Constants;
import com.rssl.phizic.business.dictionaries.regions.exceptions.DublicateRegionException;
import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.List;

/**
 * @author khudyakov
 * @ created 02.12.2009
 * @ $Author$
 * @ $Revision$
 */

public class RegionDictionaryService extends MultiInstanceRegionDictionaryService
{
	private static Cache regionCache;

	static
	{
		regionCache = CacheProvider.getCache("region-cache");
	}
	/**
	 * Поиск региона по идентификатору
	 * @param id - идентификатор
	 * @return регион
	 * @throws BusinessException
	 */
	public Region findById(Long id) throws BusinessException
	{
		return super.findById(id, null);
	}

	/**
	 * Поиск региона по идентификатору из кэша
	 * @param id - идентификатор
	 * @return регион
	 * @throws BusinessException
	 */
	public Region findByIdFromCache(Long id) throws BusinessException
	{

		String key = id.toString();
		Element element = regionCache.get(key);
        if (element == null)
		{
			Region region = super.findById(id, null);
			regionCache.put(new Element(key, region));
			return region;
		}
		return (Region)element.getObjectValue();
	}

	/**
	 * Поиск списка регионов
	 * @param ids - идентификаторы регионов
	 * @return список регионов
	 * @throws BusinessException
	 */
	public List<Region> findByIds(List<Long> ids) throws BusinessException
	{
		return super.findByIds(ids, null);
	}

	/**
	 * Поиск региона по коду
	 * @param synchKey - код региона
	 * @return регион
	 * @throws BusinessException
	 */
	public Region findBySynchKey(String synchKey) throws BusinessException
	{
		return super.findBySynchKey(synchKey, null);
	}

	/**
	 * Поиск региона по уникальному ключу
	 * @param uuid - уникальный ключ региона
	 * @return регион
	 * @throws BusinessException
	 */
	public Region findByGuid(String uuid) throws BusinessException
	{
		return super.findByGuid(uuid, null);
	}

	/**
	 * Поиск наследников по родительскому узлу
	 * @param parent - родительский узел
	 * @return - список наследников
	 * @throws BusinessException
	 */
	public List<Region> getChildren(final Region parent) throws BusinessException
	{
		return super.getChildren(parent, null);
	}

	/**
	 * Получает куку клиента
	 * @param profileId идентификатор
	 * @return кука
	 * @throws BusinessException
	 */
	public CSARegion findUserRegionByUserId(Long profileId) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(CSARegion.class).
				add(Expression.eq("profileId", profileId));

		return simpleService.findSingle(criteria, Constants.DB_CSA);
	}


	/**
	 * Обновляет регион в ЦСА регионом из ЕРИБ
	 * @param profileId идентификатор профиля
	 * @param region выбранный в ЕРИБ регион
	 * @throws BusinessException
	 */
	public void updateCsaRegion(final Long profileId, final Region region) throws BusinessException
	{
		try
		{
			HibernateExecutor trnExecutor = HibernateExecutor.getInstance(Constants.DB_CSA);

			trnExecutor.execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(CSARegion.class.getName() + ".UpdateCSARegionByCsaUID");
					query.setParameter("regionCode", region == null ? null : region.getSynchKey());
					query.setParameter("profileId", profileId);
					query.executeUpdate();
					return null;
				};
			}
			);
		}
		catch (BusinessException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Добавляет или обновляет регион
	 * @param region регион
	 * @throws BusinessException
	 * @throws DublicateRegionException
	 */
	public void addOrUpdate(Region region) throws BusinessException, DublicateRegionException
	{
		super.addOrUpdate(region, null);
	}

	public List<Region> findByCodeTB(String codeTb) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(Region.class).
				add(Expression.eq("codeTB", codeTb));

		return simpleService.find(criteria);
	}
}
