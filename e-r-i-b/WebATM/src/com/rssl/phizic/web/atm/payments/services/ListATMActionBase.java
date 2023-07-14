package com.rssl.phizic.web.atm.payments.services;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.business.regions.RegionHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.payments.ListPaymentServiceActionBase;
import com.rssl.phizic.web.actions.payments.forms.ContainRegionGuidActionFormInterface;

/**
 * @ author: Gololobov
 * @ created: 23.06.14
 * @ $Author$
 * @ $Revision$
 */
public abstract class ListATMActionBase extends ListPaymentServiceActionBase
{
	/**
	 * ������������ id ������� � ������ ������������ �������������� ������� (regionGuid), ���� �� �����.
	 * ���� ���, �� ���������� regionId, ���� �� �����.
	 * ���� �����������, �� ������������ id �������� region-� �������.
	 * ���� ������� ������ �� �����, �� ������������ null.
	 * @param form
	 * @return Long - regionId
	 * @throws com.rssl.phizic.business.BusinessException
	 */

	public static Long getRegionIdWithGuid(ContainRegionGuidActionFormInterface form) throws BusinessException
	{
		String regionGuid = form.getRegionGuid();
		Region region = null;
        //������ ����������� id �������
		if (StringHelper.isNotEmpty(regionGuid))
        {
	        if (RegionHelper.isAllRegionsSelected(regionGuid))
                return null;

	        region = RegionHelper.findRegionByGuid(regionGuid);
	        if (region != null)
		        return region.getId();
        }
		Long regionId = form.getRegionId();
		if(regionId == null)
        {
            region = RegionHelper.getCurrentRegion();
		    return region != null ? region.getId() : null;
        }

        if(RegionHelper.isAllRegionsSelected(regionId))
            return null;

        return regionId;
	}
}
