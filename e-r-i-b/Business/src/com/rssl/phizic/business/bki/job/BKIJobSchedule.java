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
 * Расписание джоба БКИ на определённую дату ("сегодня")
 */
@SuppressWarnings("PackageVisibleField")
class BKIJobSchedule
{
	/**
	 * Первый рабочий день джоба [в "этом" месяце] (плановое значение)
	 * Равен или меньше "сегодня"
	 * Дата и 00:00:00 (never null)
	 */
	final Calendar startDay;

	/**
	 * Начало рабочего дня ["сегодня"] (плановое значение)
	 * Дата и время (never null)
	 */
	final Calendar startTime;

	/**
	 * Конец рабочего дня ["сегодня"] (плановое значение)
	 * Дата и время (never null)
	 */
	final Calendar endTime;

	/**
	 * ctor
	 * @param config - конфиг БКИ (never null)
	 */
	BKIJobSchedule(CreditBureauConfig config)
	{
		Calendar today = DateHelper.getCurrentDate();

		// noinspection LocalVariableHidesMemberVariable
		Calendar startDay = config.jobStartDay.toInstantDate(today);
		// Пример:
		// Сегодня 24 ноя, а в настройках стоит 28 день месяца
		// Значит, первый рабочий день на сегодня = 28 ноя - 1 месяц = 28 окт
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
