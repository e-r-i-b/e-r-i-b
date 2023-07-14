package com.rssl.phizic.gate.clients.stoplist;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.cache.proxy.Cachable;
import com.rssl.phizic.gate.cache.proxy.composers.CheckStopListCacheKeyComposer;
import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.Calendar;

/**
 * @author Roshka
 * @ created 13.02.2007
 * @ $Author$
 * @ $Revision$
 */

public interface StopListService extends Service
{
	/**
	 * �������� �� ���� �����
	 * @param INN ���
	 * @param firstName ��� �� �������
	 * @param middleName �������� �� �������
	 * @param lastName ������� �� �������
	 * @param documentSeries ����� ���������
	 * @param documentNumber ����� ���������
	 * @param dateOfBirth ���� ��������
	 * @return ������ ������� � ���� �����.
	 * @throws GateLogicException
	 * @throws GateException
	 */
	@Cachable(keyResolver = CheckStopListCacheKeyComposer.class, name = "StopList.checkNameAndDoc", linkable = false)
	ClientStopListState check(String INN, String firstName, String middleName, String lastName, String documentSeries, String documentNumber,
				Calendar dateOfBirth)
			throws GateLogicException, GateException;
}
