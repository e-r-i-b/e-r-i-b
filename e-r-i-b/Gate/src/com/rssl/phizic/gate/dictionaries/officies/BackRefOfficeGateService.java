package com.rssl.phizic.gate.dictionaries.officies;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * Обратный сервис получения офисов
 * @author niculichev
 * @ created 24.09.13
 * @ $Author$
 * @ $Revision$
 */
public interface BackRefOfficeGateService extends Service
{
	/**
	 * Поиск офиса по внешнему идентификатору
	 * @param id внешний идентификатор
	 * @return офис
	 * @throws GateException
	 */
	Office getOfficeById(String id) throws GateException, GateLogicException;

	/**
	 * Поиск офиса по его коду
	 * @param code код офиса
	 * @return офис
	 * @throws GateException
	 */
	Office getOfficeByCode(Code code) throws GateException, GateLogicException;
}
