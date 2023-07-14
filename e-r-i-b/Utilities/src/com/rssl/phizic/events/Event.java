package com.rssl.phizic.events;

import java.io.Serializable;

/**
 * @author Krenev
 * @ created 01.06.2009
 * @ $Author$
 * @ $Revision$
 * Событие в системе. атрибутный состав события - любой
 */
public interface Event extends Serializable
{
	/**
	 * @return строка с атрибутами события для записи в лог
	 */
	public String getStringForLog();
}
