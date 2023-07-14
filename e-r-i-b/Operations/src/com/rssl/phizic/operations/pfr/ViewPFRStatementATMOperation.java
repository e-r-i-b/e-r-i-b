package com.rssl.phizic.operations.pfr;

import com.rssl.phizic.business.payments.forms.meta.XSLTService;

/**
 * Просмотр выписки из ПФР (АТМ)
 * @author Jatsky
 * @ created 22.10.13
 * @ $Author$
 * @ $Revision$
 */

public class ViewPFRStatementATMOperation extends ViewPFRStatementOperation
{
	@Override protected String getTemplateName()
	{
		return XSLTService.PFR_STATEMENT_ATM_NAME;
	}
}
