package com.rssl.phizic.business.dictionaries.security;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.depo.SecurityBase;
import com.rssl.phizic.gate.dictionaries.DictionaryRecordBase;

/**
 * @author mihaylov
 * @ created 07.09.2010
 * @ $Author$
 * @ $Revision$
 */

public class Security extends DictionaryRecordBase implements SecurityBase
{
	private Long id;
	private String issuer; // ��������
	private String name;   //  ������������ ��
	private String registrationNumber; // ��������������� ����� ��
	private String type;    // ��� ��
	private String insideCode;  // ������������ ��� ������� ��
	private Money nominal;     //  ����������� ������� ������� ��
	private boolean isDelete; //������� ��������/�� �������� �� (true ���� �� ��������)

	public Comparable getSynchKey()
	{
		return registrationNumber;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getIssuer()
	{
		return issuer;
	}

	public void setIssuer(String issuer)
	{
		this.issuer = issuer;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getRegistrationNumber()
	{
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber)
	{
		this.registrationNumber = registrationNumber;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getInsideCode()
	{
		return insideCode;
	}

	public void setInsideCode(String insideCode)
	{
		this.insideCode = insideCode;
	}

	public Money getNominal()
	{
		return nominal;
	}

	public void setNominal(Money nominal)
	{
		this.nominal = nominal;
	}
	public boolean getIsDelete()
	{
		return isDelete;
	}

	public void setIsDelete(boolean delete)
	{
		isDelete = delete;
	}
}
