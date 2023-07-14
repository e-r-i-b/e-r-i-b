package com.rssl.phizic.web.auth.registration.outerAsync;

import com.rssl.auth.csa.front.exceptions.*;
import com.rssl.auth.csa.front.operations.auth.OperationInfo;
import com.rssl.auth.csa.front.operations.auth.RegistrationOperationInfo;
import com.rssl.auth.csa.wsclient.exceptions.WrongRegionException;
import com.rssl.phizic.web.auth.AuthStageActionBase;
import com.rssl.phizic.web.auth.AuthStageFormBase;
import com.rssl.phizic.web.auth.registration.RegistrationForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * базовый экшен для асинхронных авторизационных операций
 * @author tisov
 * @ created 13.02.14
 * @ $Author$
 * @ $Revision$
 */

public abstract class AsyncAuthStageActionBase extends AuthStageActionBase
{
	public static final String REDIRECT_FAIL_FORWARD_NAME           = "redirect-fail";
	public static final String FIELD_VALIDATING_FAIL_FORWARD_NAME   = "validating-fail";
	public static final String BACK_LOGIC_POPUP_FAIL_FORWARD_NAME   = "popup-fail";
	public static final String BACK_LOGIC_MESSAGE_FAIL_FORWARD_NAME = "message-fail";
	public static final String CHECK_FIELD_RESPONSE_NAME            = "check";

	public static final String ASYNC_JSON_FAIL_STATE = "fail";
	public static final String ASYNC_JSON_SUCCESS_STATE = "success";

	@Override
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
		map.put("begin", "begin");
		map.put("next", "next");
		map.put("checkLogin", "checkLogin");
		map.put("checkPassword", "checkPassword");
		return map;
	}

	@Override
	protected ActionForward doBeginStage(ActionMapping mapping,  AuthStageFormBase frm, HttpServletRequest request, OperationInfo info) throws Exception
	{

		try
		{
			return super.doBeginStage(mapping, frm, request, info);
		}
		catch (CardIsNotMainInterruptStageException e)
		{
			frm.setRedirect("/async/page/notMainCard");
			return mapping.findForward(REDIRECT_FAIL_FORWARD_NAME);
		}
		catch (CardIsNotValidInterruptStageException e)
		{
			frm.setRedirect("/async/page/invalidCard");
			return mapping.findForward(REDIRECT_FAIL_FORWARD_NAME);
		}
		catch (SmsWasNotConfirmedInterruptStageException e)
		{
			throw e;
		}
		catch (UserAlreadyRegisteredInterruptStageException e)
		{
			frm.setRedirect("/async/page/alreadyRegistered");
			return mapping.findForward(REDIRECT_FAIL_FORWARD_NAME);
		}
		catch (BlockingRuleException e)
		{
			frm.setRedirect("/async/page/wrongRegion");
			return mapping.findForward(REDIRECT_FAIL_FORWARD_NAME);
		}
		catch (ValidateException e)
		{
			saveErrors(request, e.getMessages());
			return mapping.findForward(FIELD_VALIDATING_FAIL_FORWARD_NAME);
		}
		catch (FrontLogicException e)
		{
			frm.setPopupId("CustomMessage");
			frm.setMessageText(e.getMessage());
			return mapping.findForward(BACK_LOGIC_POPUP_FAIL_FORWARD_NAME);
		}
	}

	@Override
	protected ActionForward doNextStage(ActionMapping mapping,  AuthStageFormBase frm, HttpServletRequest request, OperationInfo info) throws Exception
	{
		ActionForward forward = null;

		try
		{

			return super.doNextStage(mapping, frm, request, info);
		}
		catch (CardIsNotMainInterruptStageException e)
		{
			frm.setRedirect("/async/page/notMainCard");
			return mapping.findForward(REDIRECT_FAIL_FORWARD_NAME);
		}
		catch (CardIsNotValidInterruptStageException e)
		{
			frm.setRedirect("/async/page/invalidCard");
			return mapping.findForward(REDIRECT_FAIL_FORWARD_NAME);
		}
		catch (SmsWasNotConfirmedInterruptStageException e)
		{
			throw e;
		}
		catch (InvalidCodeConfirmException e)
		{
			frm.setMessageId("IncorrectSMS");
			frm.setMessageText(e.getStrAttempts());
			return mapping.findForward(BACK_LOGIC_MESSAGE_FAIL_FORWARD_NAME);
		}
		catch (UserAlreadyRegisteredInterruptStageException e)
		{
			frm.setRedirect("/async/page/alreadyRegistered");
			return mapping.findForward(REDIRECT_FAIL_FORWARD_NAME);
		}
		catch (BlockingRuleException e)
		{
			frm.setRedirect("/async/page/wrongRegion");
			return mapping.findForward(REDIRECT_FAIL_FORWARD_NAME);
		}
		catch (ValidateException e)
		{
			saveErrors(request, e.getMessages());
			return mapping.findForward(FIELD_VALIDATING_FAIL_FORWARD_NAME);
		}
		catch (FrontLogicException e)
		{
			frm.setPopupId("CustomMessage");
			frm.setMessageText(e.getMessage());
			return mapping.findForward(BACK_LOGIC_POPUP_FAIL_FORWARD_NAME);
		}
	}
}
