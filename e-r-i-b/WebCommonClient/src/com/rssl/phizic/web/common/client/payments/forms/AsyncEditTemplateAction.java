package com.rssl.phizic.web.common.client.payments.forms;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.processing.StringErrorCollector;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.common.forms.validators.strategy.DefaultValidationStrategy;
import com.rssl.common.forms.validators.strategy.DraftTemplateValidationStrategy;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.documents.templates.EditTemplateOperation;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.actions.payments.forms.EditTemplateForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Асинхронное изменение шаблона
 * @author niculichev
 * @ created 05.06.2013
 * @ $Author$
 * @ $Revision$
 */
public class AsyncEditTemplateAction extends EditTemplateAction
{
	private static final String CHANGE_NAME_TEMPLATE            = "ChangeNameTemplate";
	private static final String DRAFT_TEMPLATE                  = "DraftTemplate";
	private static final String CHECK_TEMPLATE_NAME             = "CheckTemplateNewName";


	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
		map.put("changeName",               "changeName");
		map.put("button.saveDraftTemplate", "saveDraftTemplate");
		map.put("checkName", "checkName");
		return map;
	}

	public ActionForward changeName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditTemplateForm frm = (EditTemplateForm) form;
		EditTemplateOperation operation = (EditTemplateOperation) createViewOperation(frm);

		// валидируем поля
		Map<String, Object> additionFields = checkFormData(new MapValuesSource(frm.getFields()), EditTemplateForm.CHANGE_NAME_FORM, DefaultValidationStrategy.getInstance());
		if (additionFields != null)
		{
			// вытаскиваем новое имя шаблона
			String newTemplateName = (String) additionFields.get(EditTemplateForm.NEW_TEMPLATE_NAME_FIELD_NAME);
			try
			{
				operation.changeName(newTemplateName);
			}
			catch (BusinessLogicException e)
			{
				saveError(request, e);
			}
		}

		return mapping.findForward(CHANGE_NAME_TEMPLATE);
	}

	public ActionForward saveDraftTemplate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditTemplateForm frm = (EditTemplateForm) form;
		EditTemplateOperation operation = createEditOperation(frm);

		//валидируем данные шаблона стратегией для создания черновика шаблона(не требовательная к обязательности заполнения полей)
		Map<String, Object> formData = checkFormData(getFormProcessorValueSource(frm, operation), getCheckForm(operation), DraftTemplateValidationStrategy.getInstance());
		if (formData == null)
		{
			return mapping.findForward(DRAFT_TEMPLATE);
		}

		//валидируем доп поля(имя шаблона)
		ActionMessages msgs = checkAdditionFormData(frm, operation);
		if (!msgs.isEmpty())
		{
			saveErrors(request, msgs);
			return mapping.findForward(DRAFT_TEMPLATE);
		}

		String templateName = (String) frm.getField(EditTemplateForm.TEMPLATE_NAME_FIELD_NAME);
		addSaveLogParemeters(operation, formData, templateName);

		try
		{
			updateEntity(operation.getEntity(), formData);
			updateOperationAdditionalData(operation, frm, formData);
			operation.saveDraftTemplate(templateName);

			saveMessage(request, StrutsUtils.getMessage("message.create.draft.template", "paymentsBundle", templateName));
		}
		catch (BusinessLogicException e)
		{
			saveError(request, e);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			saveError(request, StrutsUtils.getMessage("error.errorHeader", "paymentsBundle"));
		}

		return mapping.findForward(DRAFT_TEMPLATE);
	}

	public ActionForward checkName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditTemplateForm frm = (EditTemplateForm) form;

		FormProcessor<List<String>, ?> processor = new FormProcessor<List<String>, StringErrorCollector>(new MapValuesSource(frm.getFields()),
				EditTemplateForm.CHECK_NAME_FORM, new StringErrorCollector(), DefaultValidationStrategy.getInstance());

		if (!processor.process())
		{
			saveMessages(currentRequest(), processor.getErrors());
		}

		return mapping.findForward(CHECK_TEMPLATE_NAME);
	}

	@Override
	protected boolean isAjax()
	{
		return true;
	}
}
