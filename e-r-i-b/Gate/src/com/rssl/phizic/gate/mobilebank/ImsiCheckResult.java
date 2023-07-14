package com.rssl.phizic.gate.mobilebank;

import com.rssl.phizic.utils.StringHelper;

/**
 * ���������� �������� IMSI
 * @author gladishev
 * @ created 18.02.2012
 * @ $Author$
 * @ $Revision$
 */
public enum ImsiCheckResult
{
	//��������� ����������
	send("��������� ����������"),
	//��������� ��������� � �������� �������� � ��������� ���� �� ��������
	yet_not_send("��������� ��������� � �������� ��������"),
	//�� ������� �������� IMSI �� ���� �������� ���
	client_error("�� ������� �������� IMSI �� ���� �������� ���"),
	//IMSI �� ������
	imsi_error("IMSI �� ������"),
	//����������� ���
	UNKNOWN("����������� ���"),
	imsi_check_ok("�������� IMSI �������"),
	quarantine("�� ��������� ����� ����������"),
	blocked("������������ ����� ��������"),
	error("����������� ������"),
	imsi_ok("IMSI ������"),
	imsi_fail("IMSI �� ������ ��� ��������"),
	msg_not_found("� �� ��� ��� ���������� @iExternalSystemID + @iMessage"),
	yet_not_check("�� ��� � ��������� ����� ��������������� � MFM IMSI WS, ����� ��������� ������");

	private final String description;

	/**
	 * @return ��������
	 */
	public String getDescription()
	{
		return description;
	}

	private ImsiCheckResult(String description)
	{
		if (StringHelper.isEmpty(description))
			throw new IllegalArgumentException("Argument 'description' cannot be null");

		this.description = description;
	}
}
