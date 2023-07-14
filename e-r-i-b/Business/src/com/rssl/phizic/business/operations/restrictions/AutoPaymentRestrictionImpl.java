package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.resources.external.AutoPaymentLink;

/**
 * @author basharin
 * @ created 16.01.2012
 * @ $Author$
 * @ $Revision$
 */

public class AutoPaymentRestrictionImpl implements AutoPaymentRestriction
{
	public boolean accept(AutoPaymentLink link) throws BusinessException
	{
		if(link.getLoginId() == null)
			return false;

		Long loginId = link.getLoginId();
		if (!loginId.equals(AuthModule.getAuthModule().getPrincipal().getLogin().getId()))
			return false;
		else
			return true;
	}
}
