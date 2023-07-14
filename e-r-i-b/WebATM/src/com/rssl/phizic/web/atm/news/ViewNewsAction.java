package com.rssl.phizic.web.atm.news;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.news.ViewNewsOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.ViewActionBase;
import com.rssl.phizic.web.common.client.news.ViewNewsForm;

/**
 * @author lukina
 * @ created 18.08.2010
 * @ $Author$
 * @ $Revision$
 */
public class ViewNewsAction extends ViewActionBase
{
	protected ViewEntityOperation createViewEntityOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		ViewNewsOperation operation = createOperation(ViewNewsOperation.class);
		operation.initialize(frm.getId());

		return operation;
	}

	protected void updateFormData(ViewEntityOperation operation, EditFormBase form)
			throws BusinessException, BusinessLogicException
	{
		ViewNewsForm frm = (ViewNewsForm) form;
		ViewNewsOperation op = (ViewNewsOperation) operation;

		frm.setNews(op.getEntity());
	}
}
