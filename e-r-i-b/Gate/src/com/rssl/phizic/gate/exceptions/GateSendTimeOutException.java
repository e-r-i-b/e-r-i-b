package com.rssl.phizic.gate.exceptions;

/**
 * ���������� ��� ����������� �������� ��� �������� ���������(send())
 * @author vagin
 * @ created 27.03.14
 * @ $Author$
 * @ $Revision$
 */
public class GateSendTimeOutException extends GateTimeOutException
{
	public GateSendTimeOutException(Throwable cause)
	{
		super(cause);
	}
}
