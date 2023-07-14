package com.rssl.phizic.operations.regions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderRegionService;
import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.OperationBase;

/**
 * �������� ��� �������� ����������� �������� ������� ����������
 * @author niculichev
 * @ created 12.07.2012
 * @ $Author$
 * @ $Revision$
 */
public class CheckProviderRegionOperation extends OperationBase
{
	private static final ServiceProviderRegionService serviceProviderRegionService = new ServiceProviderRegionService();

	/**
	 * �������� �� ��������� ��� �������� �������
	 * @param providerId - id ����������
	 * @return - true - ��������
	 */
	public boolean allowedAnyRegions(Long providerId) throws BusinessException
	{
		if(providerId == null)
			throw new BusinessException("�� ����� ������������� ���������� �����");

		//�������� ������� ������
		Region region = PersonContext.getPersonDataProvider().getPersonData().getCurrentRegion();
		return region == null ? true : serviceProviderRegionService.providerAllowedInRegion(providerId, region.getId());
	}
}
