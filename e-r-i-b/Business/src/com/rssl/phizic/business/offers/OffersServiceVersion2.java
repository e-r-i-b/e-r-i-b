package com.rssl.phizic.business.offers;

import com.rssl.ikfl.crediting.*;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.loanCardOffer.LoanCardOfferType;
import com.rssl.phizic.business.payments.PaymentsConfig;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.GuestPerson;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.loanOffer.LoanOfferConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;

import java.util.*;

/**
 * @author Rtischeva
 * @ created 20.01.15
 * @ $Author$
 * @ $Revision$
 */
class OffersServiceVersion2 extends OfferServiceBase
{
	private final OfferStorage offerStorage = new OfferStorage();

	private final CRMMessageBuilder messageBuilder = new CRMMessageBuilder();

	private final CRMMessageSender messageSender = new CRMMessageSender();

	private final CRMMessageLogger messageLogger = new CRMMessageLogger();

	private final Log syslog = PhizICLogFactory.getLog(LogModule.Core);

	private static final Map<FeedbackType, String> feedbackResult = new HashMap<FeedbackType, String>();

	static
	{
		feedbackResult.put(FeedbackType.INFORM, "01");
		feedbackResult.put(FeedbackType.PRESENTATION, "02");
		feedbackResult.put(FeedbackType.CANCEL, "03");
		feedbackResult.put(FeedbackType.ACCEPT, "04");
	}

	public List<LoanCardOffer> getLoanCardOfferByPersonData(Integer number, ActivePerson person, Boolean isViewed) throws BusinessException
	{
		List<com.rssl.ikfl.crediting.Offer> loanCardOffers = offerStorage.getLoanCardOfferByPersonData(number, person, isViewed);
		return convertLoanCardOffers(loanCardOffers, person);
	}

	public List<LoanOffer> getLoanOfferByPersonData(final Integer number, final ActivePerson person, final Boolean isViewed) throws BusinessException
	{
		if (person instanceof GuestPerson)
			return Collections.emptyList();
		List<com.rssl.ikfl.crediting.Offer> loanOffers = offerStorage.getLoanOfferByPersonData(number, person, isViewed);
		List<OfferCondition> offersConditions = offerStorage.getOfferConditionsByLoginId(person);
		return convertLoanOffers(loanOffers, offersConditions);
	}

	public LoanOffer findLoanOfferById(OfferId offerId) throws BusinessException
	{
		return convertLoanOffer(offerStorage.findLoanOfferById(offerId.getId()));
	}

	public LoanCardOffer findLoanCardOfferById(OfferId offerId) throws BusinessException
	{
		return convertLoanCardOffer(offerStorage.findCardLoanOfferById(offerId.getId()));
	}

	public void sendLoanOffersFeedback(List<OfferId> loanOfferIds, FeedbackType feedbackType) throws BusinessException
	{
		sendFeedback(loanOfferIds, feedbackType);
	}

	public void sendLoanCardOffersFeedback(List<OfferId> loanCardOfferIds, FeedbackType feedbackType) throws BusinessException
	{
		sendFeedback(loanCardOfferIds, feedbackType);
	}

	private void sendFeedback(List<OfferId> offerIds, FeedbackType feedbackType)throws BusinessException
	{
		for (OfferId offerId : offerIds)
		{
			com.rssl.ikfl.crediting.Offer offer = offerStorage.findOfferById(offerId.getId());
			if (offer == null)
				throw new BusinessException("Не найдено предложение с id " + offerId.getId());

			Feedback feedback = offerStorage.loadOfferFeedback(offer, feedbackType.toString());
			if (feedback == null)
			{

				feedback = new Feedback();
				feedback.setCampaignMemberId(offer.getCampaignMemberId());
				feedback.setSourceCode(offer.getSourceCode());
				feedback.setSurName(offer.getSurName());
				feedback.setFirstName(offer.getFirstName());
				feedback.setPatrName(offer.getPatrName());
				feedback.setBirthDay(offer.getBirthDay());
				feedback.setDocSeries(offer.getDocSeries());
				feedback.setDocNumber(offer.getDocNumber());
				feedback.setOfferEndDate(offer.getExpDate());
			}

			try
			{
				if (feedback.getChannel() == null)
					generateFeedback(feedback, feedbackType);
			}
			catch (Exception e)
			{
				syslog.error("Неудачная попытка сгенерировать отклик по предложению ID=" + offerId.getId(), e);
			}
		}
	}

