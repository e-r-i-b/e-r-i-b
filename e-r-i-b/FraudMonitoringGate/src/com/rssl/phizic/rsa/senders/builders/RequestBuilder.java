package com.rssl.phizic.rsa.senders.builders;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.integration.ws.control.generated.GenericRequest;

/**
 * ������ �������� �� �� ��
 *
 * @author khudyakov
 * @ created 14.06.15
 * @ $Author$
 * @ $Revision$
 */
public interface RequestBuilder<RQ>
{
	/**
	 * ��������� ������ �� ����-����������
	 * @return ������
	 */
	RQ build() throws GateLogicException, GateException;
}
