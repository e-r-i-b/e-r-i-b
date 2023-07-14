package com.rssl.phizic.web.client.dictionaries;

/**
 * @author Nady
 * @ created 20.05.2015
 * @ $Author$
 * @ $Revision$
 */

import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.business.regions.RegionHelper;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.dictionaries.regions.RegionsListOperation;

import java.util.List;

/**
 * ���� ������ ������� � ������ �� ��������� �����
 */
public class ShowLoanCardClaimRegionsAction extends ShowSBNKDRegionsListAction
{
	@Override
	protected void doFilter(RegionsListOperation operation, ShowRegionsListForm frm) throws Exception
	{
		Query query = operation.createQuery("list");

		Region region = operation.getRegion();
		//���� ���� ����� ���������� �������
		Long regionId = region.getId();
		query.setParameter("parent", RegionHelper.isAllRegionsSelected(regionId)? null: regionId);
		List<Region> regions = query.executeList();

		// ���� ���� ����������� � �������� ������ ��������� ���������� �������� �������
		if (regions.size() == 0 && !frm.getIsOpening()) // ������������� ������
		{
			if (RegionHelper.isOneRegionSelected(frm.getId()))
				frm.setNavigation(RegionHelper.arrayToList(region));
			return;
		}

		if (regions.isEmpty())
		{
			region = region.getParent();
			query.setParameter("parent", (region == null) ? null : region.getId());
			regions = query.executeList();
		}

		if (RegionHelper.isOneRegionSelected(frm.getId()))
			frm.setNavigation(RegionHelper.arrayToList(region));

		frm.setRegions(regions);
	}
}
