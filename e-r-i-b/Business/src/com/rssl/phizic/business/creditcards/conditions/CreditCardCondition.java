package com.rssl.phizic.business.creditcards.conditions;

import com.rssl.phizic.business.cardAmountStep.CardAmountStep;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;

import java.math.BigDecimal;

/**
 * ������� � ������� ������ ��� ���������� ���������� ��������
 * @author Dorzhinov
 * @ created 15.06.2011
 * @ $Author$
 * @ $Revision$
 */
public class CreditCardCondition
{
	private Long id;
	private Long productId;                     //��������� ��������� �������
	private Currency currency;                  //������
	private CardAmountStep minCreditLimit;      //���. ��������� �����
	private CardAmountStep maxCreditLimit;      //����. ��������� �����
	private Boolean isMaxCreditLimitInclude;    //"������������" ��� ����. ���������� ������
	private BigDecimal interestRate;            //% ������
	private BigDecimal offerInterestRate;       //%  ������  �� �������������� �����
	private Money firstYearPayment;             //����� �� ������� ������������: ������ ���
	private Money nextYearPayment;              //����� �� ������� ������������: ����������� ����

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getProductId()
	{
		return productId;
	}

	public void setProductId(Long productId)
	{
		this.productId = productId;
	}

	public Currency getCurrency()
	{
		return currency;
	}

	public void setCurrency(Currency currency)
	{
		this.currency = currency;
	}

	public CardAmountStep getMinCreditLimit()
	{
		return minCreditLimit;
	}

	public void setMinCreditLimit(CardAmountStep minCreditLimit)
	{
		this.minCreditLimit = minCreditLimit;
	}

	public CardAmountStep getMaxCreditLimit()
	{
		return maxCreditLimit;
	}

	public void setMaxCreditLimit(CardAmountStep maxCreditLimit)
	{
		this.maxCreditLimit = maxCreditLimit;
	}

	public Boolean isMaxCreditLimitInclude()
	{
		return isMaxCreditLimitInclude;
	}

	public Boolean getMaxCreditLimitInclude()
	{
		return isMaxCreditLimitInclude();
	}

	public void setMaxCreditLimitInclude(Boolean maxCreditLimitInclude)
	{
		isMaxCreditLimitInclude = maxCreditLimitInclude;
	}

	public BigDecimal getInterestRate()
	{
		return interestRate;
	}

	public void setInterestRate(BigDecimal interestRate)
	{
		this.interestRate = interestRate;
	}

	public BigDecimal getOfferInterestRate()
	{
		return offerInterestRate;
	}

	public void setOfferInterestRate(BigDecimal offerInterestRate)
	{
		this.offerInterestRate = offerInterestRate;
	}

	public Money getFirstYearPayment()
	{
		return firstYearPayment;
	}

	public void setFirstYearPayment(Money firstYearPayment)
	{
		this.firstYearPayment = firstYearPayment;
	}

	public Money getNextYearPayment()
	{
		return nextYearPayment;
	}

	public void setNextYearPayment(Money nextYearPayment)
	{
		this.nextYearPayment = nextYearPayment;
	}
}
