package com.rssl.phizic.ejb;

/**
 * @author EgorovaA
 * @ created 05.04.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ��������, ���������� ���������� �� ���-������� � ��������� IMSI. ����������� ��� ������� ���-�������
 */
public class IMSISmsRequestInfo
{
	//����� ��������, �� ������� ������������ ���
	private String phone;
	//UID �������
	private String rqUid;

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getRqUid()
	{
		return rqUid;
	}

	public void setRqUid(String rqUid)
	{
		this.rqUid = rqUid;
	}
}
