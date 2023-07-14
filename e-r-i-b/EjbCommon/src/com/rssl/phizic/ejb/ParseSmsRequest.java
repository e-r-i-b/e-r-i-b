package com.rssl.phizic.ejb;

/**
 * @author EgorovaA
 * @ created 05.04.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ��������, �������������� ��� ������� ���-������� ������������� ������
 */
public class ParseSmsRequest
{
	//����� ���-�������. ��������, ���� ������ �� ����������� �������� IMSI
	private String simpleSmsRequest;
	//��������, ���������� ���������� �� ���-������� � ��������� IMSI. ����������� ��� ������� ���-�������
	private IMSISmsRequestInfo smsRequest;

	public String getSimpleSmsRequest()
	{
		return simpleSmsRequest;
	}

	public void setSimpleSmsRequest(String simpleSmsRequest)
	{
		this.simpleSmsRequest = simpleSmsRequest;
	}

	public IMSISmsRequestInfo getSmsRequest()
	{
		return smsRequest;
	}

	public void setSmsRequest(IMSISmsRequestInfo smsRequest)
	{
		this.smsRequest = smsRequest;
	}
}
