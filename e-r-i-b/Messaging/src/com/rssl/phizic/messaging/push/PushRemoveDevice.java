package com.rssl.phizic.messaging.push;

/**
 * ��������� ���������, ��� ������� ����������� Push-�����������
 * @author miklyaev
 * @ created 24.10.13
 * @ $Author$
 * @ $Revision$
 */

public interface PushRemoveDevice
{
	/**
	 * @return ������������� ����������, ������������ ��� ��������� push-�����������
	 */
	public String getDeviceId();

	/**
	 * ������������� ������������� ����������, ������������ ��� ��������� push-�����������
	 * @param deviceId - ������������� ����������
	 */
	public void setDeviceId(String deviceId);
}
