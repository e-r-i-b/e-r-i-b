package com.rssl.phizic.messaging.push;

import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import com.rssl.phizic.common.types.transmiters.Triplet;
import com.rssl.phizic.logging.push.PushDeviceStatus;
import com.rssl.phizic.person.ClientData;

import java.util.Collection;
import java.util.List;

/**
 * @author basharin
 * @ created 07.08.13
 * @ $Author$
 * @ $Revision$
 */

public interface PushTransportService
{
	/**
	 * ��������� push-����������� ����������� ������������ �������
	 * @param clientData - ������ ������������
	 * @param pushMessage - ���������� �� ���������
	 * @param phones - ������ ������� ��� �������� sms-��������� � ������ �������������
	 * @param listTriplets - ������ id �������� �������, ��������������� �� guid � ��������
	 */
	public void sendPush(ClientData clientData, PushMessage pushMessage, Collection<String> phones, List<Triplet<String, String, String>> listTriplets) throws IKFLMessagingException;

	/**
	 * ��������� ���������� � ����������� ��� ����������� ������������ �������
	 * @param deviceId - ������������� ����������, ������������ ��� ��������� push-�����������
	 * @param �lientData - ������ ������������
	 * @param pushDeviceStatus - ������ ����������� push ����������� � ����������
	 * @param securityToken - ������ ������ ������������, �������������� ��������� �����������
	 * @param mguid - mguid
	 */
	public void registerEvent(String deviceId, ClientData �lientData, PushDeviceStatus pushDeviceStatus, String securityToken, String mguid) throws IKFLMessagingException;

	/**
	 * @return ������ ��������� � ����������� �������� Push
	 */
	public List<PushRemoveDevice> getRemoveDevices() throws IKFLMessagingException;

	/**
	 * �������� ������ �� ���������� � ����������� �������� Push � ������� ����������� ������������ �������
	 * @param device - ����������
	 */
	public void updateRemoveDeviceState(final PushRemoveDevice device) throws IKFLMessagingException;
}
