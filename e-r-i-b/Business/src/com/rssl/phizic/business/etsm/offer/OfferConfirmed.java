package com.rssl.phizic.business.etsm.offer;

import com.rssl.phizgate.ext.sbrf.etsm.OfferPrior;

import java.util.Calendar;

/**
 * @author EgorovaA
 * @ created 15.06.15
 * @ $Author$
 * @ $Revision$
 */
public class OfferConfirmed extends OfferPrior
{
	private Long id;
	// Идентификатор заявки в ЕРИБ
	private Long claimId;
	// login ID клиента в ЕРИБ
	private Long clientLoginId;
	// Идентификатор шаблона оферты
	private Long templateId;
	// Имя клиента
	private String firstName;
	// Фамилия клиента
	private String lastName;
	// Отчество клиента
	private String middleName;
	// Тип ДУЛ
	private String idType;
	// Серия ДУЛ
	private String idSeries;
	// Номер ДУЛ
	private String idNum;
	// Место выдачи ДУЛ
	private String issuedBy;
	// Дата выдачи ДУЛ
	private Calendar issueDt;
	// Счёт для выдачи кредита, выбранный клиентом при заполнении оферты
	private String accountNumber;
	// Заёмщик (ФИО)
	private String borrower;
	// Адрес регистрации
	private String registrationAddress;
	// Дата подтверждения оферты в ЕРИБ
	private Calendar offerDate;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getClaimId()
	{
		return claimId;
	}

	public void setClaimId(Long claimId)
	{
		this.claimId = claimId;
	}

	public Long getClientLoginId()
	{
		return clientLoginId;
	}

	public void setClientLoginId(Long clientLoginId)
	{
		this.clientLoginId = clientLoginId;
	}

	public Long getTemplateId()
	{
		return templateId;
	}

	public void setTemplateId(Long templateId)
	{
		this.templateId = templateId;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getMiddleName()
	{
		return middleName;
	}

	public void setMiddleName(String middleName)
	{
		this.middleName = middleName;
	}

	public String getIdType()
	{
		return idType;
	}

	public void setIdType(String idType)
	{
		this.idType = idType;
	}

	public String getIdSeries()
	{
		return idSeries;
	}

	public void setIdSeries(String idSeries)
	{
		this.idSeries = idSeries;
	}

	public String getIdNum()
	{
		return idNum;
	}

	public void setIdNum(String idNum)
	{
		this.idNum = idNum;
	}

	public String getIssuedBy()
	{
		return issuedBy;
	}

	public void setIssuedBy(String issuedBy)
	{
		this.issuedBy = issuedBy;
	}

	public Calendar getIssueDt()
	{
		return issueDt;
	}

	public void setIssueDt(Calendar issueDt)
	{
		this.issueDt = issueDt;
	}

	public String getAccountNumber()
	{
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber)
	{
		this.accountNumber = accountNumber;
	}

	public String getBorrower()
	{
		return borrower;
	}

	public void setBorrower(String borrower)
	{
		this.borrower = borrower;
	}

	public String getRegistrationAddress()
	{
		return registrationAddress;
	}

	public void setRegistrationAddress(String registrationAddress)
	{
		this.registrationAddress = registrationAddress;
	}

	public Calendar getOfferDate()
	{
		return offerDate;
	}

	public void setOfferDate(Calendar offerDate)
	{
		this.offerDate = offerDate;
	}
}
