package com.rssl.phizic.business.payments.forms.meta.ima;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.doc.HandlerFilterBase;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.gate.claims.IMAOpeningClaim;

/**
 * User: Balovtsev
 * Date: 26.09.2012
 * Time: 12:00:01
 */
public class IMAOpeningClaimFilter extends HandlerFilterBase
{
	public boolean isEnabled(StateObject stateObject) throws DocumentException
	{
		return stateObject instanceof IMAOpeningClaim;
	}
}
