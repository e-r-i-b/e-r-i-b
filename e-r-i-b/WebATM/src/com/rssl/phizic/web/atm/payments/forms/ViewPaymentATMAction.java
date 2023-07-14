package com.rssl.phizic.web.atm.payments.forms;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.access.AccessException;
import com.rssl.phizic.business.documents.exceptions.NotOwnDocumentException;
import com.rssl.phizic.business.documents.forms.FormInfo;
import com.rssl.phizic.business.documents.forms.TransformInfo;
import com.rssl.phizic.business.documents.payments.source.ExistingSource;
import com.rssl.phizic.business.documents.payments.validators.IsOwnDocumentValidator;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.forms.ViewDocumentATMOperation;
import com.rssl.phizic.operations.forms.ViewDocumentOperation;
import com.rssl.phizic.web.WebContext;
import com.rssl.phizic.web.actions.payments.forms.ViewDocumentAction;
import com.rssl.phizic.web.actions.payments.forms.ViewDocumentForm;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.action.ActionForm;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Erkin
 * @ created 01.11.2011
 * @ $Author$
 * @ $Revision$
 */
public class ViewPaymentATMAction extends ViewDocumentAction
{
	protected Map<String, String> getKeyMethodMap()
	{
		return new HashMap<String, String>();
	}

	protected String buildFormHtml(ViewDocumentOperation operation, ActionForm form) throws BusinessException, BusinessLogicException
	{
		return operation.buildATMXml(getTransformInfo(), getFormInfo(form));
	}

	protected TransformInfo getTransformInfo() throws BusinessException
	{
		return new TransformInfo(getMode(), "atm");
	}

	protected FormInfo getFormInfo(ActionForm form) throws BusinessException
	{
		return new FormInfo(WebContext.getCurrentRequest().getContextPath(), currentServletContext().getInitParameter("resourcesRealPath"), getSkinUrl(form));
	}

	protected String getAdditionPaymentInfo(ViewDocumentOperation operation)
	{
		return null;
	}

	protected ViewEntityOperation createViewEntityOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		ViewDocumentForm frm = (ViewDocumentForm) form;

		ExistingSource source = null;
		try
		{
			source = new ExistingSource(frm.getId(), new IsOwnDocumentValidator());
		}
		catch (NotOwnDocumentException e)
		{
			throw new AccessException(e.getMessage());
		}
		ViewDocumentOperation operation;
		//Право на печать чека проверяем только для тех определенных платежей
		if (checkAccess(ViewDocumentATMOperation.class, source.getMetadata().getName()))
			operation = createOperation(ViewDocumentATMOperation.class, source.getMetadata().getName());
		else
			operation = (ViewDocumentOperation) super.createViewEntityOperation(form);
		operation.initialize(source);
		return operation;
	}
}
