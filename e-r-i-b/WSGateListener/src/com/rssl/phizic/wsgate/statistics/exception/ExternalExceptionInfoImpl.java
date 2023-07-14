package com.rssl.phizic.wsgate.statistics.exception;

import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.gate.statistics.exception.ExternalExceptionInfo;
import com.rssl.phizic.logging.messaging.System;

/**
 * @author akrenev
 * @ created 01.11.2014
 * @ $Author$
 * @ $Revision$
 */

public class ExternalExceptionInfoImpl extends ExternalExceptionInfo
{
	/**
	 * ������ ����������
	 * @param application ����������
	 */
	public void setApplication(String application)
	{
		super.setApplication(Application.valueOf(application));
	}

	/**
	 * ������ ����
	 * @param gate ����
	 */
	public void setGate(String gate)
	{
		super.setGate(System.valueOf(gate));
	}
}
