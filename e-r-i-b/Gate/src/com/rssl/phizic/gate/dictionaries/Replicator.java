package com.rssl.phizic.gate.dictionaries;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * @author hudyakov
 * @ created 28.12.2009
 * @ $Author$
 * @ $Revision$
 */

public interface Replicator
{
	/**
	 * Выполнить репликацию
	 * @throws GateException
	 * @throws GateLogicException
	 */
	void replicate() throws GateException, GateLogicException;
}
