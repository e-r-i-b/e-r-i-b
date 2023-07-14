package com.rssl.phizic.web.passwordcards;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.passwordcards.GetPasswordCardsOperation;
import com.rssl.phizic.operations.passwordcards.RemovePasswordCardsOperation;
import com.rssl.phizic.web.actions.SaveFilterParameterAction;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.HashMap;
import java.util.Map;


/**
 * @author Roshka
 * @ created 29.09.2005
 * @ $Author$
 * @ $Revision$
 */

public class ListUnassignedPrintPasswordCardAction extends SaveFilterParameterAction
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(GetPasswordCardsOperation.class);
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(RemovePasswordCardsOperation.class);
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws DataAccessException, BusinessException, BusinessLogicException
	{
		Query query = operation.createQuery(getQueryName(frm));
		fillQuery(query, new HashMap<String, Object>(frm.getFilters()));
		frm.setData(query.executeList());
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ListUnassignedPasswordCardsForm.FILTER_FORM;
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams)
	{
		//filterParams.remove("keysCount");
		//filterParams.remove("cardsCount");

		query.setParameter("toDate", filterParams.get("toDate"));
		query.setParameter("fromDate", filterParams.get("fromDate"));
		query.setParameter("number", filterParams.get("number"));
		query.setParameter("passwordsCount", filterParams.get("passwordsCount"));
		
		query.setParameters(filterParams);
	}
}
