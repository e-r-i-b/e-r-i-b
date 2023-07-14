package com.rssl.phizic.gate.longoffer;

import com.rssl.phizic.common.types.Money;

import java.util.Calendar;
import java.io.Serializable;

/**
 * @author krenev
 * @ created 20.08.2010
 * @ $Author$
 * @ $Revision$
 * ������ ������� ��������� ����������� ���������
 */
public interface ScheduleItem extends Serializable
{
	/**
	 * @return ���� ���������
	 */
	public Calendar getDate();

	/**
	 * @return ����� �������
	 */
	public Money getAmount();

	/**
	 * @return ������ �������
	 */
	public SheduleItemState getState();
}
