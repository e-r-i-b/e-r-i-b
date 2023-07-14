package com.rssl.phizic.web.common.client.ext.sbrf.security;

import com.rssl.phizic.auth.LoginBlock;
import com.rssl.phizic.auth.SecurityService;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.login.LoginHelper;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.common.types.security.SecurityType;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.web.ext.sbrf.SBRFLoginActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 @author Pankin
 @ created 14.12.2010
 @ $Author$
 @ $Revision$
 */
public class ChooseDepartmentAction extends SBRFLoginActionBase
{
	private static final SimpleService simpleService = new SimpleService();
	private static final SecurityService securityService = new SecurityService();

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> keyMap = new HashMap<String, String>();
		keyMap.put("button.enter", "choose");
		return keyMap;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		updateForm(form);
		return mapping.findForward(FORWARD_SHOW);
	}

	public ActionForward choose(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ChooseDepartmentForm frm = (ChooseDepartmentForm) form;
		Long personId = frm.getSelectedId();
		Person person = personService.findById(personId);
		AuthenticationContext authenticationContext = getAuthenticationContext();
		if (person.getCreationType() == CreationType.SBOL)
		{
			PersonHelper.updatePersonPassportWay(person, authenticationContext.getDocumentNumber());
		}
		fillTrust(person, authenticationContext.getSecurityType());
		authenticationContext.setLogin(person.getLogin());
		return continueStage(mapping, request);
	}

	protected void fillTrust(Person person, SecurityType securityType) throws BusinessException
	{
		person.setSecurityType(securityType);
		person.saveStoreSecurityType(securityType);
		personService.update(person);
	}

	private void updateForm(ActionForm form) throws BusinessException, BusinessLogicException
	{
		ChooseDepartmentForm frm = (ChooseDepartmentForm) form;
		List<Long> personIds = getAuthenticationContext().getPersonIds();
		if (personIds == null)
			throw new BusinessException("Ошибка при выборе договора. Не хватает данных для получения списка клиентов.");

		List<Person> persons = simpleService.findByIds(Person.class, personIds);

		frm.setPersons(persons);
		frm.setAccountLinks(LoginHelper.getLinks(persons, AccountLink.class));
		frm.setCardLinks(LoginHelper.getLinks(persons, CardLink.class));
		frm.setLoanLinks(LoginHelper.getLinks(persons, LoanLink.class));

		Map<Person, Boolean> personBlocked = new HashMap<Person, Boolean>(persons.size());
		for (Person person : persons)
		{
			List<LoginBlock> loginBlocks = securityService.getBlocksForLogin(person.getLogin(), Calendar.getInstance().getTime(), null);
			personBlocked.put(person, loginBlocks.size() > 0);
		}
		frm.setPersonBlocked(personBlocked);
	}

}
