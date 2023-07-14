package com.rssl.phizic.web.groups;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.groups.GetEmployeeListDictionaryOperation;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.common.forms.Form;

import java.util.*;

/**
 * Created by IntelliJ IDEA. User: Omeliyanchuk Date: 15.11.2006 Time: 16:56:28 To change this template use
 * File | Settings | File Templates.
 */
public class ShowEmployeeDictionaryAction extends ListActionBase
{
	protected ListEntitiesOperation createListOperation(ListFormBase form) throws BusinessException, BusinessLogicException
	{
		ShowEmployeeDictionaryForm frm = (ShowEmployeeDictionaryForm) form;
		if( frm.getGroupId() == null)
			throw new BusinessException("Не установлен идентификатор группы");

		GetEmployeeListDictionaryOperation operation = createOperation(GetEmployeeListDictionaryOperation.class);
		String state = (String) frm.getField("state");

		operation.setGroupId( frm.getGroupId() );
		operation.setState( state );

		Date curTime = Calendar.getInstance().getTime();
		operation.setBlockedUntil(curTime);

        if (state == null || state.equals(""))
            operation.setBlocked(null);
        else if (state.equals("0"))
            operation.setBlocked(0);
        else if (state.equals("1"))
            operation.setBlocked(1);

		return operation;
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ShowEmployeeDictionaryForm.FILTER_FORM;
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		query.setParameter("surName",filterParams.get("surName") );
		query.setParameter("firstName",filterParams.get("firstName") );
		query.setParameter("patrName",filterParams.get("patrName") );
		query.setParameter("info",filterParams.get("info") );
        query.setParameter("branchCode",filterParams.get("branchCode") );
        query.setParameter("departmentCode",filterParams.get("departmentCode") );
		query.setMaxResults(webPageConfig().getListLimit() + 1);
	}
}

