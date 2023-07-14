package com.rssl.phizic.web.client.login;

import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.authgate.AuthConfig;
import com.rssl.phizic.authgate.AuthGateSingleton;
import com.rssl.phizic.business.clients.SelfRegistrationHelper;
import com.rssl.phizic.common.types.client.LoginType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.security.RegistrationStatus;
import com.rssl.phizic.self.registration.SelfRegistrationConfig;
import com.rssl.phizic.web.ext.sbrf.SBRFLoginActionBase;
import com.rssl.phizic.web.security.PageTokenUtil;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Опевещение о необходимости перезайти в систему под паролем и логином ЦСА.
 * Также выполняется проверка необходимости самостоятельной регистрации.
 *
 * @author bogdanov
 * @ created 29.04.2013
 * @ $Author$
 * @ $Revision$
 */

public class SelfRegistrationAction extends SBRFLoginActionBase
{
	private static final String FORWARD_NEW_SHOW = "NewShow";

	protected Map<String, String> getKeyMethodMap()
	{
	    Map<String,String> map = new HashMap<String, String>();
		map.put("cancel","cancel");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		LoginType loginType = AuthenticationContext.getContext().getLoginType();
		if (LoginType.DISPOSABLE.equals(loginType))
		{
			AuthenticationContext.getContext().setRegistrationStatus(RegistrationStatus.NOT_EXIST);
		}

		SelfRegistrationHelper selfRegistrationHelper = SelfRegistrationHelper.getIt();
		SelfRegistrationForm frm = (SelfRegistrationForm) form;

		SelfRegistrationConfig selfRegistrationConfig = ConfigFactory.getConfig(SelfRegistrationConfig.class);
		if (selfRegistrationConfig.isNewSelfRegistrationDesign())
		{
			if (selfRegistrationConfig.isShowLoginSelfRegistrationScreen() && selfRegistrationHelper.getRegistrationStatus() == RegistrationStatus.EXIST && loginType == LoginType.TERMINAL)
			{
				AuthConfig config = AuthGateSingleton.getAuthService().getConfig();
				frm.setUrlRegistration(config.getProperty("csaFront.registration.url"));
				frm.setHardRegistrationMode(selfRegistrationHelper.getHardRegistrationMode());
				frm.setPageToken(PageTokenUtil.getToken(request.getSession(false), true));
				frm.setDenyMultipleRegistration(selfRegistrationConfig.isDenyMultipleRegistration());
				frm.setHintDelay(selfRegistrationConfig.getHintDelay());
				return mapping.findForward(FORWARD_NEW_SHOW);
			}
			else
			{
				completeStage();
				return mapping.findForward(FORWARD_SHOW);
			}
		}
		else
		{
			if (!selfRegistrationHelper.getNeedHardRegistrationPage())
			{
				// Просто завершаем шаг. Метод continueStage вызывается либо на предудущем шаге, если у клиента
				// один договор, либо на следующем, если договоров два и более.
				completeStage();
				return mapping.findForward(FORWARD_SHOW);
			}

			frm.setTitlePreRegistrationMessage(selfRegistrationConfig.getWindowTitleMessage());
		    frm.setPreRegistrationMessage(selfRegistrationHelper.getPreregistrationMessage());
			return mapping.findForward(FORWARD_SHOW);
		}
	}

	public ActionForward cancel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SelfRegistrationHelper selfRegistrationHelper = SelfRegistrationHelper.getIt();
		if (!selfRegistrationHelper.getHardRegistrationMode())
			completeStage();
		return mapping.findForward(FORWARD_NEW_SHOW);
	}
}
