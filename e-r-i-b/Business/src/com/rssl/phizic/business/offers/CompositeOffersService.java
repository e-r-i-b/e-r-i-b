package com.rssl.phizic.business.offers;

import com.rssl.ikfl.crediting.FeedbackType;
import com.rssl.ikfl.crediting.OfferId;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.persons.ActivePerson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Rtischeva
 * @ created 21.01.15
 * @ $Author$
 * @ $Revision$
 */
public class CompositeOffersService extends OfferServiceBase
{
	private OfferService offerServiceV1 = new OffersServiceVersion1();
	private OfferService offerServiceV2 = new OffersServiceVersion2();
	private OfferService priorityService;

	public CompositeOffersService(boolean offerServiceV1Available, boolean offerServiceV2Available)
	{
		if (offerServiceV1Available)
			priorityService = offerServiceV1;

		if (offerServiceV2Available)
			priorityService = offerServiceV2;
	}

	public List<LoanCardOffer> getLoanCardOfferByPersonData(Integer number, ActivePerson person, Boolean isViewed) throws BusinessException
	{
		if (priorityService == null)
			return Collections.emptyList();
		return priorityService.getLoanCardOfferByPersonData(number, person, isViewed);
	}

	public List<LoanOffer> getLoanOfferByPersonData(Integer number, ActivePerson person, Boolean isViewed) throws BusinessException
	{
		if (priorityService == null)
			return Collections.emptyList();
		return priorityService.getLoanOfferByPersonData(number, person, isViewed);
	}

	public LoanOffer findLoanOfferById(OfferId offerId) throws BusinessException
	{
		switch (offerId.getOfferType())
		{
			case OFFER_VERSION_1:
				return offerServiceV1.findLoanOfferById(offerId);
			case OFFER_VERSION_2:
				return offerServiceV2.findLoanOfferById(offerId);
			default:
				throw new IllegalArgumentException("Неизвестен тип предложения");
		}
	}

	public LoanCardOffer findLoanCardOfferById(OfferId offerId) throws BusinessException
	{
		switch (offerId.getOfferType())
		{
			case OFFER_VERSION_1:
				return offerServiceV1.findLoanCardOfferById(offerId);
			case OFFER_VERSION_2:
				return offerServiceV2.findLoanCardOfferById(offerId);
			default:
				throw new IllegalArgumentException("Неизвестен тип предложения");
		}
	}

	public void sendLoanOffersFeedback(List<OfferId> loanOfferIds, FeedbackType feedbackType) throws BusinessException
	{
		List<OfferId> loanOffersIdsV1 = new ArrayList<OfferId>();
		List<OfferId> loanOffersIdsV2 = new ArrayList<OfferId>();

		separateOffers(loanOfferIds, loanOffersIdsV1, loanOffersIdsV2);

		offerServiceV1.sendLoanOffersFeedback(loanOffersIdsV1, feedbackType);
		offerServiceV2.sendLoanOffersFeedback(loanOffersIdsV2, feedbackType);
	}

	public void sendLoanCardOffersFeedback(List<OfferId> loanCardOfferIds, FeedbackType feedbackType) throws BusinessException
	{
		List<OfferId> loanCardOffersIdsV1 = new ArrayList<OfferId>();
		List<OfferId> loanCardOffersIdsV2 = new ArrayList<OfferId>();

		separateOffers(loanCardOfferIds, loanCardOffersIdsV1, loanCardOffersIdsV2);

		offerServiceV1.sendLoanCardOffersFeedback(loanCardOffersIdsV1, feedbackType);
		offerServiceV2.sendLoanCardOffersFeedback(loanCardOffersIdsV2, feedbackType);
	}

	private void separateOffers(List<OfferId> offerIds, List<OfferId> offerIdsV1, List<OfferId> offersIdsV2)
	{
		for (OfferId offerId : offerIds)
		{
			OfferType offerType = offerId.getOfferType();
			switch (offerType)
			{
				case OFFER_VERSION_1:
					offerIdsV1.add(offerId);
					break;
				case OFFER_VERSION_2:
					offersIdsV2.add(offerId);
					break;
			}
		}
	}

