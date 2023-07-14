package com.rssl.phizic.web.persons.search;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.operations.person.ShowPersonInfoOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.web.persons.SearchPersonForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author akrenev
 * @ created 26.04.2012
 * @ $Author$
 * @ $Revision$
 */
public abstract class ShowPersonActionBase extends OperationalActionBase
{
	protected Map<String, String> getKeyMethodMap()
	{
		return new HashMap<String, String>();
	}

	protected abstract ShowPersonInfoOperation createShowOperation() throws BusinessException;

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SearchPersonForm frm = (SearchPersonForm) form;

		ShowPersonInfoOperation operation = createShowOperation();
		operation.initialize();

		//Фиксируем данные клиента
		addLogParameters(new BeanLogParemetersReader("Данные клиента", operation.getPerson()));

		updateForm(frm, operation);

		return mapping.findForward(FORWARD_START);
	}

	private void updateForm(SearchPersonForm frm, ShowPersonInfoOperation operation)
	{
		ActivePerson person = operation.getPerson();

		frm.setField("surName", person.getSurName());
		frm.setField("firstName", person.getFirstName());
		frm.setField("patrName", person.getPatrName());
		frm.setField("birthDay", person.getBirthDay().getTime());
		frm.setField("department", operation.getUserDepartment());

		frm.setPersonDocuments(person.getPersonDocuments());
		frm.setActivePerson(person);
	}
}