package com.rssl.phizic.web.common.dictionaries;

import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.operations.dictionaries.GetAccountsListOperation;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;

import java.util.Map;
import java.util.HashMap;

/**
 * @author Omeliyanchuk
 * @ created 07.02.2007
 * @ $Author$
 * @ $Revision$
 */

/**
 * справочник списка 
 */
public class ShowAccountsListAction extends ListActionBase//TODO operationQuery??
{
	protected Map<String, String> getAditionalKeyMethodMap()
	{
	    Map<String,String> map=new HashMap<String, String>();
	    map.put("button.find", "start");
	    return map;
	}

	protected ListEntitiesOperation createListOperation(ListFormBase form) throws BusinessException, BusinessLogicException
	{
		ShowAccountsListForm     frm       = (ShowAccountsListForm)form;
		GetAccountsListOperation operation = createOperation(GetAccountsListOperation.class,getCurrentMapping().getParameter());
		if(frm.getPersonId() == 0)
	        operation.initialize();
	    else
	        operation.initialize(frm.getPersonId());

		return operation;
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws Exception
	{
		GetAccountsListOperation op  = (GetAccountsListOperation) operation;
		frm.setData(op.getAccountLinks());
	}
}
