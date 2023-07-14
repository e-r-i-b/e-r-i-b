package com.rssl.phizic.web.client.einvoicing;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.internetshop.FnsListOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mescheryakova
 * @ created 16.12.2010
 * @ $Author$
 * @ $Revision$
 */

public class ListFnsAction  extends ListActionBase
{
	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		FnsListOperation operation = createOperation(FnsListOperation.class, "FnsListOperationService");

		return operation;
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		Person person = getPerson();
		query.setParameter("person_id", person.getId());
		query.setParameter("logon_date", person.getLogin().getLogonDate());
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws Exception
	{
		super.doFilter(filterParams, operation, frm);
		ListFnsForm form = (ListFnsForm) frm;
		FnsListOperation fnsOperation = (FnsListOperation) operation;
		
		Map<String, String> linkTaxIndexState = fnsOperation.getLinkTaxIndexState(form.getData(), getPerson().getLogin());
		form.setLinkTaxIndexState(linkTaxIndexState);
	}

	protected void forwardFilterFail(ListFormBase frm, ListEntitiesOperation operation) throws Exception
	{
		ListFnsForm form = (ListFnsForm) frm;
		form.setLinkTaxIndexState(new HashMap<String, String>(0));
		super.forwardFilterFail(form, operation);
	}

	private Person getPerson()
	{
		PersonDataProvider personDataProvider =  PersonContext.getPersonDataProvider();
		PersonData personData = personDataProvider.getPersonData();
		return personData.getPerson();
	}
}