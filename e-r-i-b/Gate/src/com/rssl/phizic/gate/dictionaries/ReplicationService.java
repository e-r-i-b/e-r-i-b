package com.rssl.phizic.gate.dictionaries;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.List;

/**
 * @author Krenev
 * @ created 27.05.2009
 * @ $Author$
 * @ $Revision$
 * ������ ������������ ��� ����������
 */
public interface ReplicationService<E extends DictionaryRecord> extends Service
{
	/**
	 * �������� ������ ��������� � ��������� ���������
	 * @param firstResult ������ ������� ��������
	 * @param maxResults ���������� ���������
	 * @return ������ ���������
	 * @throws GateException
	 * @throws GateLogicException
	 */
	List<E> getAll(int firstResult, int maxResults) throws GateException, GateLogicException;

	/**
	 * �������� ������ ��������� � ����������� � ������ ��������
	 * @param template ������ ��� ������
	 * @param listLimit ���������� ���������
	 * @return ������ ���������
	 * @throws GateException
	 */
	List<E> getAll(E template, int firstResult, int listLimit) throws GateException, GateLogicException;
}
