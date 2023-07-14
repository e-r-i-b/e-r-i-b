package com.rssl.phizic.web.common.socialApi.offer;

import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.loanOffer.GetLoanCardOfferViewOperation;
import com.rssl.phizic.operations.loanOffer.GetLoanOfferViewOperation;
import com.rssl.phizic.operations.loanOffer.GetMainLoanCardOfferViewOperation;
import com.rssl.phizic.operations.loanOffer.GetMainLoanOfferViewOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Список предложений банка (на входе и после)
 * @author Dorzhinov
 * @ created 10.07.2013
 * @ $Author$
 * @ $Revision$
 */
public class BankOffersMobileAction extends OperationalActionBase
{
    public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        BankOffersMobileForm frm = (BankOffersMobileForm) form;
        ActivePerson person = PersonContext.getPersonDataProvider().getPersonData().getPerson();

        //на входе - показываем только непрочитанные
        if (frm.getIsLogin())
        {
            if (checkAccess(GetMainLoanOfferViewOperation.class))
            {
                GetMainLoanOfferViewOperation operation = createOperation(GetMainLoanOfferViewOperation.class);
                operation.initialize();
                frm.setLoanOffers(operation.getLoanOffers());
                operation.setAllLoanOfferLikeViewed();
            }

            if (checkAccess(GetMainLoanCardOfferViewOperation.class))
            {
                GetMainLoanCardOfferViewOperation operation = createOperation(GetMainLoanCardOfferViewOperation.class);
                operation.initialize(false);
                frm.setLoanCardOffers(operation.getLoanCardOffers());
                operation.setAllLoanCardOfferLikeViewed();
            }
        }
        //после входа
        else
        {
            if (checkAccess(GetLoanOfferViewOperation.class))
            {
                GetLoanOfferViewOperation operation = createOperation(GetLoanOfferViewOperation.class);
                operation.initialize(false);
                frm.setLoanOffers(operation.getLoanOffers());
            }

            if (checkAccess(GetLoanCardOfferViewOperation.class))
            {
                GetLoanCardOfferViewOperation operation = createOperation(GetLoanCardOfferViewOperation.class);
                operation.initialize(false);
                frm.setLoanCardOffers(operation.getLoanCardOffers());
            }
        }

        return mapping.findForward(FORWARD_START);
    }
}
