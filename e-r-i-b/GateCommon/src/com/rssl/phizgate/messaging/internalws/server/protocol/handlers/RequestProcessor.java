package com.rssl.phizgate.messaging.internalws.server.protocol.handlers;

import com.rssl.phizgate.messaging.internalws.server.protocol.RequestInfo;
import com.rssl.phizgate.messaging.internalws.server.protocol.ResponseInfo;

/**
 * @author krenev
 * @ created 22.08.2012
 * @ $Author$
 * @ $Revision$
 * ���������� �������
 */

public interface RequestProcessor
{
	/**
	 * ���������� ������ � ������� ���������
	 * @param requestInfo ���������� � �������
	 * @return ���������� �� ������
	 */
	ResponseInfo process(RequestInfo requestInfo) throws Exception;
}
