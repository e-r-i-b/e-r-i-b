package com.rssl.phizic.context.node;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 19.09.2014
 * @ $Author$
 * @ $Revision$
 *
 * �������� �������� �����
 */

public class NodeContext
{
	private static volatile Calendar kickTime = null;

	/**
	 * ������ ����� ��� ��������� ������ ��������
	 * @param kickTime �����
	 */
	public static void setKickTime(Calendar kickTime)
	{
		NodeContext.kickTime = kickTime;
	}

	/**
	 * @return ����� ��� ��������� ������ ��������
	 */
	public static Calendar getKickTime()
	{
		return kickTime;
	}
}
