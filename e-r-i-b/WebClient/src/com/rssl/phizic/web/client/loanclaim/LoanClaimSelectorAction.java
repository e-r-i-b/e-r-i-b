package com.rssl.phizic.web.client.loanclaim;

import com.rssl.phizic.web.client.ext.sbrf.loans.ShowLoansListAction;
import com.rssl.phizic.web.ext.sbrf.loanclaim.LoanClaimFunctions;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.actions.DispatchAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Nady
 * @ created 09.01.2014
 * @ $Author$
 * @ $Revision$
 * Ёкшн-селектор дл€ отображени€ страницы  редиты, в соответствии с установленным в настройках алгоритмом
 */
public class LoanClaimSelectorAction extends DispatchAction
{
	private final ShowLoansListAction oldAction = new ShowLoansListAction();
	private final ShowExtLoansListAction newAction = new ShowExtLoansListAction();


	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return LoanClaimFunctions.isUseNewLoanClaimAlgorithm() ? newAction.execute(mapping, form, request, response) : oldAction.execute(mapping, form, request, response);
	}

	@Override
	public void setServlet(ActionServlet servlet)
	{
		super.setServlet(servlet);
		oldAction.setServlet(servlet);
		newAction.setServlet(servlet);
	}
}
