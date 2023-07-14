package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.security.PermissionUtil;

/**
 * @ author gorshkov
 * @ created 03.03.14
 * @ $Author$
 * @ $Revision$
 *
 * Применяется для получения всех активных карт, а также виртуальных, если имеется доступ.
 */
public class BlokingVirtualCardsFilter extends CardFilterConjunction
{
	public BlokingVirtualCardsFilter()
	{
		addFilter(new ActiveCardFilter());
		addFilter(new BlokingVirtualCardFilter());
	}
}

