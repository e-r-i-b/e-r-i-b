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
	 * ���������� ������� �������
	 * @param event �������
	 * @throws Exception
	 */
	public void process(E event) throws Exception;
}
