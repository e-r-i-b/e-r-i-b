package com.rssl.phizic.web.dictionaries.pfp.insurance;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.dictionaries.pfp.insurance.ListInsuranceTypeOperation;
import com.rssl.phizic.operations.dictionaries.pfp.insurance.RemoveInsuranceTypeOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.struts.action.ActionMessages;

/**
 * @author akrenev
 * @ created 15.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListInsuranceTypeAction extends ListActionBase
{
	private static final String FOR_PARENT_DICTIONARY_ACTION_NAME = "parentDictionary";
	private static final String PARENT_LIST_QUERY_NAME = "parentList";
	
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(ListInsuranceTypeOperation.class);
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(RemoveInsuranceTypeOperation.class);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}

	protected String getQueryName(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		String action = ((ListInsuranceTypeForm) frm).getAction();
		if (FOR_PARENT_DICTIONARY_ACTION_NAME.equals(action))
			return PARENT_LIST_QUERY_NAME;
		return super.getQueryName(frm);
	}

	protected ActionMessages doRemove(RemoveEntityOperation operation, Long id) throws Exception
	{
		try
		{
			return super.doRemove(operation, id);
		}
		catch (BusinessLogicException ble)
		{
			saveMessage(currentRequest(), ble.getMessage());
		}
		return new ActionMessages();
	}
}
