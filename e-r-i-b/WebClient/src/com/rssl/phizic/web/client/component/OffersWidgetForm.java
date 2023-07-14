package com.rssl.phizic.web.client.component;

import com.rssl.phizic.business.offers.LoanCardOffer;
import com.rssl.phizic.business.offers.LoanOffer;
import com.rssl.phizic.web.component.WidgetForm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dorzhinov
 * @ created 16.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class OffersWidgetForm extends WidgetForm
{
	private List<LoanOffer> loanOffers = new ArrayList<LoanOffer>(); //предодобр. заявки на кредиты
    private List<LoanCardOffer> loanCardOffers = new ArrayList<LoanCardOffer>(); //предодобр. заявки на кред. карты

	public List<LoanOffer> getLoanOffers()
	{
		return loanOffers;
	}

	public void setLoanOffers(List<LoanOffer> loanOffers)
	{
		this.loanOffers = loanOffers;
	}

	public List<LoanCardOffer> getLoanCardOffers()
	{
		return loanCardOffers;
	}

	public void setLoanCardOffers(List<LoanCardOffer> loanCardOffers)
	{
		this.loanCardOffers = loanCardOffers;
	}
}
