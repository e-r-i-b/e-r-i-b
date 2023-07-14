package com.rssl.phizic.web.ext.sbrf.persons.templates;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.templates.service.TemplateDocumentService;
import com.rssl.phizic.business.documents.templates.service.filters.*;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.ext.sbrf.payment.templates.EmployeeListTemplateOperation;
import com.rssl.phizic.web.actions.SaveFilterParameterAction;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.utils.StringHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * @ author: Vagin
 * @ created: 05.04.2013
 * @ $Author
 * @ $Revision
 * Ёкшен просмотра списка шаблонов клиента.
 */
public class EmpolyeeListTemplatesAction extends SaveFilterParameterAction
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation("EmployeeListTemplateOperation", "EmployeeTemplateManagement");
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return EmpolyeeListTemplatesForm.FILTER_FORM;
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase form) throws Exception
	{
		EmpolyeeListTemplatesForm frm = (EmpolyeeListTemplatesForm) form;
		EmployeeListTemplateOperation op = (EmployeeListTemplateOperation) operation;
		op.initialize(frm.getPerson(), getFilters(filterParams));

		frm.setData(op.getTemplates());
		frm.setFilters(filterParams);
		frm.setActivePerson(op.getPerson());
	}

	protected Map<String, Object> getDefaultFilterParameters(ListFormBase frm, ListEntitiesOperation operation)
				throws BusinessException, BusinessLogicException
	{
		Map<String, Object> params = super.getDefaultFilterParameters(frm, operation);
		params.put("showDeleted", false);
		return params;
	}

	private TemplateDocumentFilter[] getFilters(Map<String, Object> filterParams)
	{
		return new TemplateDocumentFilter[]{
				new ClientCreationDateTemplateIntervalFilter(filterParams), new ClientOperationDateTemplateIntervalFilter(filterParams), new EmployeeOperationDateTemplateIntervalFilter(filterParams),
				new ReceiverNameTemplateFilter(filterParams), new ResemblingNameTemplateFilter(filterParams), new TemplateNumberFilter(filterParams),
				new ClientCreationChannelTemplateFilter(filterParams), new ClientOperationChannelTemplateFilter(filterParams),
				new ExactAmountTemplateIntervalFilter(filterParams), new OperationEmployeeNameTemplateValidator(filterParams),
				new FormNameTemplateFilter(filterParams), new StateTemplateFilter(filterParams), new ActiveTemplateFilter(filterParams)
			};
	}
}
