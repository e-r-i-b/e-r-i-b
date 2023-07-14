package com.rssl.phizic.web.atm.payments.forms;

import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.forms.FormInfo;
import com.rssl.phizic.business.documents.forms.TransformInfo;
import com.rssl.phizic.business.documents.templates.validators.*;
import com.rssl.phizic.operations.documents.templates.ViewTemplateOperationBase;
import com.rssl.phizic.operations.forms.ViewDocumentOperation;
import com.rssl.phizic.web.common.client.payments.forms.ViewTemplateAction;

/**
 * @author Dorzhinov
 * @ created 07.03.2012
 * @ $Author$
 * @ $Revision$
 */
public class ViewTemplateATMAction extends ViewTemplateAction
{
	private static final String SUPPORTED_FORMS = "supportedForms";

	@Override
	protected TemplateValidator[] getTemplateValidators() throws BusinessException
	{
		return new TemplateValidator[]{new OwnerTemplateValidator(), new FormTemplateValidator(currentServletContext().getInitParameter(SUPPORTED_FORMS)), new ATMTemplateValidator()};
	}

	public String buildHtmlForm(ViewTemplateOperationBase operation, FieldValuesSource source) throws BusinessException
	{
		return operation.buildATMXml(getTransformInfo(), getFormInfo());
	}

	@Override
	protected TransformInfo getTransformInfo()
	{
		return new TransformInfo("view", "atm");
	}

	@Override
	protected FormInfo getFormInfo()
	{
		return new FormInfo();
	}

	protected String getAdditionPaymentInfo(ViewDocumentOperation operation)
	{
		return null;
	}
}
