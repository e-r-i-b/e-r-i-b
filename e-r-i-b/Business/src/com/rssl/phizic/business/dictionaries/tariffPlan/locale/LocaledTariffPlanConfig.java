package com.rssl.phizic.business.dictionaries.tariffPlan.locale;

import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanConfig;
import org.apache.commons.collections.CollectionUtils;

import java.util.Set;

/**
 * ��������� ��������� ����� ������� ���������������
 * @author komarov
 * @ created 05.06.2015
 * @ $Author$
 * @ $Revision$
 */
public class LocaledTariffPlanConfig extends TariffPlanConfig
{
	private Set<TariffPlanConfigResources> resources;

	/**
	 * @return ���������, ������������ ��� ������������� ��������� �����
	 */
	@Override
	public String getPrivilegedRateMessage()
	{
		if(CollectionUtils.isNotEmpty(resources))
			return resources.iterator().next().getPrivilegedRateMessage();
		return super.getPrivilegedRateMessage();
	}

}
