package com.rssl.phizic.operations.pfp;

import com.rssl.phizic.business.dictionaries.pfp.common.PortfolioType;
import com.rssl.phizic.business.dictionaries.pfp.products.card.Card;
import com.rssl.phizic.business.dictionaries.pfp.products.card.CardProgrammType;
import com.rssl.phizic.business.pfp.PersonLoan;
import com.rssl.phizic.business.pfp.PersonalFinanceProfile;
import com.rssl.phizic.business.pfp.portfolio.PersonPortfolio;
import com.rssl.phizic.business.pfp.portfolio.ProductAmount;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.pfp.PFPConfigurationHelper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;

/**
 * @author mihaylov
 * @ created 01.09.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * ��������� ����� ���������� ������ ��� ���������� ��������������� ��������� � ���
 */
public class PfpMathUtils
{
	private static BigDecimal PERCENT_DIVISOR = new BigDecimal(4*100);//� ����������� �������� �� ������� ��������� � �����������
																	//�� ��������� ��������� � 0-1.
	private static BigDecimal incomeScale = new BigDecimal(0.1); //�������� �� ������� ����������� ����� �������� ����������
	private static int calculationScale = 10;
	private static int moneyScale = 2;
	private static final int MONTHS_IN_YEAR = 12;

	/**
	 * ��������� ��������� ��������(��������)
	 * @param portfolioType - ��� ��������
	 * @param amount - ����� ��������(��������)
	 * @param income - ����������
	 * @param quarterCount - ���������� ���������, ����� ������� �������������� ��������� ��������
	 * @return ��������� ��������(��������) ����� quarterCount ���������
	 */
	public static ProductAmount calculateProductAmount(PortfolioType portfolioType, BigDecimal amount, BigDecimal income, int quarterCount)
	{
		ProductAmount productAmount = new ProductAmount();
		BigDecimal quarterIncome = income.divide(PERCENT_DIVISOR, calculationScale,RoundingMode.UP);
		if(portfolioType == PortfolioType.START_CAPITAL)
		{
			productAmount.setInvestment(amount);
			BigDecimal totalAmount = quarterIncome.add(BigDecimal.ONE).pow(quarterCount).multiply(amount);
			productAmount.setTotalAmount(totalAmount);
			productAmount.setIncome(productAmount.getTotalAmount().subtract(productAmount.getInvestment()));
		}
		else if(portfolioType == PortfolioType.QUARTERLY_INVEST && quarterCount > 0)
		{
			BigDecimal investment = amount.multiply(BigDecimal.valueOf(quarterCount));
			BigDecimal totalAmount = investment;
			if(quarterIncome.compareTo(BigDecimal.ZERO) > 0)
			{
				BigDecimal qM = quarterIncome.add(BigDecimal.ONE);//������ ����� ����� ����������� � �������, ������� ������� ��� � ���������
				totalAmount = amount.add(amount.multiply(qM).multiply(BigDecimal.ONE.subtract(qM.pow(quarterCount-1))).divide(quarterIncome.negate(),moneyScale,RoundingMode.UP));
			}
			BigDecimal productIncome = totalAmount.subtract(investment);

			productAmount.setInvestment(investment);
			productAmount.setTotalAmount(totalAmount);
			productAmount.setIncome(productIncome);
		}
		return productAmount;
	}

	/**
	 * ��������� ��������� ��������(��������)
	 * @param portfolioType - ��� ��������
	 * @param amount - ����� ��������(��������)
	 * @param income - ����������
	 * @param executionDate - ���� ������� � ������� ���������� ������
	 * @param planDate - ���� �� ������� �������������� ��������� ��������(��������)
	 * @return ��������� ��������(��������) ����� quarterCount ���������
	 */
	public static ProductAmount calculateProductAmount(PortfolioType portfolioType, BigDecimal amount, BigDecimal income, Calendar executionDate, Calendar planDate)
	{
		Calendar startDate = executionDate;
		if(startDate == null)
			startDate = Calendar.getInstance();
		int quarterCount = DateHelper.getDiffInQuarters(startDate,planDate);
		return calculateProductAmount(portfolioType,amount,income,quarterCount);
	}

	/**
	 * ����� ��� ���������� ��������� ��������� � ������������. ��������� ��������� ��������� ����� quarterCount ��������� � ���� ������������ ���������.  
	 * @param profile - ������������ ���������� ������������
	 * @param quarterCount - ���������� ��������� ����� ������� ���������� ������ ��������� ���������.
	 * @return ��������� ��������� � ������������
	 */
	public static ProductAmount calculateProfileAmount(PersonalFinanceProfile profile, int quarterCount)
	{
		ProductAmount productAmount = new ProductAmount();
		for(PersonPortfolio portfolio: profile.getPortfolioList())
			productAmount = productAmount.add(calculateProductAmount(portfolio.getType(),portfolio.getProductSum().getDecimal(),portfolio.getIncome(),quarterCount));
		return productAmount;
	}

