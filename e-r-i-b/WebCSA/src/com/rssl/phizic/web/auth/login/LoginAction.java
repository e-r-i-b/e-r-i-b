package com.rssl.phizic.web.auth.login;

import com.rssl.auth.csa.front.operations.auth.LoginOperationInfo;
import com.rssl.auth.csa.front.operations.auth.OperationInfo;
import com.rssl.phizic.config.FrontSettingHelper;
import com.rssl.phizic.web.auth.AuthStageActionBase;
import com.rssl.phizic.web.auth.payOrder.PayOrderHelper;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author niculichev
 * @ created 26.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class LoginAction extends AuthStageActionBase
{
	public ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		if(FrontSettingHelper.isGlobalLoginRestriction())
			throw new UnsupportedOperationException("Глобальное ограничение входа");

		return super.doExecute(mapping, form, request, response);
	}

	protected OperationInfo getOperationInfo()
	{
		return new LoginOperationInfo(Stages.LOGIN);
	}

	protected void clearPayOrderData()
	{
		PayOrderHelper.clearPayOrderSessionData();	
	}

	protected List<String> getCaptchaMethodNames()
	{
		return Collections.emptyList();
	}
}
