package com.rssl.phizic.gate.login;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 09.10.13
 * @ $Author$
 * @ $Revision$
 *
 * гейтовый интерфейс блокировки логина
 */

public interface LoginBlock
{
	/**
	 * задать тип блокировки
	 * @param reasonType тип блокировки
	 */
	public void setReasonType(String reasonType);

	/**
	 * @return тип блокировки
	 */
	public String getReasonType();

	/**
	 * задать причину блокировки
	 * @param reasonDescription причина
	 */
	public void setReasonDescription(String reasonDescription);

	/**
	 * @return причина блокировки
	 */
	public String getReasonDescription();

	/**
	 * задать начало блокировки
	 * @param blockedFrom начало блокировки
	 */
	public void setBlockedFrom(Calendar blockedFrom);

	/**
	 * @return начало блокировки
	 */
	public Calendar getBlockedFrom();

	/**
	 * задать окончание блокировки
	 * @param blockedUntil окончание блокировки
	 */
	public void setBlockedUntil(Calendar blockedUntil);

	/**
	 * @return окончание блокировки
	 */
	public Calendar getBlockedUntil();
}
