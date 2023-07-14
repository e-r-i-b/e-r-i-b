package com.rssl.phizic.web.auth.guest;

import com.rssl.auth.csa.front.exceptions.InvalidCodeConfirmException;
import com.rssl.auth.csa.front.exceptions.SmsWasNotConfirmedInterruptStageException;
import com.rssl.auth.csa.front.exceptions.ValidateException;
import com.rssl.auth.csa.front.operations.auth.GuestEntryOperationInfo;
import com.rssl.auth.csa.front.operations.auth.OperationInfo;
import com.rssl.auth.csa.front.security.*;
import com.rssl.auth.security.SecurityManager;
import com.rssl.phizic.authgate.AuthConfig;
import com.rssl.phizic.authgate.AuthGateSingleton;
import com.rssl.phizic.captcha.CaptchaServlet;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.config.CSAFrontConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.http.UrlBuilder;
import com.rssl.phizic.web.auth.AuthStageActionBase;
import com.rssl.phizic.web.auth.AuthStageFormBase;
import com.rssl.phizic.web.auth.AuthenticationFormBase;
import com.rssl.phizic.web.util.RequestHelper;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author tisov
 * @ created 17.12.14
 * @ $Author$
 * @ $Revision$
 * [Гостевой вход] Экшн гостевого авторизации по номеру телефона
 */
public class GuestEntryAction extends AuthStageActionBase
{
	private static final String START_FORWARD_NAME = "Start";
	private static final String FINAL_FORWARD_NAME = "Final";
	private static final String EXPRESS_CLAIM_FORWARD_NAME = "ExpressClaim";
	private static final String GUEST_ENTRY_DISABLED_FORWARD_NAME = "Disabled";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new TreeMap<String, String>();
		map.put("begin", "begin");
		map.put("next", "next");
		return map;
	}

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CSAFrontConfig config = ConfigFactory.getConfig(CSAFrontConfig.class);
		if (config.isGuestEntryMode())
			return mapping.findForward(START_FORWARD_NAME);

		return mapping.findForward(GUEST_ENTRY_DISABLED_FORWARD_NAME);

	}

	@Override
	protected OperationInfo getOperationInfo()
	{
		return new GuestEntryOperationInfo(GuestEntryStages.INITIAL);
	}

	@Override
	protected boolean isRequiredShowCaptcha(HttpServletRequest request, String servletName, SecurityManager securityManager,String key, boolean isAlwaysShow)
	{
		if (!ConfigFactory.getConfig(CSAFrontConfig.class).isGuestCapthcaActive())
			return false;

		CaptchaServlet.CheckState checkState = getCaptchaCheckState(request, servletName);
		if (getIPSecurityManager().needShowCaptchaForOvercountPhones(RequestHelper.getIpAddress(request))
				&& checkState != CaptchaServlet.CheckState.VALID_CODE)
		{
			saveError(request, new ActionMessage(CAPTCHA_CODE_PARAMETER_NAME, new ActionMessage(INVALID_CODE_MESSAGE, false)));
			return true;
		}

		return super.isRequiredShowCaptcha(request, servletName, securityManager, key, isAlwaysShow, checkState);
	}

	@Override
	protected ActionForward doBeginStage(ActionMapping mapping,  AuthStageFormBase frm, HttpServletRequest request, OperationInfo info) throws Exception
	{
		String phone = (String) frm.getField(AuthenticationFormBase.PHONE_NUMBER);
		if (StringHelper.isNotEmpty(phone) && ConfigFactory.getConfig(CSAFrontConfig.class).isGuestCapthcaActive())
		{
			getIPSecurityManager().addPhone(RequestHelper.getIpAddress(request), phone);
		}

		return super.doBeginStage(mapping, frm, request, info);
	}

	protected ActionForward doNextStage(ActionMapping mapping,  AuthStageFormBase frm, HttpServletRequest request, OperationInfo info) throws Exception
	{
		ActionForward forward;
		try
		{
			forward =  super.doNextStage(mapping, frm, request, info);
		}
		catch (SmsWasNotConfirmedInterruptStageException e)
		{
			((GuestEntryForm)frm).setSMSAttemptsEnded(true);
			return mapping.findForward(ERROR_FORWARD);
		}
		catch (InvalidCodeConfirmException e)
		{
			saveError(request, new ActionMessage(AuthenticationFormBase.CONFIRM_PASSWORD_FIELD, new ActionMessage(e.getMessage(), false)));
			return mapping.findForward(ERROR_FORWARD);
		}
		catch(ValidateException e)
		{
			saveError(request, new ActionMessage(AuthenticationFormBase.CONFIRM_PASSWORD_FIELD, new ActionMessage(e.getMessage(), false)));
			forward = mapping.findForward(ERROR_FORWARD);
		}

		GuestEntryOperationInfo operationInfo = (GuestEntryOperationInfo) info;
		frm.setRedirect(getGuestLoginRedirect(operationInfo.getHost()));

		return forward;
	}

	private String getGuestLoginRedirect(String host)
	{
		AuthConfig authConfig = AuthGateSingleton.getAuthService().getConfig();
		UrlBuilder builder = new UrlBuilder(String.format(authConfig.getProperty("guest.postRegistration.url"), host));
		return builder.getUrl();
	}

	protected GuestEntryIPSecurityManager getIPSecurityManager()
	{
		return GuestEntryIPSecurityManager.getIt();
	}
}
