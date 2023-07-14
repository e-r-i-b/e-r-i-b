package com.rssl.phizic.notifications;

import java.util.Calendar;

/**
 * @author Omeliyanchuk
 * @ created 24.01.2007
 * @ $Author$
 * @ $Revision$
 */

public interface Notification
{
	/**
	 * @return идентификатор оповещени€
	 */
	public Long getId();
	/**
	 * @return ƒата и врем€ создани€ оповещени€
	 */
	Calendar getDateCreated();

	/**
	 * @return фактический тип оповещени€
	 */
	Class<? extends Notification> getType();
}