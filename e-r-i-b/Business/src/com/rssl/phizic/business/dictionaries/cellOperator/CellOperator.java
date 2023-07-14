package com.rssl.phizic.business.dictionaries.cellOperator;

import com.rssl.phizic.gate.dictionaries.DictionaryRecordBase;

/**
 * @author shapin
 * @ created 09.04.15
 * @ $Author$
 * @ $Revision$
 */
public class CellOperator extends DictionaryRecordBase
{
	private Long id; // ������������� � ��
	private String orgName; // ����������� ������������ �� (�� ����� �����)
	private String orgCode; // ��������  ������������ ��  (�� ����� �����)
	private String mnc; // ��� ���� �� (�� ����� �����)
	private String tin; // ��� �� (�� ����� �����)
	private long mbOperatorNumber; // ��� ��������� ����� � ��
	private String flAuto; // ������� ������ ������������: �0� - ��� ������; �1� - ���� �����
	private long balance; //  ������� �� ������� ��������, ��� ������� ����������� �� ����� ����� ������ ����������� ����� ��� ��� ���������� ��� ������������ ����� �����������
	private long minSumm; // ����������� ����� ����������� (� ����� ������)
	private long maxSumm; // ������������ ����� ����������� (� ����� ������)



	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getOrgName()
	{
		return orgName;
	}

	public void setOrgName(String orgName)
	{
		this.orgName = orgName;
	}

	public String getOrgCode()
	{
		return orgCode;
	}

	public void setOrgCode(String orgCode)
	{
		this.orgCode = orgCode;
	}

	public String getMnc()
	{
		return mnc;
	}

	public void setMnc(String mnc)
	{
		this.mnc = mnc;
	}

	public String getTin()
	{
		return tin;
	}

	public void setTin(String tin)
	{
		this.tin = tin;
	}

	public long getMbOperatorNumber()
	{
		return mbOperatorNumber;
	}

	public void setMbOperatorNumber(long mbOperatorNumber)
	{
		this.mbOperatorNumber = mbOperatorNumber;
	}

	public String getFlAuto()
	{
		return flAuto;
	}

	public void setFlAuto(String flAuto)
	{
		this.flAuto = flAuto;
	}

	public long getBalance()
	{
		return balance;
	}

	public void setBalance(long balance)
	{
		this.balance = balance;
	}

	public long getMinSumm()
	{
		return minSumm;
	}

	public void setMinSumm(long minSumm)
	{
		this.minSumm = minSumm;
	}

	public long getMaxSumm()
	{
		return maxSumm;
	}

	public void setMaxSumm(long maxSumm)
	{
		this.maxSumm = maxSumm;
	}

	public Comparable getSynchKey()
	{
		return getOrgCode() + " " + getMnc();
	}
}
