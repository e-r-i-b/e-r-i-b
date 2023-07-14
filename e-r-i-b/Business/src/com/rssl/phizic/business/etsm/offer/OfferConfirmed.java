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
	// ������������� ������ � ����
	private Long claimId;
	// login ID ������� � ����
	private Long clientLoginId;
	// ������������� ������� ������
	private Long templateId;
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
	// ����� ������ ���
	private String issuedBy;
	// ���� ������ ���
	private Calendar issueDt;
	// ���� ��� ������ �������, ��������� �������� ��� ���������� ������
	private String accountNumber;
	// ������ (���)
	private String borrower;
	// ����� �����������
	private String registrationAddress;
	// ���� ������������� ������ � ����
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
