package com.rssl.phizic.web.common;

import com.rssl.phizic.logging.messaging.System;


/**
 * ������ ��� ����������� ��������� ��
 * @author Pankin
 * @ created 13.07.2012
 * @ $Author$
 * @ $Revision$
 */

public class LogATMJSPFilter extends LogMobileApiFilter
{
	protected System getSystem()
	{
		return System.atm;
	}
}