	public boolean isOfferReceivingInProgress(ActivePerson person) throws BusinessException
	{
		return offerStorage.isOfferReceivingInProgress(person);
	}

	public void markLoanOfferAsUsed(OfferId offerId) throws BusinessException
	{
		offerStorage.markOfferAsUsed(offerId.getId());
	}

	public void markLoanCardOfferAsUsed(OfferId offerId) throws BusinessException
	{
		offerStorage.markOfferAsUsed(offerId.getId());
	}

	public LoanOffer getLoanOfferForMainPage(ActivePerson person) throws BusinessException
	{
		List<LoanOffer> loanOffers = getLoanOfferByPersonData(null, person, null);

		return (LoanOffer) getPriorityOffer(loanOffers);
	}

	//Приоритет предложения сквозной. Наименьший приоритет является наивысшим
	private Offer getPriorityOffer(List<? extends Offer> offers)
	{
		if (offers.isEmpty())
			return null;

		return Collections.min(offers, new Comparator<Offer>()
		{
			public int compare(Offer o1, Offer o2)
			{
				return o1.getPriority() < o2.getPriority() ? -1 : o1.getPriority() > o2.getPriority() ? 1 : 0;
			}
		});
	}

	public LoanCardOffer getLoanCardOfferForMainPage(ActivePerson person) throws BusinessException
	{
		List<LoanCardOffer> loanCardOffers = getLoanCardOfferByPersonData(null, person, null);

		return (LoanCardOffer) getPriorityOffer(loanCardOffers);
	}

	public boolean isFeedbackForLoanOfferSend(OfferId offerId, FeedbackType feedbackType) throws BusinessException
	{
		return isFeedbackSend(offerId, feedbackType);
	}

	public boolean isFeedbackForLoanCardOfferSend(OfferId offerId, FeedbackType feedbackType) throws BusinessException
	{
		return isFeedbackSend(offerId, feedbackType);
	}

	private boolean isFeedbackSend(OfferId offerId, FeedbackType feedbackType) throws BusinessException
	{
		Feedback feedback = offerStorage.findOfferFeedback(offerId.getId(), feedbackType.toString());
		if (feedback == null)
			return false;
		Calendar  presentTime = feedback.getFeedbackTime();
		if (presentTime != null)
		{
			if (presentTime.compareTo(DateHelper.getPreviousDay(62)) <= 0)
			{
				offerStorage.remove(feedback);
				return false;
			}
			return true;
		}
		return false;
	}

	public LoanCardOffer getNewLoanCardOffer(final ActivePerson person) throws BusinessException
	{
		return getLoanCardOfferForMainPage(person);
	}

	private List<LoanOffer> convertLoanOffers(List<com.rssl.ikfl.crediting.Offer> loanOffers, List<OfferCondition> offersConditions) throws BusinessException
	{
		List<LoanOffer> offers = new ArrayList<LoanOffer>(loanOffers.size());
		for (com.rssl.ikfl.crediting.Offer loanOffer : loanOffers)
		{
			LoanOfferVersion2 offerV2 = new LoanOfferVersion2(loanOffer.getId());
			buildLoanOffer(loanOffer, offerV2);
		    offers.add(offerV2);
		}

		buildOffersConditions(offers, offersConditions);
		return offers;
	}

	private LoanOfferVersion2 convertLoanOffer(com.rssl.ikfl.crediting.Offer loanOffer) throws BusinessException
	{
		if (loanOffer == null)
			return null;

		LoanOfferVersion2 offerV2 = new LoanOfferVersion2(loanOffer.getId());

		buildLoanOffer(loanOffer, offerV2);

		buildOfferConditions(loanOffer, offerV2);

		return offerV2;
	}

