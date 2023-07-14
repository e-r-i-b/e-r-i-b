package com.rssl.phizic.web.cards.claims;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.sberbankForEveryDay.ListGuestDebitCardClaimsOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.SaveFilterParameterAction;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.Map;

/**
 * Список заявок гостя на дебетовые карты
 * User: miklyaev
 * @ $Author$
 * @ $Revision$
 */
public class ListGuestDebitCardClaimsAction extends SaveFilterParameterAction
{
	@Override
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(ListGuestDebitCardClaimsOperation.class, "DebitCardClaimsService");
	}

	@Override
	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ListGuestDebitCardClaimsForm.FILTER_FORM;
	}

	@Override
	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		String phone = (String)filterParams.get(ListGuestDebitCardClaimsForm.PHONE_FIELD_NAME);
		if ((Boolean)filterParams.get(ListGuestDebitCardClaimsForm.SEARCH_BY_PHONE_FIELD_NAME))
			query.setParameter("phone", phone);
		else
			query.setParameter("login", filterParams.get(ListGuestDebitCardClaimsForm.LOGIN_FIELD_NAME));
		query.setParameter("startDate", filterParams.get(ListGuestDebitCardClaimsForm.FROM_DATE_FIELD_NAME));
		query.setParameter("endDate", filterParams.get(ListGuestDebitCardClaimsForm.TO_DATE_FIELD_NAME));
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws Exception
	{
		if (filterParams.get(ListGuestDebitCardClaimsForm.SEARCH_BY_PHONE_FIELD_NAME) == null || (StringHelper.isEmpty((String)filterParams.get(ListGuestDebitCardClaimsForm.PHONE_FIELD_NAME)) &&
				StringHelper.isEmpty((String)filterParams.get(ListGuestDebitCardClaimsForm.LOGIN_FIELD_NAME))))
			return;
		super.doFilter(filterParams, operation, frm);
	}
}
