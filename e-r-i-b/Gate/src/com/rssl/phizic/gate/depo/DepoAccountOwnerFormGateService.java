package com.rssl.phizic.gate.depo;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * ќбновление анкеты депонента
 * @author lukina
 * @ created 16.10.2010
 * @ $Author$
 * @ $Revision$
 */

public interface DepoAccountOwnerFormGateService extends Service
{
   	/**
	 * ќбновление/добавление анкеты депонента
	 * @param form - анкета депонента
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException
	 */
	void update(DepoAccountOwnerForm form) throws GateException, GateLogicException;

}
