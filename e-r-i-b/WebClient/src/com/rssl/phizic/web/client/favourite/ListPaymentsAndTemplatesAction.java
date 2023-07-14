package com.rssl.phizic.web.client.favourite;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.service.TemplateDocumentService;
import com.rssl.phizic.business.documents.templates.service.filters.ActiveTemplateFilter;
import com.rssl.phizic.business.documents.templates.service.filters.ChannelActivityTemplateFilter;
import com.rssl.phizic.business.documents.templates.service.filters.StateTemplateFilter;
import com.rssl.phizic.business.documents.templates.source.ExistingTemplateSource;
import com.rssl.phizic.business.documents.templates.source.TemplateDocumentSource;
import com.rssl.phizic.business.documents.templates.validators.ERIBTemplateValidator;
import com.rssl.phizic.business.documents.templates.validators.OwnerTemplateValidator;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.statemachine.MachineState;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.common.types.documents.StateCode;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.documents.templates.*;
import com.rssl.phizic.operations.payment.ListTemplatesOperation;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.actions.templates.DefaultTemplateAction;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lukina
 * @ created 18.03.2011
 * @ $Author$
 * @ $Revision$
 */

public class ListPaymentsAndTemplatesAction extends ListActionBase
{
	protected Map<String, String> getAditionalKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.template.find", "filter");
		map.put("button.template.remove", "remove");
		map.put("button.edit_template",   "edit");
		map.put("button.add_reminder", "addReminder");
		return map;
	}

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(ListTemplatesOperation.class);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase form) throws Exception
	{
		//заполняем форму данными об активных и неактивных шаблонах
		List<TemplateDocument> templates = TemplateDocumentService.getInstance().getAll(PersonHelper.getContextPerson().asClient());
		if (CollectionUtils.isEmpty(templates))
		{
			return;
		}

		ListPaymentsAndTemplatesForm frm = (ListPaymentsAndTemplatesForm) form;

		//два списка шаблонов: активные, неактивные
		for (TemplateDocument template: templates)
		{
			if (!(new ChannelActivityTemplateFilter(CreationType.internet).accept(template)))
			{
				continue;
			}

			if (!(new ActiveTemplateFilter().accept(template)))
			{
				continue;
			}

			if (new StateTemplateFilter(StateCode.TEMPLATE, StateCode.WAIT_CONFIRM_TEMPLATE).accept(template))
			{
				frm.addActiveTemplate(template);
				continue;
			}

			frm.addInactiveTemplate(template);
		}
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception
	{
		ListPaymentsAndTemplatesForm frm = (ListPaymentsAndTemplatesForm) form;

		Long id = StrutsUtils.parseIds(frm.getSelectedIds()).get(0);

		EditTemplateDocumentOperation operation = createEditOperation(new ExistingTemplateSource(id, new OwnerTemplateValidator(), new ERIBTemplateValidator()));
		try
		{
			operation.edit();
		}
		catch (BusinessLogicException e)
		{
			saveError(request, e.getMessage());
			return start(mapping, form, request, response);
		}

		addLogParameters(new BeanLogParemetersReader("Редактируемая сущность", operation.getTemplate()));

		return DefaultTemplateAction.redirectEditTemplateForm(operation.getTemplate(), operation.getExecutor().getCurrentState());
	}

	/**
	 * Переход на форму добавления напоминания об оплате к шаблону.
	 * @throws Exception
	 */
	public ActionForward addReminder(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception
	{
		ListPaymentsAndTemplatesForm frm = (ListPaymentsAndTemplatesForm) form;

		Long id = StrutsUtils.parseIds(frm.getSelectedIds()).get(0);
		EditTemplateDocumentOperation operation = createEditOperation(new ExistingTemplateSource(id, new OwnerTemplateValidator(), new ERIBTemplateValidator()));
		MachineState currentState = operation.getExecutor().getCurrentState();
		//для шаблонов требующих только подтверждения клиента - переход на урл editа.
		if(currentState.getId().equals("SAVED_TEMPLATE"))
		{
			operation.edit();
		}

		addLogParameters(new BeanLogParemetersReader("Редактируемая сущность", operation.getTemplate()));

		return DefaultTemplateAction.redirectEditTemplateForm(operation.getTemplate(), operation.getExecutor().getCurrentState());
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(RemoveClientTemplateOperation.class, "FavouriteManagment");
	}

	protected EditTemplateDocumentOperation createEditOperation(TemplateDocumentSource source) throws BusinessLogicException, BusinessException
	{
		String serviceName = source.getMetadata().getName();
		EditTemplateDocumentOperation operation = createOperation(getEditOperationClass(serviceName), serviceName);
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
}
