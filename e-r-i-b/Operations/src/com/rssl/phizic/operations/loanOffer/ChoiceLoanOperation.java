package com.rssl.phizic.operations.loanOffer;

import com.rssl.ikfl.crediting.OfferId;
import com.rssl.ikfl.crediting.CreditingConfig;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.loanOffer.LoanOfferHelper;
import com.rssl.phizic.business.loanclaim.creditProduct.condition.CreditProductCondition;
import com.rssl.phizic.business.loanclaim.creditProduct.condition.CreditProductConditionService;
import com.rssl.phizic.business.loanclaim.creditProduct.condition.CurrencyCreditProductCondition;
import com.rssl.phizic.business.loans.products.YearMonth;
import com.rssl.phizic.business.offers.LoanOffer;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.loanclaim.LoanClaimConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.operations.OperationBase;
import org.apache.commons.collections.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * Операция для получения списка кредитных предложений клиента и кредитных продуктов
 *
 * @author  Balovtsev
 * @version 26.12.13 15:21
 */
public class ChoiceLoanOperation extends OperationBase
{
	private static final CreditProductConditionService creditConditionService = new CreditProductConditionService();
	private static String ERROR_PERIOD = "Вы неправильно указали срок кредита. Пожалуйста, введите срок, который будет соответствовать условиям выдачи кредита";
	private static String ERROR_AMOUNT = "Вы неправильно указали сумму кредита. Пожалуйста, введите сумму, которая будет соответствовать условиям выдачи кредита";

    private SortedSet<LoanOffer> loanOffers = new TreeSet<LoanOffer>(new Comparator<LoanOffer>()
    {
        public int compare(LoanOffer o1, LoanOffer o2)
        {
            Money  o1MaxLimit = o1.getMaxLimit();
            Money  o2MaxLimit = o2.getMaxLimit();
            String o1currCode = o1MaxLimit.getCurrency().getCode();
            String o2currCode = o2MaxLimit.getCurrency().getCode();

            if (!o1currCode.equals(o2currCode))
            {
                if ("RUB".equals(o1currCode))
                {
                    return -1;
                }

                if ("RUB".equals(o2currCode))
                {
                    return 1;
                }

                if ("EUR".equals(o1currCode))
                {
                    return 1;
                }

                if ("EUR".equals(o2currCode))
                {
                    return -1;
                }
            }
	        int compareRes = o2MaxLimit.getDecimal().compareTo(o1MaxLimit.getDecimal());
	        //что бы при равных сумма кредит не исключался из дерева loanOffers
	        if (compareRes == 0)
		        return -1;
            return compareRes;
        }
    });

	private List<CreditProductCondition>  productConditions = new ArrayList<CreditProductCondition>();

    /**
     *
     * Инициализация операции
     *
     * @throws BusinessException
     */
    public void initialize() throws BusinessException
    {
	    PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
	    if (!ConfigFactory.getConfig(LoanClaimConfig.class).isUseNewAlgorithm())
		    throw new BusinessException("Настройка оформления заявки по новому алгоритму отключена.");
	    if (CollectionUtils.isNotEmpty(personData.getPerson().getPersonDocuments()))
	        loanOffers.addAll(LoanOfferHelper.filterLoanOffersByEndDate(personData.getLoanOfferByPersonData(null, null)) );
	    productConditions.addAll( creditConditionService.getAvaliableConditions());
    }

	/**
     *
     * Возвращает отсортированный список кредитных предложений клиента. Сортировка осуществляется по
     * максимальной сумме кредитного лимита и валюте кредитного лимита (первыми идут предложения с наибольшей
     * максимальной суммой в рублях, затем в долларах, и, последними предложения в евро).
     *
     * @return List&lt;LoanOffer&gt;
     */
    public Set<LoanOffer> getLoanOffers()
    {
        return Collections.unmodifiableSortedSet(loanOffers);
    }

