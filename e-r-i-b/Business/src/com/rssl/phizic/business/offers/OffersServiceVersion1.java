package com.rssl.phizic.business.offers;

import com.rssl.ikfl.crediting.FeedbackType;
import com.rssl.ikfl.crediting.OfferCondition;
import com.rssl.ikfl.crediting.OfferId;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.loanCardOffer.LoanCardOfferVersion1;
import com.rssl.phizic.business.loanOffer.LoanOfferVersion1;
import com.rssl.phizic.business.loanOffer.OffersService;
import com.rssl.phizic.business.persons.ActivePerson;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User: Moshenko
 * Date: 24.05.2011
 * Time: 12:24:52
 * сервис для работы с предодобренными предложениями
 */
public class OffersServiceVersion1 extends OfferServiceBase
{
	private final OffersService offersStorage = new OffersService();

	public boolean isOfferReceivingInProgress(ActivePerson person) throws BusinessException
	{
		return false;
	}

	public void sendLoanOffersFeedback(List<OfferId> loanOfferIds, FeedbackType feedbackType) throws BusinessException
	{
		offersStorage.markLoanOfferAsViewed(loanOfferIds);
	}

	public void sendLoanCardOffersFeedback(List<OfferId> loanCardOfferIds, FeedbackType feedbackType) throws BusinessException
	{
		offersStorage.markLoanCardOfferAsViewed(loanCardOfferIds);
	}

	/**
	 * Получить предложения клиента по кредитам.
	 * @param person - пользователь
	 * @param number - колличества предложений
	 * @param isViewed - true(показанные)/false(непоказанные)/null(все подряд)
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public List<LoanOffer> getLoanOfferByPersonData(final Integer number,final ActivePerson person, final Boolean isViewed) throws BusinessException
	{
		List<com.rssl.phizic.business.loanOffer.LoanOffer> loanOffers = offersStorage.getLoanOfferByPersonData(number, person, isViewed);
		return convertLoanOffers(loanOffers);
	}

	/**
	 * лолучение списка n первых  предложений клиента по кредитным картам.
	 * @param person - пользователь
	 * @param number - колличества предложений(null все подряд)
	 * @param isViewed - true(показанные)/false(непоказанные)
	 * @throws com.rssl.phizic.business.BusinessException
	 */
    public List<LoanCardOffer> getLoanCardOfferByPersonData(final Integer number, final ActivePerson person, final Boolean isViewed) throws BusinessException
    {
	    List<com.rssl.phizic.business.loanCardOffer.LoanCardOffer> loanCardOffers = offersStorage.getLoanCardOfferByPersonData(number, person, isViewed);
        return convertLoanCardOffers(loanCardOffers);
    }

	/**
	 * лолучение списка всех предложений клиента по картам.
	 * @param person - пользователь
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public LoanCardOffer getNewLoanCardOffer(final ActivePerson person) throws BusinessException
	{
		 return convertLoanCardOffer(offersStorage.getNewLoanCardOffer(person));
	}

	/**
	 * Получение кредитного предложения по id
	 * @param offerId
	 * @return предложение кредита, либо null
	 * @throws BusinessException
	 */
	public LoanOffer findLoanOfferById(OfferId offerId) throws BusinessException
	{
		return convertLoanOffer(offersStorage.findLoanOfferById(offerId));
	}

	/**
	 * Получение предложения по кредитной карте по id
	 * @param offerId
	 * @return предложение кредита, либо null
	 * @throws BusinessException
	 */
	public LoanCardOffer findLoanCardOfferById(OfferId offerId) throws BusinessException
	{
		return convertLoanCardOffer(offersStorage.findLoanCardOfferById(offerId));
	}

	public void markLoanOfferAsUsed(final OfferId offerId) throws BusinessException
	{
		offersStorage.markLoanOfferAsUsed(offerId);
	}

	public void markLoanCardOfferAsUsed(final OfferId offerId) throws BusinessException
	{
		offersStorage.markLoanCardOfferAsUsed(offerId);
	}

	public LoanOffer getLoanOfferForMainPage(ActivePerson person) throws BusinessException
	{
		List<LoanOffer> loanOffers = getLoanOfferByPersonData(null, person, null);
		if (loanOffers.isEmpty())
			return null;
		return loanOffers.get(0);
	}

	public LoanCardOffer getLoanCardOfferForMainPage(ActivePerson person) throws BusinessException
	{
		List<LoanCardOffer> loanCardOffers = getLoanCardOfferByPersonData(null, person, null);
		if (loanCardOffers.isEmpty())
			return null;
		return loanCardOffers.get(0);
	}

	public boolean isFeedbackForLoanOfferSend(OfferId offerId, FeedbackType feedbackType) throws BusinessException
	{
		return offersStorage.isLoanOfferViewed(offerId.getId());
	}

	public boolean isFeedbackForLoanCardOfferSend(OfferId offerId, FeedbackType feedbackType) throws BusinessException
	{
		return offersStorage.isLoanCardOfferViewed(offerId.getId());
	}

	public void updateLoanCardOfferRegistrationDate(OfferId offerId) throws BusinessException
	{
		offersStorage.updateLoanCardOfferRegistrationDate(offerId.getId());
	}

	public void updateLoanCardOfferTransitionDate(OfferId offerId) throws BusinessException
	{
		offersStorage.updateLoanCardOfferTransitionDate(offerId.getId());
	}


