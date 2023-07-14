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
 * �������� ��� ��������� ������ ��������� ����������� ������� � ��������� ���������
 *
 * @author  Balovtsev
 * @version 26.12.13 15:21
 */
public class ChoiceLoanOperation extends OperationBase
{
	private static final CreditProductConditionService creditConditionService = new CreditProductConditionService();
	private static String ERROR_PERIOD = "�� ����������� ������� ���� �������. ����������, ������� ����, ������� ����� ��������������� �������� ������ �������";
	private static String ERROR_AMOUNT = "�� ����������� ������� ����� �������. ����������, ������� �����, ������� ����� ��������������� �������� ������ �������";

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
	        //��� �� ��� ������ ����� ������ �� ���������� �� ������ loanOffers
	        if (compareRes == 0)
		        return -1;
            return compareRes;
        }
    });

	private List<CreditProductCondition>  productConditions = new ArrayList<CreditProductCondition>();

    /**
     *
     * ������������� ��������
     *
     * @throws BusinessException
     */
    public void initialize() throws BusinessException
    {
	    PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
	    if (!ConfigFactory.getConfig(LoanClaimConfig.class).isUseNewAlgorithm())
		    throw new BusinessException("��������� ���������� ������ �� ������ ��������� ���������.");
	    if (CollectionUtils.isNotEmpty(personData.getPerson().getPersonDocuments()))
	        loanOffers.addAll(LoanOfferHelper.filterLoanOffersByEndDate(personData.getLoanOfferByPersonData(null, null)) );
	    productConditions.addAll( creditConditionService.getAvaliableConditions());
    }

	/**
     *
     * ���������� ��������������� ������ ��������� ����������� �������. ���������� �������������� ��
     * ������������ ����� ���������� ������ � ������ ���������� ������ (������� ���� ����������� � ����������
     * ������������ ������ � ������, ����� � ��������, �, ���������� ����������� � ����).
     *
     * @return List&lt;LoanOffer&gt;
     */
    public Set<LoanOffer> getLoanOffers()
    {
        return Collections.unmodifiableSortedSet(loanOffers);
    }

    /**
     *
     * ���������� ������ ��������� �������
     *
     * @return List&lt;CreditProductCondition&gt;
     */
    public List<CreditProductCondition> getCreditProductConditions()
    {
        return Collections.unmodifiableList(productConditions);
    }

	/**
	 *
	 * @param loanOfferId Id ��������������� �����������
	 * @param loanOfferAmount ����� ��������� ��������
	 * @param loanPeriod ������ � �������
	 * @return ������ ��������� ���� null
	 */
	public String validateLoanOffer(OfferId loanOfferId, BigDecimal loanOfferAmount, Integer loanPeriod) throws BusinessException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		LoanOffer loanOffer = personData.findLoanOfferById(loanOfferId);
		if (loanOffer == null)
			throw new BusinessException("�� ������� ����� �������������� ��������� ������� � id = " + loanOfferId);

		BigDecimal maxLimit = loanOffer.getMaxLimit().getDecimal();
		if (loanOfferAmount.compareTo(maxLimit) == 1)
			return ERROR_AMOUNT;

		if (loanPeriod > loanOffer.getDuration())
			return  ERROR_PERIOD;

		return null;
	}

	/**
	 *
	 * @param loanPeriod ������ � �������
	 * @param condCurrId Id �� ��������� �������
	 * @param condId Id ���������� �������
	 * @param loanAmount ����� ��������� ��������
	 * @return ������ ��������� ���� null
	 * @throws BusinessException
	 */
	public String validateCreditProductCondition(Integer loanPeriod,Long condCurrId,Long condId, BigDecimal loanAmount) throws BusinessException
	{
		CreditProductCondition creditProductCondition = creditConditionService.findeById(condId);
		if (creditProductCondition == null)
			throw new BusinessException("�� ������� ����� ��������� ������� � id = " + condId);

		if (!creditProductCondition.isPublished())
			throw new BusinessException("��������� ������� � id = " + condId + " �� �������� �������.");


		CurrencyCreditProductCondition currencyCreditProductCondition = creditConditionService.findCurrCondById(condCurrId);
		if (currencyCreditProductCondition == null)
			throw new BusinessException("�� ������� ����� ���������� ��������� ������� � id = " + condCurrId);

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

		//���� �������� ����� ��
		if ((minLimit != null) && loanAmount.compareTo(minLimit.getDecimal()) == -1)
				return ERROR_AMOUNT;

		//���� �������� ����� ��, � ������� ���� ��� ������������ ������� �� ��������� ������� �� ������
		if (maxLimit != null && !maxLimitPercentUse)
		{
			int res = loanAmount.compareTo(maxLimit.getDecimal());
			//���� ������ ������������ , ��� ����� �� �� �� ������������ ������� ������� �����.
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
