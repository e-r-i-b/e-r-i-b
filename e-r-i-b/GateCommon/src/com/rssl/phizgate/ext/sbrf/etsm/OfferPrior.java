package com.rssl.phizgate.ext.sbrf.etsm;

import java.math.BigDecimal;

/**
 * @author EgorovaA
 * @ created 15.06.15
 * @ $Author$
 * @ $Revision$
 */
public class OfferPrior
{
	private Long id;
	// ������������� ������ � ETSM (���������� ����)
	private String applicationNumber;
	// ��������� �������
	private String clientCategory;
	// �������������� ���� � �������
	private Long altPeriod;
	// �������������� ����� ������� � ������;
	private BigDecimal altAmount;
	// �������������� ������
	private BigDecimal altInterestRate;
	// �������������� ������ ��������� �������
	private BigDecimal altFullLoanCost;
	// �������������� ����������� ������
	private BigDecimal altAnnuityPayment;
	// �������������� ����� �� ��������� �����
	private BigDecimal altCreditCardLimit;
	// ������� ��� ����������� ������ �������
	private Long visibilityCounter = Long.valueOf(0);
	// �������� �� ������� �� ����� ������
	private Boolean counterUpdated = false;
	// �������� ���������� ��������. �� ����� � ��, ��������� ��� ����������� ������
	private String loanProductName;
	//������� ������������� �������. �� ����� � ��, ��������� ��� ����������� ������
	private boolean priority;
	// UID ��������� ������� � �������� �� �����. ��� OfferConfirmed �� �����
	private String rqUid;

	public boolean isEmpty()
	{
		return id == null;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getApplicationNumber()
	{
		return applicationNumber;
	}

	public void setApplicationNumber(String applicationNumber)
	{
		this.applicationNumber = applicationNumber;
	}

	public String getClientCategory()
	{
		return clientCategory;
	}

	public void setClientCategory(String clientCategory)
	{
		this.clientCategory = clientCategory;
	}

	public Long getAltPeriod()
	{
		return altPeriod;
	}

	public void setAltPeriod(Long altPeriod)
	{
		this.altPeriod = altPeriod;
	}

	public BigDecimal getAltAmount()
	{
		return altAmount;
	}

	public void setAltAmount(BigDecimal altAmount)
	{
		this.altAmount = altAmount;
	}

	public BigDecimal getAltInterestRate()
	{
		return altInterestRate;
	}

	public void setAltInterestRate(BigDecimal altInterestRate)
	{
		this.altInterestRate = altInterestRate;
	}

	public BigDecimal getAltFullLoanCost()
	{
		return altFullLoanCost;
	}

	public void setAltFullLoanCost(BigDecimal altFullLoanCost)
	{
		this.altFullLoanCost = altFullLoanCost;
	}

	public BigDecimal getAltAnnuityPayment()
	{
		return altAnnuityPayment;
	}

	public void setAltAnnuityPayment(BigDecimal altAnnuityPayment)
	{
		this.altAnnuityPayment = altAnnuityPayment;
	}

	public BigDecimal getAltCreditCardLimit()
	{
		return altCreditCardLimit;
	}

	public void setAltCreditCardLimit(BigDecimal altCreditCardLimit)
	{
		this.altCreditCardLimit = altCreditCardLimit;
	}

	public Long getVisibilityCounter()
	{
		return visibilityCounter;
	}

	public void setVisibilityCounter(Long visibilityCounter)
	{
		this.visibilityCounter = visibilityCounter;
	}

	public Boolean getCounterUpdated()
	{
		return counterUpdated;
	}

	public void setCounterUpdated(Boolean counterUpdated)
	{
		this.counterUpdated = counterUpdated;
	}

	public String getLoanProductName()
	{
		return loanProductName;
	}

	public void setLoanProductName(String loanProductName)
	{
		this.loanProductName = loanProductName;
	}

	public boolean isPriority()
	{
		return priority;
	}

	public void setPriority(boolean priority)
	{
		this.priority = priority;
	}

	public String getRqUid()
	{
		return rqUid;
	}

	public void setRqUid(String rqUid)
	{
		this.rqUid = rqUid;
	}
}
