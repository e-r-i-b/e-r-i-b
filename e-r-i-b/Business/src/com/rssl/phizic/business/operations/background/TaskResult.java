package com.rssl.phizic.business.operations.background;

import java.util.Calendar;

/**
 * @author krenev
 * @ created 21.07.2011
 * @ $Author$
 * @ $Revision$
 * –езультат исполнени€ задачи
 */
public interface TaskResult
{
	/**
	 * @return дата начала выполени€ задачи
	 */
	public Calendar getStartDate();

	/**
	 * @return дата окончани€ выполени€ задачи
	 */
	public Calendar getEndDate();
}
