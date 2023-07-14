package com.rssl.phizic.web.common.client.payments.forms;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.validators.strategy.TemplateValidationStrategy;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.IndicateFormFieldsException;
import com.rssl.phizic.business.TemporalBusinessException;
import com.rssl.phizic.business.documents.payments.validators.CheckPermissionTemplateCreationValidator;
import com.rssl.phizic.business.documents.payments.validators.DocumentValidator;
import com.rssl.phizic.business.documents.payments.validators.IsOwnDocumentValidator;
import com.rssl.phizic.business.documents.templates.Constants;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.validators.ERIBTemplateValidator;
import com.rssl.phizic.business.documents.templates.validators.OwnerTemplateValidator;
import com.rssl.phizic.business.documents.templates.validators.TemplateValidator;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.logging.operations.SimpleLogParametersReader;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.documents.templates.EditTemplateDocumentOperation;
import com.rssl.phizic.operations.documents.templates.EditTemplateOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.NoActiveOperationException;
import com.rssl.phizic.web.actions.payments.forms.DefaultDocumentAction;
import com.rssl.phizic.web.actions.payments.forms.EditTemplateActionBase;
import com.rssl.phizic.web.actions.payments.forms.EditTemplateForm;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.log.FormLogParametersReader;
import org.apache.struts.action.*;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mihaylov
 * @ created 13.05.2010
 * @ $Author$
 * @ $Revision$
 */

public class EditTemplateAction extends EditTemplateActionBase
{
	@Override
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.confirmTemplate", "save");
		map.put("button.edit_template", "edit");
		return map;
	}

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		try
		{
			return super.start(mapping, form, request, response);
		}
		catch (TemporalBusinessException ignore)
		{
			saveSessionError(Constants.TEMPLATE_OPERATION_ERROR_MESSAGE, new ActionMessage(Constants.TEMPLATE_OPERATION_ERROR_MESSAGE, false), null);
			return createBackActionForward(form, mapping);
		}
		catch (BusinessLogicException e)
		{
			saveSessionError(e.getMessage(), new ActionMessage(e.getMessage(), false), null);
			return createBackActionForward(form, mapping);
		}
	}

	@Override
	protected void doStart(EditEntityOperation operation, EditFormBase frm) throws Exception
	{
		super.doStart(operation, frm);

		saveOperation(currentRequest(), operation);
	}

	@Override
	protected EditTemplateOperation createEditOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		try
		{
			return getOperation(currentRequest());
		}
		catch (NoActiveOperationException e)
		{
			throw new BusinessException(e);
		}
	}

	@Override
	protected Form getEditForm(EditFormBase frm)
	{
		return FormBuilder.EMPTY_FORM;
	}

	protected ActionMessages validateAdditionalFormData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		return checkAdditionFormData(frm, operation);
	}

	@Override
	protected ActionForward doSave(EditEntityOperation operation, ActionMapping mapping, EditFormBase frm) throws Exception
	{
		try
		{
			EditTemplateDocumentOperation op = (EditTemplateDocumentOperation) operation;
			EditTemplateForm form = (EditTemplateForm) frm;

			form.setMetadata(op.getMetadata());
			form.setTemplate(op.getTemplate());

			operation.save();
			resetOperation(currentRequest());

			//TODO логирование, исполнитель Худяков А, текущая задача
			addLogParameters(form);
			return createSaveActionForward(operation, form);
		}
		catch (IndicateFormFieldsException e)
		{
			// Ошибки и сообщения, для которых должны быть подсвечены поля
			ActionMessages actionErrors = new ActionMessages();
			boolean isEmptyFieldMessages = true;
			for (Map.Entry<String, String> entry : e.getFieldMessages().entrySet())
			{
				actionErrors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(entry.getKey(), new ActionMessage(entry.getValue(), false)));
				if (!StringHelper.isEmpty(entry.getValue()) && isEmptyFieldMessages)
					isEmptyFieldMessages = false;
			}
			if (e.isError())
			{
				saveErrors(currentRequest(), actionErrors);
			}
			else
			{
				// Если ни для одного из полей нет сообщения с описанием проблемы, добавляем общее сообщение в
				// блоке информационных сообщений
				if (isEmptyFieldMessages)
					actionErrors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));

				saveMessages(currentRequest(), actionErrors);
			}
			saveStateMachineEventMessages(currentRequest(), operation, true);

			updateForm(frm, operation.getEntity());
			addLogParameters(frm);

			return createStartActionForward(frm, mapping);
		}
	}

	@Override
	protected FormProcessor<ActionMessages, ?> getFormProcessor(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		EditTemplateOperation templateOperation = (EditTemplateOperation) operation;
		return createFormProcessor(getFormProcessorValueSource(frm, operation), templateOperation.getMetadata().getForm(), TemplateValidationStrategy.getInstance());
	}

	@Override
	protected Form getAdditionalForm(EditEntityOperation operation) throws BusinessLogicException, BusinessException
	{
		TemplateDocument template = (TemplateDocument) operation.getEntity();
		return EditTemplateForm.createEditForm(template);
	}

	@Override
	protected Form getCheckForm(EditEntityOperation operation) throws BusinessLogicException, BusinessException
	{
		EditTemplateOperation templateOperation = (EditTemplateOperation) operation;
		return templateOperation.getMetadata().getForm();
	}

	protected void addSaveLogParemeters(EditTemplateOperation operation, Map<String, Object> formData, String templateName)
	{
		addLogParameters(new BeanLogParemetersReader("Первоначальные данные", operation.getEntity()));
		addLogParameters(new FormLogParametersReader("Данные документа", operation.getMetadata().getForm(), formData));
		addLogParameters(new SimpleLogParametersReader("Имя шаблона", templateName));
	}

	@Override
	protected TemplateValidator[] getTemplateValidators()
	{
		return new TemplateValidator[]{new OwnerTemplateValidator(), new ERIBTemplateValidator()};
	}

	@Override
	protected DocumentValidator[] getDocumentValidators()
	{
		return new DocumentValidator[]{new IsOwnDocumentValidator(), new CheckPermissionTemplateCreationValidator()};
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception
	{
		EditTemplateDocumentOperation operation = createEditOperation((EditFormBase) form);
		operation.edit();

		addLogParameters(new BeanLogParemetersReader("Редактируемая сущность", operation.getTemplate()));

		return DefaultDocumentAction.createDefaultEditForward(operation.getTemplate().getId(), operation.getExecutor().getCurrentState());
	}
}