	private List<LoanOffer> convertLoanOffers(List<com.rssl.phizic.business.loanOffer.LoanOffer> loanOffers)
	{
		if (loanOffers.isEmpty())
			return Collections.emptyList();

		List<LoanOffer> offers = new ArrayList<LoanOffer>(loanOffers.size());
		for (com.rssl.phizic.business.loanOffer.LoanOffer loanOffer : loanOffers)
		{
		     offers.add(convertLoanOffer(loanOffer));
		}
		return offers;
	}

	private LoanOffer convertLoanOffer(com.rssl.phizic.business.loanOffer.LoanOffer loanOffer)
	{
		if (loanOffer == null)
			return null;

		LoanOfferVersion1 offer = new LoanOfferVersion1(loanOffer.getId());
		offer.setFirstName(loanOffer.getFirstName());
		offer.setSurName(loanOffer.getSurName());
		offer.setPatrName(loanOffer.getPatrName());
		offer.setBirthDay(loanOffer.getBirthDay());
		offer.setDuration(loanOffer.getDuration());
		offer.setEndDate(loanOffer.getEndDate());
		offer.setIsViewed(loanOffer.isIsViewed());
		offer.setMaxLimit(loanOffer.getMaxLimit());
		offer.setPasportNumber(loanOffer.getPasportNumber());
		offer.setPasportSeries(loanOffer.getPasportSeries());
		offer.setPercentRate(loanOffer.getPercentRate());
		offer.setProductCode(loanOffer.getProductCode());
		offer.setProductName(loanOffer.getProductName());
		offer.setProductTypeCode(loanOffer.getProductTypeCode());
		offer.setSubProductCode(loanOffer.getSubProductCode());
		offer.setTerbank(loanOffer.getTerbank());
		offer.setOfferConditions(buildConditions(loanOffer));
		offer.setCampaignMemberId(loanOffer.getCampaignMemberId());

		return offer;
	}

	private List<OfferCondition> buildConditions(com.rssl.phizic.business.loanOffer.LoanOffer loanOffer)
	{
		List<OfferCondition> offerConditions = new ArrayList<OfferCondition>();
		BigDecimal percentRate = BigDecimal.valueOf(loanOffer.getPercentRate());
		Long offerId = loanOffer.getId();

		Long month6 = loanOffer.getMonth6();
		if (month6 != null)
			offerConditions.add(new OfferCondition(offerId, percentRate, 6, BigDecimal.valueOf(month6)));

		Long month12 = loanOffer.getMonth12();
		if (month12 != null)
			offerConditions.add(new OfferCondition(offerId, percentRate, 12, BigDecimal.valueOf(month12)));

		Long month18 = loanOffer.getMonth18();
		if (month18 != null)
			offerConditions.add(new OfferCondition(offerId, percentRate, 18, BigDecimal.valueOf(month18)));

		Long month24 = loanOffer.getMonth24();
		if (month24 != null)
			offerConditions.add(new OfferCondition(offerId, percentRate, 24, BigDecimal.valueOf(month24)));

		Long month36 = loanOffer.getMonth36();
		if (month36 != null)
			offerConditions.add(new OfferCondition(offerId, percentRate, 36, BigDecimal.valueOf(month36)));

		Long month48 = loanOffer.getMonth48();
		if (month48 != null)
			offerConditions.add(new OfferCondition(offerId, percentRate, 48, BigDecimal.valueOf(month48)));

		Long month60 = loanOffer.getMonth60();
		if (month60 != null)
			offerConditions.add(new OfferCondition(offerId, percentRate, 60, BigDecimal.valueOf(month60)));

		return offerConditions;
	}

	private List<LoanCardOffer> convertLoanCardOffers(List<com.rssl.phizic.business.loanCardOffer.LoanCardOffer> loanCardOffers)
	{
		if (loanCardOffers.isEmpty())
			return Collections.emptyList();

		List<LoanCardOffer> offers = new ArrayList<LoanCardOffer>(loanCardOffers.size());
		for (com.rssl.phizic.business.loanCardOffer.LoanCardOffer loanCardOffer : loanCardOffers)
		{
		     offers.add(convertLoanCardOffer(loanCardOffer));
		}
		return offers;
	}

	private LoanCardOffer convertLoanCardOffer(com.rssl.phizic.business.loanCardOffer.LoanCardOffer loanCardOffer)
	{
		if (loanCardOffer == null)
			return null;

		LoanCardOfferVersion1 offer = new LoanCardOfferVersion1(loanCardOffer.getId());
		offer.setFirstName(loanCardOffer.getFirstName());
		offer.setSurName(loanCardOffer.getSurName());
		offer.setPatrName(loanCardOffer.getPatrName());
		offer.setBirthDay(loanCardOffer.getBirthDay());
		offer.setIsViewed(loanCardOffer.isIsViewed());
		offer.setMaxLimit(loanCardOffer.getMaxLimit());
		offer.setTerbank(loanCardOffer.getTerbank());
		offer.setOsb(loanCardOffer.getOsb());
		offer.setVsp(loanCardOffer.getVsp());
		offer.setCardNumber(loanCardOffer.getCardNumber());
		offer.setIdWay(loanCardOffer.getIdWay());
		offer.setLoadDate(loanCardOffer.getLoadDate());
		offer.setNewCardType(loanCardOffer.getNewCardType());
		offer.setOfferType(loanCardOffer.getOfferType());
		offer.setSeriesAndNumber(loanCardOffer.getSeriesAndNumber());
		offer.setViewDate(loanCardOffer.getViewDate());

		return offer;
	}
}



