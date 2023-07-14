package com.rssl.phizicgate.sbrf.ws.depocod;

import com.rssl.phizgate.common.messaging.ByteArrayMessageData;

/**
 * ������ ������ �� ������ � DepoCOD
 * @author gladishev
 * @ created 06.06.2014
 * @ $Author$
 * @ $Revision$
 */

public class DepoCODResponseMessageData extends ByteArrayMessageData
{
	private String messageId;

	/**
	 * ctor
	 * @param body - ���� ������
	 * @param messageId - ������������� ���������
	 */
	public DepoCODResponseMessageData(byte[] body, String messageId)
	{
		super(body);
		this.messageId = messageId;
	}

	/**
	 * @return ������������� ���������
	 */
	public String getMessageId()
	{
		return messageId;
	}
}
