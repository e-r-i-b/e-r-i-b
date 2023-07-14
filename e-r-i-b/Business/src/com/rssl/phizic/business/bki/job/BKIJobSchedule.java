package com.rssl.phizic.business.bki.job;

import com.rssl.phizic.config.loanreport.CreditBureauConfig;
import com.rssl.phizic.utils.DateHelper;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author Erkin
 * @ created 21.11.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * ���������� ����� ��� �� ����������� ���� ("�������")
 */
@SuppressWarnings("PackageVisibleField")
class BKIJobSchedule
{
	/**
	 * ������ ������� ���� ����� [� "����" ������] (�������� ��������)
	 * ����� ��� ������ "�������"
	 * ���� � 00:00:00 (never null)
	 */
	final Calendar startDay;

	/**
	 * ������ �������� ��� ["�������"] (�������� ��������)
	 * ���� � ����� (never null)
	 */
	final Calendar startTime;

	/**
	 * ����� �������� ��� ["�������"] (�������� ��������)
	 * ���� � ����� (never null)
	 */
	final Calendar endTime;

	/**
	 * ctor
	 * @param config - ������ ��� (never null)
	 */
	BKIJobSchedule(CreditBureauConfig config)
	{
		Calendar today = DateHelper.getCurrentDate();

		// noinspection LocalVariableHidesMemberVariable
		Calendar startDay = config.jobStartDay.toInstantDate(today);
		// ������:
		// ������� 24 ���, � � ���������� ����� 28 ���� ������
		// ������, ������ ������� ���� �� ������� = 28 ��� - 1 ����� = 28 ���
		if (startDay.after(today))
			startDay = DateHelper.addMonths(startDay, -1);

		this.startDay = startDay;
		this.startTime = config.jobStartTime.toInstantDateTime(today);
		this.endTime = config.jobEndTime.toInstantDateTime(today);
	}

	@Override
	public String toString()
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
				.append("startDay", dateFormat.format(startDay.getTime()))
				.append("startTime", dateTimeFormat.format(startTime.getTime()))
				.append("endTime", dateTimeFormat.format(endTime.getTime()))
				.toString();
	}
}
