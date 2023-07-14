package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.doc.HandlerFilterBase;
import com.rssl.common.forms.state.StateObject;
import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.business.documents.PFRStatementClaim;
import com.rssl.phizic.gate.pfr.StatementStatus;

/**
 * @author Erkin
 * @ created 17.02.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * Возвращает true, если статус выписки в заявке на выписку из ПФР совпадает со значением параметра
 */
public class PFRStatementStatusFilter extends HandlerFilterBase
{
	private static final String STATUS_PARAMETER_NAME = "status";

	public boolean isEnabled(StateObject stateObject) throws DocumentException
	{
		if (!(stateObject instanceof PFRStatementClaim))
			throw new IllegalArgumentException("Ожидается заявка на выписку из ПФР (PFRStatementClaim)");
		PFRStatementClaim claim = (PFRStatementClaim) stateObject;

		return claim.isReady() == StatementStatus.valueOf(getParameter(STATUS_PARAMETER_NAME));
	}
}
