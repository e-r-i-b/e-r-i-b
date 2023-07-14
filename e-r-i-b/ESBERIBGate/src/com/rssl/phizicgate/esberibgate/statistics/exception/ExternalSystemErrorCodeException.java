package com.rssl.phizicgate.esberibgate.statistics.exception;

import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.esberibgate.ws.generated.Status_Type;

/**
 * @author akrenev
 * @ created 22.11.13
 * @ $Author$
 * @ $Revision$
 *
 * ���������� ������ ��������� ������� �� ������� �������
 */

public class ExternalSystemErrorCodeException extends GateLogicException
{
	private final Status_Type status;
	private final Class messageType;
	@SuppressWarnings("NonFinalFieldOfException")
	private String errorMessage;

	/**
	 * �����������
	 * @param status ������ ������
	 * @param messageType ��� ������
	 * @param defaultMessage ���������
	 */
	public ExternalSystemErrorCodeException(Status_Type status, Class messageType, String defaultMessage)
	{
		this.status = status;
		this.messageType = messageType;
		errorMessage = defaultMessage;
	}

	/**
	 * ������ ����� ���������
	 * @param errorMessage ����� ���������
	 */
	public void setErrorMessage(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}

	/**
	 * @return ������ ������
	 */
	public Status_Type getStatus()
	{
		return status;
	}

	/**
	 * @return ��� ������
	 */
	public Class getMessageType()
	{
		return messageType;
	}

	@Override
	public String getMessage()
	{
		return errorMessage;
	}
}
