package com.rssl.phizic.business.dictionaries.cellNumberArray;

import com.rssl.phizic.gate.dictionaries.DictionaryRecordBase;

import java.util.Calendar;

/**
 * �������� ������� �� DEF-�����
 * @author shapin
 * @ created 02.04.15
 * @ $Author$
 * @ $Revision$
 */
public class NumberArray extends DictionaryRecordBase
{
	private Long id; //�������������
	private long numberFrom; // ������ ��������� ���������  (�� ����� �����)
	private long numberTo; // ����� ��������� ���������  (�� ����� �����)
	private String ownerId; // �������� ������������ �Ζ��������� ������� ��������� DEF-����� (�� ����� �����)
	private String orgName; // ����������� ������������ �� (�� ����� �����)
	private String mnc; // ��� ���� ��
	private String regionCode; // ��� ������� ������
	private String serviceId; //  �������� ��� ������� (��������) ���������� � �� SmartVista ��� ����������� ��������
	private String serviceCode; // ���������� ��� ������� (��������) ���������� � �� SmartVista ��� ����������� ��������
	private String cashServiceId; // ��� ������� (��������) ���������� � �� SmartVista ��� �������� ��������
	private String mbOperatorId; // ��� �� � �� ���������� ����
	private String mbOperatorNumber; // ����� ���������� ��������� � ���
	private String providerId; // ��� �� � �� ������������

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public long getNumberFrom()
	{
		return numberFrom;
	}

	public void setNumberFrom(long numberFrom)
	{
		this.numberFrom = numberFrom;
	}

	public long getNumberTo()
	{
		return numberTo;
	}

	public void setNumberTo(long numberTo)
	{
		this.numberTo = numberTo;
	}

	public String getOwnerId()
	{
		return ownerId;
	}

	public void setOwnerId(String ownerId)
	{
		this.ownerId = ownerId;
	}

	public String getOrgName()
	{
		return orgName;
	}

	public void setOrgName(String orgName)
	{
		this.orgName = orgName;
	}

	public String getMnc()
	{
		return mnc;
	}

	public void setMnc(String mnc)
	{
		this.mnc = mnc;
	}

	public String getRegionCode()
	{
		return regionCode;
	}

	public void setRegionCode(String regionCode)
	{
		this.regionCode = regionCode;
	}

	public String getServiceId()
	{
		return serviceId;
	}

	public void setServiceId(String serviceId)
	{
		this.serviceId = serviceId;
	}

	public String getServiceCode()
	{
		return serviceCode;
	}

	public void setServiceCode(String serviceCode)
	{
		this.serviceCode = serviceCode;
	}

	public String getCashServiceId()
	{
		return cashServiceId;
	}

	public void setCashServiceId(String cashServiceId)
	{
		this.cashServiceId = cashServiceId;
	}

	public String getMbOperatorId()
	{
		return mbOperatorId;
	}

	public void setMbOperatorId(String mbOperatorId)
	{
		this.mbOperatorId = mbOperatorId;
	}

	public String getMbOperatorNumber()
	{
		return mbOperatorNumber;
	}

	public void setMbOperatorNumber(String mbOperatorNumber)
	{
		this.mbOperatorNumber = mbOperatorNumber;
	}

	public String getProviderId()
	{
		return providerId;
	}

	public void setProviderId(String providerId)
	{
		this.providerId = providerId;
	}

	public Comparable getSynchKey()
	{
		return numberFrom + " " + numberTo + " " + ownerId + " " + mnc + " " + regionCode;
	}
}
