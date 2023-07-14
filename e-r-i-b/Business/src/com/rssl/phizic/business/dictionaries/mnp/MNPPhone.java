package com.rssl.phizic.business.dictionaries.mnp;

import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.dictionaries.DictionaryRecordBase;

import java.util.Calendar;

/**
 * �������� - �������, ��� �������� ������� ��������� ������� �����
 * @author Rtischeva
 * @ created 17.04.15
 * @ $Author$
 * @ $Revision$
 */
public class MNPPhone extends DictionaryRecordBase
{
	private Long id;  //������������� ������

	private String phoneNumber; //�������, ��� �������� �������� ���

	private String providerCode; //��� ���������� (���� CODE)

	private Calendar movingDate; //���� ����� ���

	private String sourceFileName; //�������� ����� - �����������

	private Long mnc; //���������� ��� ��������� ������� �����

	public Comparable getSynchKey()
	{
		return phoneNumber;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public String getProviderCode()
	{
		return providerCode;
	}

	public void setProviderCode(String providerCode)
	{
		this.providerCode = providerCode;
	}

	public Calendar getMovingDate()
	{
		return movingDate;
	}

	public void setMovingDate(Calendar movingDate)
	{
		this.movingDate = movingDate;
	}

	public String getSourceFileName()
	{
		return sourceFileName;
	}

	public void setSourceFileName(String sourceFileName)
	{
		this.sourceFileName = sourceFileName;
	}

	public Long getMnc()
	{
		return mnc;
	}

	public void setMnc(Long mnc)
	{
		this.mnc = mnc;
	}

	public void updateFrom(DictionaryRecord that)
	{
		((MNPPhone) that).setId(getId());
		super.updateFrom(that);
	}
}
