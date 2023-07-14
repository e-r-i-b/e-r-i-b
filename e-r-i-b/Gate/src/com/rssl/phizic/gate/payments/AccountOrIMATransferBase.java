package com.rssl.phizic.gate.payments;

import java.util.Calendar;

/**
 * @ author: Gololobov
 * @ created: 13.12.2012
 * @ $Author$
 * @ $Revision$
 */
public interface AccountOrIMATransferBase
{
	/**
	 * @return идентификатор операции
	 */
	public String getOperUId();

	/**
	 * Утановка идентификатора операции
	 */
	public void setOperUId(String operUid);

	/**
	 * @return дата передачи сообщения
	 */
	public Calendar getOperTime();

	/**
	 * Установка даты передачи сообщения
	 */
	public void setOperTime(Calendar operTime);
}
