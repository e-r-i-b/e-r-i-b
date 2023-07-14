package com.rssl.phizic.web.loans.claims;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.operations.loanclaim.LoanClaimFindOperation;
import com.rssl.phizic.operations.person.search.multinode.ChangeNodeLogicException;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.*;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Экшн поиска/создания кредитной заявки
 * @author Rtischeva
 * @ created 12.02.15
 * @ $Author$
 * @ $Revision$
 */
public class LoanClaimFindAction extends OperationalActionBase
{
	private static String FORWARD_SUCCESS = "Success";
	private static String CHANGE_NODE_FORWARD = "ChangeNode";
	private static String FORWARD_CREATE = "Create";

	@Override
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
		map.put("button.find", "find");
		map.put("button.create", "create");
		return map;
	}

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		LoanClaimFindForm frm = (LoanClaimFindForm) form;
		LoanClaimFindOperation operation = createOperation(LoanClaimFindOperation.class);

		if(frm.isContinueSearch())
			return continueSearch(request, frm, operation);

		// если пришли на старт без параметров, значит просто рисуем страницу поиска
		if (StringUtils.isEmpty(frm.getLoanClaimNumber()))
			return mapping.findForward(FORWARD_START);

		return mapping.findForward(FORWARD_START);
	}

	public ActionForward find(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		LoanClaimFindForm frm = (LoanClaimFindForm) form;
		LoanClaimFindOperation operation = createOperation(LoanClaimFindOperation.class);

		try
		{
			doSearch(frm.getLoanClaimNumber(), operation);
		}
		catch (ChangeNodeLogicException e)
		{
			ActionRedirect redirect = new ActionRedirect(mapping.findForward(CHANGE_NODE_FORWARD));
			redirect.addParameter("nodeId",e.getNodeId());
			redirect.addParameter("action",request.getServletPath());
			redirect.addParameter("parameters(continueSearch)","true");
			redirect.addParameter("parameters(loanClaimNumber)",frm.getLoanClaimNumber());
			return redirect;
		}
		catch (InactiveExternalSystemException e)
		{
			saveInactiveESMessage(request, e);

			return mapping.findForward(FORWARD_START);
		}
		catch (BusinessLogicException e)
		{
			saveError(request, e);
			return mapping.findForward(FORWARD_START);
		}

		return getNextRedirect(operation.getDocument());
	}
	protected void doSearch(String number, LoanClaimFindOperation operation) throws BusinessException, BusinessLogicException
	{
		operation.search(number);
	}

	/**
	 * Продолжить поиск клиента в мильтиблочной системе
	 * @param request - реквест
	 * @param form - форма
	 * @param operation операция для поиска
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	private ActionForward continueSearch(HttpServletRequest request,LoanClaimFindForm form, LoanClaimFindOperation operation) throws BusinessException, BusinessLogicException
	{
		operation.continueSearch(form.getLoanClaimNumber());
		ExtendedLoanClaim extendedLoanClaim = operation.getDocument();

		return getNextRedirect(extendedLoanClaim);
	}

	private ActionRedirect getNextRedirect(ExtendedLoanClaim loanClaim) throws BusinessLogicException
	{
		ActionRedirect redirect = new ActionRedirect(getCurrentMapping().findForward(FORWARD_SUCCESS).getPath());
		redirect.addParameter("objectFormName", "ExtendedLoanClaim");
		redirect.addParameter("id", loanClaim.getId());
		redirect.addParameter("history", true);
		redirect.addParameter("stateCode", loanClaim.getState().getCode());
		return redirect;
	}

	public final ActionForward create(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return mapping.findForward(FORWARD_CREATE);
	}
}
