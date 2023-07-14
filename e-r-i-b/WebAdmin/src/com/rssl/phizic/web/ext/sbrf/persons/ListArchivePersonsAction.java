package com.rssl.phizic.web.ext.sbrf.persons;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.person.GetDeletedPersonListOperation;
import com.rssl.phizic.web.actions.SaveFilterParameterAction;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.persons.ListPersonsForm;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author Dorzhinov
 * @ created 04.08.2011
 * @ $Author$
 * @ $Revision$
 */
public class ListArchivePersonsAction extends SaveFilterParameterAction
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(GetDeletedPersonListOperation.class);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ListPersonsForm.FILTER_FORM;
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		query.setParameter("documentSeries", filterParams.get("documentSeries"));
		query.setParameter("documentNumber", filterParams.get("documentNumber"));
		query.setParameter("agreementNumber", filterParams.get("agreementNumber"));
		query.setParameter("fio", filterParams.get("fio"));
		query.setParameter("loginId",filterParams.get("loginId"));

		query.setMaxResults(webPageConfig().getListLimit() + 1);
    }

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws Exception
	{
		boolean isFromStart = frm.isFromStart();

		Query query = operation.createQuery(getQueryName(frm));
		fillQuery(query, filterParams);		
		frm.setData((!isFromStart)?query.executeList():new ArrayList(0));
		frm.setFilters(filterParams);
		updateFormAdditionalData(frm, operation);
	}
}
