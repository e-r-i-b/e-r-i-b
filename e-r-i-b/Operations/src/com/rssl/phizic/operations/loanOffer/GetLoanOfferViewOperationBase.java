package com.rssl.phizic.operations.loanOffer;

import com.rssl.ikfl.crediting.FeedbackType;
import com.rssl.ikfl.crediting.OfferId;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.loanCardOffer.DublicateLoanCardOfferException;
import com.rssl.phizic.business.loanOffer.DublicateLoanOfferException;
import com.rssl.phizic.business.offers.LoanCardOffer;
import com.rssl.phizic.business.offers.LoanOffer;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.OperationBase;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Moshenko
 * Date: 27.05.2011
 * Time: 12:30:42
 */
public abstract class GetLoanOfferViewOperationBase extends OperationBase
{
	protected PersonData personData;
     //списки кредитных предложений клиенту
    protected List<LoanOffer> loanOffers = new ArrayList<LoanOffer>();
    protected List<LoanCardOffer> loanCardOffers = new ArrayList<LoanCardOffer>();

    protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

    protected void getFailOffer(Login login, Throwable ex)
    {
        log.error("Ошибка при получении предложений, для пользователя " + login.getUserId(), ex);
    }

	public void initialize() throws BusinessException, BusinessLogicException
	{
		personData = PersonContext.getPersonDataProvider().getPersonData();
	}

    public List<LoanCardOffer> getLoanCardOffers()
    {
        return loanCardOffers;
    }

    public List<LoanOffer> getLoanOffers()
    {
        return loanOffers;
    }

	 //помечаем все не просмотренные предложения по предодобренным кредитам как просмотренные
    public void setAllLoanOfferLikeViewed() throws BusinessException, DublicateLoanOfferException
	 {
        try
        {
	        PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
            loanOffers = personData.getLoanOfferByPersonData(null,false);
            List<OfferId> loanOfferIds = new ArrayList<OfferId>(loanOffers.size());
            for (LoanOffer loanOffer : loanOffers)
            {
                loanOfferIds.add(loanOffer.getOfferId());
            }
            personData.sendLoanOffersFeedback(loanOfferIds, FeedbackType.INFORM);
        }
        catch(Exception e)
		{
		   getFailOffer(personData.getPerson().getLogin(),e);
		}
    }

	//помечаем все не просмотренные предложения по предодобренным кредитным картам  как просмотренные
    public void setAllLoanCardOfferLikeViewed()  throws BusinessException, DublicateLoanCardOfferException
    {
	    //Если карты не пришли, то помечаем как просмотренные только предложения на получения карты,
	    //для предложений на  изменение лимита и типа признак просмотренности не ставим.
	    try
        {
            loanCardOffers = personData.getLoanCardOfferByPersonData(null, false);
            List<OfferId> loanCardOfferIds = new ArrayList<OfferId>(loanCardOffers.size());
            for (LoanCardOffer loanCardOffer : loanCardOffers)
            {
                loanCardOfferIds.add(loanCardOffer.getOfferId());
            }
            personData.sendLoanCardOffersFeedback(loanCardOfferIds, FeedbackType.INFORM);
        }
        catch (Exception e)
        {
            getFailOffer(personData.getPerson().getLogin(), e);
        }
    }

	/**
	 * Проинформировать. Для кредитов.
	 */
	public void informByLoanOffers() throws BusinessException
	{
		List<OfferId> offerIds = new ArrayList<OfferId>();
		for (LoanOffer offer:loanOffers)
		{
			offerIds.add(offer.getOfferId());
		}
		if (!offerIds.isEmpty())
			personData.sendLoanOffersFeedback(offerIds, FeedbackType.INFORM);
	}

	/**
	 * Проинформировать. Для кредитных карт.
	 */
	public void informByLoanCardOffers() throws BusinessException
	{
		List<OfferId> offerIds = new ArrayList<OfferId>();
		for (LoanCardOffer cardOffer:loanCardOffers)
		{
			offerIds.add(cardOffer.getOfferId());
		}
		if (!offerIds.isEmpty())
			personData.sendLoanCardOffersFeedback(offerIds, FeedbackType.INFORM);
	}
}
