package com.rssl.phizic.gate.mfm;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * @author Nady
 * @ created 22.09.2014
 * @ $Author$
 * @ $Revision$
 */
public interface MFMService extends Service
{
	/**
	 * ����� �������� ��������� ��� �������� IMSI
	 * @param phoneNumber - ����� ��������
	 * @param smsText - ����� ���
	 * @param priority - ���������
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 */
	public void sendSMSWithoutIMSI(String phoneNumber, String smsText, Long priority) throws GateException;
}
