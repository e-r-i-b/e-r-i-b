package com.rssl.phizic.web.client.mail;

import com.rssl.phizic.operations.mail.EditClientMailOperation;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.web.common.ViewActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.business.BusinessException;

/**
 * @author Gainanov
 * @ created 01.03.2007
 * @ $Author$
 * @ $Revision$
 */
public class ViewMailAction extends ViewActionBase
{
	protected ViewEntityOperation createViewEntityOperation(EditFormBase frm) throws BusinessException
	{
		EditClientMailOperation operation = createOperation(EditClientMailOperation.class);
		operation.initialize(frm.getId());

		return operation;
	}

	protected void updateFormData(ViewEntityOperation operation, EditFormBase form) throws BusinessException
	{
		ViewMailForm frm = (ViewMailForm)form;
		EditClientMailOperation op = (EditClientMailOperation) operation;

		frm.setMail(op.getEntity());
		op.markMailReceived();
	}
}
