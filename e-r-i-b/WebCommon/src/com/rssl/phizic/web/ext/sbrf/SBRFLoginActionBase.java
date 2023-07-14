package com.rssl.phizic.web.ext.sbrf;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.LoginExistValidator;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.StaticPersonData;
import com.rssl.phizic.business.login.LoginHelper;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.persons.clients.PersonImportService;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.security.password.BlockedException;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.security.LoginStageActionSupport;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;

/**
 @author Pankin
 @ created 11.11.2010
 @ $Author$
 @ $Revision$
 */
public abstract class SBRFLoginActionBase extends LoginStageActionSupport
{
	protected static final PersonService personService = new PersonService();
	protected static final PersonImportService personImportService = new PersonImportService();

	private static final String FORWARD_BLOCK = "Block";

	protected ActionForward continueStage(ActionMapping mapping, HttpServletRequest request) throws BusinessException, BusinessLogicException
	{
		return continueStage(mapping, request, null);
	}

	/**
	 * Продолжить стадию аутентификации
	 * @param mapping
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected ActionForward continueStage(ActionMapping mapping, HttpServletRequest request, ActionForm form) throws BusinessException, BusinessLogicException
	{
		AuthenticationContext authContext = getAuthenticationContext();
		Login login = (Login) authContext.getLogin();
		try
		{
			LoginExistValidator loginValidator = new LoginExistValidator();
			loginValidator.validateLoginInfo(login);
			LoginHelper.updateClientLogonData(login, authContext);
			completeStage();
		}
		catch (BlockedException ex)
		{
			ActivePerson person = personService.findByLogin(login);
			if (person == null)
				throw new SecurityException("Не найден клиент по логину = " + login.getUserId(), ex);
			PersonContext.getPersonDataProvider().setPersonData(new StaticPersonData(person));
			String name = person.getFirstName() + " " + StringHelper.getEmptyIfNull(person.getPatrName());
			ActionMessage msg = new ActionMessage("Уважаемый(ая) " + name + ", Ваша учетная запись заблокирована. <br/>" +
					"Для того чтобы возобновить обслуживание, позвоните в контактный центр по номеру " +
					"8-800-555-5550 или обратитесь в ближайшее отделение Сбербанка России.", false);
			return createErrorForward(mapping, request, form, msg);
		}
		return createSuccessForward(mapping);
	}

	protected ActionForward createSuccessForward(ActionMapping mapping)
	{
		return mapping.findForward(FORWARD_SHOW);
	}

	protected ActionForward createErrorForward(ActionMapping mapping, HttpServletRequest request, ActionForm form, ActionMessage msg)
	{
		ActionMessages msgs = new ActionMessages();
		msgs.add(ActionMessages.GLOBAL_MESSAGE, msg);
		saveErrors(request, msgs);
		return mapping.findForward(FORWARD_BLOCK);
	}
}
