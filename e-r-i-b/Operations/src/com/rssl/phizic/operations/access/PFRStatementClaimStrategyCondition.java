package com.rssl.phizic.operations.access;

import com.rssl.common.forms.FormConstants;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.auth.modes.StrategyCondition;

/**
 * @author Erkin
 * @ created 15.02.2011
 * @ $Author$
 * @ $Revision$
 */
public class PFRStatementClaimStrategyCondition implements StrategyCondition
{
	public boolean checkCondition(ConfirmableObject object)
	{
		if (object instanceof BusinessDocument) {
			BusinessDocument document = (BusinessDocument) object;
			// заявку на получение выписки из ПФР подтверждать не надо
			if (document.getFormName().equals(FormConstants.PFR_STATEMENT_CLAIM))
				return false;
		}
		return true;
	}

	public String getWarning()
	{
		return null;
	}
}
