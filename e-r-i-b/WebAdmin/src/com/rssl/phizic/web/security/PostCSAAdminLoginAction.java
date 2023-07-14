package com.rssl.phizic.web.security;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.config.csaadmin.CSAAdminGateConfig;
import com.rssl.phizic.operations.csaadmin.auth.PostCSAAdminAuthOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.ext.sbrf.SBRFLoginActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mihaylov
 * @ created 17.10.13
 * @ $Author$
 * @ $Revision$
 *
 * ����� ����� � ��� ���������� ����� �������������� ������������ � CSAAdmin
 */
public class PostCSAAdminLoginAction extends SBRFLoginActionBase
{
	private static final String LOGIN_FORWARD = "Login";

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CSAAdminGateConfig csaAdminConfig = ConfigFactory.getConfig(CSAAdminGateConfig.class);
		//���� ������� �������� � ����������� ������, �� ���������� �� �������� ������ � �����
		if (!csaAdminConfig.isMultiBlockMode())
			return mapping.findForward(LOGIN_FORWARD);

		PostCSAAdminLoginForm frm = (PostCSAAdminLoginForm) form;
		String token = frm.getToken();
		//���� �� �������� ����� ������, �� ���������� ������������ �� �������� ������ � CSAAdmin
		if (StringHelper.isEmpty(token))
			return new ActionRedirect(csaAdminConfig.getLoginUrl());

		PostCSAAdminAuthOperation operation = new PostCSAAdminAuthOperation();
		operation.fillAuthenticationContext(token, PageTokenUtil.getTokenAsParameter(request.getSession(false)));

		completeStage();
		return null;
	}
}
