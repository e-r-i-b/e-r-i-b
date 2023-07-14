package com.rssl.phizic.auth;

import java.util.Calendar;

/**
 * @author Roshka
 * @ created 27.12.2005
 * @ $Author$
 * @ $Revision$
 */
public interface BankLogin extends CommonLogin
{
	/**
	 * задать дату последней синхронизации данных
	 * @param lastSynchronizationDate дата последней синхронизации данных
	 */
	public void setLastSynchronizationDate(Calendar lastSynchronizationDate);

	/**
	 * @return дата последней синхронизации данных
	 */
	public Calendar getLastSynchronizationDate();

}
