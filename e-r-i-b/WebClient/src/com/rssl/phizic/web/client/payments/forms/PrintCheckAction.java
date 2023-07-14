package com.rssl.phizic.web.client.payments.forms;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.forms.FormInfo;
import com.rssl.phizic.business.documents.payments.source.ExistingSource;
import com.rssl.phizic.business.documents.payments.validators.IsOwnDocumentValidator;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.forms.ViewDocumentOperation;
import com.rssl.phizic.web.WebContext;
import com.rssl.phizic.web.actions.payments.forms.ViewDocumentAction;
import com.rssl.phizic.web.actions.payments.forms.ViewDocumentForm;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Mescheryakova
 * @ created 16.04.2010
 * @ $Author$
 * @ $Revision$
 */

public class PrintCheckAction extends ViewDocumentAction
{
	protected ViewEntityOperation createViewEntityOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		ViewDocumentForm frm = (ViewDocumentForm) form;

		ExistingSource source = new ExistingSource(frm.getId(), new IsOwnDocumentValidator());
		ViewDocumentOperation operation = createOperation(ViewDocumentOperation.class, source.getMetadata().getName());
		operation.initialize(source);

		if (!operation.checkPrintCheck())
			frm.setPrintCheck(false);

		return super.createViewEntityOperation(frm);
	}

	protected FormInfo getFormInfo(ActionForm form) throws BusinessException
	{
		return new FormInfo(WebContext.getCurrentRequest().getContextPath(), currentServletContext().getInitParameter("resourcesRealPath"));
	}

	protected ActionForward forwardSuccessShow(ActionMapping mapping, ViewEntityOperation operation)
	{
		ViewDocumentOperation op = (ViewDocumentOperation) operation;

		ActionForward forward = mapping.findForward(FORWARD_START + op.getMetadata().getName());
		forward = forward != null ? forward : mapping.findForward(FORWARD_START);
		return forward;
	}

	protected String getMode()
	{
		return "printCheck";
	}
}
