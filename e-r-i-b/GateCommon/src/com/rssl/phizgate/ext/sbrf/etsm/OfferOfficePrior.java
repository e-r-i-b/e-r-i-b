package com.rssl.phizgate.ext.sbrf.etsm;

import java.util.Calendar;
import java.util.Date;

/**
 * ������, ��������� � �������, �������� �� ���
 *
 * @author EgorovaA
 * @ created 15.06.15
 * @ $Author$
 * @ $Revision$
 */
public class OfferOfficePrior extends OfferPrior
{
	// ��� �������
	private String firstName;
	// ������� �������
	private String lastName;
	// �������� �������
	private String middleName;
	// ��� ���
	private String idType;
	// ����� ���
	private String idSeries;
	// ����� ���
	private String idNum;
	//��� ����� ��������
	private String idIssueBy;
	//���� ������
	private Calendar idIssueDate;
	// ���� �������� �������
	private Calendar birthDate;
	// ������ ������ (ACTIVE - �����������, DELETED - ����� ����, ��� ������ ���������� ������ ��� ��������� �� ���)
	private String state;
	//��� ���� ���������� ��������
	private String productTypeCode;
	//��� ���������� ��������
	private String productCode;
	//��� ��� ��������
	private String subProductCode;
	//������������� ��� ���������� ������ � �������: ���. ���� (2 �����), ��� (4 �����), ��� (5 ����)
	private String department;
	//������
	private String currency;
	//C���  �����/����� �����
	private String accountNumber;
	//������ �������. ��������� ��������: 1� �� ��������� ����� 2 � �� ����� ����� 3 � �� ��������� ���������� ����� 4 � �� ����� ���������� ����� 5 � �� ��������� ������� ���� 6 � �� ����� ������� ����
	private String typeOfIssue;
	//����� ����������� �������.
	private String registrationAddress;
	// ���� ���������� ������ � ����
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
