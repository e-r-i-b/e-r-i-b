package com.rssl.phizic.web.atm.payments.forms;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.forms.FormInfo;
import com.rssl.phizic.business.documents.forms.TransformInfo;
import com.rssl.phizic.business.documents.payments.source.ExistingSource;
import com.rssl.phizic.business.documents.payments.validators.IsOwnDocumentValidator;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.forms.ViewDocumentATMOperation;
import com.rssl.phizic.operations.forms.ViewDocumentOperation;
import com.rssl.phizic.web.actions.payments.forms.ViewDocumentForm;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.action.ActionForm;

/**
 * Печать чеков
 * @author Jatsky
 * @ created 02.07.2013
 * @ $Author$
 * @ $Revision$
 */

public class PrintCheckATMAction extends ViewPaymentATMAction
{
	protected ViewEntityOperation createViewEntityOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		ViewDocumentForm frm = (ViewDocumentForm) form;

		ExistingSource source = new ExistingSource(frm.getId(), new IsOwnDocumentValidator());
		ViewDocumentOperation operation;
		if (checkAccess(ViewDocumentATMOperation.class, source.getMetadata().getName()))
			operation = createOperation(ViewDocumentATMOperation.class, source.getMetadata().getName());
		else
			operation = (ViewDocumentOperation) super.createViewEntityOperation(form);
		operation.initialize(source);

		if (!operation.checkPrintCheck())
			throw new BusinessException("Печать чека по документу с id = " + frm.getId() + " запрещена!");

		return super.createViewEntityOperation(frm);
	}

	protected TransformInfo getTransformInfo() throws BusinessException
	{
		return new TransformInfo(getMode(), "check");
	}

	protected FormInfo getFormInfo(ActionForm form) throws BusinessException
	{
		return new FormInfo();
	}

	protected String getMode()
	{
		return "printDocumentCheck";
	}
}
