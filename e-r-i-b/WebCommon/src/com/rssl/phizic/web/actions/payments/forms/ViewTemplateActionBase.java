package com.rssl.phizic.web.actions.payments.forms;

import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.BankContextConfig;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.forms.FormInfo;
import com.rssl.phizic.business.documents.forms.TransformInfo;
import com.rssl.phizic.business.documents.metadata.source.TemplateFieldValueSource;
import com.rssl.phizic.business.documents.templates.source.ExistingTemplateSource;
import com.rssl.phizic.business.documents.templates.source.TemplateDocumentSource;
import com.rssl.phizic.business.documents.templates.validators.TemplateValidator;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.documents.templates.*;
import com.rssl.phizic.operations.forms.ViewTemplateOperation;
import com.rssl.phizic.web.WebContext;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.ViewActionBase;

/**
 * Базовый класс работы с отображением информации по шаблону
 *
 * @author khudyakov
 * @ created 20.02.14
 * @ $Author$
 * @ $Revision$
 */
public abstract class ViewTemplateActionBase extends ViewActionBase
{
	protected abstract TemplateValidator[] getTemplateValidators() throws BusinessException;

	protected EditTemplateDocumentOperation createEditEntityOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		TemplateDocumentSource source = getTemplateSource(form);
		EditTemplateDocumentOperation operation = createOperation(getEditOperationClass(source.getMetadata().getName()), source.getMetadata().getName());
		operation.initialize(source);
		return operation;
	}

	private Class<? extends EditTemplateDocumentOperation> getEditOperationClass(String serviceName)
	{
		if (FormType.INTERNAL_PAYMENT_SYSTEM_TRANSFER.getName().equals(serviceName))
		{
			return EditServicePaymentTemplateOperation.class;
		}
		if (FormType.JURIDICAL_TRANSFER.getName().equals(serviceName))
		{
			return EditJurPaymentTemplateOperation.class;
		}
		return EditTemplateOperation.class;
	}

	@Override
	protected ViewEntityOperation createViewEntityOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		TemplateDocumentSource source = getTemplateSource(form);
		ViewTemplateOperation operation = createOperation(ViewTemplateOperation.class, source.getMetadata().getName());
		operation.initialize(source);
		return operation;
	}

	protected RemoveTemplateOperation createRemoveEntityOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		TemplateDocumentSource source = getTemplateSource(form);
		RemoveClientTemplateOperation operation = createOperation(RemoveClientTemplateOperation.class, source.getMetadata().getName());
		operation.initialize(source);
		return operation;
	}

	protected TemplateDocumentSource getTemplateSource(EditFormBase form) throws BusinessLogicException, BusinessException
	{
		ViewTemplateForm frm = (ViewTemplateForm) form;

		Long templateId = frm.getId();
		if (templateId == null)
		{
			throw new BusinessException("templateId не может быть null");
		}

		return createExistingTemplateSource(templateId);
	}

	protected TemplateDocumentSource createExistingTemplateSource(Long id) throws BusinessException, BusinessLogicException
	{
		return new ExistingTemplateSource(id, getTemplateValidators());
	}

	public String buildHtmlForm(ViewTemplateOperationBase operation, FieldValuesSource source) throws BusinessException
	{
		return operation.buildFormHtml(getTransformInfo(), getFormInfo(), source);
	}

	protected TransformInfo getTransformInfo()
	{
		return new TransformInfo("view", "html");
	}

	protected FormInfo getFormInfo()
	{
		return new FormInfo(WebContext.getCurrentRequest().getContextPath(), currentServletContext().getInitParameter("resourcesRealPath"));
	}

	@Override
	protected void updateFormData(ViewEntityOperation operation, EditFormBase form) throws BusinessException, BusinessLogicException
	{
		ViewTemplateForm frm = (ViewTemplateForm) form;
		ViewTemplateOperationBase viewOperation = (ViewTemplateOperationBase) operation;

		frm.setTemplate(viewOperation.getEntity());
		frm.setHtml(buildHtmlForm(viewOperation, new TemplateFieldValueSource(viewOperation.getEntity())));

		frm.setTitle(viewOperation.getTemplateInfo().getName());

		frm.setMetadata(viewOperation.getMetadata());
		frm.setForm(viewOperation.getMetadata().getName());
		frm.setMetadataPath(viewOperation.getMetadataPath());

		frm.setOwner(viewOperation.getEntity().getOwner());

		BankContextConfig bankContext = ConfigFactory.getConfig(BankContextConfig.class);
		frm.setBankBIC(bankContext.getBankBIC());
		frm.setBankName(bankContext.getBankName());
	}
}
