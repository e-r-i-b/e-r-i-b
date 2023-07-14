package com.rssl.phizic.web.client.payments;

import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.Map;

/**
 * Форма проверки доступности текущему региону поставщика
 * @author niculichev
 * @ created 12.07.2012
 * @ $Author$
 * @ $Revision$
 */
public class AsyncCheckProviderRegionForm extends ActionFormBase
{
	private Long[] providerIds;
	private Map<Long, Boolean> allowedAnyRegions;

	public Map<Long, Boolean> getAllowedAnyRegions()
	{
		return allowedAnyRegions;
	}

	public void setAllowedAnyRegions(Map<Long, Boolean> allowedAnyRegions)
	{
		this.allowedAnyRegions = allowedAnyRegions;
	}

	public Long[] getProviderIds()
	{
		return providerIds;
	}

	public void setProviderIds(Long[] providerIds)
	{
		this.providerIds = providerIds;
	}
}
