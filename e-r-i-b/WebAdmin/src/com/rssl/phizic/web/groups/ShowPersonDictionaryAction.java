package com.rssl.phizic.web.groups;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.groups.GetPersonListDictionaryOperation;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.persons.ListPersonsAction;

import java.util.Collections;
import java.util.Map;

@SuppressWarnings({"JavaDoc"})
public class ShowPersonDictionaryAction extends ListPersonsAction
{
	protected ListEntitiesOperation createListOperation(ListFormBase form) throws BusinessException, BusinessLogicException
	{
		ShowPersonDictionaryForm frm = (ShowPersonDictionaryForm) form;
		if( frm.getGroupId() == null)
			throw new BusinessException("Не установлен идентификатор группы");

		GetPersonListDictionaryOperation operation = createOperation(GetPersonListDictionaryOperation.class);
		operation.initialize(frm.getGroupId());

		return operation;
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ShowPersonDictionaryForm.FILTER_FORM;
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		super.fillQuery(query, filterParams);
		query.setParameter("fio", filterParams.get("fio"));
		query.setParameter("mobile_phone", filterParams.get("mobile_phone"));
		query.setParameter("loginId", filterParams.get("loginId"));
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws Exception
	{
		if (frm.isFromStart())
		{
			frm.setData( Collections.emptyList() );
			frm.setFilters( filterParams );
			updateFormAdditionalData(frm, operation);
		}
		else
		{
			super.doFilter(filterParams, operation, frm);
		}
	}

	protected void updateFormAdditionalData(ListFormBase form, ListEntitiesOperation operation) throws BusinessException
	{
		GetPersonListDictionaryOperation op = (GetPersonListDictionaryOperation) operation;
		ShowPersonDictionaryForm frm = (ShowPersonDictionaryForm) form;

		frm.setAllowedTB(op.getAllowedTB());
	}
}
