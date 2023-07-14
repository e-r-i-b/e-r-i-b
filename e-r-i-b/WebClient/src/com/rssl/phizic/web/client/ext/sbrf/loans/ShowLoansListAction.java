package com.rssl.phizic.web.client.ext.sbrf.loans;

import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.common.types.bankroll.BankProductType;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.BankrollHelper;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizic.operations.loans.loan.GetLoanListOperation;
import com.rssl.phizic.operations.loans.product.LoanProductListOperation;
import com.rssl.phizic.web.client.ShowAccountsForm;
import com.rssl.phizic.web.client.ext.sbrf.accounts.ShowAccountsExtendedAction;
import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mihaylov
 * @ created 23.07.2010
 * @ $Author$
 * @ $Revision$
 */

public class ShowLoansListAction extends ShowAccountsExtendedAction
{
	protected Map<String, String> getKeyMethodMap()
    {
	    return super.getKeyMethodMap();
    }

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
	    ShowAccountsForm frm = (ShowAccountsForm) form;

	    try
	    {
		    setLoanAbstract(frm);
		    if (frm.getActiveLoans().isEmpty() && frm.getBlockedLoans().isEmpty())
		    {
			    ActivePerson person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
			    ExternalSystemHelper.check(person.asClient().getOffice(), BankProductType.Credit);
		    }
	    }
	    catch (InactiveExternalSystemException e)
	    {
		    saveInactiveESMessage(request, e);
	    }

	    if (checkAccess(LoanProductListOperation.class, "LoanProduct"))
	    {
			LoanProductListOperation loanProductListOperation = createOperation(LoanProductListOperation.class, "LoanProduct");
			frm.setEmptyLoanOffer(!loanProductListOperation.checkAvailableLoanOffer());
	    }
		if(frm.isAllLoanDown())
		{
			ActionMessages msgs = new ActionMessages();
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Информация по кредитам из АБС временно недоступна. Повторите операцию позже.", false));
			saveMessages(request, msgs);
			return mapping.findForward(FORWARD_SHOW);
		}

	    List<LoanLink> blockedLoanList = frm.getBlockedLoans();
        if (CollectionUtils.isNotEmpty(blockedLoanList))
        {
            List<String> loanNames = new ArrayList<String>();
	        GetLoanListOperation getLoanListOperation = createOperation(GetLoanListOperation.class);
            for (LoanLink blockedLoanLink : blockedLoanList)
            {
                if (blockedLoanLink.getClosedState() != null && blockedLoanLink.getClosedState())
                {
	                getLoanListOperation.setLoanLinkFalseClosedState(blockedLoanLink.getNumber());
	                loanNames.add(blockedLoanLink.getName());
                }
            }

	        if(CollectionUtils.isNotEmpty(loanNames))
	        {
		        BankrollHelper bankrollHelper = new BankrollHelper(GateSingleton.getFactory());
                Map<String, String> messaage = bankrollHelper.createBlockedLinkMessage(loanNames);

                ActionMessages msgs = new ActionMessages();
                msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(getResourceMessage("loansBundle", messaage.get("captionKey")), false));
                msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(messaage.get("bodyText") + getResourceMessage("loansBundle", messaage.get("bodyKey")), false));
                saveMessages(request, msgs);
	        }
        }

	    return mapping.findForward(FORWARD_SHOW);
    }

	protected List<LoanLink> getPersonLoanLinks(GetLoanListOperation operationLoans)
	{
		return operationLoans.getLoans();
	}
}
