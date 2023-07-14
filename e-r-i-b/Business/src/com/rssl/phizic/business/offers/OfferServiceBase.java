package com.rssl.phizic.business.offers;

import com.rssl.ikfl.crediting.FeedbackType;
import com.rssl.ikfl.crediting.OfferId;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.cardAmountStep.CardAmountStep;
import com.rssl.phizic.business.creditcards.conditions.CreditCardCondition;
import com.rssl.phizic.business.creditcards.products.CreditCardProduct;
import com.rssl.phizic.business.loanCardOffer.ConditionProductByOffer;
import com.rssl.phizic.business.loans.products.Publicity;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.hibernate.NullParameterType;
import com.rssl.phizic.util.ApplicationUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * User: Moshenko
 * Date: 29.01.15
 * Time: 12:28
 * Одинаковые методы для OffersServiceVersion1 и OffersServiceVersion2
 */
public abstract class OfferServiceBase implements OfferService
{
	private final SimpleService simpleService = new SimpleService();

	public  List<ConditionProductByOffer> getCardOfferCompositProductCondition(List<String> offerTypes, ActivePerson person) throws BusinessException
	{
		List<ConditionProductByOffer> result = new ArrayList<ConditionProductByOffer>();
		//0.Получаем предодобренные предложения
		List<LoanCardOffer> loanCardOfferList = getLoanCardOfferByPersonData(null, person, null);
		if (loanCardOfferList.isEmpty())
			return result;

		for (LoanCardOffer cardOffer: loanCardOfferList)
		{
			//1.Проверяем есть ли предложения с заданным  типов,если нет то пропускаем
			String type = cardOffer.getOfferType().toString();
			if (!offerTypes.contains(type))
				continue;

			Money maxOfferLimit = cardOffer.getMaxLimit();
			//2. Получаем диапазоны условий и продуктов по лимитам
			List<Object[]> condProdArr = getCardConditonAndProduct(maxOfferLimit.getCurrency().getCode(), Publicity.PUBLISHED, null);

			//3. Ищем подходящие  по максимальной сумме предложения.
			result.addAll(getCurrentIntersectionBySum(condProdArr, cardOffer));
		}
		return result;
	}

