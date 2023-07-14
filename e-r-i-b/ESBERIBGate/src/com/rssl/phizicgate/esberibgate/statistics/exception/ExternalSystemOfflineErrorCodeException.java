package com.rssl.phizicgate.esberibgate.statistics.exception;

import com.rssl.phizic.gate.utils.OfflineExternalSystemException;

/**
 * @author akrenev
 * @ created 12.03.2014
 * @ $Author$
 * @ $Revision$
 *
 * ���������� ������ ��������� ������� �� ������� ������� (�������)
 */

public class ExternalSystemOfflineErrorCodeException extends OfflineExternalSystemException
{
	/**
	 * �����������
	 * @param message ���������
	 */
	public ExternalSystemOfflineErrorCodeException(String message)
	{
		super(message);
	}
}