	private void buildLoanOffer(com.rssl.ikfl.crediting.Offer loanOffer, LoanOfferVersion2 offerV2) throws BusinessException
	{
		offerV2.setFirstName(loanOffer.getFirstName());
		offerV2.setSurName(loanOffer.getSurName());
		offerV2.setPatrName(loanOffer.getPatrName());
		offerV2.setBirthDay(loanOffer.getBirthDay());
		offerV2.setTerbank(Long.valueOf(loanOffer.getTb()));

		offerV2.setPasportNumber(StringHelper.getEmptyIfNull(loanOffer.getDocNumber()));
		offerV2.setPasportSeries(StringHelper.getEmptyIfNull(loanOffer.getDocSeries()));

		offerV2.setProductCode(loanOffer.getProductCode());
		offerV2.setProductName(loanOffer.getProductASName());
		offerV2.setProductTypeCode(loanOffer.getProductTypeCode());
		offerV2.setSubProductCode(loanOffer.getProductSubCode());

		offerV2.setEndDate(loanOffer.getExpDate());
		offerV2.setPriority(loanOffer.getPriority());
		offerV2.setPersonalText(loanOffer.getPersonalText());
		offerV2.setCurrencyCode(loanOffer.getCurrencyCode());
		offerV2.setTopUps(loanOffer.getTopUps());
	}

	private void buildOfferConditions(com.rssl.ikfl.crediting.Offer loanOffer, LoanOfferVersion2 offerV2) throws BusinessException
	{
		List<OfferCondition> offerConditions = offerStorage.getOfferConditionsByOfferId(loanOffer.getId());
		offerV2.setOfferConditions(offerConditions);
		buildOfferParams(offerV2, offerConditions);
	}

	private void buildOffersConditions(List<LoanOffer> loanOffers, List<OfferCondition> offersConditions)
	{
		for (LoanOffer loanOffer : loanOffers)
		{
			LoanOfferVersion2 loanOfferVersion2 = (LoanOfferVersion2) loanOffer;
			loanOfferVersion2.setOfferConditions(new ArrayList<OfferCondition>());

			List<OfferCondition> conditions = loanOffer.getConditions();
			for (OfferCondition offerCondition : offersConditions)
			{
				if (offerCondition.getOfferId() == loanOffer.getOfferId().getId())
					conditions.add(offerCondition);
			}
			buildOfferParams(loanOfferVersion2, conditions);
		}
	}

	private void buildOfferParams(LoanOfferVersion2 offerV2, List<OfferCondition> offerConditions)
	{
		if (!offerConditions.isEmpty())
		{
			OfferCondition condition = offerConditions.get(0);
			int maxPeriod = condition.getPeriod();

			for (OfferCondition offerCondition : offerConditions)
			{
				int period = offerCondition.getPeriod();
				if (period > maxPeriod)
				{
					maxPeriod = period;
					condition = offerCondition;
				}
			}

			offerV2.setDuration(Long.valueOf(condition.getPeriod()));

			Currency currency = getCurrency(offerV2.getCurrencyCode());
			offerV2.setMaxLimit(new Money(condition.getAmount(), currency));

			offerV2.setPercentRate(condition.getRate().doubleValue());
		}
	}

	private Currency getCurrency(String currencyCode)
	{
		try
		{
			CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
			return currencyService.findByAlphabeticCode(currencyCode);
		}
		catch (GateException e)
		{
			throw new RuntimeException(e);
		}
	}

