package com.rssl.auth.csa.back.servises.restrictions.operations.blockingRules.locale;

import com.rssl.auth.csa.back.servises.restrictions.operations.blockingRules.MAPIBlockingRule;
import org.apache.commons.collections.CollectionUtils;

import java.util.Set;

/**
 * ћного€зычное правило блокировки дл€ мјпи
 * @author komarov
 * @ created 20.05.2015
 * @ $Author$
 * @ $Revision$
 */
public class LocaledMAPIBlockingRule extends MAPIBlockingRule
{
	@SuppressWarnings("UnusedDeclaration")
	private Set<MAPIBlockingRuleResources> resources;

	@Override
	public String getMapiMessage()
	{
		if(CollectionUtils.isNotEmpty(resources))
			return resources.iterator().next().getMapiMessage();
		return super.getMapiMessage();
	}

	public String getEribMessage()
	{
		if(CollectionUtils.isNotEmpty(resources))
			return resources.iterator().next().getERIBMessage();
		return super.getEribMessage();
	}

}
