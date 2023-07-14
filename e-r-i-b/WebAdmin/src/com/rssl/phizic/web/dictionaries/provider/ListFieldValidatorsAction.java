package com.rssl.phizic.web.dictionaries.provider;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.dictionaries.provider.ListFieldValidatorsOperation;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import java.util.Map;

/**
 * @author krenev
 * @ created 05.04.2010
 * @ $Author$
 * @ $Revision$
 */
public class ListFieldValidatorsAction extends ServiceProviderListActionBase
{
	protected ListEntitiesOperation createListOperation(ListFormBase form) throws BusinessException, BusinessLogicException
	{
		ListFieldValidatorsForm frm = (ListFieldValidatorsForm) form;
		ListFieldValidatorsOperation operation = createOperation("ListFieldValidatorsOperation");
		operation.initialize(frm.getId());
		return operation;
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation("RemoveFieldValidatorOperation");
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws Exception
	{
		Query query = operation.createQuery(getQueryName(frm));
		ListFieldValidatorsForm form = (ListFieldValidatorsForm) frm;
		query.setParameter("recipienId", form.getId());
		form.setData(query.executeList());
		updateFormAdditionalData(frm, operation);
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
}