package com.rssl.phizic.test.esberibmock;

import java.util.Calendar;

/**
 * Входные параметры для GFL
 * User: Egorovaa
 * Date: 16.11.2011
 * Time: 16:17:36
 */
public class RequestGFL
{
	private Long id;
	/**
	 * Код территориального банка, в котором открыт счет МБК, по которой клиент произвел идентификацию на УС
	 */
	private String rbTbBrchId;
	/**
	 * Дата рождения физлица
	 */
	private Calendar birthday;
	/**
	 *  Фамилия физического лица
	 */
	private String lastName;
	/**
	 * Имя физического лица
	 */
	private String firstName;
	/**
	 * Отчество физического лица
	 */
	private String middleName;
	/**
	 * Тип документа. Используется кодировка справочника pb_document ЦАС НСИ (поле code)
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
	 * Датавыдачи документа
	 */
	private Calendar issueDt;
	/**
	 * Идентификатор карточного счета. Номер карты
	 */
	private String cardNum;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getRbTbBrchId()
	{
		return rbTbBrchId;
	}

	public void setRbTbBrchId(String rbTbBrchId)
	{
		this.rbTbBrchId = rbTbBrchId;
	}

	public Calendar getBirthday()
	{
		return birthday;
	}

	public void setBirthday(Calendar birthday)
	{
		this.birthday = birthday;
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

	public Calendar getIssueDt()
	{
		return issueDt;
	}

	public void setIssueDt(Calendar issueDt)
	{
		this.issueDt = issueDt;
	}

	public String getCardNum()
	{
		return cardNum;
	}

	public void setCardNum(String cardNum)
	{
		this.cardNum = cardNum;
	}
}
