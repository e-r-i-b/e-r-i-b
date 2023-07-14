package com.rssl.phizic.web.dictionaries;

import com.rssl.phizic.operations.dictionaries.contact.GetMemberListOperation;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.common.forms.Form;

import java.util.Map;

/**
 * @author Kosyakov
 * @ created 15.11.2005
 * @ $Author$
 * @ $Revision$
 */
public class ShowContactMemberListAction extends ListActionBase
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(GetMemberListOperation.class, getCurrentMapping().getParameter());
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ShowContactMemberListForm.FILTER_FORM;
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		query.setParameter("countryName", filterParams.get("countryName"));
		query.setParameter("city", filterParams.get("city"));
		query.setParameter("name", filterParams.get("name"));
		query.setMaxResults(webPageConfig().getListLimit() +1);
	}
}
