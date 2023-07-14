package com.rssl.phizic.test.esberibmock;

import java.util.Calendar;

/**
 * ��� (��� ��������)
 @author: Egorovaa
 @ created: 24.02.2012
 @ $Author$
 @ $Revision$
 */
public class MockIMAccount
{
	private Long id;
	/**
	 * ����� �������(���)
	 */
	private String branchId;
	/**
	 * ����� �������������(���)
	 */
	private String agencyId;
	/**
	 * ����� ��������(��) 
	 */
	private String regionId;
	/**
	 * ����� ���, �������� ���� ���� 
	 */
	private String rbBrchId;
	/**
	 * ������������� �������-��������� ��������
	 */
	private String systemId;
	/**
	 * ����� �����
	 */
	private String acctId;
	/**
	 * ��� ������� 
	 */
	private String acctCur;
	/**
	 * ������� ������������ �� ������� �����, ������� ��� ����������� ������������
	 */
	private String acctName;
	/**
	 * ���� �������� �����
	 */
	private Calendar startDate;
	/**
	 * ���� �������� �����. ����������� ��� �������� ������.
	 */
	private Calendar endDate;
	/**
	 * ������ ��� (Opened � ������, Closed � ������)
	 */
	private String status;
	/**
	 * ����� �������� ����� ���
	 */
	private String agreementNumber;
	/**
	 * ������ � ������� �� ������� � ������ ��������.
	 * ������ ���� Avail - [�������], AvailCash - [����� ��������]
	 * ������� � ������� (�������� ������� ����� ������ ���� ������� � ��������� �� ������� �����)
	 */
	private String acctBal;
	/**
	 * ���������� � ���������
	 */
	private MockPersonInfo personInfo;

	public MockPersonInfo getPersonInfo()
	{
		return personInfo;
	}

	public void setPersonInfo(MockPersonInfo personInfo)
	{
		this.personInfo = personInfo;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getBranchId()
	{
		return branchId;
	}

	public void setBranchId(String branchId)
	{
		this.branchId = branchId;
	}

	public String getAgencyId()
	{
		return agencyId;
	}

	public void setAgencyId(String agencyId)
	{
		this.agencyId = agencyId;
	}

	public String getRegionId()
	{
		return regionId;
	}

	public void setRegionId(String regionId)
	{
		this.regionId = regionId;
	}

	public String getRbBrchId()
	{
		return rbBrchId;
	}

	public void setRbBrchId(String rbBrchId)
	{
		this.rbBrchId = rbBrchId;
	}

	public String getSystemId()
	{
		return systemId;
	}

	public void setSystemId(String systemId)
	{
		this.systemId = systemId;
	}

	public String getAcctId()
	{
		return acctId;
	}

	public void setAcctId(String acctId)
	{
		this.acctId = acctId;
	}

	public String getAcctCur()
	{
		return acctCur;
	}

	public void setAcctCur(String acctCur)
	{
		this.acctCur = acctCur;
	}

	public String getAcctName()
	{
		return acctName;
	}

	public void setAcctName(String acctName)
	{
		this.acctName = acctName;
	}

	public Calendar getStartDate()
	{
		return startDate;
	}

	public void setStartDate(Calendar startDate)
	{
		this.startDate = startDate;
	}

	public Calendar getEndDate()
	{
		return endDate;
	}

	public void setEndDate(Calendar endDate)
	{
		this.endDate = endDate;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getAgreementNumber()
	{
		return agreementNumber;
	}

	public void setAgreementNumber(String agreementNumber)
	{
		this.agreementNumber = agreementNumber;
	}

	public String getAcctBal()
	{
		return acctBal;
	}

	public void setAcctBal(String acctBal)
	{
		this.acctBal = acctBal;
	}
}
