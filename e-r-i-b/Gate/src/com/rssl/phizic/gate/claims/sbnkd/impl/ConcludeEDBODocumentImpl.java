package com.rssl.phizic.gate.claims.sbnkd.impl;

import com.rssl.phizic.gate.claims.sbnkd.ConcludeEDBODocument;

/**
 * @author bogdanov
 * @ created 17.12.14
 * @ $Author$
 * @ $Revision$
 */

public class ConcludeEDBODocumentImpl extends ClientInfoDocumentImpl implements ConcludeEDBODocument
{
	private String edboBranchId;
	private String edboAgencyId;
	private String edboPhone;
	private String edboPhoneOperator;
	private long edboOrderNumber;
	private String edboTb;
	private String lastLogonCardNumber;

	/**
	 * @param eDBOBranchId ����� ������� ��������� ����.
	 */
	public void setEDBOBranchId(String eDBOBranchId)
	{
		this.edboBranchId = eDBOBranchId;
	}

	/**
	 * @return ����� ������� ��������� ����.
	 */
	public String getEDBOBranchId()
	{
		return edboBranchId;
	}

	/**
	 * @param eDBOAgencyId ����� ��������� ��������� ����.
	 */
	public void setEDBOAgencyId(String eDBOAgencyId)
	{
		this.edboAgencyId = eDBOAgencyId;
	}

	/**
	 * @return ����� ��������� ��������� ����.
	 */
	public String getEDBOAgencyId()
	{
		return edboAgencyId;
	}

	/**
	 * @param eDBOPhone ����� ���������� �������� ����.
	 */
	public void setEDBOPhone(String eDBOPhone)
	{
		this.edboPhone = eDBOPhone;
	}

	/**
	 * @return ����� ���������� �������� ����.
	 */
	public String getEDBOPhone()
	{
		return edboPhone;
	}

	/**
	 * @param eDBOPhoneOperator �������� ���������� �������� ����.
	 */
	public void setEDBOPhoneOperator(String eDBOPhoneOperator)
	{
		this.edboPhoneOperator = eDBOPhoneOperator;
	}

	/**
	 * @return �������� ���������� �������� ����.
	 */
	public String getEDBOPhoneOperator()
	{
		return edboPhoneOperator;
	}

	public String getEDBO_TB()
	{
		return edboTb;
	}

	public void setEDBOOrderNumber(Long fieldNum)
	{
		this.edboOrderNumber = fieldNum;
	}

	public Long getEDBOOrderNumber()
	{
		return edboOrderNumber;
	}

	public void setEDBO_TB(String fieldData1)
	{
		this.edboTb = fieldData1;
	}

	public String getLastLogonCardNumber()
	{
		return lastLogonCardNumber;
	}

	public void setLastLogonCardNumber(String lastLogonCardNumber)
	{
		this.lastLogonCardNumber = lastLogonCardNumber;
	}
}
