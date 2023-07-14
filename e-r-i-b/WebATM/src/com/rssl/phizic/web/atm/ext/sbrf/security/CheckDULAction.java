package com.rssl.phizic.web.atm.ext.sbrf.security;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.security.SecurityUtil;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.web.struts.forms.FormHelper;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * авторизация по ДУЛ
 * @author Jatsky
 * @ created 05.07.2013
 * @ $Author$
 * @ $Revision$
 */

public class CheckDULAction extends OperationalActionBase
{
	private static final String UNAUTHORIZED_FORWARD = "GotoUnauthorizedIndex";
	private static final String FORWARD_FAIL = "Fail";


	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CheckDULForm frm = (CheckDULForm) form;
		if (!SecurityUtil.isAuthenticationComplete())
			return mapping.findForward(UNAUTHORIZED_FORWARD);

		FormProcessor<ActionMessages, ?> formProcessor =
				FormHelper.newInstance(new RequestValuesSource(currentRequest()), getLoginForm());

		if (!formProcessor.process())
		{
			saveErrors(request, formProcessor.getErrors());
			return mapping.findForward(FORWARD_SHOW);
		}

		if (!PersonHelper.checkDUL(frm.getCode()))
		{
			frm.setErrMessage(StrutsUtils.getMessage("error.checkDUL.failed", "securityBundle"));
			return mapping.findForward(FORWARD_FAIL);
		}

		return mapping.findForward(FORWARD_SHOW);
	}

	private Form getLoginForm()
	{
		return CheckDULForm.ATM_CHECK_DUL_FORM;
	}
}
