package com.rssl.phizic.gate.reminder;

import java.util.Calendar;

/**
 * @author osminin
 * @ created 26.10.14
 * @ $Author$
 * @ $Revision$
 *
 * ��������� �����������
 */
public interface ReminderState
{
	/**
	 * @return ���� ������ �����������
	 */
	Calendar getProcessDate();

	/**
	 * @return ����, �� ������� ���� �������� �����������
	 */
	Calendar getDelayedDate();

	/**
	 * @return ���� ����������� ������������� �����
	 */
	Calendar getResidualDate();
}
