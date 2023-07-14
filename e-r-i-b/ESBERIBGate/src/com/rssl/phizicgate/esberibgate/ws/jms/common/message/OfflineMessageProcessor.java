package com.rssl.phizicgate.esberibgate.ws.jms.common.message;

/**
 * @author akrenev
 * @ created 21.05.2015
 * @ $Author$
 * @ $Revision$
 *
 * ��������� ������� jms ���������
 */

public interface OfflineMessageProcessor extends MessageProcessor<OfflineMessageProcessor>
{
	/**
	 * ��������, ����������� ����� �������� �������
	 * @param request ������
	 */
	void processAfterSend(Request request);
}