	/**
	 * ����� ��� ���������� ��������� ��������� � ������������. � ������������ ������ ���� ������ ���� ��������.
	 * ��������� ����� ���������� � ���� ����������� ������������(startPlaning), ���� ��� �����, �� � ������� ����.
	 * @param profile - ���
	 * @return ��������� ���������
	 */
	public static ProductAmount calculateProfileAmount(PersonalFinanceProfile profile)
	{
		Calendar planDate = profile.getPlanDate();
		Calendar executionDate = profile.getStartPlaningDate();
		if(executionDate == null)
			executionDate = Calendar.getInstance();
		int quarterCount = DateHelper.getDiffInQuarters(executionDate,planDate);
		return calculateProfileAmount(profile,quarterCount);
	}

	/**
	 * ��������� ����������� ����� �������� � �������� ��� ��������� ����������� ��������� ��������
	 * @param portfolioType - ��� ��������
	 * @param wantedAmount - �������� ��������� ��������
	 * @param income - ���������� ��������
	 * @param executionDate - ���� ������������ ��������
	 * @param planDate - ���� ��������
	 * @return ����� �������� � ��������(���������/��������������)
	 */
	public static BigDecimal calculateStartAmount(PortfolioType portfolioType, BigDecimal wantedAmount, BigDecimal income, Calendar executionDate, Calendar planDate)
	{
		Calendar startDate = executionDate;
		if(startDate == null)
			startDate = Calendar.getInstance();
		int quarterCount = DateHelper.getDiffInQuarters(startDate,planDate);
		BigDecimal quarterIncome = income.divide(PERCENT_DIVISOR, calculationScale,RoundingMode.UP);
		if(portfolioType == PortfolioType.START_CAPITAL)
		{
			return wantedAmount.divide(BigDecimal.ONE.add(quarterIncome).pow(quarterCount),moneyScale,RoundingMode.UP);
		}
		else if(portfolioType == PortfolioType.QUARTERLY_INVEST && quarterIncome.compareTo(BigDecimal.ZERO) > 0)
		{
			BigDecimal qM = quarterIncome.add(BigDecimal.ONE);//������ ����� ����� ����������� � �������, ������� ������� ��� � ���������
			BigDecimal divisor = qM.multiply(BigDecimal.ONE.subtract(qM.pow(quarterCount-1))).divide(quarterIncome.negate(),calculationScale,RoundingMode.UP).add(BigDecimal.ONE);
			return wantedAmount.divide(divisor,moneyScale,RoundingMode.UP);
		}
		else if(portfolioType == PortfolioType.QUARTERLY_INVEST)
		{
			return wantedAmount.divide(BigDecimal.valueOf(quarterCount),moneyScale,RoundingMode.UP);
		}
		else
			throw new IllegalArgumentException("����������� ��� �������� " + portfolioType);
	}

	/**
	 * ����� ��� ���������� ���������� �������� portfolioType, ��� ������� ����� ���������� ��������� �������� wantedAmount
	 * @param portfolioType - ��� ��������
	 * @param wantedAmount - �������� ��������� ��������
	 * @param startAmount - ��������������/�������������� �������� � ��������
	 * @param executionDate - ���� ����������� ���
	 * @param planDate - ���� ��������
	 * @return �������� ���������� ��������
	 */
	public static BigDecimal calculateWantedIncome(PortfolioType portfolioType, BigDecimal wantedAmount, BigDecimal startAmount,
	                                               Calendar executionDate, Calendar planDate)
	{
		Calendar startDate = executionDate;
		if(startDate == null)
			startDate = Calendar.getInstance();
		int quarterCount = DateHelper.getDiffInQuarters(startDate,planDate);
		BigDecimal wantedIncome = null;
		if(portfolioType == PortfolioType.START_CAPITAL)
		{
			wantedIncome = new BigDecimal(Math.pow(wantedAmount.divide(startAmount,calculationScale,RoundingMode.UP).doubleValue(),1.0/quarterCount)).subtract(BigDecimal.ONE).multiply(PERCENT_DIVISOR).setScale(1, RoundingMode.UP);
			if(PFPConfigurationHelper.getStartCapitalMaxWantedIncome().compareTo(wantedIncome) < 0)
				return null;
		}
		else if(portfolioType == PortfolioType.QUARTERLY_INVEST)
		{
			BigDecimal minIncm = BigDecimal.ZERO;
			BigDecimal maxIncm = PFPConfigurationHelper.getQuarterlyInvestMaxWantedIncome();
			ProductAmount tempAmount = calculateProductAmount(portfolioType,startAmount,maxIncm,quarterCount);
			//���� ��������� �������� ��� ����������� ����������� ���������� ������ �������� ���������, �� ��� ������ ������ ����������   
			if(tempAmount.getTotalAmount().compareTo(wantedAmount) < 0)
				return null;
			while(maxIncm.subtract(minIncm).compareTo(incomeScale) > 0)
			{
				wantedIncome = minIncm.add(maxIncm).divide(BigDecimal.valueOf(2),1,RoundingMode.UP);
				tempAmount = calculateProductAmount(portfolioType,startAmount,wantedIncome,quarterCount);
				if(tempAmount.getTotalAmount().compareTo(wantedAmount) > 0)
					maxIncm = wantedIncome;
				else
					minIncm = wantedIncome;
			}
		}
		else
			throw new IllegalArgumentException("����������� ��� �������� " + portfolioType);
		return wantedIncome;
	}

