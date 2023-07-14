package com.rssl.phizicgate.manager.services.routablePersistent.offices;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.dictionaries.officies.OfficeGateService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizicgate.manager.routing.AdapterService;
import com.rssl.phizicgate.manager.services.objects.OfficeWithoutRouteInfo;
import com.rssl.phizicgate.manager.services.objects.RouteInfoReturner;
import com.rssl.phizicgate.manager.services.routablePersistent.RoutablePersistentServiceBase;
import net.sf.ehcache.Cache;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bogdanov
 * @ created 29.11.2012
 * @ $Author$
 * @ $Revision$
 * Врапер для обеспечения уникальности идентификаторов офисов
 */
public class OfficeGateServiceImpl extends RoutablePersistentServiceBase<OfficeGateService> implements OfficeGateService
{
	private static Cache officeToAdapterUUID;

	static
	{
		officeToAdapterUUID = CacheProvider.getCache("OfficeGateService.officeToAdapter");
	}
	private static AdapterService adapterService = new AdapterService();

	/**
	 * @param office офис.
	 * @return маршрутизирующая информация по офису.
	 */
	private String getRouteInfoByOffice(Office office) throws GateException
	{
		String cacheKey = office.getCode().getFields().get("region") + "|" + office.getCode().getFields().get("branch");
		if (officeToAdapterUUID.isKeyInCache(cacheKey))
			return (String) officeToAdapterUUID.get(cacheKey).getObjectValue();

		String uuid = adapterService.getAdapterUUIDByOffice(office);
		officeToAdapterUUID.put(new net.sf.ehcache.Element(cacheKey, uuid));
		return uuid;
	}

	public OfficeGateServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	protected OfficeGateService endService(String routeInfo) throws GateLogicException, GateException
	{
		return getDelegateFactory(routeInfo).service(OfficeGateService.class);
	}

	public Office getOfficeById(String id) throws GateException, GateLogicException
	{
		RouteInfoReturner ri = removeRouteInfoString(id);
		return storeRouteInfo(endService(ri.getRouteInfo()).getOfficeById(ri.getId()), ri.getRouteInfo());
	}

	public List<Office> getAll(Office template, int firstResult, int listLimit) throws GateException, GateLogicException
	{
		List<Office> offices = delegate.getAll(template, firstResult, listLimit);
		List<Office> rezult = new ArrayList<Office>();
		for (Office office : offices)
		{
			rezult.add(storeRouteInfo(office, getRouteInfoByOffice(office)));
		}
		return rezult;
	}

	public List<Office> getAll(int firstResult, int maxResults) throws GateException, GateLogicException
	{
		List<Office> offices =  delegate.getAll(firstResult, maxResults);
		List<Office> rezult = new ArrayList<Office>();
		for (Office office : offices)
		{
			rezult.add(storeRouteInfo(office, getRouteInfoByOffice(office)));
		}
		return rezult;
	}
}

