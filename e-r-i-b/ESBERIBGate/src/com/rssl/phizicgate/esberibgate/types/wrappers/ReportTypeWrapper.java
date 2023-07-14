package com.rssl.phizicgate.esberibgate.types.wrappers;

import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.ReportTypeType;

/**
 * @author akrenev
 * @ created 21.05.2014
 * @ $Author$
 * @ $Revision$
 *
 * ������� �������� �������� �� ������ �� �����
 */

public class ReportTypeWrapper
{
	/**
	 * �������� ������������ ������� ������� �� ������ �������������
	 * @param use ���� �������������
	 * @return ������������ ������� �������
	 */
	public static ReportTypeType toGate(boolean use)
	{
		return use? ReportTypeType.E: ReportTypeType.N;
	}
}
