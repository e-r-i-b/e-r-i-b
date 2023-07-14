package com.rssl.phizic.gate.payments.systems.recipients;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * �������� ������ BackRefReceiverInfoService ������������� ���������� � ���������� �������,
 * ���������� � �.�.
 *
 * @author khudyakov
 * @ created 20.03.2011
 * @ $Author$
 * @ $Revision$
 */
public interface BackRefReceiverInfoService extends Service
{
	/**
	 * ������� ���������� � ���������� ������� �� �.�.
	 * @param pointCode   ��� ���������� �����
	 * @param serviceCode ��� ������ � ����������� �������
	 * @return ���������� � ���������� �����
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public BusinessRecipientInfo getRecipientInfo(String pointCode, String serviceCode) throws GateException, GateLogicException;
}
