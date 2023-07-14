package com.rssl.phizic.web.common.dictionaries;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.dictionaries.securities.GetSecuritiesListOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.Map;

/**
 * @author mihaylov
 * @ created 09.09.2010
 * @ $Author$
 * @ $Revision$
 */

public class ShowSecuritiesListAction extends ListActionBase
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(GetSecuritiesListOperation.class);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ShowSecuritiesListForm.FILTER_FORM;
	}

	protected void updateFormAdditionalData(ListFormBase form, ListEntitiesOperation operation) throws BusinessException
	{
		ShowSecuritiesListForm frm = (ShowSecuritiesListForm) form;
		GetSecuritiesListOperation op = (GetSecuritiesListOperation) operation;
		frm.setSecurityTypes(op.getOpenSecurityTypes());
	}
	
	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		query.setParameter("issuer",filterParams.get("issuer"));
		query.setParameter("type",filterParams.get("type"));
		query.setParameter("number",filterParams.get("registrationNumber"));
		query.setMaxResults(webPageConfig().getListLimit() + 1);
	}
}
