package com.rssl.phizic.web.persons;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.person.ShowPersonInfoOperation;
import com.rssl.phizic.web.persons.search.ShowPersonActionBase;

/**
 * @author bogdanov
 * @ created 22.03.2012
 * @ $Author$
 * @ $Revision$
 */

public class ShowPersonAction extends ShowPersonActionBase
{
	protected ShowPersonInfoOperation createShowOperation() throws BusinessException
	{
		return createOperation(ShowPersonInfoOperation.class);
	}
}
