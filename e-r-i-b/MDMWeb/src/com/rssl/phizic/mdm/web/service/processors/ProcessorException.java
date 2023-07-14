package com.rssl.phizic.mdm.web.service.processors;

import com.rssl.phizic.gate.exceptions.GateException;

/**
 * @author akrenev
 * @ created 13.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * ������ ��������� �������
 */

public class ProcessorException extends GateException
{
	/**
	 * @param message ��������� ������
	 * @param cause �������� ������
	 */
	public ProcessorException(String message, Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * @return ��� ������
	 */
	public long getErrorCode()
	{
		return -1;
	}

	/**
	 * @return �������� ������
	 */
	public String getErrorDescription()
	{
		return getMessage();
	}
}
