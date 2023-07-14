package com.rssl.ikfl.crediting;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * @author Balovtsev
 * @since 22.06.2015.
 */
public class OfferTopUp
{
	private Long id;

	// ����� ���������� ��������
	private String agreementNumber;

	// �� ��: ����� �������� �����
	private String loanAccountNumber;

	// ������ �������
	private String currency;

	// �� ��������� ������������ ��������� �������
	private String idSource;

	// �� ��������
	private String idContract;

	// ���� ������ �������
	private Calendar startDate;

	// �������� ���� ��������� �������
	private Calendar maturityDate;

	// ����� ������� (���������)
	private BigDecimal totalAmount;

	//���������� ������ ���������� ���������
	private Integer topUpLoanBlockCount;

	//����� ������� ���������� ���������
	private BigDecimal totalRepaymentSum;

	// ��������� �����������
	private Long offerId;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public void setAgreementNumber(String agreementNumber)
	{
		this.agreementNumber = agreementNumber;
	}

	public String getAgreementNumber()
	{
		return agreementNumber;
	}

	public void setLoanAccountNumber(String loanAccountNumber)
	{
		this.loanAccountNumber = loanAccountNumber;
	}

	public String getLoanAccountNumber()
	{
		return loanAccountNumber;
	}

	public void setStartDate(Calendar startDate)
	{
		this.startDate = startDate;
	}

	public Calendar getStartDate()
	{
		return startDate;
	}

	public void setMaturityDate(Calendar maturityDate)
	{
		this.maturityDate = maturityDate;
	}

	public Calendar getMaturityDate()
	{
		return maturityDate;
	}

	public void setTotalAmount(BigDecimal totalAmount)
	{
		this.totalAmount = totalAmount;
	}

	public BigDecimal getTotalAmount()
	{
		return totalAmount;
	}

	public void setCurrency(String currency)
	{
		this.currency = currency;
	}

	public String getCurrency()
	{
		return currency;
	}

	public void setIdSource(String idSource)
	{
		this.idSource = idSource;
	}

	public String getIdSource()
	{
		return idSource;
	}

	public void setIdContract(String idContract)
	{
		this.idContract = idContract;
	}

	public String getIdContract()
	{
		return idContract;
	}

	public void setTopUpLoanBlockCount(Integer topUpLoanBlockCount)
	{
		this.topUpLoanBlockCount = topUpLoanBlockCount;
	}

	public Integer getTopUpLoanBlockCount()
	{
		return topUpLoanBlockCount;
	}

	public void setTotalRepaymentSum(BigDecimal totalRepaymentSum)
	{
		this.totalRepaymentSum = totalRepaymentSum;
	}

	public BigDecimal getTotalRepaymentSum()
	{
		return totalRepaymentSum;
	}

	public Long getOfferId()
	{
		return offerId;
	}

	public void setOfferId(Long offerId)
	{
		this.offerId = offerId;
	}
}
