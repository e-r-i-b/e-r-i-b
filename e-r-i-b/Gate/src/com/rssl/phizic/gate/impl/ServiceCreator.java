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
	 * Создать сервис
	 * @param serviceClassName имя класса сервиса гейта
	 * @param factory фабрика для инициализации сервиса
	 * @return сервис.
	 */
	Service createService(String serviceClassName, GateFactory factory)  throws GateException;
}
