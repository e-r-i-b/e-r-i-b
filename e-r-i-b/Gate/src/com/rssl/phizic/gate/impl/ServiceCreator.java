package com.rssl.phizic.gate.impl;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * @author Omeliyanchuk
 * @ created 11.05.2010
 * @ $Author$
 * @ $Revision$
 */

public interface ServiceCreator
{
	/**
	 * ������� ������
	 * @param serviceClassName ��� ������ ������� �����
	 * @param factory ������� ��� ������������� �������
	 * @return ������.
	 */
	Service createService(String serviceClassName, GateFactory factory)  throws GateException;
}
