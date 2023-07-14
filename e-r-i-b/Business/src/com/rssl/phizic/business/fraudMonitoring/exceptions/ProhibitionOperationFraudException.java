package com.rssl.phizic.business.fraudMonitoring.exceptions;

import com.rssl.phizic.business.BusinessLogicException;

/**
 * ����������, ����������� ���������� ��������
 *
 * @author khudyakov
 * @ created 17.06.15
 * @ $Author$
 * @ $Revision$
 */
public class ProhibitionOperationFraudException extends BusinessLogicException
{
	public ProhibitionOperationFraudException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
