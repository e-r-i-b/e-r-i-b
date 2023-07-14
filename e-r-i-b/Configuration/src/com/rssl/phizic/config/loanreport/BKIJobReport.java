package com.rssl.phizic.config.loanreport;

import com.rssl.phizic.common.types.annotation.PlainOldJavaObject;

import java.util.Calendar;

/**
 * @author Erkin
 * @ created 18.11.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * Отчёт по работе джоба БКИ
 */
@PlainOldJavaObject
@SuppressWarnings("PublicField")
public final class BKIJobReport
{
	/**
	 * Дата последнего запуска джоба
	 * Дата и время
	 * Может отсутствовать
	 */
	public Calendar lastStartTime;

	/**
	 * Начало последнего рабочего периода джоба
	 * Дата и время
	 * Может отсутствовать
	 */
	public Calendar lastPeriodBegin;

	/**
	 * Конец последнего рабочего периода джоба
	 * Дата и время
	 * Может отсутствовать
	 */
	public Calendar lastPeriodEnd;
}
