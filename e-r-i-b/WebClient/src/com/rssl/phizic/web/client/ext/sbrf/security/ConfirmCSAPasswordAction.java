package com.rssl.phizic.web.client.ext.sbrf.security;

import com.rssl.phizic.authgate.*;
import com.rssl.phizic.authgate.authorization.AuthGateService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.web.security.ConfirmLoginActionBase;
import org.apache.struts.action.*;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Gainanov
 * @ created 03.08.2009
 * @ $Author$
 * @ $Revision$
 */
/**
 * Проверка одноразового (например,смс) пароля в ЦСА
 */
public class ConfirmCSAPasswordAction extends ConfirmLoginActionBase
{
	private static final String FORWARD_SHOW = "Show";
//	private static final String FORWARD_REDIRECT = "Redirect";

	protected Map<String, String> getKeyMethodMap()
	{
		return null;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
//		ConfirmCSAPasswordForm frm = (ConfirmCSAPasswordForm)form;
		if (request.getParameter("AuthToken") != null)
			return validateAuthentication(mapping, form, request, response);
		return createCSARequestAndRedirect(mapping, form, request, response);
	}

	private ActionForward createCSARequestAndRedirect(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException, BusinessLogicException
	{
		try
		{
			AuthGateService authService = AuthGateSingleton.getAuthService();
			AuthConfig csaConfig = authService.getConfig();
			AuthParamsContainer container = new AuthParamsContainer();
			container.addParameter("SID", getAuthenticationContext().getCSA_SID());
			container.addParameter("Service", "ESK2");
			container.addParameter("BackRef", csaConfig.getProperty("ikfl.add.auth.back.url"));
			container.addParameter("Text","VHOD V SYSTEMU");
			container.addParameter("AuthType", "");
			container.addParameter("OID", "ff11");
			AuthParamsContainer result = authService.prepareAuthentication(container);
			String authToken = result.getParameter("AuthToken");
			ConfirmCSAPasswordForm frm = (ConfirmCSAPasswordForm)form;
			frm.setPath(csaConfig.getProperty("csa.password.url")+"?AuthToken="+authToken);
//			frm.setCsa(true);
			return mapping.findForward(FORWARD_SHOW);
//			return new ActionForward(csaConfig.getProperty("csa.password.url")+"?AuthToken="+authToken, true);
		}
		catch (AuthGateException e)
		{
			throw new BusinessException(e);
		}
		catch (AuthGateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
	}

	private ActionForward validateAuthentication(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException, BusinessLogicException
	{
		try
		{
//			if(request.getParameter("redirect")!= null )
//			{
//				ConfirmCSAPasswordForm frm = (ConfirmCSAPasswordForm)form;
//				frm.setToken(request.getParameter("AuthToken"));
//				return mapping.findForward(FORWARD_REDIRECT);
//			}
			String authToken = request.getParameter("AuthToken");
			AuthGateService authService = AuthGateSingleton.getAuthService();
			AuthParamsContainer container = new AuthParamsContainer();
			container.addParameter("AuthToken", authToken);
			container.addParameter("Service", "ESK2");
			authService.checkAuthentication(container);
			completeStage();
			return mapping.findForward(FORWARD_SHOW);
		}
		catch (AuthGateException e)
		{
			throw new BusinessException(e);
		}
		catch (AuthGateLogicException e)
		{
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
			saveErrors(request.getSession(), errors);
			return mapping.findForward(FORWARD_SHOW);
		}
	}
}
