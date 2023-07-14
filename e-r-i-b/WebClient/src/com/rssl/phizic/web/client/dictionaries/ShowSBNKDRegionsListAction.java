package com.rssl.phizic.web.client.dictionaries;

import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.business.regions.RegionHelper;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.sbnkd.SBNKDConfig;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.dictionaries.regions.RegionsListOperation;

import java.util.ArrayList;
import java.util.List;

/**
 * выбор региона ограничен доступными ТБ для СБНКД
 * @author basharin
 * @ created 06.02.15
 * @ $Author$
 * @ $Revision$
 */

public class ShowSBNKDRegionsListAction extends ShowRegionsListAction
{
	@Override
	protected void doFilter(RegionsListOperation operation, ShowRegionsListForm frm) throws Exception
	{
		Query query = operation.createQuery("list");

		Region region = operation.getRegion();
		//Ищем всех детей выбранного региона
		Long regionId = region.getId();
		query.setParameter("parent", RegionHelper.isAllRegionsSelected(regionId)? null: regionId);
		List<Region> tempRegions = query.executeList();

		List<String> allowedTB = ConfigFactory.getConfig(SBNKDConfig.class).getAllowedTB();
		List<Region> regions = filterAllowedTB(allowedTB, tempRegions);

		// Если дети отсутствуют и страница только открылась необходимо показать соседей
		if (regions.size() == 0 && !frm.getIsOpening()) // Устанавливаем регион
		{
			if (RegionHelper.isOneRegionSelected(frm.getId()))
				frm.setNavigation(RegionHelper.arrayToList(region));
			return;
		}

		if (regions.isEmpty())
		{
			region = region.getParent();
			query.setParameter("parent", (region == null) ? null : region.getId());
			tempRegions = query.executeList();
			regions = filterAllowedTB(allowedTB, tempRegions);
		}

		if (RegionHelper.isOneRegionSelected(frm.getId()))
			frm.setNavigation(RegionHelper.arrayToList(region));

		frm.setRegions(regions);
	}

	private List<Region> filterAllowedTB(List<String> allowedTB, List<Region> tempRegions)
	{
		List<Region> regions = new ArrayList<Region>();
		for (Region region : tempRegions)
			if (region.getCodeTB() != null && allowedTB.contains(region.getCodeTB()))
				regions.add(region);
		return regions;
	}
}
