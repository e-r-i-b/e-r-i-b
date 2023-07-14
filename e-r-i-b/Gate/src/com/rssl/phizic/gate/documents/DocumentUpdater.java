package com.rssl.phizic.gate.documents;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.Map;

/**
 * @author Maleyev
 * @ created 07.04.2008
 * @ $Author$
 * @ $Revision$
 */
public interface DocumentUpdater
{
	/**
	 * обновить документ
	 * @param document документ
	 * @return StateUpdateInfo Состояние
	 */
	StateUpdateInfo execute(GateDocument document) throws GateException, GateLogicException;

	/**
	 * Установить параметры
	 * @param params параметры
	 */
	void setParameters(Map<String,?> params);
}
