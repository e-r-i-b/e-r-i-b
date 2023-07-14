package com.rssl.phizic.web.common.client.offer;

import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.loanOffer.GetLoanCardOfferViewOperation;
import com.rssl.phizic.operations.loanOffer.GetLoanOfferViewOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.log.CollectionLogParametersReader;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Moshenko
 * Date: 30.05.2011
 * Time: 9:55:45
 *
 */
public class BankLoanOfferCommonAction extends OperationalActionBase
{
    public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LoanOfferCommonForm frm = (LoanOfferCommonForm) form;
        ActivePerson person = PersonContext.getPersonDataProvider().getPersonData().getPerson();

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

        addLogParameters(new CollectionLogParametersReader("ѕредложени€ по кредитным продуктам(кредиты)",(frm.getLoanOffers() != null)?frm.getLoanOffers():new ArrayList()));
        addLogParameters(new CollectionLogParametersReader("ѕредложени€ по кредитным продуктам(карты)",(frm.getLoanCardOffers() != null)?frm.getLoanCardOffers():new ArrayList()));

	    return currentRedirect(frm,mapping);
    }

	protected ActionForward currentRedirect(ActionForm from, ActionMapping mapping)
	{
		return mapping.findForward(FORWARD_SHOW);
	}
}



