package com.rssl.phizic.web.client.mail;

import com.rssl.phizic.web.common.ViewActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.operations.mail.EditClientMailOperation;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.business.mail.Mail;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;

/**
 * @author gladishev
 * @ created 03.09.2007
 * @ $Author$
 * @ $Revision$
 */

public class ViewSentMailAction extends ViewActionBase
{
	protected ViewEntityOperation createViewEntityOperation(EditFormBase frm) throws BusinessException
	{
		EditClientMailOperation operation = createOperation(EditClientMailOperation.class);
		operation.initialize(frm.getId());

		return operation;
	}

	protected void updateFormData(ViewEntityOperation operation, EditFormBase form)
			throws BusinessException, BusinessLogicException
	{
		ViewMailForm frm = (ViewMailForm)form;

		frm.setMail((Mail) operation.getEntity());
	}
}
