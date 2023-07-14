package com.rssl.phizicgate.manager.services.routablePersistent;

import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizicgate.manager.services.objects.RouteInfoReturner;

import java.util.List;

/**
 * Обертка для пар.
 *
 * @author bogdanov
 * @ created 30.11.2012
 * @ $Author$
 * @ $Revision$
 */

public class PairRouteInfo implements RouteInfoReturner
{
	private Pair pairWithoutRouteInfo;
	private String firstRouteInfo;
	private String secondRouteInfo;

	/**
	 * Создает роутируюмую пару.
	 *
	 * @param pair пара.
	 * @param serviceBase создающий сервис.
	 */
	@SuppressWarnings({"unchecked"})
	public PairRouteInfo(Pair pair, RoutablePersistentServiceBase serviceBase)
	{
		this.pairWithoutRouteInfo = new Pair();
		{
			RouteInfoReturner rir = serviceBase.removeRouteInfo(pair.getFirst());
			firstRouteInfo = rir.getRouteInfo();
			pairWithoutRouteInfo.setFirst(getRouteInformer(rir));
		}

		RouteInfoReturner rir = serviceBase.removeRouteInfo(pair.getSecond());
		secondRouteInfo = rir.getRouteInfo();
		pairWithoutRouteInfo.setSecond(getRouteInformer(rir));
	}

	private Object getRouteInformer(RouteInfoReturner rir)
	{

		if (rir instanceof StringRouteInfo)
		{
			return rir.getId();
		}
		return rir;
	}

	public String getRouteInfo()
	{
		if (StringHelper.isNotEmpty(firstRouteInfo))
			return firstRouteInfo;
		return secondRouteInfo;
	}

	public String getId()
	{
		return null;
	}

	/**
	 * @return пару без роутирующей информации.
	 */
	public Pair getPair()
	{
		return pairWithoutRouteInfo;
	}

	/**
	 * @param pairs список пар.
	 * @return массив из пар.
	 */
	@SuppressWarnings({"unchecked"})
	public static Pair[] copyToArray(List<PairRouteInfo> pairs)
	{
		Pair<String, Office>[] inner = new Pair[pairs.size()];
		for (int i = 0; i < inner.length; i++)
		{
			inner[i] = pairs.get(i).getPair();
		}
		return inner;
	}
}
