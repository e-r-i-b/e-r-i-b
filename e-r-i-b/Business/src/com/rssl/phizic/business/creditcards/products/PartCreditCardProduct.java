package com.rssl.phizic.business.creditcards.products;


import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * ��������� ��������� ������� + ������� � ������� ������ ��� ���������� ���������� ��������
 * @author komarov
 * @ created 27.05.2015
 * @ $Author$
 * @ $Revision$
 */
@SuppressWarnings("UnusedDeclaration")
public class PartCreditCardProduct
{

	private Long id;
	private BigDecimal minCreditLimit;      //���. ��������� �����
	private Currency minCreditLimitCurrency;      //���. ��������� �����

	private BigDecimal maxCreditLimit;      //����. ��������� �����
	private Currency maxCreditLimitCurrency;      //���. ��������� �����

	private Boolean isMaxCreditLimitInclude;    //"������������" ��� ����. ���������� ������
	private BigDecimal offerInterestRate;       //%  ������  �� �������������� �����

	private BigDecimal firstYearPayment;             //����� �� ������� ������������: ������ ���
	private Currency   firstYearPaymentCurrency;             //����� �� ������� ������������: ������ ���
	private BigDecimal nextYearPayment;              //����� �� ������� ������������: ����������� ����
	private Currency   nextYearPaymentCurrency;              //����� �� ������� ������������: ����������� ����

	private Calendar changeDate;
	private String name;                        //������������
	private Boolean allowGracePeriod;           //���� �������� ������
	private Integer gracePeriodDuration;        //�������� ������ ��
	private BigDecimal gracePeriodInterestRate; //% ������ � �������� ������
	private String additionalTerms;             //���. �������

	/**
	 * @return �������������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id �������������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return ��������� �����
	 */
	public Money getMinCreditLimit()
	{
		if(minCreditLimit == null || minCreditLimitCurrency == null)
			return new Money();
		return new Money(minCreditLimit, minCreditLimitCurrency);
	}

	/**
	 * @param minCreditLimit ��������� �����
	 */
	public void setMinCreditLimit(Money minCreditLimit)
	{

	}

	/**
	 * @return ��������� �����
	 */
	public Money getMaxCreditLimit()
	{
		if(maxCreditLimit == null || maxCreditLimitCurrency == null)
			return new Money();
		return new Money(maxCreditLimit, maxCreditLimitCurrency);
	}

	/**
	 * @param maxCreditLimit ��������� �����
	 */
	public void setMaxCreditLimit(Money maxCreditLimit)
	{

	}

	/**
	 * @return ������������ �����
	 */
	public Boolean getIsMaxCreditLimitInclude()
	{
		return isMaxCreditLimitInclude;
	}

	/**
	 * @param maxCreditLimitInclude ������������ �����
	 */
	public void setIsMaxCreditLimitInclude(Boolean maxCreditLimitInclude)
	{
		isMaxCreditLimitInclude = maxCreditLimitInclude;
	}

	/**
	 * @return %  ������  �� �������������� �����
	 */
	public BigDecimal getOfferInterestRate()
	{
		return offerInterestRate;
	}

	/**
	 * @param offerInterestRate %  ������  �� �������������� �����
	 */
	public void setOfferInterestRate(BigDecimal offerInterestRate)
	{
		this.offerInterestRate = offerInterestRate;
	}

	/**
	 * @return ����� �� ������� ������������: ������ ���
	 */
	public Money getFirstYearPayment()
	{
		if(firstYearPayment == null || firstYearPaymentCurrency == null)
			return new Money();
		return new Money(firstYearPayment, firstYearPaymentCurrency);
	}

	/**
	 * @return ����� �� ������� ������������: ������ ���
	 */
	public Money getNextYearPayment()
	{
		if(nextYearPayment == null || nextYearPaymentCurrency == null)
			return new Money();
		return new Money(nextYearPayment, nextYearPaymentCurrency);
	}

	/**
	 * @return ���� ���������
	 */
	public Calendar getChangeDate()
	{
		return changeDate;
	}

	/**
	 * @param changeDate ���� ���������
	 */
	public void setChangeDate(Calendar changeDate)
	{
		this.changeDate = changeDate;
	}

	/**
	 * @return ������������
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name ������������
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return ���� �������� ������
	 */
	public Boolean getAllowGracePeriod()
	{
		return allowGracePeriod;
	}

	/**
	 * @param allowGracePeriod ���� �������� ������
	 */
	public void setAllowGracePeriod(Boolean allowGracePeriod)
	{
		this.allowGracePeriod = allowGracePeriod;
	}

	/**
	 * @return �������� ������ ��
	 */
	public Integer getGracePeriodDuration()
	{
		return gracePeriodDuration;
	}

	/**
	 * @param gracePeriodDuration �������� ������ ��
	 */
	public void setGracePeriodDuration(Integer gracePeriodDuration)
	{
		this.gracePeriodDuration = gracePeriodDuration;
	}

	/**
	 * @return % ������ � �������� ������
	 */
	public BigDecimal getGracePeriodInterestRate()
	{
		return gracePeriodInterestRate;
	}

	/**
	 * @param gracePeriodInterestRate % ������ � �������� ������
	 */
	public void setGracePeriodInterestRate(BigDecimal gracePeriodInterestRate)
	{
		this.gracePeriodInterestRate = gracePeriodInterestRate;
	}

	/**
	 * @return ���. �������
	 */
	public String getAdditionalTerms()
	{
		return additionalTerms;
	}

	/**
	 * @param additionalTerms ���. �������
	 */
	public void setAdditionalTerms(String additionalTerms)
	{
		this.additionalTerms = additionalTerms;
	}

	/**
	 * @return ���. ��������� �����
	 */
	public Currency getMinCreditLimitCurrency()
	{
		return minCreditLimitCurrency;
	}

	/**
	 * @param minCreditLimitCurrency ���. ��������� �����
	 */
	public void setMinCreditLimitCurrency(Currency minCreditLimitCurrency)
	{
		this.minCreditLimitCurrency = minCreditLimitCurrency;
	}

	/**
	 * @return ���. ��������� �����
	 */
	public Currency getMaxCreditLimitCurrency()
	{
		return maxCreditLimitCurrency;
	}

	/**
	 * @param maxCreditLimitCurrency ���. ��������� �����
	 */
	public void setMaxCreditLimitCurrency(Currency maxCreditLimitCurrency)
	{
		this.maxCreditLimitCurrency = maxCreditLimitCurrency;
	}

	/**
	 * @return  ����� �� ������� ������������: ������ ���
	 */
	public Currency getFirstYearPaymentCurrency()
	{
		return firstYearPaymentCurrency;
	}

	/**
	 * @param firstYearPaymentCurrency ����� �� ������� ������������: ������ ���
	 */
	public void setFirstYearPaymentCurrency(Currency firstYearPaymentCurrency)
	{
		this.firstYearPaymentCurrency = firstYearPaymentCurrency;
	}

	/**
	 * @return ����� �� ������� ������������: ����������� ����
	 */
	public Currency getNextYearPaymentCurrency()
	{
		return nextYearPaymentCurrency;
	}

	/**
	 * @param nextYearPaymentCurrency  ����� �� ������� ������������: ����������� ����
	 */
	public void setNextYearPaymentCurrency(Currency nextYearPaymentCurrency)
	{
		this.nextYearPaymentCurrency = nextYearPaymentCurrency;
	}
}
