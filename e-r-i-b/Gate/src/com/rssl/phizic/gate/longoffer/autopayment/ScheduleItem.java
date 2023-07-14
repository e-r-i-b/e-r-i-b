package com.rssl.phizic.gate.longoffer.autopayment;

import com.rssl.phizic.common.types.Money;

import java.util.Calendar;
import java.io.Serializable;

/**
 * @author osminin
 * @ created 31.01.2011
 * @ $Author$
 * @ $Revision$
 * ������ ������� ���������� �����������
 */
public interface ScheduleItem extends Serializable
{
	/**
	 * @return ���� ����������
	 */
	public Calendar getDate();

	/**
	 * @return ����� �������
	 */
	public Money getAmount();

	/**
	 * @return ������ �������
	 */
	public ScheduleItemState getState();
}
