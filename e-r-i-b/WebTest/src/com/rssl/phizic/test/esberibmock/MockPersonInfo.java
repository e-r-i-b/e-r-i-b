package com.rssl.phizic.test.esberibmock;

import java.util.Calendar;

/**
 * ������ (��� ��������)
 * User: Egorovaa
 * Date: 06.12.2011
 * Time: 9:21:25
 */

public class MockPersonInfo
{
	private Long id;
	/**
	 * ������������� �����������
	 */
	private String custId;
	/**
	 * ���� ��������
	 */
	private Calendar birthday;
	/**
	 * ����� ��������
	 */
	private String birthPlace;
	/**
	 * ���
	 */
	private String taxId;
	/**
	 * �����������
	 */
	private String citizenship;
	/**
	 * �������������� ����������.
	 */
	private String additionalInfo;
	/**
	 * �������
	 */
	private String lastName;
	/**
	 * ���
	 */
	private String firstName;
	/**
	 * ��������
	 */
	private String middleName;
	/**
	 * ��� ���������
	 */
	private String idType;
	/**
	 * ����� ���������
	 */
	private String idSeries;
	/**
	 * ����� ���������. ��� �������� way ����� ��������� ���������� � ���� � ��� ����, ��� ������ �� ���.
	 */
	private String idNum;
	/**
	 * ��� ����� ��������
	 */
	private String issuedBy;
	/**
	 * ��� �������������, ��������� ��������
	 */
	private String issuedCode;
	/**
	 * ���� ������
	 */
	private Calendar issueDt;
	/**
	 * ������������ ��
	 */
	private Calendar expDt;
	/**
	 * ����� ����������� �����
	 */
	private String emailAddr;
	/**
	 * ������������ �� ����� �� Email? E � ��, N � ���
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
