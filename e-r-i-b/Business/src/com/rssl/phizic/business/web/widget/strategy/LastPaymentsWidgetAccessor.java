package com.rssl.phizic.business.web.widget.strategy;

import com.rssl.phizic.business.web.WidgetDefinition;
import com.rssl.phizic.security.PermissionUtil;

/**
 * @author Erkin
 * @ created 24.01.2013
 * @ $Author$
 * @ $Revision$
 */

public class LastPaymentsWidgetAccessor extends GenericWidgetAccessor
{
	@Override
	protected boolean impliesMainPermission(WidgetDefinition widgetDefinition)
	{
		return PermissionUtil.impliesServiceRigid("RurPayJurSB");
	}
}