	private List<LoanCardOffer> convertLoanCardOffers(List<com.rssl.ikfl.crediting.Offer> loanCardOffers, ActivePerson person) throws BusinessException
	{
		if (loanCardOffers.isEmpty())
			return Collections.emptyList();

		List<LoanCardOffer> offers = new ArrayList<LoanCardOffer>(loanCardOffers.size());
		for (com.rssl.ikfl.crediting.Offer offer : loanCardOffers)
		{
			LoanCardOffer loanCardOffer = convertLoanCardOffer(offer);
			//если по предложению были отправлены отклики "согласие" или "отказ" то его больше не нужно показывать
			if (loanCardOffer.getOfferType() == LoanCardOfferType.newCard || !isFeedbackForLoanCardOfferSend(loanCardOffer.getOfferId(), FeedbackType.ACCEPT) && !isFeedbackForLoanCardOfferSend(loanCardOffer.getOfferId(), FeedbackType.CANCEL))
		    {
			    offers.add(loanCardOffer);
		    }
		}
		return offers;
	}

	private LoanCardOffer convertLoanCardOffer(com.rssl.ikfl.crediting.Offer loanCardOffer) throws BusinessException
	{
		if (loanCardOffer == null)
			return null;

		LoanCardOfferVersion2 offerV2 = new LoanCardOfferVersion2(loanCardOffer.getId());

		offerV2.setFirstName(loanCardOffer.getFirstName());
		offerV2.setSurName(loanCardOffer.getSurName());
		offerV2.setPatrName(loanCardOffer.getPatrName());
		offerV2.setBirthDay(loanCardOffer.getBirthDay());

		offerV2.setSeriesAndNumber(StringHelper.getEmptyIfNull(loanCardOffer.getDocNumber()) + StringHelper.getEmptyIfNull(loanCardOffer.getDocSeries()));

		offerV2.setTerbank(Long.valueOf(loanCardOffer.getTb()));

		Currency currency = getCurrency(loanCardOffer.getCurrencyCode());
		offerV2.setMaxLimit(new Money(loanCardOffer.getLimitMax(), currency));
		offerV2.setPriority(loanCardOffer.getPriority());
		offerV2.setOfferUsed(loanCardOffer.isOfferUsed());
		offerV2.setPersonalText(loanCardOffer.getPersonalText());
		offerV2.setLoadDate(loanCardOffer.getLoadDate());
		offerV2.setExpDate(loanCardOffer.getExpDate());
		if (loanCardOffer.getProductType() == OfferProductType.CHANGE_LIMIT)
			offerV2.setOfferType(LoanCardOfferType.changeLimit);
		else
			offerV2.setOfferType(LoanCardOfferType.newCard);
		return offerV2;
	}

	private void generateFeedback(Feedback feedback, FeedbackType feedbackType) throws Exception
	{
		// 1. Выставляем канал, тип и время отклика
		feedback.setChannel(getOfferResponseChannel());
		feedback.setFeedbackTime(Calendar.getInstance());
		feedback.setFeedbackType(feedbackType);
		//Отклик "согласие" отпраляем только при включенной настройке и только из СБОЛ
		if (ConfigFactory.getConfig(LoanOfferConfig.class).isNeedSendFeedbackAccept() || feedbackType != FeedbackType.ACCEPT)
		{
			// 2. Формируем запрос к CRM
			CRMMessage crmMessage = messageBuilder.makeFeedbackRequestMessage(feedback, feedbackResult.get(feedbackType));
			messageSender.sendMessageToCRMForOffers(crmMessage);

			// 3. Запоминаем запрос к CRM в Журнале Сообщений
			messageLogger.logMessage(crmMessage);
		}
		// 4. Сохраняем отклик в БД
		offerStorage.saveFeedback(feedback);
	}

	private OfferResponseChannel getOfferResponseChannel()
	{
		ApplicationInfo applicationInfo = ApplicationConfig.getIt().getApplicationInfo();

		if (applicationInfo.isMobileApi())
			return OfferResponseChannel.MP;
		else if (applicationInfo.isATM())
			return OfferResponseChannel.US;
		else if (PersonContext.getPersonDataProvider().getPersonData().getGuestProfileId() != null)
			return OfferResponseChannel.SBOL_GUEST;
		else
			return OfferResponseChannel.SBOL;
	}



	public void updateLoanCardOfferRegistrationDate(OfferId offerId) throws BusinessException
	{

	}

	public void updateLoanCardOfferTransitionDate(OfferId offerId) throws BusinessException
	{

	}
}
