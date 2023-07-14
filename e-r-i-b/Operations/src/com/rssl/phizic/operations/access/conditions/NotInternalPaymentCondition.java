package com.rssl.phizic.operations.access.conditions;

import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.common.forms.FormConstants;
import com.rssl.phizic.auth.modes.StrategyCondition;

/**
 * Указание на то, что платеж - не перевод между своими счетами/картами
 @author Pankin
 @ created 02.12.2010
 @ $Author$
 @ $Revision$
 */
public class NotInternalPaymentCondition implements StrategyCondition
{

	public String getWarning()
	{
		return null;
	}

	public boolean checkCondition(ConfirmableObject object)
	{
		return object instanceof BusinessDocument &&
				!((BusinessDocument) object).getFormName().equals(FormConstants.INTERNAL_PAYMENT_FORM);
	}
}
