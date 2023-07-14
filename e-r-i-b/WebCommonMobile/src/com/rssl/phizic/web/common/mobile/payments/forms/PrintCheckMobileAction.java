package com.rssl.phizic.web.common.mobile.payments.forms;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.forms.FormInfo;
import com.rssl.phizic.business.documents.forms.TransformInfo;
import com.rssl.phizic.business.documents.payments.source.ExistingSource;
import com.rssl.phizic.business.documents.payments.validators.IsOwnDocumentValidator;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.forms.ViewDocumentMobileOperation;
import com.rssl.phizic.web.actions.payments.forms.ViewDocumentForm;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.action.ActionForm;

/**
 * @author Jatsky
 * @ created 06.10.14
 * @ $Author$
 * @ $Revision$
 */

public class PrintCheckMobileAction extends ViewMobilePaymentAction
{
	protected ViewEntityOperation createViewEntityOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		ViewDocumentForm frm = (ViewDocumentForm) form;

		ExistingSource source = new ExistingSource(frm.getId(), new IsOwnDocumentValidator());
		ViewDocumentMobileOperation operation = createOperation(ViewDocumentMobileOperation.class, source.getMetadata().getName());
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
