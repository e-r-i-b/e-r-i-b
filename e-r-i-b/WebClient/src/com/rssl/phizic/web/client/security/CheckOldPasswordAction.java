package com.rssl.phizic.web.client.security;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.operations.userprofile.ChangeClientPasswordOperation;
import com.rssl.phizic.self.registration.SelfRegistrationConfig;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.web.ext.sbrf.SBRFLoginActionBase;
import com.rssl.phizic.web.security.PageTokenUtil;
import org.apache.struts.action.*;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * экшн дл€ проверки устарел пароль или нет.
 *
 * @author bogdanov
 * @ created 11.09.2012
 * @ $Author$
 * @ $Revision$
 */

public class CheckOldPasswordAction extends SBRFLoginActionBase
{
	private static final String FORWARD_NEW_DESIGN_SHOW = "NewDesignShow";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.save", "changePassword");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		//ƒанные о том, что пароль устарел занос€тс€ во врем€ аутентификации.
		//«десь необходимо только проверить, что пароль устарел.
		//≈сли пароль не устарел, то просто переходим на следующий этап аутентификации.
		if (!AuthenticationContext.getContext().isExpiredPassword())
			return continueStage(mapping, request);

		SelfRegistrationConfig config = ConfigFactory.getConfig(SelfRegistrationConfig.class);
		if (config.isNewSelfRegistrationDesign())
		{
			CheckOldPasswordForm frm = (CheckOldPasswordForm) form;
			frm.setHintDelay(config.getHintDelay());
			frm.setPageToken(PageTokenUtil.getToken(request.getSession(false), true));
			return mapping.findForward(FORWARD_NEW_DESIGN_SHOW);
		}

		return mapping.findForward(FORWARD_SHOW);
	}

	public ActionForward changePassword(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionMessages errors = new ActionMessages();
		ActionForward forward = doChangePassword(mapping, form, request, response, errors);
		saveErrors(request, errors);

		return forward;
	}

	protected ActionForward doChangePassword(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionMessages errors) throws Exception
	{
		ChangeClientPasswordOperation op = createOperation(ChangeClientPasswordOperation.class);
		
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(new RequestValuesSource(request), CheckOldPasswordForm.CREATE_FORM);

		ActionForward forward = mapping.findForward(FORWARD_SHOW);
		if (!processor.process())
		{
			errors.add(processor.getErrors());
		}
		else
		{
			try
			{
				op.initialize(
						AuthenticationContext.getContext().getCSA_SID(),
						(String) processor.getResult().get("pswd"));

				//измен€ем пароль.
				op.saveConfirm();
				//если все хорошо, продолжаем.
				forward = continueStage(mapping, request, form);
			}
			catch (BusinessLogicException ex)
			{
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(ex.getMessage()));
			}
		}

		return forward;
	}
}
