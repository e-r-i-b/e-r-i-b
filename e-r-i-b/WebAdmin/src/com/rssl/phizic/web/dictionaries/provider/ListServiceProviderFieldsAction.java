package com.rssl.phizic.web.dictionaries.provider;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.dictionaries.provider.ListServiceProviderFieldsOperation;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.struts.action.*;

import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author krenev
 * @ created 05.04.2010
 * @ $Author$
 * @ $Revision$
 */
public class ListServiceProviderFieldsAction extends ServiceProviderListActionBase
{
	protected Map<String, String> getAditionalKeyMethodMap()
	{
		Map<String, String> map = super.getAditionalKeyMethodMap();
		map.put("button.up", "upInList");
		map.put("button.down", "downInList");

		return map;
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws Exception
	{
		//переопределил, т. к. необходим порядок следования полей как в поставщике
		ListServiceProviderFieldsOperation op = (ListServiceProviderFieldsOperation) operation;
		frm.setData(op.getProvider().getFieldDescriptions());
		frm.setFilters(filterParams);
		updateFormAdditionalData(frm, operation);
	}

	public ActionForward upInList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ServiceProviderListFormBase frm = (ServiceProviderListFormBase) form;
		ListServiceProviderFieldsOperation operation = createOperation("ListServiceProviderFieldsOperation");
		operation.initialize(frm.getId(), frm.getSelectedId());
		operation.upInList();
		return start(mapping, form, request, response);
	}

	public ActionForward downInList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ServiceProviderListFormBase frm = (ServiceProviderListFormBase) form;
		ListServiceProviderFieldsOperation operation = createOperation("ListServiceProviderFieldsOperation");
		operation.initialize(frm.getId(), frm.getSelectedId());
		operation.downInList();
		return start(mapping, form, request, response);
	}

	protected ListEntitiesOperation createListOperation(ListFormBase form) throws BusinessException, BusinessLogicException
	{
		ListServiceProviderFieldsForm frm = (ListServiceProviderFieldsForm) form;
		ListServiceProviderFieldsOperation operation = createOperation("ListServiceProviderFieldsOperation");
		operation.initialize(frm.getId());
		return operation;
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation("RemoveServiceProviderFieldOperation");
	}

	protected ActionMessages doRemove(RemoveEntityOperation operation, Long id) throws Exception
	{
		ActionMessages msgs = new ActionMessages();
		try
		{
			super.doRemove(operation, id);
		}
		catch(BusinessLogicException ex)
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(ex.getMessage(),false));
		}
		return msgs;
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}
}
