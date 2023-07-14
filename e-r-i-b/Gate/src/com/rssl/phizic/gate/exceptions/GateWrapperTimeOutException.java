package com.rssl.phizic.gate.exceptions;

/**
 * @author gladishev
 * @ created 11.10.2012
 * @ $Author$
 * @ $Revision$
 * ���������� �������� �� �����
 */
public class GateWrapperTimeOutException extends GateTimeOutException
{
	public static final String TIMEOUT_WRAPPER_DOCUMENT_STATE_DESCRIPTION = "������� ������� �������� ������ �� �����";
	public static final String GATE_WRAPPER_TIMEOUT_DEFAULT_MESSAGE = "�������� �������� ����������. ��������� ������� �����.";
	public static final String GATE_TECHNICAL_EXCEPTION_TIMEOUT_MESSAGE = "����������� ������ ���";

	public GateWrapperTimeOutException(String message)
	{
		super(message);
	}

	public GateWrapperTimeOutException(Throwable cause)
	{
		super(cause.getMessage(), cause);
	}

	public GateWrapperTimeOutException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