	/**
	 * @param currencyCode Валюта кредитного условия
	 * @param publicity Состояние кредитного продукта
	 * @param conditionId  id кредитного условия
	 * @return  Условия с указанными диапазонами и связанные с условиями кредитные продукты.
	 *          -  Условие в разрезе валюты для Карточного кредитного продукта (CREDIT_CARD_CONDITIONS)
	 *          -  Карточный кредитный продукт .(CREDIT_CARD_PRODUCTS)
	 * @throws BusinessException
	 */
	private List<Object[]> getCardConditonAndProduct(final String currencyCode, final Publicity publicity, final Long conditionId) throws BusinessException
	{
		try
		{
			return  HibernateExecutor.getInstance().execute(new HibernateAction< List<Object[]> >()
			{
				public  List<Object[]> run(Session session)
				{
					Query query = session.getNamedQuery("com.rssl.phizic.operations.loans.offer.LoanOfferListOperation.cond_prod");
					query.setParameter("currency", currencyCode);
					query.setParameter("productState", publicity != null ? publicity.toString() : "");
					if (conditionId != null)
						query.setParameter("conditionId", conditionId);
					else
						query.setParameter("conditionId", NullParameterType.VALUE, NullParameterType.INSTANCE);
					if (ApplicationUtil.isMobileApi())
						query.setParameter("useForPreapprovedOffers", "1");
					else
						query.setParameter("useForPreapprovedOffers", "0");
					return (List<Object[]>) query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @param condProdArr Условия с указанными диапазонами и связанные с условиями кредитные продукты.
	 *                  -  Условие в разрезе валюты для Карточного кредитного продукта (CREDIT_CARD_CONDITIONS)
	 *                  -  Карточный кредитный продукт .(CREDIT_CARD_PRODUCTS)
	 * @param cardOffer Предодобренное кредитное предложение
	 * @return Ищем пресечение  максимальной сумму предодобренной кредитной карты с промежутками кредитного условия,
	 */
	private List<ConditionProductByOffer> getCurrentIntersectionBySum(List<Object[]> condProdArr, LoanCardOffer cardOffer)
	{
		List<ConditionProductByOffer> result = new ArrayList<ConditionProductByOffer>(condProdArr.size());
		for (Object[] reange: condProdArr)
		{
			CreditCardCondition creditCardCondition =  (CreditCardCondition) reange[0];
			if (!creditCardCondition.getCurrency().equals(cardOffer.getMaxLimit().getCurrency()))
				continue;
			CardAmountStep maxCreditLimit = creditCardCondition.getMaxCreditLimit();
			CardAmountStep minCreditLimit = creditCardCondition.getMinCreditLimit();
			Boolean magCreditLimitInclude = creditCardCondition.getMaxCreditLimitInclude();

			Money maxOfferLimit = cardOffer.getMaxLimit();
			int minLimCompare = maxOfferLimit.compareTo(minCreditLimit.getValue());
			int maxLimCompare = maxOfferLimit.compareTo(maxCreditLimit.getValue());
			if  ((minLimCompare >= 0 ) &&
					((maxLimCompare < 0 && !magCreditLimitInclude) ||
							(maxLimCompare <= 0 && magCreditLimitInclude)))
			{
				List<Object> arrList = new ArrayList<Object>(Arrays.asList(reange));
				arrList.add(cardOffer);
				Object[] arr =  arrList.toArray();
				result.add(getConditionProductByOffer(arr));
			}
		}
		return  result;
	}

	public ConditionProductByOffer findConditionProductByOffer(ActivePerson person, Long conditionId) throws BusinessException
	{

		//0.Получаем условие в разрезе валюты для Карточного кредитного продукта
		CreditCardCondition creditCardCondition = simpleService.findById(CreditCardCondition.class, conditionId);
		if (creditCardCondition == null)
			return null;

		Currency currency = creditCardCondition.getCurrency();
		//1.Получаем предодобренные предложения
		List<LoanCardOffer> loanCardOfferList = getLoanCardOfferByPersonData(null, person, null);
		if (loanCardOfferList.isEmpty())
			return null;

		//2. Получаем диапазоны условий и продуктов по id Заданного условия и валюте
		List<Object[]> condProdArr = getCardConditonAndProduct(currency.getCode(), null, conditionId);

		List<ConditionProductByOffer> resultList = new ArrayList<ConditionProductByOffer>();
		for (LoanCardOffer cardOffer: loanCardOfferList)
		{
			//3. Ищем подходящие  по максимальной сумме предложения.
			resultList.addAll(getCurrentIntersectionBySum(condProdArr, cardOffer));
		}
		if (!resultList.isEmpty())
			return resultList.get(0);

		return null;
	}

	private ConditionProductByOffer getConditionProductByOffer(Object[] condProd)
	{
		ConditionProductByOffer conditionProductByOffer = new ConditionProductByOffer();
		CreditCardCondition condition = (CreditCardCondition)condProd[0];
		CreditCardProduct product = (CreditCardProduct)condProd[1];
		LoanCardOffer offer = (LoanCardOffer)condProd[2];

		conditionProductByOffer.setConditionId(condition.getId());
		conditionProductByOffer.setInterestRate(condition.getInterestRate());
		conditionProductByOffer.setOfferInterestRate(condition.getOfferInterestRate());
		conditionProductByOffer.setFirstYearPayment(condition.getFirstYearPayment());
		conditionProductByOffer.setNextYearPayment(condition.getNextYearPayment());
		conditionProductByOffer.setOfferId(offer.getOfferId());
		conditionProductByOffer.setMaxLimit(offer.getMaxLimit());
		conditionProductByOffer.setSeriesAndNumber(offer.getSeriesAndNumber());
		conditionProductByOffer.setIdWay(offer.getIdWay());
		conditionProductByOffer.setVsp(offer.getVsp());
		conditionProductByOffer.setOsb(offer.getOsb());
		conditionProductByOffer.setFirstName(offer.getFirstName());
		conditionProductByOffer.setSurName(offer.getSurName());
		conditionProductByOffer.setPatrName(offer.getPatrName());
		conditionProductByOffer.setBirthDay(offer.getBirthDay());
		conditionProductByOffer.setName(product.getName());
		conditionProductByOffer.setCardTypeCode(product.getCardTypeCode());
		conditionProductByOffer.setOfferType(offer.getOfferType());
		conditionProductByOffer.setTerbank(offer.getTerbank());
		conditionProductByOffer.setAllowGracePeriod(product.getAllowGracePeriod());
		conditionProductByOffer.setGracePeriodDuration(product.getGracePeriodDuration());
		conditionProductByOffer.setGracePeriodInterestRate(product.getGracePeriodInterestRate());
		conditionProductByOffer.setTerms(product.getAdditionalTerms());
		conditionProductByOffer.setProductId(product.getId());

		return conditionProductByOffer;
	}

	public boolean existCreditCardOffer(ActivePerson person, Long conditionId) throws BusinessException
	{
		if (findConditionProductByOffer(person, conditionId) != null)
			return true;
		else
			return false;
	}

	public ConditionProductByOffer findConditionProductByOfferIdAndConditionId(OfferId loanCardOfferId, Long conditionId) throws BusinessException
	{
		//0. Получаем условие в разрезе валюты для Карточного кредитного продукта
		CreditCardCondition creditCardCondition = simpleService.findById(CreditCardCondition.class, conditionId);
		if (creditCardCondition == null)
			return null;

		//1. Ищем предодобренную кредитную карту по переданному id
		LoanCardOffer cardOffer = findLoanCardOfferById(loanCardOfferId);
		if (cardOffer == null)
			return null;

		//2. Получаем диапазоны условий и продуктов по id Заданного условия и валюте
		List<Object[]> condProdArr = getCardConditonAndProduct(cardOffer.getMaxLimit().getCurrency().getCode(), null, conditionId);

		//3. Ищем подходящие  по максимальной сумме предложения.
		List<ConditionProductByOffer> resultList  = getCurrentIntersectionBySum(condProdArr, cardOffer);

		if (!resultList.isEmpty())
		{
			return resultList.get(0);
		}
		return null;
	}

	public Long findConditionIdByOfferId(OfferId loanCardOfferId) throws BusinessException
	{
		//1. Ищем предодобренную кредитную карту по переданному id
		LoanCardOffer cardOffer = findLoanCardOfferById(loanCardOfferId);
		if (cardOffer == null)
			return null;

		//2. Получаем диапазоны условий и продуктов по валюте
		List<Object[]> condProdArr = getCardConditonAndProduct(cardOffer.getMaxLimit().getCurrency().getCode(), null, null);

		//3. Ищем подходящие  по максимальной сумме предложения.
		List<ConditionProductByOffer> resultList  = getCurrentIntersectionBySum(condProdArr, cardOffer);

		if (!resultList.isEmpty())
		{
			ConditionProductByOffer conditionProductByOffer = resultList.get(0);
			return  conditionProductByOffer.getConditionId();
		}
		return null;
	}

	public List<LoanCardOffer> getLoanCardOfferByPersonData(Integer number, ActivePerson person, Boolean isViewed) throws BusinessException
	{
		return null;
	}

	public List<LoanOffer> getLoanOfferByPersonData(Integer number, ActivePerson person, Boolean isViewed) throws BusinessException
	{
		return null;
	}

	public LoanOffer findLoanOfferById(OfferId offerId) throws BusinessException
	{
		return null;
	}

	public LoanCardOffer findLoanCardOfferById(OfferId offerId) throws BusinessException
	{
		return null;
	}

	public void sendLoanOffersFeedback(List<OfferId> loanOfferIds, FeedbackType feedbackType) throws BusinessException
	{
	}

	public void sendLoanCardOffersFeedback(List<OfferId> loanCardOfferIds, FeedbackType feedbackType) throws BusinessException
	{
	}

	public boolean isOfferReceivingInProgress(ActivePerson person) throws BusinessException
	{
		return false;
	}

	public void markLoanOfferAsUsed(OfferId offerId) throws BusinessException
	{

	}

	public void markLoanCardOfferAsUsed(OfferId offerId) throws BusinessException
	{
	}

	public LoanOffer getLoanOfferForMainPage(ActivePerson person) throws BusinessException
	{
		return null;
	}

	public LoanCardOffer getLoanCardOfferForMainPage(ActivePerson person) throws BusinessException
	{
		return null;
	}


	public boolean isFeedbackForLoanOfferSend(OfferId offerId, FeedbackType feedbackType) throws BusinessException
	{
		return false;
	}

	public boolean isFeedbackForLoanCardOfferSend(OfferId offerId, FeedbackType feedbackType) throws BusinessException
	{
		return false;
	}

	public List<Object[]> getCardOfferCompositProductCondition(ActivePerson person, Boolean isViewed) throws BusinessException
	{
		return null;
	}

	public LoanCardOffer getNewLoanCardOffer(ActivePerson person) throws BusinessException
	{
		return null;
	}
}
