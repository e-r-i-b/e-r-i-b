package com.rssl.phizgate.ext.sbrf.etsm;

import java.util.Calendar;
import java.util.Date;

/**
 * Оферта, созданная в каналах, отличных от УКО
 *
 * @author EgorovaA
 * @ created 15.06.15
 * @ $Author$
 * @ $Revision$
 */
public class OfferOfficePrior extends OfferPrior
{
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
	//Кем выдан документ
	private String idIssueBy;
	//Дата выдачи
	private Calendar idIssueDate;
	// Дата рождения клиента
	private Calendar birthDate;
	// Статус оферты (ACTIVE - действующая, DELETED - после того, как клиент подтвердил оферту или отказался от нее)
	private String state;
	//Код типа кредитного продукта
	private String productTypeCode;
	//Код кредитного продукта
	private String productCode;
	//Код суб продукта
	private String subProductCode;
	//Подразделение для оформления заявки в формате: тер. банк (2 цифры), ОСБ (4 цифры), ВСП (5 цифр)
	private String department;
	//Валюта
	private String currency;
	//Cчет  вклад/Номер карты
	private String accountNumber;
	//Выдача кредита. Возможные значения: 1– на имеющийся вклад 2 – на новый вклад 3 – на имеющуюся банковскую карту 4 – на новую банковскую карту 5 – на имеющийся текущий счет 6 – на новый текущий счет
	private String typeOfIssue;
	//Адрес регистрации клиента.
	private String registrationAddress;
	// Дата сохранения оферты в ЕРИБ
	private Date offerDate;

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

	public Calendar getBirthDate()
	{
		return birthDate;
	}

	public void setBirthDate(Calendar birthDate)
	{
		this.birthDate = birthDate;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public String getProductCode()
	{
		return productCode;
	}

	public void setProductCode(String productCode)
	{
		this.productCode = productCode;
	}

	public String getSubProductCode()
	{
		return subProductCode;
	}

	public void setSubProductCode(String subProductCode)
	{
		this.subProductCode = subProductCode;
	}

	public String getDepartment()
	{
		return department;
	}

	public void setDepartment(String department)
	{
		this.department = department;
	}

	public String getCurrency()
	{
		return currency;
	}

	public void setCurrency(String currency)
	{
		this.currency = currency;
	}

	public String getProductTypeCode()
	{
		return productTypeCode;
	}

	public void setProductTypeCode(String productTypeCode)
	{
		this.productTypeCode = productTypeCode;
	}

	public String getAccountNumber()
	{
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber)
	{
		this.accountNumber = accountNumber;
	}

	public String getIdIssueBy()
	{
		return idIssueBy;
	}

	public void setIdIssueBy(String idIssueBy)
	{
		this.idIssueBy = idIssueBy;
	}

	public Calendar getIdIssueDate()
	{
		return idIssueDate;
	}

	public void setIdIssueDate(Calendar idIssueDate)
	{
		this.idIssueDate = idIssueDate;
	}

	public String getTypeOfIssue()
	{
		return typeOfIssue;
	}

	public void setTypeOfIssue(String typeOfIssue)
	{
		this.typeOfIssue = typeOfIssue;
	}

	public String getRegistrationAddress()
	{
		return registrationAddress;
	}

	public void setRegistrationAddress(String registrationAddress)
	{
		this.registrationAddress = registrationAddress;
	}

	public Date getOfferDate()
	{
		return offerDate;
	}

	public void setOfferDate(Date offerDate)
	{
		this.offerDate = offerDate;
	}
}
