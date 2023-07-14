package com.rssl.phizic.gate.payments;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * @author osminin
 * @ created 04.04.2011
 * @ $Author$
 * @ $Revision$
 * ��������� ������������ ����������
 */
public interface ReceiverNameService extends Service 
{
	/**
	 * ������������ ����������
	 * @param account ����� ����� ���������� �������
	 * @param bic ��� ���������� �������
	 * @param tb  ����� �� ���������� �������
	 * @return ������������
	 */
	String getReceiverName(String account, String bic, String tb) throws GateException, GateLogicException;
}
