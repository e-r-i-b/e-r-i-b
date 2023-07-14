package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.doc.HandlerFilterBase;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.gate.claims.AccountOpeningClaim;

/**
 * @author Dorzhinov
 * @ created 01.09.2011
 * @ $Author$
 * @ $Revision$
 */
public class AccountOpeningClaimFilter extends HandlerFilterBase
{
	public boolean isEnabled(StateObject stateObject) throws DocumentException
	{
		return stateObject instanceof AccountOpeningClaim;
	}
}
