package com.rssl.phizic.gate.documents;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.Map;

/**
 * @author Krenev
 * @ created 15.06.2007
 * @ $Author$
 * @ $Revision$
 */
public interface CommissionCalculator
{

	/**
	 * ���������� ���������
	 * @param params ���������
	 */
	void setParameters(Map<String, ?> params);

	/**
	 * �������� �������� �� �������
	 * @param transfer
	 */
	void calcCommission(GateDocument transfer) throws GateException, GateLogicException;
}
