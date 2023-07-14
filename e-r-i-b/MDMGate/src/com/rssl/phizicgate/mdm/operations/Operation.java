package com.rssl.phizicgate.mdm.operations;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * @author akrenev
 * @ created 14.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * Интерфейс операции
 */

public interface Operation<SourceType, ResultType>
{
	/**
	 * инициализировать операцию
	 * @param source данные инициализации
	 */
	public void initialize(SourceType source);

	/**
	 * выполнить операцию
	 * @return результат выполнения операции
	 */
	public ResultType execute() throws GateLogicException, GateException;
}
