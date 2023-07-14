package com.rssl.phizic.web.client.dictionaries;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.dictionaries.receivers.ConfirmCSAPaymentReceiverOperation;
import com.rssl.phizic.operations.dictionaries.receivers.ConfirmPaymentReceiverOperation;
import com.rssl.phizic.web.util.HttpSessionUtils;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Gainanov
 * @ created 23.11.2009
 * @ $Author$
 * @ $Revision$
 */
public class ConfirmCSAPaymentReceiverAction extends ConfirmPaymentReceiverAction
{
	private static final String CSA_CONFIRMATION_COMPLETE = "csa_confirm_complete";
	private static final String FORWARD_REDIRECT_FROM_CSA = "CSARedirect";
	private static final String FORWARD_CONFIRM_CSA = "CSAConfirm";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.confirm", "confirm");
		return map;
	}

	protected ConfirmPaymentReceiverOperation createOperation() throws BusinessException, BusinessLogicException
	{
		return createOperation(ConfirmPaymentReceiverOperation.class);
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Object complete = HttpSessionUtils.getSessionAttribute(request, CSA_CONFIRMATION_COMPLETE);
		if (request.getParameter("AuthToken") == null || (complete != null && complete.equals(true)))
			return super.start(mapping, form, request, response);
		else
		{
			ConfirmCSAPaymentReceiverOperation operation = (ConfirmCSAPaymentReceiverOperation)createOperation();
			ConfirmPaymentReceiverForm frm = (ConfirmPaymentReceiverForm)form;
			try
			{
				operation.initialize(frm.getId());
				operation.validateConfirm(request.getParameter("AuthToken"));
				operation.confirm();
				return mapping.findForward(FORWARD_SUCCESS);
			}
			catch (BusinessLogicException e)
			{
				saveError(request, e);
				request.getSession().setAttribute(CSA_CONFIRMATION_COMPLETE, true);
				return mapping.findForward(FORWARD_START);
			}
		}
	}

	public ActionForward confirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		try
		{
			ConfirmPaymentReceiverForm frm = (ConfirmPaymentReceiverForm)form;
			ConfirmCSAPaymentReceiverOperation operation = getOperation(request);
			String path = request.getRequestURL().toString();
			path = path.substring(0, path.indexOf("/PhizIC") + 7);
			path = path + mapping.findForward(FORWARD_REDIRECT_FROM_CSA).getPath() + "?" + request.getQueryString()+"&rec=1";
			if (path.contains("AuthToken"))
				path = path.substring(0, path.indexOf("&AuthToken"));
			String token = operation.prepareConfirm(AuthenticationContext.getContext().getCSA_SID(), path.replace("http", "https"));
			HttpSessionUtils.removeSessionAttribute(request, CSA_CONFIRMATION_COMPLETE);


			operation.initialize(frm.getId());

			List<String> errors = validateConfirmResponse(operation, request);

			if ((errors != null) && (!errors.isEmpty()))
			{
				saveErrors(request, errors);
				return start(mapping, form, request, response);
			}
			saveOperation(request, operation);
			return  new ActionForward(mapping.findForward(FORWARD_CONFIRM_CSA).getPath()+"?AuthToken="+token);
		}
		catch (BusinessLogicException e)
		{
			saveError(request, e);
			return mapping.findForward(FORWARD_START);
		}
	}
}
