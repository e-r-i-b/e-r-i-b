package com.rssl.phizic.web.client.loanoffer;

import com.rssl.ikfl.crediting.OfferId;
import com.rssl.phizic.business.offers.LoanCardOffer;
import com.rssl.phizic.business.offers.LoanOffer;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gulov
 * @ created 27.01.15
 * @ $Author$
 * @ $Revision$
 */

/**
 * Форма для загрузки блока предложений на главной странице
 */
public class ViewOfferForm extends ActionFormBase
{
	/**
	 * список предложений по кредитам
	 */
	private List<LoanOffer> bottomLoanOffers = new ArrayList<LoanOffer>();

	/**
	 * список предложений по кредитным картам
	 */
	private List<LoanCardOffer> bottomLoanCardOffers = new ArrayList<LoanCardOffer>();

	/**
	 * Предложения по кредитам, по которым не отправлен отклик "Проинформирован"
	 */
	private List<OfferId> loanOffersNoInformed;

	/**
	 * Предложения по кредитным картам, по которым не отправлен отклик "Проинформирован"
	 */
	private List<OfferId> loanCardOffersNoInformed;


	/**
	 * Предложения по кредитам, по которым не отправлен отклик "Презентация"
	 */
	private List<OfferId> loanOffersNoPresentation;


	/**
	 * Предложения по кредитным картам, по которым не отправлен отклик "Презентация"
	 */
	private List<OfferId> loanCardOffersNoPresentation;

	public void setBottomLoanOffers(List<LoanOffer> bottomLoanOffers)
	{
		this.bottomLoanOffers = bottomLoanOffers;
	}

	public List<LoanOffer> getBottomLoanOffers()
	{
		return bottomLoanOffers;
	}

	public void setBottomLoanCardOffers(List<LoanCardOffer> bottomLoanCardOffers)
	{
		this.bottomLoanCardOffers = bottomLoanCardOffers;
	}

	public List<LoanCardOffer> getBottomLoanCardOffers()
	{
		return bottomLoanCardOffers;
	}

	public List<OfferId> getLoanOffersNoInformed()
	{
		return loanOffersNoInformed;
	}

	public void setLoanOffersNoInformed(List<OfferId> loanOffersNoInformed)
	{
		this.loanOffersNoInformed = loanOffersNoInformed;
	}

	public List<OfferId> getLoanCardOffersNoInformed()
	{
		return loanCardOffersNoInformed;
	}

	public void setLoanCardOffersNoInformed(List<OfferId> loanCardOffersNoInformed)
	{
		this.loanCardOffersNoInformed = loanCardOffersNoInformed;
	}

	public List<OfferId> getLoanOffersNoPresentation()
	{
		return loanOffersNoPresentation;
	}

	public void setLoanOffersNoPresentation(List<OfferId> loanOffersNoPresentation)
	{
		this.loanOffersNoPresentation = loanOffersNoPresentation;
	}

	public List<OfferId> getLoanCardOffersNoPresentation()
	{
		return loanCardOffersNoPresentation;
	}

	public void setLoanCardOffersNoPresentation(List<OfferId> loanCardOffersNoPresentation)
	{
		this.loanCardOffersNoPresentation = loanCardOffersNoPresentation;
	}
}
