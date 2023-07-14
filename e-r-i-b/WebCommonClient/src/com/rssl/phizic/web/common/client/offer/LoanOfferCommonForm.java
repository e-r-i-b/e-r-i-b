package com.rssl.phizic.web.common.client.offer;

import com.rssl.phizic.business.offers.LoanCardOffer;
import com.rssl.phizic.business.offers.LoanOffer;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Moshenko
 * Date: 30.05.2011
 * Time: 10:07:24
 */
public class LoanOfferCommonForm extends ActionFormBase
{
    private List<LoanOffer> loanOffers = new ArrayList<LoanOffer>();
	private List<LoanCardOffer> loanCardOffers =  new ArrayList<LoanCardOffer>();


    public List<LoanOffer> getLoanOffers() {
        return loanOffers;
    }

    public void setLoanOffers(List<LoanOffer> loanOffers) {
        this.loanOffers = loanOffers;
    }

    public List<LoanCardOffer> getLoanCardOffers() {
        return loanCardOffers;
    }

    public void setLoanCardOffers(List<LoanCardOffer> loanCardOffers) {
        this.loanCardOffers = loanCardOffers;
    }
}
