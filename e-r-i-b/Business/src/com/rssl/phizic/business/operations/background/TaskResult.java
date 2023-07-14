package com.rssl.phizic.business.operations.background;

import java.util.Calendar;

/**
 * @author krenev
 * @ created 21.07.2011
 * @ $Author$
 * @ $Revision$
 * ��������� ���������� ������
 */
public interface TaskResult
{
	/**
	 * @return ���� ������ ��������� ������
	 */
	public Calendar getStartDate();

	/**
	 * @return ���� ��������� ��������� ������
	 */
	public Calendar getEndDate();
}
