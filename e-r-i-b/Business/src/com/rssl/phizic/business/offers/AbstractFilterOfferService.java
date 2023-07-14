package com.rssl.phizic.business.offers;

import com.rssl.ikfl.crediting.FeedbackType;
import com.rssl.ikfl.crediting.OfferId;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.loanCardOffer.ConditionProductByOffer;
import com.rssl.phizic.business.persons.ActivePerson;

import java.util.List;

/**
 * @author Nady
 * @ created 19.03.2015
 * @ $Author$
 * @ $Revision$
 */

/**
 * Абстрактный сервис
 */
public abstract class AbstractFilterOfferService implements OfferService
{
	private final OfferService delegate;

	public AbstractFilterOfferService(OfferService delegate)
	{
		this.delegate = delegate;
	}

	public List<LoanCardOffer> getLoanCardOfferByPersonData(Integer number, ActivePerson person, Boolean isViewed) throws BusinessException
	{
		return delegate.getLoanCardOfferByPersonData(number, person, isViewed);
	}

	public List<LoanOffer> getLoanOfferByPersonData(Integer number, ActivePerson person, Boolean isViewed) throws BusinessException
	{
		return delegate.getLoanOfferByPersonData(number, person, isViewed);
	}

	public LoanOffer findLoanOfferById(OfferId offerId) throws BusinessException
	{
		return delegate.findLoanOfferById(offerId);
	}

	public LoanCardOffer findLoanCardOfferById(OfferId offerId) throws BusinessException
	{
		return delegate.findLoanCardOfferById(offerId);
	}

	public void sendLoanOffersFeedback(List<OfferId> loanOfferIds, FeedbackType feedbackType) throws BusinessException
	{
		delegate.sendLoanOffersFeedback(loanOfferIds, feedbackType);
	}

	public void sendLoanCardOffersFeedback(List<OfferId> loanCardOfferIds, FeedbackType feedbackType) throws BusinessException
	{
		delegate.sendLoanCardOffersFeedback(loanCardOfferIds, feedbackType);
	}

	public boolean isOfferReceivingInProgress(ActivePerson person) throws BusinessException
	{
		return delegate.isOfferReceivingInProgress(person);
	}

	public void markLoanOfferAsUsed(OfferId offerId) throws BusinessException
	{
		delegate.markLoanOfferAsUsed(offerId);
	}

	public void markLoanCardOfferAsUsed(OfferId offerId) throws BusinessException
	{
		delegate.markLoanCardOfferAsUsed(offerId);
	}

	public LoanOffer getLoanOfferForMainPage(ActivePerson person) throws BusinessException
	{
		return delegate.getLoanOfferForMainPage(person);
	}

	public LoanCardOffer getLoanCardOfferForMainPage(ActivePerson person) throws BusinessException
	{
		return delegate.getLoanCardOfferForMainPage(person);
	}

	public boolean isFeedbackForLoanOfferSend(OfferId offerId, FeedbackType feedbackType) throws BusinessException
	{
		return delegate.isFeedbackForLoanOfferSend(offerId, feedbackType);
	}

	public boolean isFeedbackForLoanCardOfferSend(OfferId offerId, FeedbackType feedbackType) throws BusinessException
	{
		return delegate.isFeedbackForLoanCardOfferSend(offerId, feedbackType);
	}

	public LoanCardOffer getNewLoanCardOffer(ActivePerson person) throws BusinessException
	{
		return delegate.getNewLoanCardOffer(person);
	}

	public void updateLoanCardOfferRegistrationDate(OfferId offerId) throws BusinessException
	{
		delegate.updateLoanCardOfferRegistrationDate(offerId);
	}

	public void updateLoanCardOfferTransitionDate(OfferId offerId) throws BusinessException
	{
		delegate.updateLoanCardOfferTransitionDate(offerId);
	}

	public List<ConditionProductByOffer> getCardOfferCompositProductCondition(List<String> offerTypes, ActivePerson person) throws BusinessException
	{
		return delegate.getCardOfferCompositProductCondition(offerTypes, person);
	}

	public ConditionProductByOffer findConditionProductByOffer(ActivePerson person, Long conditionId) throws BusinessException
	{
		return delegate.findConditionProductByOffer(person, conditionId);
	}

	public boolean existCreditCardOffer(ActivePerson person, Long conditionId) throws BusinessException
	{
		return delegate.existCreditCardOffer(person, conditionId);
	}

	public ConditionProductByOffer findConditionProductByOfferIdAndConditionId(OfferId loanCardOfferId, Long conditionId) throws BusinessException
	{
		return delegate.findConditionProductByOfferIdAndConditionId(loanCardOfferId, conditionId);
	}

	public Long findConditionIdByOfferId(OfferId loanCardOfferId) throws BusinessException
	{
		return delegate.findConditionIdByOfferId(loanCardOfferId);
	}
}
