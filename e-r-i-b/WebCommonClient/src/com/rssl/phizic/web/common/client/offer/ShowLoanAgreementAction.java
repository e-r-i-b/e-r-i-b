package com.rssl.phizic.web.common.client.offer;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.loanclaim.LoanClaimConfig;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author sergunin
 * @ created 29.04.14
 * @ $Author$
 * @ $Revision$
 */

public class ShowLoanAgreementAction extends OperationalActionBase {

    public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        ShowLoanAgreementForm frm = (ShowLoanAgreementForm) form;

        LoanClaimConfig loanClaimConfig = ConfigFactory.getConfig(LoanClaimConfig.class);
        String agreement = loanClaimConfig.getLatestLoanOffersTerm().getConditionsText();

        frm.setAgreement(agreement);

        return mapping.findForward(FORWARD_START);
    }
}
