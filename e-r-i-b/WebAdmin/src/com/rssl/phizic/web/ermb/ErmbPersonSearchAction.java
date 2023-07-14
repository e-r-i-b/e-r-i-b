package com.rssl.phizic.web.ermb;

import com.rssl.common.forms.Form;
import com.rssl.phizic.auth.modes.UserVisitingMode;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.ermb.person.ErmbPersonSearchOperation;
import com.rssl.phizic.operations.person.search.SearchPersonOperation;
import com.rssl.phizic.web.persons.SearchPersonForm;
import com.rssl.phizic.web.persons.formBuilders.ErmbPersonSearchFormBuilder;
import com.rssl.phizic.web.persons.search.SearchPersonActionBase;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionRedirect;

/**
 * User: Moshenko
 * Date: 27.05.2013
 * Time: 12:08:41
 * Поиск клиента для создания профиля ЕРМБ
 */
public class ErmbPersonSearchAction extends SearchPersonActionBase
{
	private static final String FORWARD_SUCCESS = "Success";

	@Override
	protected SearchPersonOperation createSearchOperation() throws BusinessException
	{
		return createOperation(ErmbPersonSearchOperation.class);
	}

	@Override
	protected ActionForward getStartActionForward()
	{
		return getCurrentMapping().findForward(FORWARD_START);
	}

	@Override
	protected ActionForward getErrorActionForward()
	{
		return getCurrentMapping().findForward(FORWARD_START);
	}

	@Override
	protected ActionForward getNextActionForward(SearchPersonForm frm)
	{
		ActionRedirect redirect = new ActionRedirect(getCurrentMapping().findForward(FORWARD_SUCCESS).getPath());
		redirect.addParameter("person", PersonContext.getPersonDataProvider().getPersonData().getPerson().getId());
		return redirect;
	}

	@Override
	protected UserVisitingMode getUserVisitingMode(SearchPersonForm frm)
	{
		if (((ErmbPersonSearchForm) frm).isSearchByPhone())
			return UserVisitingMode.EMPLOYEE_INPUT_BY_PHONE;
		return UserVisitingMode.EMPLOYEE_INPUT_BY_IDENTITY_DOCUMENT;
	}

	@Override
	protected Form getSearchForm(SearchPersonForm frm)
	{
		if (((ErmbPersonSearchForm) frm).isSearchByPhone())
			return ErmbPersonSearchFormBuilder.SEARCH_BY_PHONE_FORM;
		return super.getSearchForm(frm);
	}
}
