package com.rssl.phizic.gate.operations;

/**
 * Интерфейс операция поиска
 *
 * @author khudyakov
 * @ created 20.05.14
 * @ $Author$
 * @ $Revision$
 */
public interface FindEntityOperation<O> extends Operation
{
	/**
	 * @return Искомая сущность
	 * @throws Exception
	 */
	O getEntity() throws Exception;
}
