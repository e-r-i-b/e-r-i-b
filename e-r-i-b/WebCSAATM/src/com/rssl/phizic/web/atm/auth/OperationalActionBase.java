package com.rssl.phizic.web.atm.auth;

import com.rssl.auth.csa.front.exceptions.BlockingRuleException;
import com.rssl.auth.csa.front.exceptions.FrontException;
import com.rssl.auth.csa.front.exceptions.FrontLogicException;
import com.rssl.auth.csa.front.operations.Operation;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.phizic.business.Constants;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.web.auth.ActionFormBase;
import com.rssl.phizic.web.auth.LookupDispatchAction;
import com.rssl.phizic.web.struts.forms.FormHelper;
import org.apache.struts.action.*;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author osminin
 * @ created 26.08.13
 * @ $Author$
 * @ $Revision$
 */
public abstract class OperationalActionBase extends LookupDispatchAction
{
	public static final String FORWARD_START       = "start";
	public static final String FORWARD_SHOW        = "show";
	public static final String FORWARD_BLOCK       = "blockError";

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionFormBase frm = (ActionFormBase) form;
		FormProcessor<ActionMessages, ?> formProcessor = FormHelper.newInstance(new RequestValuesSource(currentRequest()), getForm());

		if (!formProcessor.process())
		{
			saveErrors(request, formProcessor.getErrors());
			return mapping.findForward(FORWARD_SHOW);
		}

		try
		{
			Operation operation = getOperation(formProcessor.getResult());
			operation.execute();
			updateForm(operation, frm);
		}
		catch (BlockingRuleException e)
		{
			return blockErrorForward(mapping, request, e.getMessage(), e);
		}
		catch (FrontException e)
		{
			return errorForward(mapping, request, Constants.COMMON_EXCEPTION_MESSAGE, e);
		}
		catch (FrontLogicException e)
		{
			return errorForward(mapping, request, e.getMessage(), e);
		}

		return mapping.findForward(getForwardName());
	}

	protected abstract void updateForm(Operation operation, ActionFormBase form) throws FrontException, FrontLogicException;

	protected abstract Operation getOperation(Map<String, Object> data) throws FrontException, FrontLogicException;

	protected abstract Form getForm();

	protected String getForwardName()
	{
		return FORWARD_SHOW;
	}

	private ActionForward errorForward(ActionMapping mapping, HttpServletRequest request, String message, Throwable e)
	{
		return errorForwardBase(mapping, request, message, e, FORWARD_SHOW);
	}

	private ActionForward blockErrorForward(ActionMapping mapping, HttpServletRequest request, String message, Throwable e)
	{
		return errorForwardBase(mapping, request, message, e, FORWARD_BLOCK);
	}

	private ActionForward errorForwardBase(ActionMapping mapping, HttpServletRequest request, String message, Throwable e, String forward)
	{
		log.error(message, e);
		saveError(request, new ActionMessage(message, false));
		return mapping.findForward(forward);
	}
}
