package com.rssl.phizic.task;

import com.rssl.phizic.common.types.annotation.MandatoryParameter;
import com.rssl.phizic.common.types.annotation.Statefull;
import com.rssl.phizic.module.Module;

/**
 * @author Erkin
 * @ created 03.04.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * «адача.
 * —одержит: 
 * - параметры задачи,
 * - переменные задачи,
 * - алгоритмы, используемые в задаче.
 * ќграничени€:
 * - реализации должны предоставл€ть конструктор без параметров
 */
@Statefull
public interface Task
{
	/**
	 * ”становить модуль, в котором выполн€етс€ задача
	 * @param module - модуль, в котором выполн€етс€ задача (never null)
	 */
	@MandatoryParameter
	void setModule(Module module);
}
