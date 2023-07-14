package com.rssl.phizicgate.sbrf.ws.listener;

import com.rssl.phizic.gate.documents.AbstractAccountTransfer;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * @author gladishev
 * @ created 22.10.2013
 * @ $Author$
 * @ $Revision$
 */
public interface ConfirmOfflineRequestHandler extends OfflineRequestHandler
{
	/**
	 * ��������� �������� �������
	 * @param document - ���������
	 * @param messageText - ������ � �������
	 */
	void fillPaymentData(AbstractAccountTransfer document, String messageText) throws GateException;
}