	public boolean isOfferReceivingInProgress(final ActivePerson person) throws BusinessException
	{
		if (priorityService == null)
			return false;
		return priorityService.isOfferReceivingInProgress(person);
	}

	public void markLoanOfferAsUsed(OfferId offerId) throws BusinessException
	{
		switch (offerId.getOfferType())
		{
			case OFFER_VERSION_1:
				offerServiceV1.markLoanOfferAsUsed(offerId);
				break;
			case OFFER_VERSION_2:
				offerServiceV2.markLoanOfferAsUsed(offerId);
				break;
			default:
				throw new IllegalArgumentException("Неизвестен тип предложения");
		}
	}

	public void markLoanCardOfferAsUsed(OfferId offerId) throws BusinessException
	{
		switch (offerId.getOfferType())
		{
			case OFFER_VERSION_1:
				offerServiceV1.markLoanCardOfferAsUsed(offerId);
				break;
			case OFFER_VERSION_2:
				offerServiceV2.markLoanCardOfferAsUsed(offerId);
				break;
			default:
				throw new IllegalArgumentException("Неизвестен тип предложения");
		}
	}

	public LoanOffer getLoanOfferForMainPage(ActivePerson person) throws BusinessException
	{
		if (priorityService == null)
			return null;
		return priorityService.getLoanOfferForMainPage(person);
	}

	public LoanCardOffer getLoanCardOfferForMainPage(ActivePerson person) throws BusinessException
	{
		if (priorityService == null)
			return null;
		return priorityService.getLoanCardOfferForMainPage(person);
	}

	public boolean isFeedbackForLoanOfferSend(OfferId offerId, FeedbackType feedbackType) throws BusinessException
	{
		switch (offerId.getOfferType())
		{
			case OFFER_VERSION_1:
				return offerServiceV1.isFeedbackForLoanOfferSend(offerId, feedbackType);
			case OFFER_VERSION_2:
				return offerServiceV2.isFeedbackForLoanOfferSend(offerId, feedbackType);
			default:
				throw new IllegalArgumentException("Неизвестен тип предложения");
		}
	}

	public boolean isFeedbackForLoanCardOfferSend(OfferId offerId, FeedbackType feedbackType) throws BusinessException
	{
		switch (offerId.getOfferType())
		{
			case OFFER_VERSION_1:
				return offerServiceV1.isFeedbackForLoanCardOfferSend(offerId, feedbackType);
			case OFFER_VERSION_2:
				return offerServiceV2.isFeedbackForLoanCardOfferSend(offerId, feedbackType);
			default:
				throw new IllegalArgumentException("Неизвестен тип предложения");
		}
	}

	public LoanCardOffer getNewLoanCardOffer(ActivePerson person) throws BusinessException
	{
		if (priorityService == null)
			return null;
		return priorityService.getNewLoanCardOffer(person);
	}

	public void updateLoanCardOfferRegistrationDate(OfferId offerId) throws BusinessException
	{
		switch (offerId.getOfferType())
		{
			case OFFER_VERSION_1:
				offerServiceV1.updateLoanCardOfferRegistrationDate(offerId);
				break;
			case OFFER_VERSION_2:
				offerServiceV2.updateLoanCardOfferRegistrationDate(offerId);
				break;
			default:
				throw new IllegalArgumentException("Неизвестен тип предложения");
		}
	}

	public void updateLoanCardOfferTransitionDate(OfferId offerId) throws BusinessException
	{
		switch (offerId.getOfferType())
		{
			case OFFER_VERSION_1:
				offerServiceV1.updateLoanCardOfferTransitionDate(offerId);
				break;
			case OFFER_VERSION_2:
				offerServiceV2.updateLoanCardOfferTransitionDate(offerId);
				break;
			default:
				throw new IllegalArgumentException("Неизвестен тип предложения");
		}
	}
}
