package com.rssl.phizic.messaging.ext.sbrf;

import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;

/**
 * ��������� ������� ������� ���
 * @author Pankin
 * @ created 21.11.2011
 * @ $Author$
 * @ $Revision$
 */

public class SendMessageResult
{
	private boolean sended;
	private IKFLMessagingException exception;

	/**
	 * �����������
	 * @param sended ���������� �� ���������
	 * @param exception ����������
	 */
	public SendMessageResult(boolean sended, IKFLMessagingException exception)
	{
		this.sended = sended;
		this.exception = exception;
	}

	/**
	 * ���������� �� ���������
	 * @return true - ����������
	 */
	public boolean isSended()
	{
		return sended;
	}

	public void setSended(boolean sended)
	{
		this.sended = sended;
	}

	/**
	 * @return ������� ������
	 */
	public IKFLMessagingException getException()
	{
		return exception;
	}

	public void setException(IKFLMessagingException exception)
	{
		this.exception = exception;
	}
}
