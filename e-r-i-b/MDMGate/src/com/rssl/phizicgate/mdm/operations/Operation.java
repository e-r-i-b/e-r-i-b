package com.rssl.phizicgate.mdm.operations;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * @author akrenev
 * @ created 14.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * ��������� ��������
 */

public interface Operation<SourceType, ResultType>
{
	/**
	 * ���������������� ��������
	 * @param source ������ �������������
	 */
	public void initialize(SourceType source);

	/**
	 * ��������� ��������
	 * @return ��������� ���������� ��������
	 */
	public ResultType execute() throws GateLogicException, GateException;
}
