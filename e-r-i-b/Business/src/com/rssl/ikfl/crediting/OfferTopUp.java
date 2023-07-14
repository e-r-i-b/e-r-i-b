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

	// Номер кредитного договора
	private String agreementNumber;

	// АС СД: Номер ссудного счёта
	private String loanAccountNumber;

	// Валюта кредита
	private String currency;

	// ИД источника формирования кредитных историй
	private String idSource;

	// ИД Договора
	private String idContract;

	// Дата выдачи кредита
	private Calendar startDate;

	// Плановая дата погашения кредита
	private Calendar maturityDate;

	// Сумма кредита (начальная)
	private BigDecimal totalAmount;

	//Количество блоков погашаемых договоров
	private Integer topUpLoanBlockCount;

	//Сумма полного досрочного погашения
	private BigDecimal totalRepaymentSum;

	// Связанное предложение
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
