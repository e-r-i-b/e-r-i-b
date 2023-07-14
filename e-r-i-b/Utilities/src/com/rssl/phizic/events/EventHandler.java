package com.rssl.phizic.events;

/**
 * @author Krenev
 * @ created 01.06.2009
 * @ $Author$
 * @ $Revision$
 */
public interface EventHandler<E extends Event>
{
	/**
	 * Обработать событие системы
	 * @param event событие
	 * @throws Exception
	 */
	public void process(E event) throws Exception;
}
