package com.rssl.phizic.messaging.ermb.jms;

/**
 * @author EgorovaA
 * @ created 04.04.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ��������, ���������� ���������� � �������� IMSI
 */
public class ErmbCheckIMSIResult
{
	// UID �������
	private String smsRqUid;
	// ��������� ��������
	private Boolean result;
	// ����� ��������
	private String phone;

	public String getSmsRqUid()
	{
		return smsRqUid;
	}

	public void setSmsRqUid(String smsRqUid)
	{
		this.smsRqUid = smsRqUid;
	}

	public Boolean getResult()
	{
		return result;
	}

	public void setResult(Boolean result)
	{
		this.result = result;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}
}