	/**
	 * ��������� ����������� ������ �� �������
	 * @param personLoan - ������ �������
	 * @return ����������� ������
	 */
	public static BigDecimal calcQuarterPayment(PersonLoan personLoan)
	{
		BigDecimal quarterRate = personLoan.getRate().divide(PERCENT_DIVISOR,calculationScale,RoundingMode.UP);
		int quarterCount = DateHelper.getDiffInQuarters(personLoan.getStartDate(),personLoan.getEndDate());
		BigDecimal k = quarterRate.add(BigDecimal.ONE).pow(quarterCount);
		return personLoan.getAmount().getDecimal().multiply(quarterRate).multiply(k).divide(k.subtract(BigDecimal.ONE),moneyScale,RoundingMode.HALF_UP);
	}

	/**
	 * ��������� ���������� �� ������
	 * @param amount - ��������� ��������
	 * @param income - ���������� ������ �� ������
	 * @param yearCount - ���������� ���
	 * @return ���������� �� ������
	 */
	public static ProductAmount calculateAccountIncomeForCreditCard(BigDecimal amount, BigDecimal income, int yearCount)
    {
	    ProductAmount accountProduct = new ProductAmount();
        accountProduct.setInvestment(amount);
        accountProduct.setTotalAmount(new BigDecimal(Math.pow(income.divide(PERCENT_DIVISOR, calculationScale, RoundingMode.UP).add(BigDecimal.ONE).doubleValue(), 4 * yearCount)).multiply(amount));
        accountProduct.setIncome(accountProduct.getTotalAmount().subtract(accountProduct.getInvestment()));

        return accountProduct;
    }

	/**
	 * ��������� ���������� �� ������
	 * @param amount - ��������� ��������
	 * @param income - ���������� ������ �������
	 * @param yearCount - ���������� ���
	 * @return ���������� �� ������
	 */
	public static ProductAmount calculateThanksIncomeForCreditCard(BigDecimal amount, BigDecimal income, int yearCount)
    {
	    ProductAmount thanksProduct = new ProductAmount();
	    thanksProduct.setInvestment(amount.multiply(BigDecimal.valueOf(yearCount)).multiply(BigDecimal.valueOf(MONTHS_IN_YEAR)));
	    thanksProduct.setIncome(thanksProduct.getInvestment().multiply(income).divide(BigDecimal.valueOf(100), calculationScale, RoundingMode.UP));
	    thanksProduct.setTotalAmount(thanksProduct.getInvestment().add(thanksProduct.getIncome()));

	    return thanksProduct;
    }

	/**
     * ��������� ���������� �� �������� �����
     * @param amount - �������� ��������
     * @param yearCount - ���������� ���
     * @param card - ��������� �����
	 * @return ����������
     */
	public static ProductAmount calculateIncomeForCreditCardByType(BigDecimal amount, int yearCount, Card card)
    {
	    ProductAmount productAmount = new ProductAmount();
	    BigDecimal bonus = calculateBonus(card.getProgrammType(), amount, yearCount, card.getBonus(), card.getInputs() != null ? card.getInputs() : BigDecimal.valueOf(100));
	    productAmount.setInvestment(amount.multiply(BigDecimal.valueOf(yearCount)).multiply(BigDecimal.valueOf(MONTHS_IN_YEAR)));
	    productAmount.setIncome(bonus);
	    productAmount.setTotalAmount(productAmount.getInvestment().add(productAmount.getIncome()));

        return productAmount;
    }

	/**
	 * ��������� ������ � ����������� �� �����
	 * @param type - ��� �����
	 * @param amount - �����
	 * @param yearCount - ���������� ���
	 * @param bonus - ������ �� �����
	 * @param divider - ����������� ��������
	 * @return ������
	 */
	public static BigDecimal calculateBonus(CardProgrammType type, BigDecimal amount, int yearCount, BigDecimal bonus, BigDecimal divider)
	{
	    switch (type)
	    {
		    case beneficent:
			    return amount.multiply(BigDecimal.valueOf(yearCount)).multiply(BigDecimal.valueOf(MONTHS_IN_YEAR)).multiply(bonus).divide(divider, calculationScale, RoundingMode.UP).multiply(BigDecimal.valueOf(2));
		    case aeroflot:
		    case mts:
			    return amount.multiply(BigDecimal.valueOf(yearCount)).multiply(BigDecimal.valueOf(MONTHS_IN_YEAR)).multiply(bonus).divide(divider, calculationScale, RoundingMode.UP);
		    case empty:
			    return BigDecimal.ZERO;
	    }
		return BigDecimal.ZERO;
	}
}
