package com.rssl.phizic.web.common.socialApi.ext.sbrf.security;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.client.ext.sbrf.security.ChooseDepartmentAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Dorzhinov
 * @ created 25.10.2011
 * @ $Author$
 * @ $Revision$
 */
public class MobileChooseDepartmentAction extends ChooseDepartmentAction
{
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> keyMap = new HashMap<String, String>();
		keyMap.put("choose", "choose");
		return keyMap;
	}

	public ActionForward choose(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		MobileChooseDepartmentForm frm = (MobileChooseDepartmentForm) form;
		String agreementId = frm.getAgreementId();
		if(StringHelper.isEmpty(agreementId))
			throw new BusinessException("agreementId не может быть пустым");
		Long personId = Long.valueOf(agreementId);
		Person person = personService.findById(personId);
		if(person == null)
			throw new BusinessException(" лиент с users.id=" + personId + " не найден.");
		Login login = person.getLogin();
		AuthenticationContext authenticationContext = getAuthenticationContext();
		authenticationContext.setLogin(login);
		fillTrust(person, authenticationContext.getSecurityType());
		return continueStage(mapping, request);
	}
}
