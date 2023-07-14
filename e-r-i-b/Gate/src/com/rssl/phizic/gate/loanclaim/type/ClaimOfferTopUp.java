package com.rssl.phizic.gate.loanclaim.type;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * @author Balovtsev
 * @since  26.06.2015.
 */
public class ClaimOfferTopUp
{
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

	public String getAgreementNumber()
	{
		return agreementNumber;
	}

	public void setAgreementNumber(String agreementNumber)
	{
		this.agreementNumber = agreementNumber;
	}

	public String getLoanAccountNumber()
	{
		return loanAccountNumber;
	}

	public void setLoanAccountNumber(String loanAccountNumber)
	{
		this.loanAccountNumber = loanAccountNumber;
	}

	public String getCurrency()
	{
		return currency;
	}

	public void setCurrency(String currency)
	{
		this.currency = currency;
	}

	public String getIdSource()
	{
		return idSource;
	}

	public void setIdSource(String idSource)
	{
		this.idSource = idSource;
	}

	public String getIdContract()
	{
		return idContract;
	}

	public void setIdContract(String idContract)
	{
		this.idContract = idContract;
	}

	public Calendar getStartDate()
	{
		return startDate;
	}

	public void setStartDate(Calendar startDate)
	{
		this.startDate = startDate;
	}

	public Calendar getMaturityDate()
	{
		return maturityDate;
	}

	public void setMaturityDate(Calendar maturityDate)
	{
		this.maturityDate = maturityDate;
	}

	public BigDecimal getTotalAmount()
	{
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount)
	{
		this.totalAmount = totalAmount;
	}

	public Integer getTopUpLoanBlockCount()
	{
		return topUpLoanBlockCount;
	}

	public void setTopUpLoanBlockCount(Integer topUpLoanBlockCount)
	{
		this.topUpLoanBlockCount = topUpLoanBlockCount;
	}

	public BigDecimal getTotalRepaymentSum()
	{
		return totalRepaymentSum;
	}

	public void setTotalRepaymentSum(BigDecimal totalRepaymentSum)
	{
		this.totalRepaymentSum = totalRepaymentSum;
	}
}