    /**
     *
     * Возвращает список кредитных условий
     *
     * @return List&lt;CreditProductCondition&gt;
     */
    public List<CreditProductCondition> getCreditProductConditions()
    {
        return Collections.unmodifiableList(productConditions);
    }

	/**
	 *
	 * @param loanOfferId Id предодобренного предложения
	 * @param loanOfferAmount Сумма введенная клиентом
	 * @param loanPeriod Период в месяцах
	 * @return Ошибка валидации либо null
	 */
	public String validateLoanOffer(OfferId loanOfferId, BigDecimal loanOfferAmount, Integer loanPeriod) throws BusinessException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		LoanOffer loanOffer = personData.findLoanOfferById(loanOfferId);
		if (loanOffer == null)
			throw new BusinessException("Не удалось найти предодобренное кредитное условие с id = " + loanOfferId);

		BigDecimal maxLimit = loanOffer.getMaxLimit().getDecimal();
		if (loanOfferAmount.compareTo(maxLimit) == 1)
			return ERROR_AMOUNT;

		if (loanPeriod > loanOffer.getDuration())
			return  ERROR_PERIOD;

		return null;
	}

	/**
	 *
	 * @param loanPeriod Период в месяцах
	 * @param condCurrId Id по валютного условия
	 * @param condId Id кредитного условия
	 * @param loanAmount Сумма введенная клиентом
	 * @return Ошибка валидации либо null
	 * @throws BusinessException
	 */
	public String validateCreditProductCondition(Integer loanPeriod,Long condCurrId,Long condId, BigDecimal loanAmount) throws BusinessException
	{
		CreditProductCondition creditProductCondition = creditConditionService.findeById(condId);
		if (creditProductCondition == null)
			throw new BusinessException("Не удалось найти кредитное условие с id = " + condId);

		if (!creditProductCondition.isPublished())
			throw new BusinessException("Кредитное условие с id = " + condId + " не доступно клиенту.");


		CurrencyCreditProductCondition currencyCreditProductCondition = creditConditionService.findCurrCondById(condCurrId);
		if (currencyCreditProductCondition == null)
			throw new BusinessException("Не удалось найти повалютное кредитное условие с id = " + condCurrId);

		YearMonth minDuration = creditProductCondition.getMinDuration();
		YearMonth maxDuration = creditProductCondition.getMaxDuration();
		boolean maxDurationInclude = creditProductCondition.isMaxRangeInclude();

		if (minDuration != null && loanPeriod < minDuration.getDuration())
			return ERROR_PERIOD;

		if( (maxDuration != null && maxDurationInclude && loanPeriod > maxDuration.getDuration())||
		    (maxDuration != null && !maxDurationInclude && loanPeriod >= maxDuration.getDuration()))
			return ERROR_PERIOD;

		Money minLimit = currencyCreditProductCondition.getMinLimitAmount();
		Money maxLimit = currencyCreditProductCondition.getMaxLimitAmount();
		Boolean maxLimitInclude = currencyCreditProductCondition.isMaxLimitInclude();
		Boolean maxLimitPercentUse = currencyCreditProductCondition.isMaxLimitPercentUse();

		//Если указанна сумма от
		if ((minLimit != null) && loanAmount.compareTo(minLimit.getDecimal()) == -1)
				return ERROR_AMOUNT;

		//Если указанна сумма до, а признак того что используется процент от стоимости кредита не указан
		if (maxLimit != null && !maxLimitPercentUse)
		{
			int res = loanAmount.compareTo(maxLimit.getDecimal());
			//Если больше максимальной , или равна ей но не включительно верхней границы суммы.
			if (res == 1 || res == 0 && !maxLimitInclude)
				return ERROR_AMOUNT;
		}

		return  null;
	}

	public int getTimeToRefresh() throws BusinessException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		if (personData.isOfferReceivingInProgress())
			return ConfigFactory.getConfig(CreditingConfig.class).getWaitingTime();
		else return 0;
	}
}
