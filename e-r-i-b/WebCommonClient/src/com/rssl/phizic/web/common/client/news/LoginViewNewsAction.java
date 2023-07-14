package com.rssl.phizic.web.common.client.news;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.news.LoginViewNewsOperation;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author lukina
 * @ created 11.05.2011
 * @ $Author$
 * @ $Revision$
 */

public class LoginViewNewsAction extends ViewNewsAction
{
	protected ViewEntityOperation createViewEntityOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		LoginViewNewsOperation operation = new LoginViewNewsOperation();
		operation.initialize(frm.getId());

		return operation;
	}
}
