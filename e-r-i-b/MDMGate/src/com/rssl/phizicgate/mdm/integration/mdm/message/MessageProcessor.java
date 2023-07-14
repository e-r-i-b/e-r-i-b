package com.rssl.phizicgate.mdm.integration.mdm.message;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.jms.MessageCreator;

import javax.jms.TextMessage;

/**
 * @author akrenev
 * @ created 14.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * ��������� jms ���������
 */

public interface MessageProcessor<MP extends MessageProcessor<MP>> extends MessageCreator<TextMessage>
{
	/**
	 * ��������� ������
	 * @return ������
	 */
	Request<MP> makeRequest() throws GateException, GateLogicException;
}
