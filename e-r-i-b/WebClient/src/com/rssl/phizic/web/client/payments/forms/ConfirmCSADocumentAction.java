package com.rssl.phizic.web.client.payments.forms;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.source.ExistingSource;
import com.rssl.phizic.operations.payment.ConfirmCSAPaymentOperation;
import com.rssl.phizic.operations.payment.ConfirmFormOperation;
import com.rssl.phizic.operations.payment.ConfirmFormPaymentOperation;
import com.rssl.phizic.business.documents.payments.validators.IsOwnDocumentValidator;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.web.actions.payments.forms.ConfirmPaymentByFormForm;
import com.rssl.phizic.web.actions.payments.forms.ConfirmDocumentAction;
import com.rssl.phizic.web.util.HttpSessionUtils;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Gainanov
 * @ created 05.08.2009
 * @ $Author$
 * @ $Revision$
 */
public class ConfirmCSADocumentAction extends ConfirmDocumentAction
{
	private static final String FORWARD_CONFIRM_CSA = "CSAConfirm";
	private static final String FORWARD_REDIRECT_FROM_CSA = "CSARedirect";
	private static final String CSA_CONFIRMATION_COMPLETE = "csa_confirm_complete";

	public ActionForward confirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ConfirmCSAPaymentOperation operation = getOperation(request);
		ConfirmPaymentByFormForm frm = (ConfirmPaymentByFormForm) form;
		frm.setMetadataPath(operation.getMetadataPath());
		frm.setConfirmStrategyType(operation.getStrategyType());
		try
		{
			String path = request.getRequestURL().toString(); 
			path = path.substring(0, path.indexOf("/PhizIC")+7);
			path = path + mapping.findForward(FORWARD_REDIRECT_FROM_CSA).getPath() + "?" + request.getQueryString() + "&payment=1";
			if(path.contains("AuthToken"))
				path = path.substring(0,path.indexOf("&AuthToken"));
			operation.prepareConfirm(AuthenticationContext.getContext().getCSA_SID(), path.replace("http", "https"));
			HttpSessionUtils.removeSessionAttribute(request, CSA_CONFIRMATION_COMPLETE);
			saveOperation(request, operation);
			return super.confirm(mapping, form, request, response);
		}
		catch (BusinessLogicException e)
		{
			saveError(request, e);
			return forwardShow(mapping, operation, frm, request, true);
		}
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ConfirmPaymentByFormForm frm = (ConfirmPaymentByFormForm) form;
		ConfirmCSAPaymentOperation operation = (ConfirmCSAPaymentOperation) getConfirmOperation(request, frm);
		Object complete = HttpSessionUtils.getSessionAttribute(request, CSA_CONFIRMATION_COMPLETE);
		if (request.getParameter("AuthToken") == null || (complete != null && complete.equals(true)))
		{
			saveOperation(request, operation);
			return forwardShow(mapping, operation, frm, request, true);
		}
		else
		{
			try
			{
				
				frm.setMetadataPath(operation.getMetadataPath());
				frm.setConfirmStrategyType(operation.getStrategyType());
				operation.validateConfirm(request.getParameter("AuthToken"));
				operation.fireSend();
				return createNextStageDocumentForward(operation, false);
			}
			catch (BusinessLogicException e)
			{
				saveError(request, e);
				request.getSession().setAttribute(CSA_CONFIRMATION_COMPLETE, true);
				return forwardShow(mapping, operation, frm, request, true);
			}
		}
	}

	protected ActionForward appendIdentifier(ActionMapping mapping, ConfirmFormOperation operation) throws BusinessException
	{
		ConfirmCSAPaymentOperation confirmOperation = (ConfirmCSAPaymentOperation) operation;
		return new ActionForward(mapping.findForward(FORWARD_CONFIRM_CSA).getPath()+"?AuthToken="+confirmOperation.getToken());
	}

	protected ConfirmFormPaymentOperation getConfirmOperation(HttpServletRequest request, ConfirmPaymentByFormForm frm)
			throws BusinessException, BusinessLogicException
	{
		Long id = frm.getId();

		ExistingSource source = new ExistingSource(id, new IsOwnDocumentValidator());
		ConfirmCSAPaymentOperation operation = createOperation(ConfirmCSAPaymentOperation.class, source.getMetadata().getName());

		operation.initialize(source);

		return operation;
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ConfirmFormPaymentOperation operation = getOperation(request);
		ConfirmPaymentByFormForm frm = (ConfirmPaymentByFormForm) form;
		frm.setMetadataPath(operation.getMetadataPath());
	    frm.setConfirmStrategyType(operation.getStrategyType());
		operation.edit();

		addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));

		return createNextStageDocumentForward(operation, false);
	}
}
