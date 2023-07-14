package com.rssl.phizic.operations.access.conditions;

import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.common.forms.doc.CreationSourceType;
import com.rssl.phizic.auth.modes.StrategyCondition;

/**
 @author Pankin
 @ created 02.12.2010
 @ $Author$
 @ $Revision$
 */
public class PaymentByMobileTemplateCondition implements StrategyCondition
{
	public String getWarning()
	{
		return null;
	}

	public boolean checkCondition(ConfirmableObject object)
	{
		return object instanceof BusinessDocument &&
				((BusinessDocument) object).getCreationSourceType() == CreationSourceType.mobiletemplate;
	}
}
