package com.rssl.phizic.test.esberibmock;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * ������ (��� ��������)
 @author: Egorovaa
 @ created: 06.03.2012
 @ $Author$
 @ $Revision$
 */
public class MockCredit
{
	private Long id;
	/**
	 * ������������� �������-��������� ��������
	 */
	private String systemId;
	/**
	 * ����� �������� �����
	 */
	private String acctId;
	/**
	 * ����� ���������� ��������
	 */
	private String agreemtNum;
	/**
	 * ������������� ����������� ��������
	 */
	private String prodType;
	/**
	 * ������� �������� �������. ����� �� ������� ����� ������� ��� ����������� �������.
	 */
	private String loanType;
	/**
	 * ������
	 */
	private String acctCur;
	/**
	 * ����� �� ��������
	 */
	private BigDecimal origAmt;
	/**
	 * ����� �������, � ������� ������� �������
	 */
	private String branchId;
	/**
	 * ����� ���, ��� ������� ������ 
	 */
	private String agencyId;
	/**
	 * ����� ��������(��) 
	 */
	private String regionId;
	/**
	 * ����� ���, ��� ������� ������� ����
	 */
	private String rbBrchId;
	/**
	 * ������� ������������� (True-�����������, false � ������������������)
	 */
	private Boolean ann;
	/**
	 * ���� ���������� ��������
	 */
	private Calendar startDt;
	/**
	 * ���� ���������/�������� ��������
	 */
	private Calendar expDt;
	/**
	 * ���� ������� � �������� (1 � �������, ���������, 2 � ����������, ������������)
	 */
	private Long personRole;
	/**
	 * ���������� � ���������� ����
	 */
	private MockPersonInfo personInfo;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
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

	public String getAgreemtNum()
	{
		return agreemtNum;
	}

	public void setAgreemtNum(String agreemtNum)
	{
		this.agreemtNum = agreemtNum;
	}

	public String getProdType()
	{
		return prodType;
	}

	public void setProdType(String prodType)
	{
		this.prodType = prodType;
	}

	public String getLoanType()
	{
		return loanType;
	}

	public void setLoanType(String loanType)
	{
		this.loanType = loanType;
	}

	public String getAcctCur()
	{
		return acctCur;
	}

	public void setAcctCur(String acctCur)
	{
		this.acctCur = acctCur;
	}

	public BigDecimal getOrigAmt()
	{
		return origAmt;
	}

	public void setOrigAmt(BigDecimal origAmt)
	{
		this.origAmt = origAmt;
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

	public Boolean isAnn()
	{
		return ann;
	}

	public void setAnn(Boolean ann)
	{
		this.ann = ann;
	}

	public Calendar getStartDt()
	{
		return startDt;
	}

	public void setStartDt(Calendar startDt)
	{
		this.startDt = startDt;
	}

	public Calendar getExpDt()
	{
		return expDt;
	}

	public void setExpDt(Calendar expDt)
	{
		this.expDt = expDt;
	}

	public Long getPersonRole()
	{
		return personRole;
	}

	public void setPersonRole(Long personRole)
	{
		this.personRole = personRole;
	}

	public MockPersonInfo getPersonInfo()
	{
		return personInfo;
	}

	public void setPersonInfo(MockPersonInfo personInfo)
	{
		this.personInfo = personInfo;
	}
}
