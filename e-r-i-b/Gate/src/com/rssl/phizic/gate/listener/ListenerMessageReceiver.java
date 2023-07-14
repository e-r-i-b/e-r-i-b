package com.rssl.phizic.gate.listener;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * @author Roshka
 * @ created 18.10.2006
 * @ $Author$
 * @ $Revision$
 */

public interface ListenerMessageReceiver extends Service
{
	/**
	 * ���� � ��������� �������� ������� ������.
	 * @param message xml-������
	 * @return xml-������
	 */
	String handleMessage(String message) throws GateException;
}
