package com.rssl.phizic.web.persons.search.pfp;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.graphics.ViewFinanceOperation;
import com.rssl.phizic.operations.person.ViewPFPFinanceOperation;
import com.rssl.phizic.web.common.graphics.ShowFinancesAction;

/**
 * @author komarov
 * @ created 14.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class ShowPFPFinancesAction extends ShowFinancesAction
{
	private static final String VIEW_FINANCE_SERVICE_KEY = "EmployeePfpEditService";

	protected ViewFinanceOperation createOperation() throws BusinessException
	{
		return createOperation(ViewPFPFinanceOperation.class, VIEW_FINANCE_SERVICE_KEY);
	}
}
