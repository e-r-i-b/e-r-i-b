package com.rssl.phizic.gate.schemes;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.List;

/**
 * @author akrenev
 * @ created 03.10.13
 * @ $Author$
 * @ $Revision$
 *
 * ������ ������ �� ������� ���� �����������
 */

public interface AccessSchemeService extends Service
{
	/**
	 * @param id �������������
	 * @return ������ ���� ����
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public AccessScheme getById(Long id) throws GateException, GateLogicException;

	/**
	 * @param categories ���������
	 * @return ������ ���� ����
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public List<AccessScheme> getList(String[] categories) throws GateException, GateLogicException;

	/**
	 * ��������� �����
	 * @param schema ����������� �����
	 * @return ����������� �����
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public AccessScheme save(AccessScheme schema) throws GateException, GateLogicException;

	/**
	 * ������� �����
	 * @param schema �����
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public void delete(AccessScheme schema) throws GateException, GateLogicException;
}
