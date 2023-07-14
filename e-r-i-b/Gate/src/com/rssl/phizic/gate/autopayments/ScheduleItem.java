package com.rssl.phizic.gate.autopayments;

import java.util.Calendar;
import java.math.BigDecimal;
import java.io.Serializable;

/**
 * @author bogdanov
 * @ created 27.01.2012
 * @ $Author$
 * @ $Revision$
 *
 * ������, ����������� �� �������� �� �����������.
 */

public interface ScheduleItem extends Serializable
{
	/**
	 * @return ������� ������������� �������.
	 */
	String getExternalId();

	/**
	 * @return ����� �������.
	 */
	Long getNumber();

	/**
	 * @return ����.
	 */
	Calendar getDate();

	/**
	 * @return ������.
	 */
	PaymentStatus getStatus();

	/**
	 * @return ������� ������.
	 */
	String getRejectionCause();

	/**
	 * @return �����.
	 */
	BigDecimal getSumm();

	/**
	 * @return ��������.
	 */
	BigDecimal getCommission();

	/**
	 * @return ��� ����������.
	 */
	String getRecipientName();
}
