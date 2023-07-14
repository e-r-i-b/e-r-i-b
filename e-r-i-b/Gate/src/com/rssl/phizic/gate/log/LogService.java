package com.rssl.phizic.gate.log;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * @author Dorzhinov
 * @ created 03.08.2011
 * @ $Author$
 * @ $Revision$
 */
public interface LogService extends Service
{
	/**
	 * ����� id ������������ �� ��� ����
	 * @param tb ��
	 * @param osb ���
	 * @param vsp ���
	 * @return id ������������
	 * @throws GateException
	 * @throws GateLogicException
	 */
	Long getDepartmentIdByCode(String tb, String osb, String vsp) throws GateException, GateLogicException;

	/**
	 * ����� �������� ������������ �� ��� ����
	 * @param tb
	 * @param osb
	 * @param vsp
	 * @return �������� ������������
	 * @throws GateException
	 * @throws GateLogicException
	 */
	String getDepartmentNameByCode(String tb, String osb, String vsp) throws GateException, GateLogicException;
}
