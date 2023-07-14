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
	// Идентификатор заявки в ETSM (уникальный ключ)
	private String applicationNumber;
	// Категория клиента
	private String clientCategory;
	// Альтернативный срок в месяцах
	private Long altPeriod;
	// Альтернативная сумма кредита в рублях;
	private BigDecimal altAmount;
	// Альтернативная ставка
	private BigDecimal altInterestRate;
	// Альтернативная полная стоимость кредита
	private BigDecimal altFullLoanCost;
	// Альтернативный аннуитетный платеж
	private BigDecimal altAnnuityPayment;
	// Альтернативный лимит по кредитной карте
	private BigDecimal altCreditCardLimit;
	// Счетчик для отображения заявки клиенту
	private Long visibilityCounter = Long.valueOf(0);
	// Обновлен ли счетчик за время сессии
	private Boolean counterUpdated = false;
	// Название кредитного продукта. Не мапим в БД, заполняем при отображении оферты
	private String loanProductName;
	//Признак приоритетного условия. Не мапим в БД, заполняем при отображении оферты
	private boolean priority;
	// UID сообщения запроса о согласии на оферт. Для OfferConfirmed не мапим
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
