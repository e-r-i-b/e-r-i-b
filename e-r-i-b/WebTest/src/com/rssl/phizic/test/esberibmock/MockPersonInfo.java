package com.rssl.phizic.test.esberibmock;

import java.util.Calendar;

/**
 * Клиент (для заглушки)
 * User: Egorovaa
 * Date: 06.12.2011
 * Time: 9:21:25
 */

public class MockPersonInfo
{
	private Long id;
	/**
	 * Идентификатор потребителя
	 */
	private String custId;
	/**
	 * Дата рождения
	 */
	private Calendar birthday;
	/**
	 * Место рождения
	 */
	private String birthPlace;
	/**
	 * ИНН
	 */
	private String taxId;
	/**
	 * Гражданство
	 */
	private String citizenship;
	/**
	 * Дополнительная информация.
	 */
	private String additionalInfo;
	/**
	 * Фамилия
	 */
	private String lastName;
	/**
	 * Имя
	 */
	private String firstName;
	/**
	 * Отчество
	 */
	private String middleName;
	/**
	 * Тип документа
	 */
	private String idType;
	/**
	 * Серия документа
	 */
	private String idSeries;
	/**
	 * Номер документа. Для паспорта way номер документа передается в этом в том виде, как пришло из ЦСА.
	 */
	private String idNum;
	/**
	 * Кем выдан документ
	 */
	private String issuedBy;
	/**
	 * Код подразделения, выдавшего документ
	 */
	private String issuedCode;
	/**
	 * Дата выдачи
	 */
	private Calendar issueDt;
	/**
	 * Действителен до
	 */
	private Calendar expDt;
	/**
	 * Адрес электронной почты
	 */
	private String emailAddr;
	/**
	 * Отправляется ли отчет на Email? E – да, N – нет
	 */
	private String messageDeliveryType;

	public String getMessageDeliveryType()
	{
		return messageDeliveryType;
	}

	public void setMessageDeliveryType(String messageDeliveryType)
	{
		this.messageDeliveryType = messageDeliveryType;
	}

	public String getEmailAddr()
	{
		return emailAddr;
	}

	public void setEmailAddr(String emailAddr)
	{
		this.emailAddr = emailAddr;
	}

	public String getAdditionalInfo()
	{
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo)
	{
		this.additionalInfo = additionalInfo;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getCustId()
	{
		return custId;
	}

	public void setCustId(String custId)
	{
		this.custId = custId;
	}

	public Calendar getBirthday()
	{
		return birthday;
	}

	public void setBirthday(Calendar birthday)
	{
		this.birthday = birthday;
	}

	public String getBirthPlace()
	{
		return birthPlace;
	}

	public void setBirthPlace(String birthPlace)
	{
		this.birthPlace = birthPlace;
	}

	public String getTaxId()
	{
		return taxId;
	}

	public void setTaxId(String taxId)
	{
		this.taxId = taxId;
	}

	public String getCitizenship()
	{
		return citizenship;
	}

	public void setCitizenship(String citizenship)
	{
		this.citizenship = citizenship;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
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

	public String getIssuedCode()
	{
		return issuedCode;
	}

	public void setIssuedCode(String issuedCode)
	{
		this.issuedCode = issuedCode;
	}

	public Calendar getIssueDt()
	{
		return issueDt;
	}

	public void setIssueDt(Calendar issueDt)
	{
		this.issueDt = issueDt;
	}

	public Calendar getExpDt()
	{
		return expDt;
	}

	public void setExpDt(Calendar expDt)
	{
		this.expDt = expDt;
	}
}
