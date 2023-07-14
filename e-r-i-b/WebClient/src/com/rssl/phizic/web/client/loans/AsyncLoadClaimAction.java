package com.rssl.phizic.web.client.loans;


import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.loanclaim.ExtendedLoanClaimListOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.rssl.phizic.auth.Login;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.rssl.phizic.logging.system.Log;

/**
 * @author usachev
 * @ created 15.05.15
 * @ $Author$
 * @ $Revision$
 * Action для получения списка расширенных кредитных заявок
 */
public class AsyncLoadClaimAction extends OperationalActionBase
{
	private Log LOG = PhizICLogFactory.getLog(LogModule.Web);

	/**
	 * Получение списка расширенных кредитных заявок
	 */
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AsyncLoadClaimForm frm = (AsyncLoadClaimForm) form;
		ExtendedLoanClaimListOperation operation = createOperation(ExtendedLoanClaimListOperation.class, "ExtendedLoanClaim");
		Login login = PersonContext.getPersonDataProvider().getPersonData().getLogin();
		try
		{
			frm.setData(operation.getAllLoanClaims(login));
		}
		catch (BusinessException e)
		{
			LOG.error(e);
			frm.setHasErrors(true);
		}

		return mapping.findForward(FORWARD_START);
	}
}
