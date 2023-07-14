package com.rssl.phizic.web.access;

import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.auth.modes.UserRegistrationMode;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.MultiInstancePersonService;
import com.rssl.phizic.business.schemes.SharedAccessScheme;
import com.rssl.phizic.operations.access.AssignAccessHelper;
import com.rssl.phizic.operations.access.AssignPersonAccessOperation;
import com.rssl.phizic.operations.access.CategoryAssignAccessHelper;
import com.rssl.phizic.operations.access.PersonLoginSource;
import com.rssl.phizic.operations.person.EditPersonOperation;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: Roshka
 * Date: 07.10.2005
 * Time: 12:57:56
 */
@SuppressWarnings({"JavaDoc"})
public class AssignPersonAccessAction extends AssignAccessActionBase
{
	private MultiInstancePersonService personService = new MultiInstancePersonService();
	/** ¬ывести список прав пользовател€ */
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AssignPersonAccessForm frm = (AssignPersonAccessForm) form;
		Long personId = frm.getPerson();

		PersonLoginSource           loginSource  = new PersonLoginSource(personId);
		AssignAccessHelper assignAccessHelper    = CategoryAssignAccessHelper.createClient();

		AssignPersonAccessOperation simpleOperation = createOperation("ViewPersonAccessOperation");
		simpleOperation.initialize(loginSource, AccessType.simple, assignAccessHelper);
		updateForm(frm, loginSource.getPerson(), simpleOperation);

		frm.setModified(personService.getPersonInstanceName(personId)!=null);

		return mapping.findForward(FORWARD_SHOW);
	}

	/** Ќазначить пользователю права доступа */
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AssignPersonAccessForm frm = (AssignPersonAccessForm) form;
		Long personId = frm.getPerson();

		PersonLoginSource  loginSource        = new PersonLoginSource(personId);
		AssignAccessHelper assignAccessHelper = CategoryAssignAccessHelper.createClient();

		try
		{
			AssignPersonAccessOperation simpleOperation = createOperation(AssignPersonAccessOperation.class);
			simpleOperation.initialize(loginSource, AccessType.simple, assignAccessHelper);
			simpleOperation.switchToShadow();
			doOperation(frm.getSimpleAccess(), simpleOperation);

			AssignPersonAccessOperation secureOperation = createOperation(AssignPersonAccessOperation.class);
			secureOperation.initialize(loginSource, AccessType.secure, assignAccessHelper);
			secureOperation.switchToShadow();
			doOperation(frm.getSecureAccess(), secureOperation);

			AssignPersonAccessOperation smsBankingOperation = createOperation(AssignPersonAccessOperation.class);
			smsBankingOperation.initialize(loginSource, AccessType.smsBanking, assignAccessHelper);
			smsBankingOperation.switchToShadow();
			AssignAccessSubForm smsBankingSubform = frm.getSmsBankingAccess();
			for (SharedAccessScheme scheme : assignAccessHelper.getSchemes())
			{
				if ("sms-banking-scheme".equals(scheme.getKey()))
				{
					// дл€ sms-банкинга на форме не указываетс€ схема.. она предустановлена (в operations.xml)
					smsBankingSubform.setAccessSchemeId("" + scheme.getId());
				}
			}

			updatePerson(personId, frm);

			doOperation(frm.getSmsBankingAccess(), smsBankingOperation);
		}
		catch (BusinessLogicException e)
		{
			ActionMessages msgs = new ActionMessages();
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
			saveErrors(request, msgs);
			return start(mapping, frm, request, response);
		}

		return sendRedirectToSelf(request);
	}

	private void updatePerson(Long id, AssignPersonAccessForm frm) throws BusinessLogicException, BusinessException
	{
		EditPersonOperation personOperation = createOperation("EditPersonOperation");
		personOperation.setPersonId(id);
		ActivePerson person = personOperation.getPerson();

		if (frm.getUserRegistrationMode() != null)
			person.setUserRegistrationMode(UserRegistrationMode.valueOf(frm.getUserRegistrationMode()));

		personOperation.save();
		personOperation.switchToShadow();
	}
}