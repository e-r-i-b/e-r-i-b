package com.rssl.phizic.gate.dictionaries;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.List;

/**
 * @author: Pakhomova
 * @created: 07.05.2009
 * @ $Author$
 * @ $Revision$
 * TODO �������
 */
public interface CurrenciesGateService extends ReplicationService
{
	/**
	 * @param firstResult ����� ������ ������� �����������
	 * @param maxResults ������������ ���������� �����������
	 * @return ������ ������ �����
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException
	 */
	List<Currency> getAll(int firstResult, int maxResults) throws GateException, GateLogicException;
}

