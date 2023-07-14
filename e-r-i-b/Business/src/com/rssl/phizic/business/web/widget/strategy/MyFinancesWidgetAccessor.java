package com.rssl.phizic.business.web.widget.strategy;

import com.rssl.phizic.business.web.WidgetDefinition;
import com.rssl.phizic.security.PermissionUtil;

/**
 * @author Erkin
 * @ created 24.01.2013
 * @ $Author$
 * @ $Revision$
 */

public class MyFinancesWidgetAccessor extends GenericWidgetAccessor
{
	@Override
	protected boolean impliesMainPermission(WidgetDefinition widgetDefinition)
	{
		boolean rc = PermissionUtil.impliesServiceRigid("AccountAndCardInfo");
		rc = rc && PermissionUtil.impliesServiceRigid("IMAccountInfoService");
		rc = rc && PermissionUtil.impliesServiceRigid("ViewFinance");
		return rc;
	}
}
