package com.rssl.phizic.business.web.widget.strategy;

import com.rssl.phizic.business.web.WidgetDefinition;
import com.rssl.phizic.security.PermissionUtil;

/**
 * @author Gulov
 * @ created 26.02.2013
 * @ $Author$
 * @ $Revision$
 */
public class EventWidgetAccessor extends GenericWidgetAccessor
{
	@Override
	protected boolean impliesMainPermission(WidgetDefinition widgetDefinition)
	{
		return PermissionUtil.impliesServiceRigid("ViewNewsManagment");
	}
}
