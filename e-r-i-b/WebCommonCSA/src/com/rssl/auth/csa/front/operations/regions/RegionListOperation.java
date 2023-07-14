package com.rssl.auth.csa.front.operations.regions;

import com.rssl.auth.csa.front.business.regions.Region;
import com.rssl.auth.csa.front.business.regions.RegionCSAService;
import com.rssl.auth.csa.front.business.regions.RegionHelper;
import com.rssl.auth.csa.front.operations.OperationBase;
import com.rssl.auth.csa.front.exceptions.FrontException;
import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.query.Query;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import java.util.List;

/**
 * @author komarov
 * @ created 18.03.2013
 * @ $Author$
 * @ $Revision$
 */
public class RegionListOperation extends OperationBase
{
	private static final String LIST_REGIONS_KEY = "list-regions";
	private static final Cache regionCache;
	private static final Cache listRegionsCache;
	static
	{
		regionCache = CacheProvider.getCache("region-client-csa-cache");
		listRegionsCache = CacheProvider.getCache("regions-list-client-csa-cache");
	}
	private static final RegionCSAService regionService = new RegionCSAService();
	private Region region = null;

	/**
	 * Первоначальния инициализация
	 */
	public void initialize()
	{
		region = new Region();
	}	

	/**
	 * Инициализация региона
	 * @param id - идентификатор региона
	 * @throws FrontException
	 */
	public void initialize(Long id) throws FrontException
	{
		if(id == null)
			initialize();
		else if( id == -1)
			region = RegionHelper.createAllRegion();
		else
			region = findRegionById(id);
		if (region == null)
			throw new FrontException("регион с id = " + id + " не найден");
	}

	private Region findRegionById(Long id) throws FrontException
	{
		Element element = regionCache.get(id);
		if(element == null)
		{
			Region temp = regionService.findById(id);
			regionCache.put(new Element(id, temp));
			return temp;
		}
		return (Region) element.getObjectValue();
	}

	/**
	 * @return регион.
	 */
	public Region getRegion()
	{
		return this.region;
	}

	/**
	 * Сохранение региона
	 * @throws FrontException
	 */
	public void saveRegion() throws FrontException
	{
		RegionHelper.updateRegion(region);		
	}

	/**
	 * Возвращает список регионов
	 * @return список регионов
	 * @throws DataAccessException
	 */
	public List<Region> getRegionsList() throws DataAccessException
	{
		Element element = listRegionsCache.get(LIST_REGIONS_KEY);
		if(element == null)
		{
			Query query = createQuery("list");
			List<Region> regions = query.executeList();
			listRegionsCache.put(new Element(LIST_REGIONS_KEY, regions));
			return regions;
		}
		return (List<Region>)element.getObjectValue();
	}
}
