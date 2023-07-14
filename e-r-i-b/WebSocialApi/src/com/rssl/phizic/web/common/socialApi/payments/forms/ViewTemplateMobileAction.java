package com.rssl.phizic.web.common.socialApi.payments.forms;

import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.forms.FormInfo;
import com.rssl.phizic.business.documents.forms.TransformInfo;
import com.rssl.phizic.business.documents.templates.validators.*;
import com.rssl.phizic.operations.documents.templates.ViewTemplateOperationBase;
import com.rssl.phizic.web.common.client.payments.forms.ViewTemplateAction;

/**
 * @author Dorzhinov
 * @ created 07.03.2012
 * @ $Author$
 * @ $Revision$
 */
public class ViewTemplateMobileAction extends ViewTemplateAction
{
	private static final String SUPPORTED_FORMS = "supportedForms";

	public String buildHtmlForm(ViewTemplateOperationBase operation, FieldValuesSource source) throws BusinessException
	{
		return operation.buildMobileXml(getTransformInfo(), getFormInfo());
	}

	protected TransformInfo getTransformInfo()
	{
		return new TransformInfo("view", "mobile");
	}

	protected FormInfo getFormInfo()
	{
		return new FormInfo();
	}

	protected TemplateValidator[] getTemplateValidators() throws BusinessException
	{
		return new TemplateValidator[]{new OwnerTemplateValidator(), new FormTemplateValidator(currentServletContext().getInitParameter(SUPPORTED_FORMS)), new MAPITemplateValidator()};
	}
}
