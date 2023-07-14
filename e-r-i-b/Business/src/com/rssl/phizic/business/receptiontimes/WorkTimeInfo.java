package com.rssl.phizic.business.receptiontimes;

/**
 * @author Erkin
 * @ created 21.04.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * Информация о рабочем времени
 */
public class WorkTimeInfo
{
	private final ReceptionTime receptionTime;

	private final TimeMatching workTimeNow;

	///////////////////////////////////////////////////////////////////////////

	WorkTimeInfo(ReceptionTime receptionTime, TimeMatching workTimeNow)
	{
		this.receptionTime = receptionTime;
		this.workTimeNow = workTimeNow;
	}

	/**
	 * @return время приёма (документов)
	 */
	public ReceptionTime getReceptionTime()
	{
		return receptionTime;
	}

	/**
	 * Отвечает на вопрос: наступило рабочее время или нет
	 * Праздничные дни учитываются как "слишком поздно"
	 * @return
	 */
	public TimeMatching isWorkTimeNow()
	{
		return workTimeNow;
	}
}
