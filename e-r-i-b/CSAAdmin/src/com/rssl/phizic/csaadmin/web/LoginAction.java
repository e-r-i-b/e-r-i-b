package com.rssl.phizic.csaadmin.web;

import com.rssl.phizic.gate.csa.NodeInfo;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.processing.StringErrorCollector;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.common.forms.validators.strategy.DefaultValidationStrategy;
import com.rssl.phizic.csaadmin.business.authtoken.AuthenticationToken;
import com.rssl.phizic.csaadmin.business.common.AdminException;
import com.rssl.phizic.csaadmin.business.common.AdminLogicException;
import com.rssl.phizic.csaadmin.business.login.Login;
import com.rssl.phizic.csaadmin.operation.ChangePasswordOperation;
import com.rssl.phizic.csaadmin.operation.LoginOperation;
import com.rssl.phizic.csaadmin.operation.OpenSessionOperationBase;
import com.rssl.phizic.csaadmin.utils.CSAAdminUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author akrenev
 * @ created 27.09.13
 * @ $Author$
 * @ $Revision$
 *
 * Ёкшен входа сотрудника в систему
 */

public class LoginAction extends ActionBase
{
	private static final String FORWARD_SHOW = "Show";
	private static final String FORWARD_CHANGE_PASSWORD = "ChangePassword";
	private static final String LOGIN_SERVER_RND_KEY  = "com.rssl.phizic.web.LoginServerRnd.key";
	private static final String LOGIN_KEY  = "com.rssl.phizic.web.Login.key";

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		LoginForm frm = (LoginForm) form;
		LoginOperation operation = new LoginOperation();
		String serverRandom = operation.generateServerRandom();
		frm.setField("serverRandom",serverRandom);
		setLoginServerRnd(request,serverRandom);
		return mapping.findForward(FORWARD_SHOW);
	}

	public ActionForward login(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		LoginForm frm = (LoginForm) form;
		FormProcessor<List<String>,?> processor = createFormProcessor(frm, LoginForm.LOGIN_FORM);
		if(processor.process())
		{
			Map<String,Object> result = processor.getResult();
			String loginName = (String)result.get("login");
			String password = (String)result.get("password");
			String clientRandom = (String)result.get("clientRandom");
			String serverRandom = getLoginServerRnd(request);
			LoginOperation operation = new LoginOperation();
			try
			{
				operation.initialize(loginName,password,clientRandom,serverRandom);
				operation.validate();
				Login login = operation.getLogin();
				if (login.needChangePassword())
				{
					setLogin(request, login);
					return mapping.findForward(FORWARD_CHANGE_PASSWORD);
				}
				login.setLastLogonDate(Calendar.getInstance());
				operation.save();
				return finishAuthentication(operation, request);
			}
			catch (AdminLogicException e)
			{
				saveError(request, e.getMessage());
			}
		}
		else
		{
			saveErrors(request, processor.getErrors());
		}
		return start(mapping,form,request,response);
	}


	public ActionForward changePassword(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		LoginForm frm = (LoginForm) form;

		Login login = getLogin(request);
		if (login == null)
			return start(mapping,form,request,response);

		FormProcessor<List<String>,?> processor = createFormProcessor(frm, LoginForm.CHANGE_PASSWORD_FORM);
		if(processor.process())
		{
			Map<String,Object> result = processor.getResult();
			String newPassword = (String)result.get("newPassword");
			ChangePasswordOperation operation = new ChangePasswordOperation();
			try
			{
				operation.initialize(login);
				operation.changePassword(newPassword);
				return finishAuthentication(operation, request);
			}
			catch (AdminLogicException e)
			{
				saveError(request, e.getMessage());
			}
		}
		else
		{
			saveErrors(request, processor.getErrors());
		}
		return mapping.findForward(FORWARD_CHANGE_PASSWORD);
	}

	public ActionForward exit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		closeSession(request);
		return start(mapping,form,request,response);
	}

	private ActionRedirect finishAuthentication(OpenSessionOperationBase operation, HttpServletRequest request) throws AdminLogicException, AdminException
	{
		operation.openSession();
		AuthenticationToken authToken = operation.createToken();
		NodeInfo currentNode = operation.getCurrentNode();
		ActionRedirect redirect = new ActionRedirect(CSAAdminUtils.generateEribLoginUrl(authToken, currentNode));
		closeSession(request);
		return redirect;
	}

	private FormProcessor<List<String>,?> createFormProcessor(LoginForm actionForm, Form form)
	{
		StringErrorCollector collector = new StringErrorCollector();
		MapValuesSource valuesSource = new MapValuesSource(actionForm.getFields());
		return new FormProcessor<List<String>, StringErrorCollector>(valuesSource, form, collector, DefaultValidationStrategy.getInstance());
	}

	private String getLoginServerRnd(HttpServletRequest request)
	{
		HttpSession session = request.getSession(false);
		if (session == null)
			return null;
		return (String)session.getAttribute(LOGIN_SERVER_RND_KEY);
	}

	private void setLoginServerRnd(HttpServletRequest request, String randomString)
	{
		request.getSession(true).setAttribute(LOGIN_SERVER_RND_KEY, randomString);
	}

	private Login getLogin(HttpServletRequest request)
	{
		HttpSession session = request.getSession(false);
		if (session == null)
			return null;
		return (Login)session.getAttribute(LOGIN_KEY);
	}

	private void setLogin(HttpServletRequest request, Login login)
	{
		request.getSession(true).setAttribute(LOGIN_KEY, login);
	}

	private void closeSession(HttpServletRequest request)
	{
		HttpSession session = request.getSession(false);
		if (session != null)
			session.invalidate();
	}
}
