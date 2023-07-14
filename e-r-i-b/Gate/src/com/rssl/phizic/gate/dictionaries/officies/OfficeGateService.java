package com.rssl.phizic.gate.dictionaries.officies;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.dictionaries.ReplicationService;

import java.util.List;

/**
 * @author Omeliyanchuk
 * @ created 04.04.2008
 * @ $Author$
 * @ $Revision$
 */

public interface OfficeGateService extends ReplicationService<Office>
{
	/**
	 * Поиск офиса по внешнему идентификатору
	 * @param id внешний идентификатор
	 * @return офис
	 * @throws GateException
	 */
	Office getOfficeById(String id) throws GateException, GateLogicException;
}
