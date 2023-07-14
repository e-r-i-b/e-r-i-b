package com.rssl.phizic.gorod.messaging;

import com.rssl.phizic.gate.messaging.GateMessagingClientException;
import com.rssl.phizic.gate.messaging.impl.DefaultErrorMessageHandler;

/**
 * @author Gainanov
 * @ created 18.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class GorodErrorMessageHandler extends DefaultErrorMessageHandler
{
	protected static final String CODE_TAG    = "errCode";
	protected static final String MESSAGE_TAG = "errDesc";

	protected String getCodeTag()
	{
		return CODE_TAG;
	}

	protected String getMessageTag()
	{
		return MESSAGE_TAG;
	}

	public void throwException() throws GateMessagingClientException
	{
		throw new GateMessagingClientException("���������� ��������� ������. ��������� ������������ ���������� ����������");
	}
	public void throwCardNotFoundException() throws GateMessagingClientException
	{
		throw new GateMessagingClientException(Constants.LOGIC_MESSAGE_PREFIX +"����� �� �������"+Constants.LOGIC_MESSAGE_SUFFIX);
	}
}
