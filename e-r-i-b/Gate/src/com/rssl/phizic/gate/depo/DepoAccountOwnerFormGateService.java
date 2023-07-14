package com.rssl.phizic.gate.depo;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * ���������� ������ ���������
 * @author lukina
 * @ created 16.10.2010
 * @ $Author$
 * @ $Revision$
 */

public interface DepoAccountOwnerFormGateService extends Service
{
   	/**
	 * ����������/���������� ������ ���������
	 * @param form - ������ ���������
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException
	 */
	void update(DepoAccountOwnerForm form) throws GateException, GateLogicException;

}
