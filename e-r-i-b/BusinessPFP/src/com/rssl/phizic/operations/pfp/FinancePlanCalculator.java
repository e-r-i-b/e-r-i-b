package com.rssl.phizic.operations.pfp;

import com.rssl.phizic.business.dictionaries.pfp.common.PortfolioType;
import com.rssl.phizic.business.pfp.PersonLoan;
import com.rssl.phizic.business.pfp.PersonTarget;
import com.rssl.phizic.business.pfp.PersonalFinanceProfile;
import com.rssl.phizic.business.pfp.portfolio.PersonPortfolio;
import com.rssl.phizic.utils.DateHelper;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lepihina
 * @ created 13.09.13
 * @ $Author$
 * @ $Revision$
 */
public class FinancePlanCalculator
{
	private BigDecimal MIN_NEGATIVE_AMOUNT = new BigDecimal(-0.01);
    private static final int DEFAULT_QUARTER_COUNT = 10;
	private static final int CALCULATION_SCALE = 10;
	private static final BigDecimal QUARTERS_IN_YEAR = BigDecimal.valueOf(4);
	private static final int MONTHS_IN_QUARTER = 3;
	private static final int QUARTER_IN_YEAR = 4;

    //данные на основе которых производим вычислени€
	private BigDecimal startCapitalAmount;
	private BigDecimal quarterlyInvestAmount;
	private BigDecimal startCapitalQuarterIncome;
	private BigDecimal quarterlyInvestQuarterIncome;
	private Calendar startFinancePlanDate;

	private Map<Integer, BigDecimal> loanQuarterAmountList = new HashMap<Integer, BigDecimal>();
	private Map<Integer, BigDecimal> loanQuarterPaymentList = new HashMap<Integer, BigDecimal>();
	private Map<Integer, BigDecimal> targetQuarterAmountList = new HashMap<Integer, BigDecimal>();

	private Map<Integer, BigDecimal> totalAmountList = new HashMap<Integer, BigDecimal>();
	private Map<Integer, BigDecimal> startCapitalAmountList = new HashMap<Integer, BigDecimal>(); //стоимость портфел€ стартовый капитал по кварталам
	private Map<Integer, BigDecimal> quarterlyInvestAmountList = new HashMap<Integer, BigDecimal>(); //стоимость портфел€ ежеквартальные вложени€ по кварталам
	private Map<Integer, BigDecimal> quarterlyInvestQuarterAmountList = new HashMap<Integer, BigDecimal>(); //ежеквартальные вложени€ по кварталам
	private Map<Integer, BigDecimal> negativeBalanceList = new HashMap<Integer, BigDecimal>();

	private int lastTargetQuarter = -1; // цель "создание пенсионных накоплений"
	private int quarterCount = DEFAULT_QUARTER_COUNT;

	/**
	 * @param personalFinanceProfile - анкета прохождени€ персонального финансового планировани€
	 */
	public FinancePlanCalculator(PersonalFinanceProfile personalFinanceProfile)
	{
		PersonPortfolio startCapital = personalFinanceProfile.getPortfolioByType(PortfolioType.START_CAPITAL);
		PersonPortfolio quarterlyInvest = personalFinanceProfile.getPortfolioByType(PortfolioType.QUARTERLY_INVEST);

		Calendar executionDate = personalFinanceProfile.getStartPlaningDate();
		this.startCapitalAmount = startCapital.getProductSum().getDecimal();
		this.quarterlyInvestAmount = quarterlyInvest.getProductSum().getDecimal();
		this.startCapitalQuarterIncome = startCapital.getIncome().divide(QUARTERS_IN_YEAR).divide(BigDecimal.valueOf(100));
		this.quarterlyInvestQuarterIncome = quarterlyInvest.getIncome().divide(QUARTERS_IN_YEAR).divide(BigDecimal.valueOf(100));
		this.startFinancePlanDate = executionDate;
		setQuartetCount(personalFinanceProfile);
		setTargetList(executionDate, personalFinanceProfile.getPersonTargets());
		setLoanList(executionDate, personalFinanceProfile.getPersonLoans());
		calculate();
	}

