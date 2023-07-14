package com.rssl.phizic.gate.operations;

/**
 * Интерфейс операции требующей фозврат сущности в generated виде
 *
 * @author khudyakov
 * @ created 20.05.14
 * @ $Author$
 * @ $Revision$
 */
public interface CorrelationOperation<G> extends Operation
{
	/**
	 * @return сущность
	 */
	G toGeneratedEntity() throws Exception;
}
