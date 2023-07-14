package com.rssl.phizic.web.persons.search.pfp;

import com.rssl.phizic.web.persons.search.ShowPersonActionBase;
import com.rssl.phizic.operations.person.ShowPersonInfoOperation;
import com.rssl.phizic.business.BusinessException;

/**
 * @author akrenev
 * @ created 26.04.2012
 * @ $Author$
 * @ $Revision$
 */
public class ShowPersonForPFPAction extends ShowPersonActionBase
{
	protected ShowPersonInfoOperation createShowOperation() throws BusinessException
	{
		return createOperation(ShowPersonInfoOperation.class, "EmployeePfpEditService");
	}
}