	/**
	 * @return суммы кредитов по кварталам
	 */
	public Map<Integer, BigDecimal> getLoanQuarterAmountList()
	{
		return loanQuarterAmountList;
	}

	/**
	 * @return суммы платежей по кредитам по кварталам
	 */
	public Map<Integer, BigDecimal> getLoanQuarterPaymentList()
	{
		return loanQuarterPaymentList;
	}

	public Map<Integer, BigDecimal> getTotalAmountList()
	{
		return totalAmountList;
	}

	public Map<Integer, BigDecimal> getStartCapitalAmountList()
	{
		return startCapitalAmountList;
	}

	public Map<Integer, BigDecimal> getQuarterlyInvestAmountList()
	{
		return quarterlyInvestAmountList;
	}

	public Map<Integer, BigDecimal> getQuarterlyInvestQuarterAmountList()
	{
		return quarterlyInvestQuarterAmountList;
	}

	public Map<Integer, BigDecimal> getNegativeBalanceList()
	{
		return negativeBalanceList;
	}

	public void setQuartetCount(PersonalFinanceProfile personalFinanceProfile)
	{
		Calendar endDate = (Calendar) personalFinanceProfile.getLastTargetDate().clone();
		quarterCount = DateHelper.getDiffInQuarters(startFinancePlanDate, endDate);
		if(quarterCount > 4*QUARTER_IN_YEAR)
		{
			// шаг отображени€ будет год (несколько лет), поэтому отображаем значени€ на последний квартал года
			int quarterDelta = QUARTER_IN_YEAR - DateHelper.getQuarter(endDate);
			quarterCount += quarterDelta;
		}
	}

	/**
	 * @return количество кварталов, на которое производ€тс€ расчеты
	 */
	public int getQuarterCount()
	{
		return quarterCount;
	}

	/**
	 * –асчитывает суммы целей по кварталам
	 * @param startDate - дата начала ѕ‘ѕ
	 * @param targetList - список целей клиента
	 */
	public void setTargetList(Calendar startDate, List<PersonTarget> targetList)
	{
        for(PersonTarget personTarget : targetList)
        {
	        Integer quarter = DateHelper.getDiffInQuarters(startDate, personTarget.getPlannedDate());
	        BigDecimal targetQuarterAmount = targetQuarterAmountList.get(quarter);
            if(targetQuarterAmount != null)
                targetQuarterAmountList.put(quarter, targetQuarterAmount.add(personTarget.getAmount().getDecimal()));
            else
	            targetQuarterAmountList.put(quarter, personTarget.getAmount().getDecimal());
	        if(personTarget.isVeryLast())
		        lastTargetQuarter = quarter;
        }
	}

	/**
	 * –асчитывает суммы кредитов по кварталам и суммы платежей по кредитам по кварталам
	 * @param startDate - дата начала ѕ‘ѕ
	 * @param loanList - список кредитов клиента
	 */
	public void setLoanList(Calendar startDate, List<PersonLoan> loanList)
	{
        for(PersonLoan personLoan : loanList)
        {
	        Integer quarter = DateHelper.getDiffInQuarters(startDate, personLoan.getStartDate());
	        BigDecimal loanQuarterSum = loanQuarterAmountList.get(quarter);
	        if(loanQuarterSum != null)
                loanQuarterAmountList.put(quarter, loanQuarterSum.add(personLoan.getAmount().getDecimal()));
            else
	            loanQuarterAmountList.put(quarter, personLoan.getAmount().getDecimal());
        }

		for(PersonLoan personLoan : loanList)
        {
	        Calendar quarter = DateHelper.getNextQuarter(personLoan.getStartDate());
	        Calendar endQuarter = DateHelper.getNextQuarter(personLoan.getEndDate());
	        while(quarter.before(endQuarter))
            {
	            Integer quarters = DateHelper.getDiffInQuarters(startDate, quarter);
	            BigDecimal loanQuarterPayment = loanQuarterPaymentList.get(quarters);
	            if(loanQuarterPayment != null)
		            loanQuarterPaymentList.put(quarters, loanQuarterPayment.add(PfpMathUtils.calcQuarterPayment(personLoan)));
	            else
		            loanQuarterPaymentList.put(quarters, PfpMathUtils.calcQuarterPayment(personLoan));
	            quarter = DateHelper.getNextQuarter(quarter);
            }
        }
	}

