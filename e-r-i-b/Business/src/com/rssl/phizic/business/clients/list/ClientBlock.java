package com.rssl.phizic.business.clients.list;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 07.11.13
 * @ $Author$
 * @ $Revision$
 *
 * Блокировка клиента
 */

public class ClientBlock
{
	private Calendar from;
	private Calendar to;
	private String reason;
	private String blockerFIO;

	/**
	 * @return начало блокировки
	 */
	public Calendar getFrom()
	{
		return from;
	}

	/**
	 * задать начало блокировки
	 * @param from начало блокировки
	 */
	public void setFrom(Calendar from)
	{
		this.from = from;
	}

	/**
	 * @return окончание блокировки
	 */
	public Calendar getTo()
	{
		return to;
	}

	/**
	 * задать окончание блокировки
	 * @param to окончание блокировки
	 */
	public void setTo(Calendar to)
	{
		this.to = to;
	}

	/**
	 * @return причина блокировки
	 */
	public String getReason()
	{
		return reason;
	}

	/**
	 * задать причину блокировки
	 * @param reason причина блокировки
	 */
	public void setReason(String reason)
	{
		this.reason = reason;
	}

	/**
	 * @return ФИО заблокировавшего
	 */
	public String getBlockerFIO()
	{
		return blockerFIO;
	}

	/**
	 * задать ФИО заблокировавшего
	 * @param blockerFIO ФИО заблокировавшего
	 */
	public void setBlockerFIO(String blockerFIO)
	{
		this.blockerFIO = blockerFIO;
	}
}
