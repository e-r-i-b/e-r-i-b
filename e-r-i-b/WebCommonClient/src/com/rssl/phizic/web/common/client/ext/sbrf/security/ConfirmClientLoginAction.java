package com.rssl.phizic.web.common.client.ext.sbrf.security;

import com.rssl.phizic.auth.modes.ConfirmStrategy;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.security.config.SecurityConfig;
import com.rssl.phizic.web.common.confirm.AutoConfirmRequestType;
import com.rssl.phizic.web.common.confirm.ConfirmHelper;
import com.rssl.phizic.utils.StringHelper;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author gladishev
 * @ created 23.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class ConfirmClientLoginAction extends ConfirmLoginAction
{
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ConfirmStrategy strategy = initConfirmForm((ConfirmWay4PasswordForm) form, request);

		SecurityConfig securityConfig = ConfigFactory.getConfig(SecurityConfig.class);
		if (securityConfig.getNeedLoginConfirmAutoselect() && getErrors(request).isEmpty())
		{
			String userOptionType =  getAuthenticationContext().getPolicyProperties().getProperty("userOptionType");
			if (StringHelper.isEmpty(userOptionType))
				userOptionType="sms";

			ConfirmStrategyType type = ConfirmStrategyType.valueOf(userOptionType);
			((ConfirmWay4PasswordForm)form).setAutoConfirmRequestType(AutoConfirmRequestType.login);
			if (type == ConfirmStrategyType.card &&
					ConfirmHelper.strategySupported(strategy, ConfirmStrategyType.card))
			{
				return showCardsConfirm(mapping, form, request, response);
			}
			else if (type == ConfirmStrategyType.push &&
					ConfirmHelper.strategySupported(strategy, ConfirmStrategyType.push))
			{
				return changeToPush(mapping, form, request, response);
			}
			else
			{
				return changeToSMS(mapping, form, request, response);
			}
		}

		return mapping.findForward(FORWARD_SHOW);
	}
}