	/**
	 * ѕроизвести рассчет будующей стоимости портфелей
	 */
	public void calculate()
	{
		startCapitalAmountList.put(0, startCapitalAmount);
		quarterlyInvestAmountList.put(0, BigDecimal.ZERO);
		quarterlyInvestQuarterAmountList.put(0, BigDecimal.ZERO);
		totalAmountList.put(0, startCapitalAmount);
		BigDecimal factor = BigDecimal.ZERO;

        for(int i=1; i <= quarterCount; i++)
        {
	        BigDecimal startCapitalPrevQuarterAmount = startCapitalAmountList.get(i-1);
	        BigDecimal startCapitalQuarterAmount = startCapitalPrevQuarterAmount.multiply(startCapitalQuarterIncome.add(BigDecimal.ONE));

	        BigDecimal loanQuarterPayment = loanQuarterPaymentList.get(i);
	        if (loanQuarterPayment == null)
		        loanQuarterPayment = BigDecimal.ZERO;

	        BigDecimal quarterlyInvestPrevQuarterAmount = quarterlyInvestAmountList.get(i-1);
	        BigDecimal currentQuarterlyInvestAmount = quarterlyInvestAmount.subtract(loanQuarterPayment);
	        BigDecimal quarterlyInvestQuarterAmount = currentQuarterlyInvestAmount.add(quarterlyInvestPrevQuarterAmount.multiply(quarterlyInvestQuarterIncome.add(BigDecimal.ONE)));
	        quarterlyInvestQuarterAmountList.put(i, currentQuarterlyInvestAmount);

	        BigDecimal currentQuarterLoanAmount = loanQuarterAmountList.get(i);
	        if (currentQuarterLoanAmount != null)
		        factor = factor.add(currentQuarterLoanAmount);

	        if (lastTargetQuarter < 0 || i < lastTargetQuarter)
	        {
		        BigDecimal currentQuarterTargetAmount = targetQuarterAmountList.get(i);
		        if (currentQuarterTargetAmount != null)
			        factor = factor.subtract(currentQuarterTargetAmount);
	        }

			if (factor.compareTo(BigDecimal.ZERO) < 0)
			{

				BigDecimal totalSum = startCapitalQuarterAmount.add(quarterlyInvestQuarterAmount);
				BigDecimal factorAbsoluteValue = factor.abs();
				if (totalSum.compareTo(factorAbsoluteValue) <= 0)
				{
					startCapitalQuarterAmount = BigDecimal.ZERO;
					quarterlyInvestQuarterAmount = BigDecimal.ZERO;
					factor = factor.add(totalSum);
				}
				else
				{
					BigDecimal startCapitalRatio = startCapitalQuarterAmount.divide(totalSum, CALCULATION_SCALE, BigDecimal.ROUND_UP);
					BigDecimal quarterlyInvestRatio = quarterlyInvestQuarterAmount.divide(totalSum, CALCULATION_SCALE, BigDecimal.ROUND_UP);
					startCapitalQuarterAmount = startCapitalQuarterAmount.subtract(startCapitalRatio.multiply(factorAbsoluteValue));
					quarterlyInvestQuarterAmount = quarterlyInvestQuarterAmount.subtract(quarterlyInvestRatio.multiply(factorAbsoluteValue));
					factor = BigDecimal.ZERO;
				}
			}
	        startCapitalAmountList.put(i, startCapitalQuarterAmount);
            quarterlyInvestAmountList.put(i, quarterlyInvestQuarterAmount);
	        BigDecimal totalAmount = startCapitalQuarterAmount.add(quarterlyInvestQuarterAmount).add(factor);
            totalAmountList.put(i, totalAmount);

	        if (totalAmount.compareTo(MIN_NEGATIVE_AMOUNT) <= 0)
            {
                negativeBalanceList.put(i, totalAmount.abs());
            }
        }
	}
}
