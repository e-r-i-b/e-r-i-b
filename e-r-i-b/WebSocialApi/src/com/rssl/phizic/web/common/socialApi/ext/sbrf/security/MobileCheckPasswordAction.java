package com.rssl.phizic.web.common.socialApi.ext.sbrf.security;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.exceptions.AuthenticationFailedException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.auth.csa.wsclient.exceptions.ConnectorBlockedException;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.auth.modes.Stage;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.security.SecurityUtil;
import com.rssl.phizic.web.security.AuthenticationManager;
import com.rssl.phizic.web.security.SessionIdFilter;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * проверка пароля для использования полного функционала мобильного приложения
 * @author Pankin
 * @ created 23.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class MobileCheckPasswordAction extends OperationalActionBase
{
	private static final String UNAUTHORIZED_FORWARD = "GotoUnauthorizedIndex";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> keyMethodMap = new HashMap<String, String>();
		keyMethodMap.put("check", "start");
		return keyMethodMap;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CheckPasswordForm frm = (CheckPasswordForm) form;
		if (StringHelper.isEmpty(frm.getPassword()))
		{
			saveError(request, new ActionMessage("error.login.password.failed"));
			return mapping.findForward(FORWARD_SHOW);
		}

		if (!SecurityUtil.isAuthenticationComplete())
			return mapping.findForward(UNAUTHORIZED_FORWARD);

		try
		{
			AuthenticationContext context = AuthenticationContext.getContext();
			CSABackRequestHelper.sendCheckPasswordRq(context.getCSA_SID(), frm.getPassword());
		}
		catch (ConnectorBlockedException e)
		{
			saveError(request, e.getMessage());

			Long badAuthDelay = e.getBadAuthDelay();
			if (badAuthDelay != null)
			{
				frm.setBadAuthDelay(badAuthDelay);
			}

			HttpSession session = request.getSession(false);
			if (session != null)
				session.setAttribute(SessionIdFilter.INVALIDATE_SESSION_KEY, true);
			return mapping.findForward(FORWARD_SHOW);
		}
		catch (AuthenticationFailedException ignored)
		{
			saveError(request, new ActionMessage("error.login.password.check.failed"));
			return mapping.findForward(FORWARD_SHOW);
		}
		catch (BackLogicException e)
		{
			saveError(request, e.getMessage());
			return mapping.findForward(FORWARD_SHOW);
		}

		try
		{
			MobileSecurityUtils.grantMobileFullAccess();
		}
		catch (InactiveExternalSystemException e)
		{
			saveInactiveESMessage(request, e.getMessage());
			return mapping.findForward(FORWARD_SHOW);
		}
		
		return mapping.findForward(FORWARD_SHOW);
	}
}
