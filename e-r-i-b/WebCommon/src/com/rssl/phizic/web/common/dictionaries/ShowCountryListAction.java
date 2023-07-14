package com.rssl.phizic.web.common.dictionaries;

import com.rssl.phizic.operations.dictionaries.GetCountryListOperation;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.common.forms.Form;

import java.util.Map;

/**
 * @author Kosyakova
 * @ created 07.12.2006
 * @ $Author$
 * @ $Revision$
 */
public class ShowCountryListAction extends ListActionBase
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(GetCountryListOperation.class, getCurrentMapping().getParameter());
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ShowCountryListForm.FILTER_FORM;
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		query.setParameter("code",filterParams.get("code"));
		query.setParameter("intCode",filterParams.get("intCode"));
		query.setParameter("name",filterParams.get("name"));
		query.setMaxResults(webPageConfig().getListLimit() + 1);
	}
}

