package com.rssl.phizic.web.client.loanoffer;

import com.rssl.phizic.web.common.client.offer.BankLoanOfferCommonAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Moshenko
 * Date: 30.05.2011
 * Time: 9:55:45
 *
 */
public class BankLoanOfferAction extends BankLoanOfferCommonAction
{
    private static final String FORWARD_MAIN = "Main";
	private static final String LOAN_CARD_OFFER = "LoanCardOffer";
	private static final String PAYMENT = "Payment";

    protected Map<String, String> getKeyMethodMap()
    {
        Map<String, String> result = new HashMap<String, String>(1);
	    result.put("button.placeAnOrder", "loanMake");
	    
	    return result;
    }

	protected ActionForward currentRedirect(ActionForm form, ActionMapping mapping)
	{
		LoanOfferForm frm = (LoanOfferForm) form;
		if ((frm.getLoanCardOffers() == null || frm.getLoanCardOffers().isEmpty()) &&
				(frm.getLoanOffers() == null || frm.getLoanOffers().isEmpty()))
			return mapping.findForward(FORWARD_MAIN);
		return mapping.findForward(FORWARD_SHOW);
	}

	public ActionForward loanMake(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		LoanOfferForm frm = (LoanOfferForm) form;
		ActionForward actionForward = new ActionForward(mapping.findForward(LOAN_CARD_OFFER));
		actionForward.setPath(actionForward.getPath() + "?form=LoanCardOffer&loan=" + frm.getLoan());
		actionForward.setRedirect(true);

		return actionForward;
	}
}


